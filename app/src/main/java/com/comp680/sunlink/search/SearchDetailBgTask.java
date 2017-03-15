package com.comp680.sunlink.search;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.comp680.sunlink.LOGGER;
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

class SearchDetailBgTask extends AsyncTask<String, Void, String> {
    private View rootView;
    private String address, differenceDate;
    private Context ctx;

    SearchDetailBgTask(Context ctx, View rootView) {
        this.ctx = ctx;
        this.rootView = rootView;
    }

    @Override
    protected String doInBackground(String... params) {
        final String searchDetailUrl = ctx.getString(R.string.search_detail_url);
        String result, jobIdKey, method, userId;
        method = params[0];
        switch (method) {
            case "showJobDetail":
                jobIdKey = params[1];
                userId = params[2];
                address = params[3];
                differenceDate = params[4];
                try {
                    URL url = new URL(searchDetailUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //send request
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8") + "&" +
                            URLEncoder.encode("jobIdKey", "UTF-8") + "=" + URLEncoder.encode(jobIdKey, "UTF-8") + "&" +
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
                    LOGGER.info(e);
                }
                break;
            case "saveJob":
                jobIdKey = params[1];
                userId = params[2];
                try {
                    URL url = new URL(searchDetailUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //send request
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8") + "&" +
                            URLEncoder.encode("jobIdKey", "UTF-8") + "=" + URLEncoder.encode(jobIdKey, "UTF-8") + "&" +
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
                    LOGGER.info(e);
                }
                break;
            case "unSaveJob":
                jobIdKey = params[1];
                userId = params[2];
                try {
                    URL url = new URL(searchDetailUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //send request
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8") + "&" +
                            URLEncoder.encode("jobIdKey", "UTF-8") + "=" + URLEncoder.encode(jobIdKey, "UTF-8") + "&" +
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
                    LOGGER.info(e);
                }
                break;
        }
        return null;
    }

    @Override
    protected void onPostExecute(String finalResult) {
        TextView jobTitleTextView, companyNameTextView, positionTypeTextView, companyAddTextView,
                postedDateTextView, jobDesTextView, jobDutiesTextView, essentialSkillsTextView, desiredSkillsTextView;
        JSONObject jsonObj;
        JSONArray jsonArray;
        JSONObject jsonObject = null;
        String answerMethod = "empty";
        Button saveJob = (Button) rootView.findViewById(R.id.search_detail_save_button);
        try {
            jsonObj = new JSONObject(finalResult);
            jsonArray = jsonObj.getJSONArray("server_res");
            jsonObject = jsonArray.getJSONObject(0);
            answerMethod = jsonObject.getString("result_type");
        } catch (JSONException e) {
            LOGGER.jsonInfo(e);
        }

        switch (answerMethod) {
            case "jobDetail":
                jobTitleTextView = (TextView) rootView.findViewById(R.id.search_detail_job_title);
                companyNameTextView = (TextView) rootView.findViewById(R.id.search_detail_company_name);
                positionTypeTextView = (TextView) rootView.findViewById(R.id.search_detail_position_type);
                companyAddTextView = (TextView) rootView.findViewById(R.id.search_detail_company_add);
                postedDateTextView = (TextView) rootView.findViewById(R.id.search_detail_posted_date);
                jobDesTextView = (TextView) rootView.findViewById(R.id.search_detail_job_des_detail);
                jobDutiesTextView = (TextView) rootView.findViewById(R.id.search_detail_job_duty_detail);
                essentialSkillsTextView = (TextView) rootView.findViewById(R.id.search_detail_ess_detail);
                desiredSkillsTextView = (TextView) rootView.findViewById(R.id.search_detail_des_detail);
                ImageView imageView = (ImageView) rootView.findViewById(R.id.company_logo);
                try {
                    assert jsonObject != null;
                    jobTitleTextView.setText(jsonObject.getString("job_title"));
                    companyNameTextView.setText(jsonObject.getString("company_name"));
                    positionTypeTextView.setText("Full time");
                    companyAddTextView.setText(address);
                    if (!differenceDate.equals("Today")){
                        String newDifferenceDate = differenceDate.replaceAll("[\\D]", "")+" days ago";
                        postedDateTextView.setText(newDifferenceDate);
                    }
                    else
                        postedDateTextView.setText(differenceDate);
                    jobDesTextView.setText(jsonObject.getString("job_summary"));

                    String duty = jsonObject.getString("job_duties");
                    if (!duty.equals("0")) {
                        rootView.findViewById(R.id.search_detail_job_duty).setVisibility(View.VISIBLE);
                        String replacedDuty = duty.replace("*", "\u2022");
                        jobDutiesTextView.setVisibility(View.VISIBLE);
                        jobDutiesTextView.setText(replacedDuty);
                    }

                    String essentialSkills = jsonObject.getString("essential_skills");
                    if (!essentialSkills.equals("0")) {
                        rootView.findViewById(R.id.search_detail_essential_skills).setVisibility(View.VISIBLE);
                        String replacedEssentialSkills = essentialSkills.replace("*", "\u2022");
                        essentialSkillsTextView.setVisibility(View.VISIBLE);
                        essentialSkillsTextView.setText(replacedEssentialSkills);
                    }

                    String desiredSkills = jsonObject.getString("desired_skills");
                    if (!desiredSkills.equals("0")) {
                        rootView.findViewById(R.id.search_detail_desired_skills).setVisibility(View.VISIBLE);
                        String replacedDesiredSkills = desiredSkills.replace("*", "\u2022");
                        desiredSkillsTextView.setVisibility(View.VISIBLE);
                        desiredSkillsTextView.setText(replacedDesiredSkills);
                    }

                    String savedJob = jsonObject.getString("saved_job");
                    if (savedJob.equals("alreadySaved")) {
                        saveJob.setText(R.string.un_save);
                    } else if (savedJob.equals("notSavedBefore")) {
                        saveJob.setText(R.string.save_to_favorit);
                    }

                    String encodedImage = jsonObject.getString("company_logo");
                    if (encodedImage != null) {
                        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                        Bitmap companyLogo = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imageView.setImageBitmap(companyLogo);
                    }
                } catch (JSONException e) {
                    LOGGER.jsonInfo(e);
                }
                break;
            case "deletedSuccessfully":
                saveJob.setText(R.string.save_to_favorit);
                break;
            case "savedSuccessfully":
                saveJob.setText(R.string.un_save);
                break;
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
