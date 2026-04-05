# 📊 Middle-earth Metrics: The Tumblr Poll Engine

An automated data pipeline that allows for data extraction of Tumblr polls, with an example usage for my own LOTR Stats project that utilizes Plotly graphs to visualize LOTR Races and Characters. 

## Status - Refining Data Extraction
Officially have all json values from Tumblr able to be pulled into my own app. Now working on stabilizing this system for scalability and mapping `content_id`s for poll responses to their associated answer option.

## 🏗️ Project Description
This project implements a "serverless" architecture to bridge social engagement and data science. By scraping custom-tagged Tumblr polls, the engine calculates weighted averages across several "High-Fantasy" dimensions and updates a live Plotly visualization hosted on GitHub Pages.

### The Tech Stack
* **Engine:** Java
* **Automation:** GitHub Actions (Scheduled Cron Jobs)
* **Data Format:** JSON-based flat-file database
* **Frontend:** Plotly.js Radar Charts
* **Hosting:** GitHub.io (Static Site)
---

## Docs
### Results API Call
This is the secret to this project working. Tumblr's official API has no documented endpoint, but there is an endpoint exposed. Bear in mind that since it's not documented, it may not have official support.

This request is structured as follows: `https://api.tumblr.com/v2/polls/<blogName>/<postId>/<pollId>/results`
- blogName: The name of the post's author. E.G. `mizeress`
- postId: The ID of the post. This _can_ be retrieved from the official API
- pollId: The Poll's specific content_id. Also obtained from the official API
