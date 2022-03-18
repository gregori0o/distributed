import requests


class NYTProvider(object):
    url = "https://api.nytimes.com/svc/movies/v2/reviews/search.json"
    apikey = "Q8h70zj5KtGL3vv5EGXAtO64e0BlCvRY"

    params_names = {
        "title": "query",
        "year": "opening-date",
    }

    def __init__(self):
        self.params = dict()
        self.data = None

    def set_default(self):
        self.params.clear()
        self.params["api-key"] = self.apikey

    def get(self, title=None, year=None):
        self.set_default()
        if year:
            year = '{0}-01-01:{0}-12-31'.format(year)

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
            if self.data['num_results'] == 0:
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
        return self.data.get("display_title")

    def get_review(self):
        headline = self.data.get("headline", "")
        summary = self.data.get("summary_short", "")
        if not (headline or summary):
            return ""
        review = "{0} {1}".format(headline, summary)
        return review

    def get_url(self):
        link = self.data.get("link")
        if link is None:
            return ""
        return link.get("url", "")
