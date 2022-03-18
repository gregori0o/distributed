from flask import Flask, render_template, request
from logic import Evaluator

app = Flask(__name__)


@app.route('/')
def index():
    return render_template('index.html')


@app.route('/rating', methods=['GET', 'POST'])
def rating():
    if request.method == 'GET':
        return render_template('index.html')

    params = dict()
    params["title"] = request.form['title']
    year = request.form['year']
    if year:
        params["year"] = year

    result = Evaluator().evaluate(**params)

    if result["url"]:
        result["url_description"] = "Full review"
    else:
        result["url_description"] = ""

    if result is None:
        return render_template('index.html', error="Nie udało się pobrać danych!")
    return render_template('rating.html', **result)
