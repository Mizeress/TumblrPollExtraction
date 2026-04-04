# 📊 Middle-earth Metrics: The Tumblr Poll Engine

An automated data pipeline that transforms Tumblr community sentiment into dynamic, multidimensional visualizations of *Lord of the Rings* characters and races.

## Status - Reworking
Upon attempting to use the Tumblr API to receive poll results, I discovered that this may not be possible. Opened issues with Tumblr for these features and began an investigation of JSoup for webscraping this data. 

## 🏗️ Project Description
This project implements a "serverless" architecture to bridge social engagement and data science. By scraping custom-tagged Tumblr polls, the engine calculates weighted averages across several "High-Fantasy" dimensions and updates a live Plotly visualization hosted on GitHub Pages.

### The Tech Stack
* **Engine:** Java
* **Automation:** GitHub Actions (Scheduled Cron Jobs)
* **Data Format:** JSON-based flat-file database
* **Frontend:** Plotly.js Radar Charts
* **Hosting:** GitHub.io (Static Site)
---
