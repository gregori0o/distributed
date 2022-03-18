messages = {
    "close": "**CLOSE**",
    "msg": lambda text: "**MSG** " + text,
    "pic": lambda text: "**PIC** " + text,
    "nick": lambda text: "**NICK** " + text,
    "msg_from": lambda type, sender, text: "**" + type + "** " + sender + " " + text,
    "U": """**PIC** 
           __
      (___()'`;
      /,    /`
jgs   \\"--\\
""",
    "M": lambda user: "**PIC**" + user + """ 
  ,-.       _,---._ __  / \  
 /  )    .-'       `./ /   \ 
(  (   ,'            `/    /|
 \  `-"             \'\   / |
  `.              ,  \ \ /  |
   /`.          ,'-`----Y   |
  (            ;        |   '
  |  ,-.    ,-'         |  / 
  |  | (   |        hjw | /  
  )  |  \  `.___________|/   
"""
}


def parse(msg):
    if msg[:2] == "**" and "**" in msg[2:]:
        result = msg.split(sep="**", maxsplit=2)[1:]
    else:
        result = ["ERR"]
    if result[0] == "MSG":
        return "msg", result[1]
    elif result[0] == "PIC":
        return "pic", result[1]
    elif result[0] == "NICK":
        return "nick", result[1]
    elif result[0] == "CLOSE":
        return "close", None
    else:
        return "msg", "Błąd odczytu! -> " + msg
