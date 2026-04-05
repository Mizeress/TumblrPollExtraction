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
This is the secret to this project working. Tumblr's official API has no official endpoint for getting Poll results, so you have to use the endpoint exposed to browsers. 

This request is structured as follows: `https://www.tumblr.com/api/v2/polls/<blogName>/<postId>/<pollId>/results`
- blogName: The name of the post's author. E.G. `mizeress`
- postId: The ID of the post. This _can_ be retrieved from the official API
- pollId: The Poll's specific content_id. Also obtained from the official API

You also need the following headers:
- `X-Tumblr-Form-Key`: a csrfToken authorizing the current user. This is assigned by Tumblr and can be extracted from an https request.
- `User-Agent`: What Tumblr sees the traffic as being. In this case, our traffic is essentially a web browser.
- `Content-Type`: What type of content you want returned. Here we want application/json
- `Accept`: application/json
- `Referer`: Where you're browsing from. "https://www.tumblr.com/blogName" is a good bet.

Note: As this is not an officially exposed API, there is no documentation or support for it, so it is liable to be changed. If this happens, you may be able to reverse engineer the API yourself by examining the network traffic of your browser while on a poll post. 
If Tumblr does eventually add an official way to get poll data, this App should be updated to use that instead. 

Warning: If you use this, be conscientious about it. This is not an official endpoint, so their built-in rate limits to the API don't apply. If you abuse this, they will block your IP. 
