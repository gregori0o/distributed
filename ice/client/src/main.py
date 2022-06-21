import sys
import Ice
import Home


def is_numeric(value):
    try:
        int(value)
        return True
    except:
        return False


def get_data():
    year = int(input("year -> "))
    month = int(input("month -> "))
    day = int(input("day -> "))
    hour = int(input("hour -> "))
    minutes = int(input("minutes -> "))
    return Home.Heating.Date(day, month, year, hour, minutes)


if __name__ == "__main__":
    with Ice.initialize(sys.argv) as communicator:
        servers = []

        base = communicator.propertyToProxy("Server1.Proxy")
        server = Home.Service.ServerInformationPrx.checkedCast(base)
        if not server:
            raise RuntimeError("Invalid proxy")
        servers.append(server)

        base = communicator.propertyToProxy("Server2.Proxy")
        server = Home.Service.ServerInformationPrx.checkedCast(base)
        if not server:
            raise RuntimeError("Invalid proxy")
        servers.append(server)

        base = communicator.propertyToProxy("Music1.Proxy")
        ground = Home.Audio.MusicPrx.checkedCast(base)
        if not ground:
            raise RuntimeError("Invalid proxy")

        base = communicator.propertyToProxy("Music2.Proxy")
        floor = Home.Audio.MusicPrx.checkedCast(base)
        if not floor:
            raise RuntimeError("Invalid proxy")

        base = communicator.propertyToProxy("Furnace1.Proxy")
        furnace = Home.Heating.FurnacePrx.checkedCast(base)
        if not furnace:
            raise RuntimeError("Invalid proxy")

        lamps = {}

        base = communicator.propertyToProxy("Kitchen.Proxy")
        kitchen_lamp = Home.Lighting.SimpleLampPrx.checkedCast(base)
        if not kitchen_lamp:
            raise RuntimeError("Invalid proxy")
        lamps['kitchen'] = ('simple', kitchen_lamp)

        base = communicator.propertyToProxy("Hall.Proxy")
        hall_lamp = Home.Lighting.SimpleLampPrx.checkedCast(base)
        if not hall_lamp:
            raise RuntimeError("Invalid proxy")
        lamps['hall'] = ('simple', hall_lamp)

        base = communicator.propertyToProxy("Attic.Proxy")
        attic_lamp = Home.Lighting.SimpleLampPrx.checkedCast(base)
        if not attic_lamp:
            raise RuntimeError("Invalid proxy")
        lamps['attic'] = ('simple', attic_lamp)

        base = communicator.propertyToProxy("Room.Proxy")
        room_lamp = Home.Lighting.BulbsLampPrx.checkedCast(base)
        if not room_lamp:
            raise RuntimeError("Invalid proxy")
        lamps['room'] = ('bulbs', room_lamp)

        base = communicator.propertyToProxy("LivingRoom.Proxy")
        living_room_lamp = Home.Lighting.BulbsLampPrx.checkedCast(base)
        if not living_room_lamp:
            raise RuntimeError("Invalid proxy")
        lamps['living_room'] = ('bulbs', living_room_lamp)

        base = communicator.propertyToProxy("SittingRoom.Proxy")
        sitting_room_lamp = Home.Lighting.SmoothLampPrx.checkedCast(base)
        if not sitting_room_lamp:
            raise RuntimeError("Invalid proxy")
        lamps['sitting_room'] = ('smooth', sitting_room_lamp)

        while True:
            command = input("==>\n")
            match command:
                case 'servers':
                    for i, server in enumerate(servers):
                        result = server.getAllIdentities()
                        print(f"In server {i+1} there are:")
                        print(result)
                case 'light':
                    print("This is light section. Please enter 'exit' to return to main menu.")
                    print("Enter command -> exit, state, [name cmd] e.g. kitchen on, room off, living_room 3, "
                          "sitting_room 76")
                    while True:
                        light_command = input("==>\n")
                        if light_command == 'exit':
                            break
                        if light_command == 'state':
                            for name, lamp in lamps.items():
                                result = lamp[1].getState()
                                print(f"Lamp {name} -> {result}")
                        if ' ' not in light_command:
                            print("Please enter command -> exit, state, [name cmd] e.g. kitchen on, room off, "
                                  "living_room 3, sitting_room 76")
                            continue
                        light_command = light_command.split(maxsplit=1)
                        if light_command[0] not in lamps.keys():
                            print(f"Valid names -. {lamps.keys()}")
                            continue
                        match light_command[1]:
                            case 'on':
                                lamps[light_command[0]][1].turnOn()
                                print(f"Lamp {light_command[0]} is turned on")
                            case 'off':
                                lamps[light_command[0]][1].turnOff()
                                print(f"Lamp {light_command[0]} is turned off")
                            case _ if is_numeric(light_command[1]):
                                if lamps[light_command[0]][0] == 'simple':
                                    continue
                                try:
                                    if lamps[light_command[0]][0] == 'smooth':
                                        lamps[light_command[0]][1].turnOnPercentage(int(light_command[1]))
                                        print(f"Lamp {light_command[0]}, change percent to {int(light_command[1])}")
                                    else:
                                        lamps[light_command[0]][1].turnOnFew(int(light_command[1]))
                                        print(f"Lamp {light_command[0]}, set bulbs - {int(light_command[1])}")
                                except Exception as e:
                                    print(e)
                            case _:
                                print("Please enter command -> exit, state, [name cmd] e.g. kitchen on, room off, "
                                      "living_room 3, sitting_room 76")
                case 'audio':
                    print("This is music section. Please enter 'exit' to return to main menu.")
                    print("Enter command -> exit, state, play, pause, volume up, volume down, next, prev, songs, "
                          "playlists, music, songs 'name of playlist', playlist 'name of playlist', song 'name of song'")
                    level = input("Please enter 'ground' or 'floor': ")
                    if level == 'ground':
                        music = ground
                    elif level == 'floor':
                        music = floor
                    else:
                        print('exit')
                        continue
                    while True:
                        music_command = input("==>\n")
                        match music_command:
                            case 'state':
                                result = music.getState()
                                print(result)
                            case 'exit':
                                break
                            case 'volume up':
                                music.volumeUp()
                                print("Volume up music")
                            case 'volume down':
                                music.volumeDown()
                                print("Volume down music")
                            case 'next':
                                music.nextSong()
                                print("Change to next song")
                            case 'prev':
                                music.prevSong()
                                print("Change to prev song")
                            case 'play':
                                music.startStop()
                                print("Play music")
                            case 'pause':
                                music.startStop()
                                print("Pause music")
                            case 'songs':
                                result = music.getSongs(Ice.Unset)
                                print("Songs in actual playlist:")
                                print(result)
                            case 'playlists':
                                result = music.getPlaylists()
                                print("Available playlists:")
                                print(result)
                            case 'music':
                                result = music.getMusic()
                                print("All stored music in device:")
                                print(result)
                            case _ if ' ' in music_command:
                                cmd, name = music_command.split(maxsplit=1)
                                match cmd:
                                    case 'songs':
                                        try:
                                            result = music.getSongs(name)
                                            print(f"Songs from playlist {name}")
                                            print(result)
                                        except Exception as e:
                                            print(e)
                                    case 'song':
                                        try:
                                            music.setSong(name)
                                            print(f"Set song {name}")
                                        except Exception as e:
                                            print(e)
                                    case 'playlist':
                                        try:
                                            music.setPlaylist(name)
                                            print(f"Set playlist {name}")
                                        except Exception as e:
                                            print(e)
                            case _:
                                print("Enter command -> exit, state, play, pause, volume up, volume down, next, prev, "
                                      "songs, playlists, music, songs 'name of playlist', playlist 'name of playlist', "
                                      "song 'name of song'")
                case 'furnace':
                    print("This is heating section. Please enter 'exit' to return to main menu.")
                    print("Enter command -> exit, state, periods, new period, temperature")
                    while True:
                        furnace_command = input("==>\n")
                        match furnace_command:
                            case 'exit':
                                break
                            case 'state':
                                result = furnace.getState()
                                print(result)
                            case 'periods':
                                result = furnace.getAllPeriods()
                                print("All actual periods:")
                                print(result)
                            case 'new period':
                                print("Enter start date:")
                                start = get_data()
                                print("Enter end date:")
                                end = get_data()
                                val = int(input("Enter temperature: "))
                                try:
                                    furnace.addPeriod(start, end, val)
                                    print(f"Set new period from {start} to {end} with temperature {val}")
                                except Exception as e:
                                    print(e)
                            case 'temperature':
                                val = int(input("Enter new temperature: "))
                                try:
                                    furnace.setTemperature(val)
                                    print(f"Set new temperature {val}")
                                except Exception as e:
                                    print(e)
                            case _:
                                print("Enter command -> exit, state, periods, new period, temperature")
                case 'exit':
                    break
                case _:
                    print("This command it is not valid")
                    print("Try servers, light, audio or furnace")

