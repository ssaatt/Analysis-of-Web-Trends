Analysis-of-Web-Trends
======================

<img src="https://s3.amazonaws.com/js4153/25.png">
<br>

1. First, use Google App Eninge's Java SKD and Twitter Search API. 

2. Use Google DataStore Service to cache the returned tweets: create a DataStore class having data members such as: id, text, from_user, from_user_name, Created_at, location. Based on the Twitter response, extract information from the response and store it into an object instance of DataStore class for each tweet. Later, when you are answering the same query, you should reuse the tweets stored in the DataStore rather than sending a new request to Twitter API.

3. Write an analytics to detect "what is trending?" in twitter data. 

4. Next, implement a Hadoop/Map Reduce version of your algorithm. One example algorithm for trending is as follows: You only count meaningful words and ignore general/common words like this, that, here, a, there, home etc. Now you would rank the meaningful words based on their frequency. Once you rank those, then you could simply list those as trends or do some more trick. You go back to searching those twits where these words have occured and try to pull some news or set of associated words that occur with these high frequency words.

5. Next you create an EMR. Copy your twit data to a S3 bucket. Run your algorithm there and show the results.


