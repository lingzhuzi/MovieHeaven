package com.kenvin.movieHeaven;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import android.os.AsyncTask;

public class GetMovieListAsyncTask extends
		AsyncTask<String, Integer, String> {

	private List<String> movieNameList;
	private List<String> movieUrlList;
	private MovieListCallback callback;
	
	public GetMovieListAsyncTask(MovieListCallback callBack){
		this.movieNameList = new ArrayList<String>();
		this.movieUrlList = new ArrayList<String>();
		this.callback = callBack;
	}
	
	@Override
	protected String doInBackground(String... urls) {
		try {
			String url = urls[0];
			if(url == null){
				return null;
			}
			Document doc = Jsoup.connect(url).get();
            Element content = doc.select(".co_content8").first();
            Elements pages = content.select("a:contains(下一页)");
            
            String hasPages = pages != null && pages.size() > 0 ? "y" : "n";
            content.select("a[href^=/plus/search.php]").remove();
            content.select(".title_all").remove();
            content.select(">b").remove();
            
			Elements links = content.select(".ulink");
            if(links.size() == 0){
            	links = content.select("a");
            }
            
            for(Element link : links){
            	String href = "http://www.ygdy8.net" + link.attr("href");
            	String text = link.text();
            
            	if(href != null && !href.endsWith("index.html")){
                	movieNameList.add(text);
                	movieUrlList.add(href);	
            	}
            }
			return hasPages;
        } catch (IOException e) {
            return "error";
        }
	}
	
	@Override  
    protected void onPostExecute(String result) {  
		if(result != null){
			if("error".equals(result)){
				callback.failed();
			} else {
				callback.succeed(movieNameList, movieUrlList, "y".equals(result));
			}
		}
    }  
	
}