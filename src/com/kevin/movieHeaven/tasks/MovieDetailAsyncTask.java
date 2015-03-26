package com.kevin.movieHeaven.tasks;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import android.os.AsyncTask;
import android.webkit.WebView;

public class MovieDetailAsyncTask extends AsyncTask<String, Integer, String> {

    private WebView webView;

    public MovieDetailAsyncTask(WebView webView) {
        this.webView = webView;
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            Document doc = Jsoup.connect(urls[0]).get();

            Elements contents = doc.select("#Zoom");
            if (contents.size() > 0) {
                Element content = contents.first();
                content.select("script").remove();
                content.select(">span>font").remove();
                content.select(">span>center").remove();
                content.select(">span>hr").remove();
                content.select("img").attr("style", "width:100%;height:inherit;");
                return content.outerHtml();
            }

            return "";
        } catch (IOException e) {
            return "error";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        webView.loadData(result, "text/html; charset=UTF-8", null);
    }
}
