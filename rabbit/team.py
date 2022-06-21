from threading import Thread
import pika

EXCHANGE_NAME = 'main_exchange'


class ReadMessage(Thread):

    def __init__(self, name: str) -> None:
        super().__init__(daemon=True)
        self.connections = []
        self.channel = self.make_connection()
        result = self.channel.queue_declare('', exclusive=True)
        self.queue_name = result.method.queue
        self.bind_queue('*.{}.*'.format(name))
        self.bind_queue('all')
        self.bind_queue('teams')

    def make_connection(self) -> pika.adapters.blocking_connection.BlockingChannel:
        connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
        channel = connection.channel()
        channel.exchange_declare(exchange=EXCHANGE_NAME, exchange_type='topic')
        self.connections.append(connection)
        return channel

    def bind_queue(self, key: str) -> None:
        self.channel.queue_bind(exchange=EXCHANGE_NAME, queue=self.queue_name, routing_key=key)

    def response(self, ch: pika.adapters.blocking_connection.BlockingChannel, method: pika.spec.Basic.Deliver,
                 properties: pika.spec.BasicProperties, body: bytes) -> None:
        routing_key_from = method.routing_key
        if '.' in routing_key_from:
            print("Order confirmation: {}".format(str(body, 'UTF-8')))
        else:
            print("Message from administrator: {}".format(str(body, 'UTF-8')))
        ch.basic_ack(delivery_tag=method.delivery_tag)

    def run(self) -> None:
        self.channel.basic_consume(queue=self.queue_name, on_message_callback=self.response, auto_ack=False)
        self.channel.start_consuming()

    def __del__(self) -> None:
        for connection in self.connections:
            connection.close()


class Team:

    def __init__(self) -> None:
        self.connections = []
        self.name = self.get_name()
        self.channel = self.make_connection()
        self.routing_key_generator = lambda product: "{}.{}".format(self.name, product)
        self.reader = ReadMessage(self.name)

    def make_connection(self) -> pika.adapters.blocking_connection.BlockingChannel:
        connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
        channel = connection.channel()
        channel.exchange_declare(exchange=EXCHANGE_NAME, exchange_type='topic')
        self.connections.append(connection)
        return channel

    def make_order(self, product: str) -> None:
        routing_key = self.routing_key_generator(product)
        message = "We need {}".format(product)
        self.channel.basic_publish(exchange=EXCHANGE_NAME, routing_key=routing_key, body=message)

    def end_connection(self) -> None:
        for connection in self.connections:
            connection.close()

    def get_name(self) -> str:
        print("You should click enter after every information.")
        name = input("Enter your name: ")
        print("You enter name: {}".format(name))
        return name

    def run(self) -> None:
        self.reader.start()
        print("Enter products what you need, at the end write text 'end'")
        while True:
            product = input("Enter product name: \n")
            if product == "end":
                break
            print("You enter product: {}".format(product))
            self.make_order(product)
        self.end_connection()


if __name__ == '__main__':
    team = Team()
    team.run()
