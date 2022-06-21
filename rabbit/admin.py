from threading import Thread
import pika

EXCHANGE_NAME = 'main_exchange'


class ReadMessage(Thread):

    def __init__(self) -> None:
        super().__init__(daemon=True)
        self.connections = []
        self.channel = self.make_connection()
        result = self.channel.queue_declare('', exclusive=True)
        self.queue_name = result.method.queue
        key = '*.*.#'
        self.channel.queue_bind(exchange=EXCHANGE_NAME, queue=self.queue_name, routing_key=key)

    def make_connection(self) -> pika.adapters.blocking_connection.BlockingChannel:
        connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
        channel = connection.channel()
        channel.exchange_declare(exchange=EXCHANGE_NAME, exchange_type='topic')
        self.connections.append(connection)
        return channel

    def response(self, ch: pika.adapters.blocking_connection.BlockingChannel, method: pika.spec.Basic.Deliver,
                 properties: pika.spec.BasicProperties, body: bytes) -> None:
        print("Message addressed {}: {}".format(method.routing_key, str(body, 'UTF-8')))
        ch.basic_ack(delivery_tag=method.delivery_tag)

    def run(self) -> None:
        self.channel.basic_consume(queue=self.queue_name, on_message_callback=self.response, auto_ack=False)
        self.channel.start_consuming()

    def __del__(self) -> None:
        for connection in self.connections:
            connection.close()


class Admin:

    def __init__(self) -> None:
        self.connections = []
        self.sender = self.make_connection()

    def make_connection(self) -> pika.adapters.blocking_connection.BlockingChannel:
        connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
        channel = connection.channel()
        channel.exchange_declare(exchange=EXCHANGE_NAME, exchange_type='topic')
        self.connections.append(connection)
        return channel

    def end_connection(self) -> None:
        for connection in self.connections:
            connection.close()

    def run(self) -> None:
        reader = ReadMessage()
        reader.start()
        while True:
            key = input("Enter address (all/suppliers/teams); if you enter 'end' the program will end: \n")
            if key == 'end':
                break
            if key not in ['all', 'suppliers', 'teams']:
                print("Invalid routing key!")
                continue
            message = input("Enter message:\n")
            self.sender.basic_publish(exchange=EXCHANGE_NAME, routing_key=key, body=message)
        self.end_connection()


if __name__ == '__main__':
    admin = Admin()
    admin.run()
