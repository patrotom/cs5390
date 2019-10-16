package edu.utep.cs.cs4330.mygrade;

import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Find a CS4330 grade by querying the grade web service available at:
 * <code>http://www.cs.utep.edu/cheon/cs4330/grade/index.php</code>.
 * The web service takes a user name and a PIN as a query string, e.g.,
 * <code>?user=guest&pin=1234</code>, and returns the requested grade,
 * or an error if no grade is found.
 *
 * This class provides two query methods: <code>query</code>
 * and <code>queryAync</code>. The first one performs a network
 * operation (HTTP connection) on the caller's thread, and the
 * second method creates a new thread for the network operation.
 * The follow code shows how to use the two methods.
 *
 * <pre>
 *     WebClient web = new WebClient(new GradeListener() {
 *         public void onGrade(String date, Grade grade) {
 *             ... date ... grade ...
 *         }
 *         public void onError(String msg) {
 *             ... msg ...
 *         }
 *     });
 *     web.queryAsync("guest", "1234"));
 *
 *     WebClient web = new WebClient(new GradeListener() {
 *         public void onGrade(String date, Grade grade) {
 *            MainActivity.this.runOnUiThread(() -> { ... date ... grade... });
 *         }
 *         public void onError(String msg) {
 *            MainActivity.this.runOnUiThread(() -> { ... msg ... });
 *         }
 *     });
 *     new Thread(() -> web.query("guest", "1234")).start();
 * </pre>
 *
 * Note that in both cases the callback methods (onGrade and onError) are
 * invoked in the caller's thread.
 */
public class WebClient {

    /** To notify a requested grade, or an error. */
    public interface GradeListener {

        /** Called when a grade is found. This method is
         * invoked in the caller's thread.
         *
         * @param date The posting date of the grade.
         * @param grade The requested grade.
         */
        void onGrade(String date, Grade grade);

        /** Called when an error is encountered. This method is invoked
         * in the caller's thread. */
        void onError(String msg);
    }

    private static final String WS_URL = "http://www.cs.utep.edu/cheon/cs4330/grade/index.php";
    private static final String QS_USER = "?user=";
    private static final String QS_PIN = "&pin=";
    private static final String GET = "GET";
    private static final int RESPONSE_OK = 200;

    private static final String JS_RESPONSE = "response";
    private static final String JS_REASON = "reason";
    private static final String JS_DATE = "date";
    private static final String JS_GRADE = "grade";
    private static final String JS_TOTAL = "percent";
    private static final String JS_DETAIL = "detail";
    private static final String JS_NAME = "name";
    private static final String JS_MAX = "max";
    private static final String JS_EARNED = "earned";

    /** The consumer of the requested grade. */
    private final GradeListener listener;

    /** Create a new instance. */
    public WebClient(GradeListener listener) {
        this.listener = listener;
    }

    /** Query the grade of the specified user and notify it to the listener.
     * This method creates a new thread for a network (HTTP) connection,
     * however, the result is notified on the caller's thread. */
    public void queryAsync(String user, String pin) {
        if (listener != null) {
            Handler handler = new Handler();
            new Thread(() -> query(user, pin, new GradeListener() {
                @Override
                public void onGrade(String date, Grade grade) {
                    handler.post(() -> listener.onGrade(date, grade));
                }

                @Override
                public void onError(String msg) {
                    handler.post(() -> listener.onError(msg));
                }
            })).start();
        }
    }

    /** Query the grade of the specified user and notify it to the listener.
     * This method makes a network (HTTP) connection on the caller's thread. */
    public void query(String user, String pin) {
        if (listener != null) {
            query(user, pin, listener);
        }
    }

    /** Query the grade of the specified user and notify it to the given listener. */
    private void query(String user, String pin, GradeListener listener) {
        try {
            StringBuilder query = new StringBuilder(WS_URL);
            query.append(QS_USER);
            query.append(URLEncoder.encode(user, "utf-8"));
            query.append(QS_PIN);
            query.append(URLEncoder.encode(pin, "utf-8"));
            URL url = new URL(query.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(GET);
            if (con.getResponseCode() == RESPONSE_OK) {
                parseResponse(readAll(con.getInputStream()), listener);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        listener.onError("Connection failed.");
    }

    private static String readAll(InputStream stream) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        StringBuffer response = new StringBuffer();
        String output;
        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();
        return response.toString();
    }

    /**
     * Parse a response from the grade web service and notify it
     * to the listener. The response is a JSON string of the form:
     *
     *  { "response": true,
     *    "date": "Generated on Sun, Jan 21, 2018 5:35:09 PM",
     *    "sid": "1234",
     *    "grade": "A",
     *    "total": 98,
     *    "detail": [
     *      { "name": "hw1", "max": 100, "earned": 96 },
     *      { "name": "hw2", "max": 100, "earned": 100 }
     *    ]
     *  }
     *
     * or
     *  { "response": false, "reason": "PIN not specified" }
     */
    private void parseResponse(String response, GradeListener listener) {
        try {
            JSONObject obj = new JSONObject(response);
            boolean ok = obj.getBoolean(JS_RESPONSE);
            if (!ok) {
                listener.onError(obj.getString(JS_REASON));
                return;
            }

            String date = obj.getString(JS_DATE);
            String letter = obj.getString(JS_GRADE);
            int total = obj.getInt(JS_TOTAL);
            JSONArray details = obj.getJSONArray(JS_DETAIL);

            List<Grade.Score> scores = new ArrayList<>();
            for (int i = 0; i < details.length(); i++) {
                JSONObject score = details.getJSONObject(i);
                String name = score.getString(JS_NAME);
                int max = score.getInt(JS_MAX);
                int earned = score.getInt(JS_EARNED);
                scores.add(new Grade.Score(name, max, earned));
            }
            Grade grade = new Grade(letter, total, scores);
            listener.onGrade(date, grade);
            return;
        } catch (JSONException e) {
        }
        listener.onError("Malformed response.");
    }
}
