import requests


class IMDBProvider(object):
    url = "https://data-imdb1.p.rapidapi.com/titles/search/title/"
    apihost = "data-imdb1.p.rapidapi.com"
    apikey = "79b484254bmsh267702e9c5262bdp12dd08jsneb739f943dd0"

    params_names = {
        "year": "year"
    }

    def __init__(self):
        self.params = dict()
        self.headers = {
            "x-rapidapi-key": self.apikey,
            "x-rapidapi-host": self.apihost
        }
        self.data = None
        self.title = ""

    def set_default(self):
        self.params.clear()
        self.params["titleType"] = "movie"

    def get(self, title=None, year=None):
        self.set_default()
        if not title:
            return False
        self.url += title

        params = {
            "year": year
        }

        for key, value in params.items():
            if value:
                k = self.params_names.get(key)
                if k:
                    self.params[k] = value

        self.data = self.request(self.url)

        if self.data is None:
            return False

        self.data = self.data.json()
        if self.data.get('entries', 0) == 0:
            return False
        id = self.data['results'][0].get("id")
        self.title = self.data['results'][0].get("titleText", dict()).get("text", "")
        if not id:
            return False
        url_details = "https://data-imdb1.p.rapidapi.com/titles/{}/ratings".format(id)
        self.set_default()
        self.data = self.request(url_details)
        if self.data is None:
            return False
        self.data = self.data.json()
        self.data = self.data.get("results", dict())
        if self.data is None:
            return False
        return True

    def request(self, url_request):
        res = requests.get(url_request, headers=self.headers, params=self.params)
        if res.status_code != requests.codes.ok:
            print("Error: ", res.status_code)
            return None
        return res

    def get_title(self):
        return self.title

    def get_votes(self):
        value = self.data.get("numVotes")
        if not value:
            return None
        return int(value)

    def get_rating(self):
        value = self.data.get("averageRating")
        return float(value) if value else None

    def __str__(self):
        return "IMDb"
