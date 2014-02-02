package com.example.myproject;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class parseQuery{

	
	static List<String> findTrends(String s) throws IOException, TwitterException {
		
		HashMap<String, Integer> words = new HashMap<String, Integer>();
		
		Twitter twitter = new TwitterFactory().getInstance();
        AccessToken accessToken =new AccessToken("2233989774-byNN34yujDsBBWh2GHIxDcTpb2CUlHyQxIaNFYL","OXCunabUMEZp9H3AVR2jp3IFwQ5XRAMmdUeWCPuhmnDFl");
    	
    	twitter.setOAuthConsumer("CuUI9V9t7gR3xwgq0sByA","47kQkKVaxBbZ1XYsfx64gADFcHgPQ01UwxtHqKQcDo");
    	twitter.setOAuthAccessToken(accessToken);
    
            Query query = new Query(s);
            query.setResultType(Query.RECENT);
    	    query.setLang("en");
    	    query.setCount(100);
            QueryResult result;
            result = twitter.search(query);
            
            List<Status> tweets = result.getTweets();
            for(int i = 0;i < tweets.size();i++) {  	
            	Status tweet = tweets.get(i);
            	String Text = tweet.getText().trim().toLowerCase();
            	String regex = "[^a-zA-Z0-9]";
            	String tempArray [] = Text.split(regex);
            	for(int j = 0; j < tempArray.length; j++) {
            		String temp = tempArray[j].toLowerCase();
            		if(temp.length()>2){
						if(temp != "" && temp != null) {
							Integer freq = (Integer) words.get(temp);
							words.put(tempArray[j], freq == null? 1 : (freq+1));
						}					
					}
            		
            	}
            }
            
            List<Map.Entry> list = new ArrayList<Map.Entry>(words.entrySet());
    	    System.out.println(list.size());
    		Collections.sort(list, new Comparator() {
    			public int compare(Object o2, Object o1) {
    				Map.Entry e1 = (Map.Entry) o1;
    				Map.Entry e2 = (Map.Entry) o2;
    				return ((Comparable) e1.getValue()).compareTo(e2.getValue());
    				
    			}
    		});
    		
    		List<String> trend = new ArrayList<String>();
    		for (int i = 0, j = 0; i < 10; j++) {
    			String word = (String) list.get(j).getKey();
    			if(!StopWords.stopWordsList.contains(word)) {
    				trend.add(word);
    				i++;
    			}	
    		}
    	    return trend;
    	 }      
	}

