import requests


class OMDProvider(object):
    url = "http://www.omdbapi.com"
    apikey = "d289e0a2"

    params_names = {
        "title": "t",
        "year": "y"
    }

    def __init__(self):
        self.params = dict()
        self.data = None

    def set_default(self):
        self.params.clear()
        self.params["apikey"] = self.apikey

    def get(self, title=None, year=None):
        self.set_default()

        params = {
            "title": title,
            "year": year
        }

        for key, value in params.items():
            if value:
                self.params[self.params_names[key]] = value

        self.data = self.request()

        if self.data is not None:
            self.data = self.data.json()
            if self.data['Response'] == 'False':
                return False
            return True
        return False

    def request(self):
        res = requests.get(self.url, self.params)
        if res.status_code != requests.codes.ok:
            print("Error: ", res.status_code)
            return None
        return res

    def get_title(self):
        return self.data.get("Title")

    def get_poster(self):
        return self.data.get("Poster", "")

    def __str__(self):
        return ""
