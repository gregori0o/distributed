from rt import RTProvider
from tmdb import TMDBProvider
from nyt import NYTProvider
from fa import FAProvider
from imdb_omd import IMDBOMDProvider
from mc import MCProvider
from imdb import IMDBProvider

from concurrent.futures import ThreadPoolExecutor
import matplotlib.pyplot as plt


class Evaluator(object):
    providers = [NYTProvider(), RTProvider(), TMDBProvider(), FAProvider(), IMDBOMDProvider(), MCProvider(), IMDBProvider()]

    def __init__(self):
        self.returns = [False] * len(self.providers)
        self.ratings = [0] * len(self.providers)
        self.kwargs = dict()

    def run_provider(self, idx):
        self.returns[idx] = self.providers[idx].get(**self.kwargs)

    def run_parallel(self):
        with ThreadPoolExecutor() as executor:
            for idx in range(len(self.providers)):
                executor.submit(self.run_provider, idx)

    def make_barplot(self): #todo szerokość wykresu
        labels = []
        values = []
        for i, b in enumerate(self.returns):
            if i == 0:
                continue
            if not b:
                continue
            labels.append(str(self.providers[i]))
            values.append(self.ratings[i])
        plt.figure(figsize=(10, 5))
        plt.barh(labels, values)
        plt.xticks(fontsize=16)
        plt.yticks(fontsize=20)
        plt.xlim([0, 10])
        plt.title("Statistics:", fontsize=25)
        plt.savefig("static/barplot.png")

    def evaluate(self, **kwargs):
        self.kwargs = kwargs
        self.returns = [False] * len(self.providers)
        self.run_parallel()

        votes = 0
        minimum = 10
        maximum = 0
        connected = 0
        w_avg = 0
        avg = 0
        title = ""

        for i, provider in enumerate(self.providers):
            if i == 0:
                continue
            if not self.returns[i]:
                continue
            rating = provider.get_rating()
            vote = provider.get_votes()
            if not isinstance(rating, float):
                self.returns[i] = False
                continue
            if not (isinstance(vote, int) and vote > 0):
                vote = 1
            self.ratings[i] = rating
            votes += vote
            minimum = min(minimum, rating)
            maximum = max(maximum, rating)
            connected += 1
            w_avg += vote * rating
            avg += rating
            if not title:
                title = provider.get_title()
        if connected == 0:
            return None
        w_avg /= votes
        avg /= connected

        url = ""
        review = ""
        poster = ""
        if self.returns[0]:
            url = self.providers[0].get_url()
            review = self.providers[0].get_review()

        if self.returns[1]:
            poster = self.providers[1].get_poster()

        self.make_barplot()

        result = {
            "title": title,
            "minimum": minimum,
            "maximum": maximum,
            "w_average": round(w_avg, 1),
            "average": round(avg, 1),
            "num": len(self.providers) - 1,
            "num_accept": connected,
            "url": url,
            "review": review,
            "poster": poster
        }
        return result
