import socket
from threading import Thread, Lock
from data import *


MULTICAST_ADDRESS = ("224.0.1.1", 8018)


class Client(Thread):
    def __init__(self, server_port, server_ip="127.0.1.1"):
        super().__init__()
        self.server_address = (server_ip, server_port)
        address = socket.gethostbyname(socket.gethostname())
        self.flag = True

        self.client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.client_socket.connect(self.server_address)
        self.client_address = self.client_socket.getsockname()
        self.username = str(self.client_address)

        self.client_udp = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        self.client_udp.setsockopt(socket.IPPROTO_IP, socket.IP_MULTICAST_TTL, 20)
        self.client_udp.bind(self.client_address)

        self.client_multi = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        self.client_multi.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        try:
            self.client_multi.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEPORT, 1)
        except AttributeError:
            pass  # Some systems don't support SO_REUSEPORT
        self.client_multi.setsockopt(socket.SOL_IP, socket.IP_MULTICAST_TTL, 20)
        self.client_multi.setsockopt(socket.SOL_IP, socket.IP_MULTICAST_LOOP, 1)
        self.client_multi.bind(('', MULTICAST_ADDRESS[1]))
        self.client_multi.setsockopt(socket.SOL_IP, socket.IP_MULTICAST_IF, socket.inet_aton(address))
        self.client_multi.setsockopt(socket.SOL_IP, socket.IP_ADD_MEMBERSHIP, socket.inet_aton(MULTICAST_ADDRESS[0])
                                     + socket.inet_aton(address))
        print("Nawiązano połączenie! - ", self.client_address)
        print("Jeśli chcesz ustawić swój nick wpisz komendę 'NICK'.")

    def multi_msg(self):
        while self.flag:
            buff, address = self.client_multi.recvfrom(4096)
            msg = parse(str(buff, "UTF-8"))
            if address == self.client_address or msg[0] != "pic":
                continue
            print("Otrzymana grafika to:\n", msg[1])

    def udp_msg(self):
        while self.flag:
            buff, address = self.client_udp.recvfrom(4096)
            msg = parse(str(buff, "UTF-8"))
            if msg[0] != "pic":
                continue
            print("Otrzymana grafika to:\n", msg[1])

    def send_msg(self):
        while self.flag:
            msg = input("Podaj treść wiadomości:\n")
            if msg == "NICK":
                nick = input("Podaj swój nowy nick:\n")
                self.username = nick
                buff = bytes(messages["nick"](nick), "UTF-8")
                if self.client_socket.fileno() != -1:
                    self.client_socket.send(buff)
            elif msg == "U":
                buff = bytes(messages["U"], "UTF-8")
                self.client_udp.sendto(buff, self.server_address)
            elif msg == "M":
                buff = bytes(messages["M"](self.username), "UTF-8")
                self.client_udp.sendto(buff, MULTICAST_ADDRESS)
            elif msg == "close":
                buff = bytes(messages["close"], "UTF-8")
                if self.client_socket.fileno() != -1:
                    self.client_socket.send(buff)
            elif self.client_socket.fileno() != -1 and msg:
                buff = bytes(messages["msg"](msg), "UTF-8")
                self.client_socket.send(buff)

    def recv_msg(self):
        while self.flag:
            buff = self.client_socket.recv(4096)
            msg = parse(str(buff, "UTF-8"))
            if len(buff) == 0 or msg[0] == "close":
                self.flag = False
                self.client_udp.sendto(bytes(messages["close"], "UTF-8"), MULTICAST_ADDRESS)
                self.client_udp.sendto(bytes(messages["close"], "UTF-8"), self.client_address)
                print("Zostałeś rozłączony z serwerem!")
                break
            print("Otrzymana wiadomość to:\n", msg[1])
        self.client_socket.close()

    def run(self) -> None:
        thread_send = Thread(target=self.send_msg)
        thread_recv = Thread(target=self.recv_msg)
        thread_udp = Thread(target=self.udp_msg, daemon=True)
        thread_multi = Thread(target=self.multi_msg, daemon=True)
        thread_send.start()
        thread_recv.start()
        thread_udp.start()
        thread_multi.start()
        thread_send.join()
        thread_recv.join()
        self.client_multi.setsockopt(socket.SOL_IP, socket.IP_DROP_MEMBERSHIP,
                                     socket.inet_aton(MULTICAST_ADDRESS[0]) + socket.inet_aton(self.client_address[0]))
        self.client_multi.close()
        self.client_udp.close()


if __name__ == '__main__':
    client = Client(1236, server_ip="127.0.0.1")
    client.start()
    client.join()
