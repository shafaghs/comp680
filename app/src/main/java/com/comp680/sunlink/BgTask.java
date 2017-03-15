package com.comp680.sunlink;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.comp680.sunlink.profile.UserPersonal;

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

class BgTask extends AsyncTask<String, Void, String> {
    private Context ctx;
    private View rootView;
    private String userEmail;
    private String userPass;
    private String firstName;
    private String familyName;
    private static final String UTF8 = "UTF-8";
    private static final String FIRSTNAMECON = "first_name";
    private static final String FAMILYNAMECON = "family_name";
    private static final String EMAILCON = "user_email";
    private static final String USERID ="user_id";
    private Intent intent;
    private SharedPreferences pref;

    BgTask(Context ctx, View rootView) {
        this.ctx = ctx;
        this.rootView = rootView;
    }

    @Override
    protected String doInBackground(String... params){
        String method;
        pref = PreferenceManager.getDefaultSharedPreferences(ctx.getApplicationContext());
        method = params[0];
        switch (method) {
            case "signUp":
                try {
                    return signUp(params);}
                catch (IOException e) {
                    LOGGER.info(e);}
                break;
            case "register":
                try {
                    return register(params);}
                catch (IOException e) {
                    LOGGER.info(e);}
                break;
            case "logIn":
                try {
                    return logIn(params);}
                catch (IOException e) {
                    LOGGER.info(e);}
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
        String method = "empty";
        JSONObject jsonObj;
        JSONObject jsonObject=null;
        JSONArray jsonArray;
        try {
            jsonObj = new JSONObject(result);
            jsonArray = jsonObj.getJSONArray("server_res");
            jsonObject = jsonArray.getJSONObject(0);
            method = jsonObject.getString("result_type");
        } catch (JSONException e) {
            LOGGER.jsonInfo(e);
        }
        switch (method) {
            case "redundantUser":
                TextView signUpEmailError = (TextView) rootView.findViewById(R.id.sign_up_email_error);
                signUpEmailError.setVisibility(View.VISIBLE);
                signUpEmailError.setText(R.string.redundant_email);
                break;
            case "validEmail":
                validEmail();
                break;
            case "registeredSuccessfully":
                registeredSuccessfully(jsonObject);
                break;
            case "invalidUser":
                TextView errorText = (TextView) rootView.findViewById(R.id.sign_in_error);
                errorText.setVisibility(View.VISIBLE);
                errorText.setText(R.string.invalid_userPass);
                break;
            case "validUser":
                validUser(jsonObject);
                break;
            case "empty":
                Log.w("invalid", "invalid query");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPreExecute() {
        //empty
    }

    private String signUp(String... params) throws IOException {
        userEmail = params[1];
        userPass = params[2];
        final String signUpUrl = ctx.getString(R.string.sign_up_url);
        URL url = new URL(signUpUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
        String data = URLEncoder.encode(EMAILCON, UTF8) + "=" + URLEncoder.encode(userEmail, UTF8);
        String output = getResult(urlConnection,data);
        urlConnection.disconnect();
        return output;
    }

    private String register(String... params) throws IOException {
        final String registerUrl = ctx.getString(R.string.register_url);
        userEmail = params[1];
        userPass = params[2];
        firstName = params[3];
        familyName = params[4];
        String middleName = params[5];
        URL url = new URL(registerUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString();
        String data = URLEncoder.encode("user_email", UTF8) + "=" + URLEncoder.encode(userEmail, UTF8) + "&" +
                URLEncoder.encode("userPass", UTF8) + "=" + URLEncoder.encode(userPass, UTF8) + "&" +
                URLEncoder.encode(FIRSTNAMECON, UTF8) + "=" + URLEncoder.encode(firstName, UTF8) + "&" +
                URLEncoder.encode(FAMILYNAMECON, UTF8) + "=" + URLEncoder.encode(familyName, UTF8) + "&" +
                URLEncoder.encode("middleName", UTF8) + "=" + URLEncoder.encode(middleName, UTF8) + "&" +
                URLEncoder.encode("date", UTF8) + "=" + URLEncoder.encode(date, UTF8);
        String output = getResult(urlConnection,data);
        urlConnection.disconnect();
        return output;
    }

    private String logIn(String... params) throws IOException {
        final String loginUrl = ctx.getString(R.string.login_url);
        userEmail = params[1];
        userPass = params[2];
        URL url = new URL(loginUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
        String data = URLEncoder.encode("userName", UTF8) + "=" + URLEncoder.encode(userEmail, UTF8) + "&" +
                URLEncoder.encode("userPass", UTF8) + "=" + URLEncoder.encode(userPass, UTF8);
        String output = getResult(urlConnection,data);
        urlConnection.disconnect();
        return output;
    }

    private String getResult(HttpURLConnection urlConnection,String data) throws IOException{
        OutputStream os = urlConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, UTF8));
        bufferedWriter.write(data);
        bufferedWriter.flush();
        bufferedWriter.close();
        os.close();
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String result;
        while ((result = bufferedReader.readLine()) != null) {
            stringBuilder.append(result).append("\n");
        }
        bufferedReader.close();
        inputStream.close();
        return stringBuilder.toString().trim();
    }

    private void validEmail(){
        intent = new Intent(ctx, SignUpContinue.class);
        intent.putExtra("EmailAddress", userEmail);
        intent.putExtra("Password",userPass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }

    private void registeredSuccessfully(JSONObject jsonObject){
        SharedPreferences.Editor editor = pref.edit();
        try {
            String userId=jsonObject.getString(USERID);
            editor.putString(USERID, userId);
        } catch (JSONException e) {
            LOGGER.jsonInfo(e);
        }
        intent = new Intent(ctx, HomePage.class);
        intent.putExtra("EmailAddress", userEmail);
        intent.putExtra("NewUser","Yes");
        editor.putString(EMAILCON, userEmail);
        editor.putString(FIRSTNAMECON, firstName);
        editor.putString(FAMILYNAMECON, familyName);
        editor.apply();
        UserPersonal up = new UserPersonal();
        up.setFirstName(firstName);
        up.setLastName(familyName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }

    private void validUser(JSONObject jsonObject){
        SharedPreferences.Editor editor = pref.edit();
        intent = new Intent(ctx, HomePage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            firstName = jsonObject.getString("first_name");
            familyName = jsonObject.getString(FAMILYNAMECON);
            String email = jsonObject.getString(EMAILCON);
            String userId = jsonObject.getString(USERID) ;
            editor.putString(EMAILCON, email);
            editor.putString(FIRSTNAMECON, firstName);
            editor.putString(FAMILYNAMECON, familyName);
            editor.putString(USERID, userId);
            editor.apply();
        } catch (JSONException e) {
            LOGGER.jsonInfo(e);
        }
        editor.apply();
        ctx.startActivity(intent);
    }
}
