import os
import pytumblr
from dotenv import load_dotenv

# Load the variables from .env into the environment
load_dotenv()

# Retrieve them using os.getenv
# The strings here must match the keys in your .env exactly
CONSUMER_KEY = os.getenv('TUMBLR_CONSUMER_KEY')
CONSUMER_SECRET = os.getenv('TUMBLR_CONSUMER_SECRET')
OAUTH_TOKEN = os.getenv('TUMBLR_TOKEN')
OAUTH_TOKEN_SECRET = os.getenv('TUMBLR_TOKEN_SECRET')

# Initialize the client
client = pytumblr.TumblrRestClient(
    CONSUMER_KEY,
    CONSUMER_SECRET,
    OAUTH_TOKEN,
    OAUTH_TOKEN_SECRET
)

try:
    # Test call to verify credentials
    info = client.info()
    print(f"✅ Connection successful for blog: {info['user']['name']}")
except Exception as e:
    print(f"❌ Connection failed. Error: {e}")
    print("Double-check that your .env variable names match the script.")