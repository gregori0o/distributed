from providers.omd import OMDProvider


class IMDBOMDProvider(OMDProvider):

    def __init__(self):
        super().__init__()

    def get_votes(self):
        value = self.data.get("imdbVotes")
        if not value:
            return None
        if ',' in value:
            value = value.split(',')
            value = "".join(value)
        return int(value)

    def get_rating(self):
        value = self.data.get("imdbRating")
        return float(value) if value else None

    def __str__(self):
        return "IMDb from OMD"
