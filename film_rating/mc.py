from omd import OMDProvider


class MCProvider(OMDProvider):

    def __init__(self):
        super().__init__()

    def get_votes(self):
        return 1

    def get_rating(self):
        value = self.data.get("Ratings", [])
        for rate in value:
            if rate.get("Source", "") == "Metacritic":
                val = rate.get("Value", "")
                if not val:
                    continue
                val = val.split('/')
                return int(val[0])/10
        return None

    def __str__(self):
        return "Metacritic"
