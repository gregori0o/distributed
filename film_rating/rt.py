from omd import OMDProvider


class RTProvider(OMDProvider):

    def __init__(self):
        super().__init__()

    def get_votes(self):
        return 1

    def get_rating(self):
        value = self.data.get("Ratings", [])
        for rate in value:
            if rate.get("Source", "") == "Rotten Tomatoes":
                val = rate.get("Value", "")
                if not val:
                    continue
                return int(val[:-1])/10
        return None

    def __str__(self):
        return "Rotten Tomatoes"
