import requests


class TMDBProvider(object):
    url = "https://api.themoviedb.org/3/search/movie"
    apikey = "49bcacaf8f51052423260862062ba9ae"

    params_names = {
        "title": "query",
        "year": "year"
    }

    def __init__(self):
        self.params = dict()
        self.data = None

    def set_default(self):
        self.params.clear()
        self.params["api_key"] = self.apikey

    def get(self, title=None, year=None):
        self.set_default()

        params = {
            "title": title,
            "year": year
        }

        for key, value in params.items():
            if value:
                k = self.params_names.get(key)
                if k:
                    self.params[k] = value

        self.data = self.request()

        if self.data is not None:
            self.data = self.data.json()
            if self.data['total_results'] == 0:
                return False
            self.data = self.data['results'][0]
            return True
        return False

    def request(self):
        res = requests.get(self.url, self.params)
        if res.status_code != requests.codes.ok:
            print("Error: ", res.status_code)
            return None
        return res

    def get_title(self):
        return self.data.get("title")

    def get_votes(self):
        value = self.data.get("vote_count")
        if not value:
            return None
        return int(value)

    def get_rating(self):
        value = self.data.get("vote_average")
        return float(value) if value else None

    def __str__(self):
        return "The Movie Db"
