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

### .env File
This project uses a .env file to configure usage. Simply create a file at the root of the repo titled .env and provide the following: 
- TUMBLR_CONSUMER_KEY: Consumer key provided by Tumblr API registration
- TUMBLR_CONSUMER_SECRET: Consumer Secret provided by Tumblry API registration
- TUMBLR_TOKEN: Token provided by tumblr API registration
- TUMBLR_TOKEN_SECRET: Token secret provided by Tumblr API registration

- BLOG_NAME: The name of your blog
- BLOG_DOMAIN: Full domain of your blog (Usually, but not always, <blogName>.tumblr.com)
- TAGS: The tags of the post you want to search for. Should be unique otherwise the post you recieve will be undeterministic. 

Example:
```
TUMBLR_CONSUMER_KEY=***
TUMBLR_CONSUMER_SECRET=***
TUMBLR_TOKEN=***
TUMBLR_TOKEN_SECRET=***

BLOG_NAME=mizeress
BLOG_DOMAIN=mizeress.tumblr.com
TAGS=lotr_stats,polls,Mordor Coeffecient
```
