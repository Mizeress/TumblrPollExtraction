# 📊 Middle-earth Metrics: The Tumblr Poll Engine

An automated data pipeline that transforms Tumblr community sentiment into dynamic, multidimensional visualizations of *Lord of the Rings* characters and races.

## 🏗️ Project Description
This project implements a "serverless" architecture to bridge social engagement and data science. By scraping custom-tagged Tumblr polls, the engine calculates weighted averages across several "High-Fantasy" dimensions and updates a live Plotly visualization hosted on GitHub Pages.


### The Tech Stack
* **Engine:** Python 3.x (with `pytumblr` & `pandas`)
* **Automation:** GitHub Actions (Scheduled Cron Jobs)
* **Data Format:** JSON-based flat-file database
* **Frontend:** Plotly.js Radar Charts
* **Hosting:** GitHub.io (Static Site)

---

## 🛠️ Usage Guide

### 1. Tumblr Post Strategy
To ensure the scraper picks up your data, posts must follow this tagging convention:
* **Project Tag:** `#lotr-stats-project` (Required)
* **Category Tag:** e.g., `#Smol-ness`, `#Old-AF`, `#The-Mordor-Coefficient`
* **Character/Race Tag:** e.g., `#Gimli`, `#Dwarves`, `#Frodo`

*Note: Polls should be formatted with values 1 through 10 as the options.*

### 2. Local Installation
1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/your-username/lotr-poll-engine.git](https://github.com/your-username/lotr-poll-engine.git)
    cd lotr-poll-engine
    ```
2.  **Install Dependencies:**
    ```bash
    pip install python-dotenv pytumblr
    ```
3.  **Environment Setup:**
    Create a `.env` file in the root directory:
    ```env
    TUMBLR_CONSUMER_KEY=your_key
    TUMBLR_CONSUMER_SECRET=your_secret
    TUMBLR_TOKEN=your_token
    TUMBLR_TOKEN_SECRET=your_token_secret
    ```
4.  **Run Extraction Test:**
    ```bash
    python src/main.py
    ```

---

## 🗺️ Roadmap

- [x] **Phase 1: Connectivity** - Establish Tumblr API handshake and OAuth verification.
- [ ] **Phase 2: The Logic Engine** - Develop the `Poll` and `Character` OOP classes to calculate weighted averages.
- [ ] **Phase 3: Discovery Automation** - Build the GitHub Action that scans for new tags and generates `active_polls.json`.
- [ ] **Phase 4: Cross-Repo Deployment** - Configure the Action to push processed data to the `github.io` repository.
- [ ] **Phase 5: Visualization** - Implement the Plotly Radar Chart with interactive character comparisons.
- [ ] **Phase 6: The Mordor Update** - Add support for "The Mordor Coefficient" specifically as a primary metric.

---
