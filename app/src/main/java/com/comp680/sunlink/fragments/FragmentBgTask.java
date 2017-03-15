package com.comp680.sunlink.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.ListView;

import com.comp680.sunlink.LOGGER;
import com.comp680.sunlink.R;
import com.comp680.sunlink.search.ItemAdapter;
import com.comp680.sunlink.search.ItemInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

class FragmentBgTask extends AsyncTask<String, Void, String> {
    private Context ctx;
    private ListView listView;
    private String searchKey;
    private static final String UTF8 = "UTF-8";

    FragmentBgTask(Context ctx, ListView rootView) {
        this.ctx = ctx;
        this.listView = rootView;
    }

    @Override
    protected String doInBackground(String... params) {
        searchKey = params[0];
        switch (searchKey) {
            case "jobListing":
                try {
                    return jobListing();
                } catch (IOException e) {
                    LOGGER.info(e);}
                break;
            case "eventListing":
                try {
                    return eventListing();
                } catch (IOException e) {
                    LOGGER.info(e);}
                break;
            default:
                break;
        }
        return null;
    }

    private String jobListing() throws IOException {
        final String searchUrl = ctx.getString(R.string.job_search_url);
        URL url = new URL(searchUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String result;
        while ((result = bufferedReader.readLine()) != null) {
            stringBuilder.append(result).append("\n");
        }
        bufferedReader.close();
        inputStream.close();
        httpURLConnection.disconnect();
        return stringBuilder.toString().trim();
    }

    private String eventListing() throws IOException {
        final String eventUrl = ctx.getString(R.string.event_listing_url);
        URL url = new URL(eventUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        //send request
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        OutputStream outputStream = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, UTF8));
        String data = URLEncoder.encode("eventTitle", UTF8) + "=" + URLEncoder.encode(searchKey, UTF8);
        bufferedWriter.write(data);
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStream.close();
        //get result
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String result;
        while ((result = bufferedReader.readLine()) != null) {
            stringBuilder.append(result).append("\n");
        }
        bufferedReader.close();
        inputStream.close();
        httpURLConnection.disconnect();
        return stringBuilder.toString().trim();
    }


    @Override
    protected void onPostExecute(String finalResult) {
        JSONObject jsonObj;
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        String method = "empty";
        try {
            jsonObj = new JSONObject(finalResult);
            jsonArray = jsonObj.getJSONArray("server_res");
            jsonObject = jsonArray.getJSONObject(0);
            method = jsonObject.getString("result_type");
        } catch (JSONException e) {
            LOGGER.jsonInfo(e);
        }
        switch (method) {
            case "jobListing":
                jobListingOutput(jsonArray);
                break;
            case "eventListing":
                eventListingOutput(jsonArray);
                break;
            default:
                break;
        }

    }

    private void jobListingOutput(JSONArray jsonArray){
        JSONObject jsonObjJobListing;
        try {
            String companyCityName;
            String companyState;
            String companyCountry;
            String jobId;
            String jobTitle;
            String postedDate;
            String companyName;
            String differenceDate;
            String companyId;
            String companyUrl;
            int count = 0;
            ItemAdapter itemAdapter;
            itemAdapter = new ItemAdapter(ctx, R.layout.row_layout);
            listView.setAdapter(itemAdapter);
            while (count < jsonArray.length()) {
                jsonObjJobListing = jsonArray.getJSONObject(count);
                jobTitle = jsonObjJobListing.getString("job_title");
                companyId = jsonObjJobListing.getString("company_id");
                postedDate = jsonObjJobListing.getString("posted_date").trim();
                differenceDate = extractDate(postedDate);
                companyName = jsonObjJobListing.getString("company_name");
                companyCityName = jsonObjJobListing.getString("city_name");
                companyState = jsonObjJobListing.getString("state_name");
                companyCountry = jsonObjJobListing.getString("country_name");
                companyUrl = jsonObjJobListing.getString("company_url");

                jobId = jsonObjJobListing.getString("job_id");
                StringBuilder address = new StringBuilder();
                address.append(companyCityName).append(",");
                if (!companyState.isEmpty()) {
                    address.append(companyState).append(",");

                }
                address.append(companyCountry).append(".");
                String encodedImage = jsonObjJobListing.getString("company_logo");
                byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                Bitmap companyLogo = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                ItemInfo itemInfo = new ItemInfo(jobId, jobTitle, companyName, differenceDate,
                        address.toString(), companyId, companyLogo, companyUrl);
                itemAdapter.add(itemInfo);
                count++;
            }
        } catch (JSONException e) {
            LOGGER.jsonInfo(e);
        }
    }

    private void eventListingOutput(JSONArray jsonArray){
        JSONObject jsonObjEventListing;
        try {
            String eventId;
            String eventTitle;
            String eventLocation;
            String dateStart;
            String eventInfo;
            int count = 0;
            EventAdapter itemAdapter;
            itemAdapter = new EventAdapter(ctx, R.layout.row_layout);
            listView.setAdapter(itemAdapter);
            while (count < jsonArray.length()) {
                jsonObjEventListing = jsonArray.getJSONObject(count);
                eventId = jsonObjEventListing.getString("event_id");
                eventTitle = jsonObjEventListing.getString("event_title");
                eventLocation = jsonObjEventListing.getString("event_location");
                eventInfo = jsonObjEventListing.getString("event_info");
                dateStart = jsonObjEventListing.getString("date_start").trim();
                EventInfo eventI = new EventInfo(eventId, eventTitle, dateStart, eventInfo, eventLocation);
                itemAdapter.add(eventI);
                count++;
            }
        } catch (JSONException e) {
            LOGGER.jsonInfo(e);
        }
    }

    private String extractDate(String postedDate){
        SimpleDateFormat dfDate = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        java.util.Date d = null;
        java.util.Date d1 = null;
        Calendar cal = Calendar.getInstance();
        try {
            d = dfDate.parse(postedDate);
            d1 = dfDate.parse(dfDate.format(cal.getTime()));
        } catch (java.text.ParseException e) {
            LOGGER.parseInfo(e);
        }

        int diffInDays = 0;
        if (d1 != null) {
            diffInDays = (int) ((d1.getTime() - d.getTime()) / (1000 * 60 * 60 * 24));
        }
        if (diffInDays == 0)
            return "Today";
        else
            return Integer.toString(diffInDays) + "d ago";

    }

}
