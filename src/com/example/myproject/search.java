package com.example.myproject;


import java.io.BufferedReader;  
import java.io.IOException;
import java.io.InputStreamReader;  
import java.io.PrintWriter;
import java.net.URL;  
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.apphosting.api.DeadlineExceededException;

public class search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public search() {
        super();
        // TODO Auto-generated constructor stub
    }

	
    private Key createKey(String name) {
        Key key = KeyFactory.createKey("Queries", name);
        return key;
}
    
    
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter pw = new PrintWriter(resp.getOutputStream());
		String query1 = req.getParameter("query").replace(" ", "+");
		
		 resp.setContentType("text/html");
         pw.println("<html><link type=\"text/css\" rel=\"stylesheet\" href=\"result.css\" />");
       
         pw.println("<form action=\"/search\" method=\"GET\">");
         pw.println("<div class = \"center\"><input name = \"query\" value = \"\" " +
                         "class = \"center text\" placeholder=\"keyword\">&nbsp;&nbsp;&nbsp;&nbsp;");
         
         pw.println("&nbsp;&nbsp;<input type='submit' value='Find Trends'/></form></div>");                        
        
         
         pw.println("<br><h1>Trends for \"" + req.getParameter("query") + "\" are "+" </h1>");
         try {
        	 store ds = new store();
			List<String> WORDS = parseQuery.findTrends(query1);
			 int count = 0;
             String space = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
             for(String s : WORDS) {
                 count++;
                 String query = "q=" + s;
                 String datastoreKey = s ;
                 String key = datastoreKey;
                 pw.println("<h2> Top" + count + " Trend: " + s + space +"</h2>");
                 if (ds.findQuery(key)!=null) {
                     Entity temp = ds.findQuery(key);
                     Iterable<Entity> pq = ds.listQueryResults(temp);
                             
                     
                             pw.println("<br><table class=\"tablestyle\">");
                  
                 
                     for (Entity result : pq) {
                         pw.println("<tr>");
                         java.lang.Long Id = (Long) result.getProperty("id");
                         String Text = (String) result.getProperty("text");
                         java.lang.Long From_user = (Long) result.getProperty("from_user");
                         String From_user_name = (String) result.getProperty("from_user_name");
                         String Created_at = (String) result.getProperty("Created_at");
                         String imageUrl = (String) result.getProperty("imageUrl");
                         pw.println("<td>" + From_user + "</td><td>" + Text + "</td><td><img src=\""
                                         + imageUrl + "\" /></td><td>" + From_user_name
                                         + "</td></tr>");        
                 }
                 pw.println("</table>");
                 }
                 else {
                	 Twitter twitter = new TwitterFactory().getInstance();
                     AccessToken accessToken =new AccessToken("2233989774-byNN34yujDsBBWh2GHIxDcTpb2CUlHyQxIaNFYL","OXCunabUMEZp9H3AVR2jp3IFwQ5XRAMmdUeWCPuhmnDFl");
                 	
                 	twitter.setOAuthConsumer("CuUI9V9t7gR3xwgq0sByA","47kQkKVaxBbZ1XYsfx64gADFcHgPQ01UwxtHqKQcDo");
                 	twitter.setOAuthAccessToken(accessToken);
                 	
                 	
                         Query a = new Query(query);
                         QueryResult result;
                         result = twitter.search(a);
                         
                         List<Status> tweets = result.getTweets();
                         
                	 Entity temp = ds.createQuery(key);
                	 if(count%2 == 0) {
                         pw.println("<br><table class=\"tablesalt\">");
                 }
                 else {
                         pw.println("<br><table class=\"tablestyle\">");
                 }
                	 for(int i = 0;i < tweets.size();i++) {  	
                      	Status tweet = tweets.get(i);
                      	String Text = tweet.getText();
                      	int Id=(int) tweet.getId();
                      	int From_user=(int) tweet.getUser().getId();
                      	String From_user_name=tweet.getUser().getName();
                      	String Created_at=String.valueOf(tweet.getCreatedAt());
                      	String imageUrl=tweet.getUser().getProfileImageURL();
                      	ds.localStorage(temp, Id, Text, From_user, From_user_name, Created_at, imageUrl);
                      	pw.println("<td>" + From_user + "</td><td>" + Text + "</td><td><img src=\""
                                + imageUrl + "\" /></td><td>" + From_user_name
                                + "</td></tr>");        
                      	
                	 }
             	 
                	
                  
                 pw.println("</table>");
                	 
                	 
                	 
                	 
                 }
                 
                 
             }
			
			
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
         pw.println("</html>");
         pw.close();
         
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
