package com.kevin.movieHeaven.tasks;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import com.kevin.movieHeaven.models.UpdateInfo;
import com.kevin.movieHeaven.utils.IOUtils;
import com.kevin.movieHeaven.utils.UpdateInfoCallback;
import android.os.AsyncTask;

public class CheckUpdateInfoAsyncTask extends AsyncTask<String, Integer, String> {

    private UpdateInfo info;
    private UpdateInfoCallback callback;

    public CheckUpdateInfoAsyncTask(UpdateInfoCallback callBack) {
        this.callback = callBack;
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            String path = "https://raw.githubusercontent.com/lingzhuzi/MovieHeaven/master/release/version.json";

            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            if (200 == conn.getResponseCode()) {
                InputStream instream = conn.getInputStream();
                parseJSON(instream);
            }
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }

    /**
     * 解析JSON
     */
    private void parseJSON(InputStream instream) throws Exception {
        byte[] data = IOUtils.read(instream);
        String jsonStr = new String(data);
        JSONObject object = new JSONObject(jsonStr);
        String version = object.getString("version");
        String logs = object.getString("logs");
        info = new UpdateInfo();
        info.setLogs(logs);
        info.setVersion(version);
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            if ("success".equals(result) && info != null) {
                callback.succeed(info, "success".equals(result));
            } else {
                callback.failed();
            }
        }
    }

}
