package com.comp680.sunlink.search;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.comp680.sunlink.R;

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

class SearchStartBgTask extends AsyncTask<String, Void, String> {
    private Context ctx;
    private View rootView;
    private String searchKey;

    SearchStartBgTask(Context ctx, View rootView) {
        this.ctx = ctx;
        this.rootView = rootView;
    }

    @Override
    protected String doInBackground(String... params) {
        final String searchUrl = "http://10.0.2.2/CsunSunlink/search.php";
        final String savedJobUrl = "http://10.0.2.2/CsunSunlink/savedJob.php";
        String method, userId, result;
        method = params[0];
        switch (method) {
            case "showSavedJob":
                try {
                    URL url = new URL(savedJobUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //send request
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    userId = params[1];
                    String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8") + "&" +
                                  URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(userId, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    //get result
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((result = bufferedReader.readLine()) != null) {
                        stringBuilder.append(result).append("\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString().trim();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "searchJob":
                searchKey = params[1];
                try {
                    URL url = new URL(searchUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //send request
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("jobTitle", "UTF-8") + "=" + URLEncoder.encode(searchKey, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    //get result
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((result = bufferedReader.readLine()) != null) {
                        stringBuilder.append(result).append("\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString().trim();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        return null;
    }

    @Override
    protected void onPostExecute(String finalResult) {
        String companyCityName, companyState, companyCountry, jobId, companyUrl;
        String jobTitle, postedDate, companyName, companyId;
        ListView listView;
        String differenceDate;
        JSONObject jsonObj;
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        String answerMethod = "empty";
        ItemAdapter itemAdapter;
        int count = 0;
        TextView txt = (TextView) rootView.findViewById(R.id.section_label_tab2);
        TextView txt1 = (TextView) rootView.findViewById(R.id.section_label_tab1);
        try {
            jsonObj = new JSONObject(finalResult);
            jsonArray = jsonObj.getJSONArray("server_res");
            jsonObject = jsonArray.getJSONObject(0);
            answerMethod = jsonObject.getString("result_type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        switch (answerMethod) {
            case "savedJob":
                txt.setText("");
                itemAdapter = new ItemAdapter(ctx, R.layout.row_layout);
                listView = (ListView) rootView.findViewById(R.id.search_result_list_tab2);
                listView.setAdapter(itemAdapter);
                itemAdapter.notifyDataSetChanged();
                while (count < jsonArray.length()) {
                    try {
                        jsonObject = jsonArray.getJSONObject(count);
                        jobTitle = jsonObject.getString("job_title");
                        postedDate = jsonObject.getString("posted_date").trim();
                        SimpleDateFormat dfDate = new SimpleDateFormat("MM/dd/yyyy");
                        java.util.Date d = null;
                        java.util.Date d1 = null;
                        Calendar cal = Calendar.getInstance();
                        try {
                            d = dfDate.parse(postedDate);
                            d1 = dfDate.parse(dfDate.format(cal.getTime()));
                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }

                        assert d1 != null;
                        int diffInDays = (int) ((d1.getTime() - d.getTime()) / (1000 * 60 * 60 * 24));
                        if (diffInDays == 0)
                            differenceDate = "Today";
                        else
                            differenceDate = Integer.toString(diffInDays) + " d";

                        companyName = jsonObject.getString("company_name");
                        companyCityName = jsonObject.getString("city_name");
                        companyState = jsonObject.getString("state_name");
                        companyCountry = jsonObject.getString("country_name");
                        jobId = jsonObject.getString("job_id");
                        companyId = jsonObject.getString("company_id");
                        companyUrl = jsonObject.getString("company_url");

                        StringBuilder address = new StringBuilder();
                        address.append(companyCityName).append(",");
                        if (!companyState.equals("null")) {
                            address.append(companyState).append(",");
                        }
                        address.append(companyCountry).append(".");

                        String encodedImage = jsonObject.getString("company_logo");
                        Bitmap companyLogo;
                        if(!encodedImage.equals("noImage")){
                            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                            companyLogo = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        }else
                            companyLogo = null;

                        ItemInfo itemInfo = new ItemInfo(jobId, jobTitle, companyName, differenceDate,
                                address.toString(), companyId, companyLogo,companyUrl);
                        itemAdapter.add(itemInfo);
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "You did not save any job.":
                txt.setText(answerMethod);
                break;
            case "searchJob":
                txt1.setVisibility(View.VISIBLE);
                String text = "Search result for: "+searchKey;
                int searchKeyLength = searchKey.length();
                int start = 19;
                int end = 19+searchKeyLength;
                Spannable WordtoSpan = new SpannableString(text);
                int color = ctx.getResources().getColor(R.color.colorAccent);
                WordtoSpan.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                txt1.setText(WordtoSpan);
                itemAdapter = new ItemAdapter(ctx, R.layout.row_layout);
                listView = (ListView) rootView.findViewById(R.id.search_result_list_tab1);
                listView.setAdapter(itemAdapter);
                itemAdapter.notifyDataSetChanged();
                while (count < jsonArray.length()) {
                    try {
                        jsonObject = jsonArray.getJSONObject(count);
                        jobTitle = jsonObject.getString("job_title");
                        postedDate = jsonObject.getString("posted_date").trim();
                        SimpleDateFormat dfDate = new SimpleDateFormat("MM/dd/yyyy");
                        java.util.Date d = null;
                        java.util.Date d1 = null;
                        Calendar cal = Calendar.getInstance();
                        try {
                            d = dfDate.parse(postedDate);
                            d1 = dfDate.parse(dfDate.format(cal.getTime()));
                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }

                        assert d1 != null;
                        int diffInDays = (int) ((d1.getTime() - d.getTime()) / (1000 * 60 * 60 * 24));
                        if (diffInDays == 0)
                            differenceDate = "Today";
                        else
                            differenceDate = Integer.toString(diffInDays) + " d";

                        companyName = jsonObject.getString("company_name");
                        companyCityName = jsonObject.getString("city_name");
                        companyState = jsonObject.getString("state_name");
                        companyCountry = jsonObject.getString("country_name");
                        jobId = jsonObject.getString("job_id");
                        companyId = jsonObject.getString("company_id");
                        companyUrl = jsonObject.getString("company_url");
                        StringBuilder address = new StringBuilder();
                        address.append(companyCityName).append(",");
                        if (!companyState.equals("null")) {
                            address.append(companyState).append(",");
                        }
                        address.append(companyCountry).append(".");

                        String encodedImage = jsonObject.getString("company_logo");
                        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                        Bitmap companyLogo = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                        ItemInfo itemInfo = new ItemInfo(jobId, jobTitle, companyName, differenceDate,
                                address.toString(), companyId, companyLogo,companyUrl);
                        itemAdapter.add(itemInfo);
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
