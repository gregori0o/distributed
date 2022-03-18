import requests


class FAProvider(object):
    url = "https://filmaffinity-unofficial.p.rapidapi.com/movie/search/"
    apihost = "filmaffinity-unofficial.p.rapidapi.com"
    apikey = "79b484254bmsh267702e9c5262bdp12dd08jsneb739f943dd0"

    params_names = {
        "title": "query",
        "year": "fromYear",
        "year2": "toYear"
    }

    def __init__(self):
        self.params = dict()
        self.headers = {
            "x-rapidapi-key": self.apikey,
            "x-rapidapi-host": self.apihost
        }
        self.data = None

    def set_default(self):
        self.params.clear()
        self.params["lang"] = "en"

    def get(self, title=None, year=None):
        self.set_default()

        params = {
            "title": title,
            "year": year,
            "year2": year
        }

        for key, value in params.items():
            if value:
                k = self.params_names.get(key)
                if k:
                    self.params[k] = value

        self.data = self.request()

        if self.data is not None:
            self.data = self.data.json()
            self.data = self.data["movies"]
            if len(self.data) == 0:
                return False
            self.data = self.data[0]
            return True
        return False

    def request(self):
        res = requests.get(self.url, headers=self.headers, params=self.params)
        if res.status_code != requests.codes.ok:
            print("Error: ", res.status_code)
            return None
        return res

    def get_title(self):
        return self.data.get("title")

    def get_votes(self):
        value = self.data.get("votes")
        if not value:
            return None
        return int(value)

    def get_rating(self):
        value = self.data.get("rating")
        return float(value) if value else None

    def __str__(self):
        return "FilmAffinity"
