package com.example.myproject;


import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

public class TweetDownloader {

	public static void main(String[] args) {
		String query = (args.length > 0) ? args[0] : "Cloud"; 
		
		PrintWriter out = null;
		String fileName = "tweets.txt";
		try {
			List<String> tweets = TwitterClient.search(query);
			out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
			
			for (String t : tweets) {
				out.println(t.replace("\n", "")); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}
}
