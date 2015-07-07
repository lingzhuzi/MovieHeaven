package com.kevin.movieHeaven.tasks;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.kevin.movieHeaven.utils.MovieListCallback;

import android.os.AsyncTask;
import android.util.Log;

public class MovieListAsyncTask extends AsyncTask<String, Integer, String> {

    private List<String> movieNameList;
    private List<String> movieUrlList;
    private MovieListCallback callback;

    public MovieListAsyncTask(MovieListCallback callBack) {
        this.movieNameList = new ArrayList<String>();
        this.movieUrlList = new ArrayList<String>();
        this.callback = callBack;
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            String url = urls[0];
            if (url == null) {
                return null;
            }
            Log.d("net_connection", url);

            Document doc = Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.2) Gecko/2008070208 Firefox/3.0.1")
                    .header("Accept", "text ml,application/xhtml+xml").header("Accept-Language", "zh-cn,zh;q=0.5").header("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7")
                    .timeout(30000).get();
            Log.d("content", doc.html());
            Elements areas = doc.select(".co_area2");
            Element content = null;
            if (areas.size() > 0) {
                for (Element area : areas) {
                    Element titleElement = area.select(".title_all").first();
                    if (titleElement != null) {
                        String title = titleElement.text();
                        if (title != null && title.contains("新片精品")) {
                            Log.d("title", "got it");
                            content = area.select(".co_content8").first();
                            if (content == null) {
                                content = area.select(".co_content222").first();
                            }
                            break;
                        }
                    }
                }
            }
            if (content == null) {
                content = doc.select(".co_content8").first();
            }
            if (content == null) {
                return "n";
            }
            Elements pages = content.select("a:contains(下一页)");

            String hasPages = pages != null && pages.size() > 0 ? "y" : "n";
            content.select("a[href^=/plus/search.php]").remove();
            content.select(".title_all").remove();
            content.select(">b").remove();

            Elements links = content.select(".ulink");
            if (links.size() == 0) {
                links = content.select("a");
            }

            URI uri = URI.create(url);
            for (Element link : links) {
                URI mUri = uri.resolve(link.attr("href"));
                String href = mUri.toString();
                String text = link.text();

                if (href != null && !href.endsWith("index.html") && mUri.getHost().equals(uri.getHost())) {
                    movieNameList.add(text);
                    movieUrlList.add(href);
                    Log.d("movie list", text + "  " + href);
                }
            }
            return hasPages;
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg != null) {
                Log.d("error", msg);
            }
            e.printStackTrace();
            return "error";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            if ("error".equals(result)) {
                callback.failed();
            } else {
                callback.succeed(movieNameList, movieUrlList, "y".equals(result));
            }
        }
    }

}
