package bosunard.aston.com.finalyearproject.models;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TimetableUtility {

    private static final String TAG = "TimetableUtility";

    public static Timetable findTimetable(String uri, String referer) {


        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            URL url = new URL(uri);
            Log.i(TAG, url.toString());
            conn = (HttpURLConnection) url.openConnection();
            if (referer != null) {
                conn.setRequestProperty("Referer", referer);
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            Gson gson = new Gson();
            Timetable timetable = gson.fromJson(in, Timetable.class);
            Log.i(TAG, "We got timetable for: " + timetable.getOrigin_name() + " → " + timetable.getDestination_name());
            // Load  results into a StringBuilder

//            int read;
//            char[] buff = new char[1024];
//            while ((read = in.read(buff)) != -1) {
//                jsonResults.append(buff, 0, read);
//            }

            return timetable;
        } catch (MalformedURLException e) {
            Log.i(TAG, "Error processing TransportAPI URL");
            return null;
        } catch (IOException e) {
            Log.i(TAG, "Error connecting to TransportAPI: " + e.getMessage());
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
