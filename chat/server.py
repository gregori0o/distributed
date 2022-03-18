import socket
from threading import Thread, Lock
from data import *
import os


class ServerThread(Thread):
    def __init__(self, conn_socket, address, server):
        super().__init__()
        self.conn_socket = conn_socket
        self.address = address
        self.server = server

    def run(self) -> None:
        print("Nawiązano połączenie z: ", str(self.address))
        while True:
            buff = self.conn_socket.recv(4096)
            if len(buff) == 0:
                break
            msg = parse(str(buff, "UTF-8"))
            if msg[0] == "close":
                self.send(messages["close"])
                break
            if msg[0] == "nick":
                self.server.set_username(self.address, msg[1])
                continue
            self.server.send_message(msg[1], self.address)
        self.server.remove_thread(self.address)
        self.conn_socket.close()
        print("Zakończono połączenie z: ", str(self.address))

    def send(self, msg):
        self.conn_socket.send(bytes(msg, "UTF-8"))


class Server(Thread):
    def __init__(self, server_port):
        super().__init__()

        self.server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.server_socket.bind(("", server_port))
        self.server_socket.listen()

        self.server_udp = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        self.server_udp.bind(("", server_port))

        self.lock = Lock()
        self.threads = []
        self.flag = True
        self.usernames = {}
        ip4_address = os.popen('hostname -I').read()

        print("SERWER START!\n" + str(self.server_socket.getsockname()) + "\nIPv4: ", str(ip4_address))

    def set_username(self, address, nick):
        print("Użytkownik", nick, "posiada adres:", address)
        self.usernames[address] = nick

    def recv_udp(self):
        while self.flag:
            buff, address = self.server_udp.recvfrom(4096)
            msg = parse(str(buff, "UTF-8"))
            if msg[0] == 'pic':
                username = self.usernames.get(address, str(address))
                self.send_message(username + "\n" + msg[1], address, True)
            elif msg[0] == 'close':
                break
            else:
                print(msg[1])

    def accept_tcp(self):
        while self.flag:
            conn, address = self.server_socket.accept()
            self.lock.acquire()
            self.threads.append(ServerThread(conn, address, self))
            self.threads[-1].start()
            self.lock.release()

    def run(self) -> None:
        udp = Thread(target=self.recv_udp, daemon=True)
        tcp = Thread(target=self.accept_tcp, daemon=True)
        udp.start()
        tcp.start()
        while True:
            buff = input("Napisz 'close' aby zamknąć serwer.\n")
            if buff == "close":
                print("Trwa zamykanie serweru...")
                break
        self.flag = False
        for client in self.threads.copy():
            client.send(messages["close"])
            client.join()
        self.server_udp.close()
        self.server_socket.close()
        print("SERWER KONIEC!")

    def send_message(self, message, sender, udp=False):
        self.lock.acquire()
        username = self.usernames.get(sender, str(sender))
        for client in self.threads:
            if client.address == sender:
                continue
            if udp:
                self.server_udp.sendto(bytes(messages["pic"](message), "UTF-8"), client.address)
            else:
                client.send(messages["msg"](username + " " + message))
        self.lock.release()

    def remove_thread(self, address):
        self.lock.acquire()
        for client in self.threads:
            if client.address == address:
                self.threads.remove(client)
                break
        self.lock.release()


if __name__ == '__main__':
    server = Server(1236)
    server.start()
    server.join()
