package com.example.androidhive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class OfficialListActivity extends ListActivity {
    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jsonParser = new JSONParser();

    ArrayList<HashMap<String, String>> officialList;

    // products JSONArray
    JSONArray official = null;

    // Inbox JSON url
    private static final String OFFICIAL_POSTS_URL = "http://api.androidhive.info/mail/inbox.json";

    // ALL JSON node names
    private static final String TAG_MESSAGES = "messages";
    private static final String TAG_ID = "id";
    private static final String TAG_FROM = "from";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_SUBJECT = "subject";
    private static final String TAG_DATE = "date";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.official_list);

        // Hashmap for ListView
        officialList = new ArrayList<HashMap<String, String>>();

        // Loading OFFICAL in Background Thread
        new LoadOfficialPosts().execute();
    }

    /**
     * Background Async Task to Load all OFFICIAL POSTS messages by making HTTP Request
     * */
    class LoadOfficialPosts extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OfficialListActivity.this);
            pDialog.setMessage("Loading Official Posts ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting Inbox JSON
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            // getting JSON string from URL
            JSONObject json = jsonParser.makeHttpRequest(OFFICIAL_POSTS_URL, "GET",
                    params);

            // Check your log cat for JSON reponse
            Log.d("Inbox JSON: ", json.toString());

            try {
                official = json.getJSONArray(TAG_MESSAGES);
                // looping through All messages
                for (int i = 0; i < official.length(); i++) {
                    JSONObject c = official.getJSONObject(i);

                    // Storing each json item in variable
                    String id = c.getString(TAG_ID);
                    String from = c.getString(TAG_FROM);
                    String subject = c.getString(TAG_SUBJECT);
                    String date = c.getString(TAG_DATE);

                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put(TAG_ID, id);
                    map.put(TAG_FROM, from);
                    map.put(TAG_SUBJECT, subject);
                    map.put(TAG_DATE, date);

                    // adding HashList to ArrayList
                    officialList.add(map);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            OfficialListActivity.this, officialList,
                            R.layout.official_list_item, new String[] { TAG_FROM, TAG_SUBJECT, TAG_DATE },
                            new int[] { R.id.from, R.id.subject, R.id.date });
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }
}
