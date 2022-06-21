from threading import Thread
from typing import Tuple, List, Callable
import pika
from random import randint
from time import sleep

EXCHANGE_NAME = 'main_exchange'


class ReadMessage(Thread):

    def __init__(self) -> None:
        super().__init__(daemon=True)
        self.connections = []
        self.channel = self.make_connection()
        result = self.channel.queue_declare('', exclusive=True)
        self.queue_name = result.method.queue
        self.bind_queue('all')
        self.bind_queue('suppliers')

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
        print("Message from administrator: {}".format(str(body, 'UTF-8')))
        ch.basic_ack(delivery_tag=method.delivery_tag)

    def run(self) -> None:
        self.channel.basic_consume(queue=self.queue_name, on_message_callback=self.response, auto_ack=False)
        self.channel.start_consuming()

    def __del__(self) -> None:
        for connection in self.connections:
            connection.close()


class ProcessOrder(Thread):

    def __init__(self, product: str, get_order_id: Callable[[], str],
                 sender: pika.adapters.blocking_connection.BlockingChannel) -> None:
        super().__init__(daemon=True)
        self.connections = []
        self.queue_name = product
        self.routing_key = '*.{}'.format(product)
        self.channel = self.make_connection()
        self.get_order_id = get_order_id
        self.sender = sender

    def make_connection(self) -> pika.adapters.blocking_connection.BlockingChannel:
        connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
        channel = connection.channel()
        channel.exchange_declare(exchange=EXCHANGE_NAME, exchange_type='topic')
        channel.queue_declare(self.queue_name, exclusive=False)
        channel.queue_bind(exchange=EXCHANGE_NAME, queue=self.queue_name, routing_key=self.routing_key)
        self.connections.append(connection)
        return channel

    def response(self, ch: pika.adapters.blocking_connection.BlockingChannel, method: pika.spec.Basic.Deliver,
                 properties: pika.spec.BasicProperties, body: bytes) -> None:
        routing_key_from = method.routing_key.split('.')
        order_id = self.get_order_id()
        routing_key = '{}.{}.{}'.format(order_id, routing_key_from[0], routing_key_from[1])
        print("Process {} for {}".format(routing_key_from[1], routing_key_from[0]))
        message = "{} <-- {}".format(routing_key_from[1], order_id)
        # sleep_time = randint(3, 8)
        # sleep(sleep_time)
        self.sender.basic_publish(exchange=EXCHANGE_NAME, routing_key=routing_key, body=message)
        print("End processing")
        ch.basic_ack(delivery_tag=method.delivery_tag)

    def run(self) -> None:
        self.channel.basic_qos(prefetch_count=1)
        self.channel.basic_consume(queue=self.queue_name, on_message_callback=self.response, auto_ack=False)
        self.channel.start_consuming()

    def __del__(self) -> None:
        for connection in self.connections:
            connection.close()


class Supplier:

    def __init__(self) -> None:
        self.connections = []
        self.name, self.products = self.get_information()
        self.threads = {}
        self.order_number = 0
        self.reader = ReadMessage()
        self.sender = self.make_connection()

    def make_connection(self) -> pika.adapters.blocking_connection.BlockingChannel:
        connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
        channel = connection.channel()
        channel.exchange_declare(exchange=EXCHANGE_NAME, exchange_type='topic')
        self.connections.append(connection)
        return channel

    def get_order_id(self) -> str:
        self.order_number += 1
        return '{}_{}'.format(self.name, str(self.order_number).zfill(5))

    def start_threads(self) -> None:
        for product in self.products:
            self.threads[product] = ProcessOrder(product, self.get_order_id, self.sender)
        for thread in self.threads.values():
            thread.start()

    def get_information(self) -> Tuple[str, List[str]]:
        print("You should click enter after every information.")
        name = input("Enter your name: ")
        print("Enter your products, at the end write text 'end'")
        products = []
        while True:
            product = input("Enter product name: ")
            if product == "end":
                break
            print("You enter product: {}".format(product))
            products.append(product)
        return name, products

    def run(self) -> None:
        self.reader.start()
        self.start_threads()
        while True:
            command = input("Enter 'end' to end program\n")
            if command == 'end':
                break
        self.end_connection()

    def end_connection(self) -> None:
        for connection in self.connections:
            connection.close()


if __name__ == '__main__':
    supplier = Supplier()
    supplier.run()
