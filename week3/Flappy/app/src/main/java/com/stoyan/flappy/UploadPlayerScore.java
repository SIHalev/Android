package com.stoyan.flappy;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class UploadPlayerScore extends AsyncTask<String, Void, String> {
    public static final String SERVER_ADDRESS = "http://95.111.103.224:8080/Flappy/scores";
    private WeakReference<Activity> weakActivity;
    @Override
    protected String doInBackground(String... params) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPut request = new HttpPut(SERVER_ADDRESS);

            request.setHeader("Content-type", "application/json");
            //String data = "{ \"name\" : \"TESTTHREAD\" , \"mail\" : \"phd@mitkoland.eu\" , \"whereFrom\" : \"Tijuana\" , \"score\" : " + params[0] + " }";
            String data = "{ \"name\" : \""+ params[0] +"\", \"mail\" : \""+ params[1] +"\" , \"whereFrom\" : \"" + params[2] + "\" , \"score\" : " + params[3] + " }";
            //Log.v("DATAAAAAAAAAAA: ", data);
            request.setEntity(new StringEntity(data));

            HttpResponse response = httpClient.execute(request);
            //Log.v("RESPOND: ", IOUtil.toString(response.getEntity().getContent()));

            //out.close();
            return IOUtil.toString(response.getEntity().getContent());


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Activity activity = weakActivity.get();
        if (activity != null) {

            // do your stuff with activity here
        }
    }

    public void setWeekActivity(Activity activity) {

        this.weakActivity = new WeakReference<Activity>(activity);
    }


    //onPostExecute -> pastva go kum opa6kata na UI threada. - ka4ih skora.
    //onPorgresUpdated
}
