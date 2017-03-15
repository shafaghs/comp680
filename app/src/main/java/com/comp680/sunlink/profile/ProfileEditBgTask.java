package com.comp680.sunlink.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.comp680.sunlink.HomePage;
import com.comp680.sunlink.LOGGER;
import com.comp680.sunlink.R;

import org.json.JSONException;

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

class ProfileEditBgTask extends AsyncTask<String, Void, String> {
    private Context ctx;

    private UserPersonal currUser;
    private UserProfessional pfUser;
    private UserAcademic paUser;

    private static final String UTF8 = "UTF-8";

    ProfileEditBgTask(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String searchKey = params[0];
        switch (searchKey) {
            case "editPersonalFragment":
                try {
                    return editPersonal();
                } catch (IOException e) {
                    LOGGER.info(e);}
                break;
            case "editAcademicFragment":
                try {
                    return editAcademic();
                } catch (IOException e) {
                    LOGGER.info(e);}
                break;
            case "editProfessional":
                try {
                    return editProfessional();
                } catch (IOException e) {
                    LOGGER.info(e);}
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    protected void onPostExecute(String finalResult) {
        //Log.w("result", finalResult);
    }

    void setUser(UserPersonal newUser) {
        this.currUser = newUser;
    }

    void setProfessional(UserProfessional newUser) {
        this.pfUser = newUser;
    }

    void setAcademic(UserAcademic newUser) {
        this.paUser = newUser;
    }

    private String editPersonal() throws IOException {
        final String personalEditUrl = ctx.getString(R.string.edit_personal_profile);
        URL url = new URL(personalEditUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
        String s;
        switch (currUser.getStatus()){
            case "Seeking Employment":
                s = "1";
                break;
            case "Not Seeking Employment":
                s = "2";
                break;
            default:
                s= "1";
                break;
        }
        String data = URLEncoder.encode("userID", UTF8) + "=" + URLEncoder.encode(currUser.getID(), UTF8) + "&" +
                URLEncoder.encode("firstName", UTF8) + "=" + URLEncoder.encode(currUser.getFirstName(), UTF8) + "&" +
                URLEncoder.encode("lastName", UTF8) + "=" + URLEncoder.encode(currUser.getLastName(), UTF8) + "&" +
                URLEncoder.encode("middleName", UTF8) + "=" + URLEncoder.encode(currUser.getMiddleName(), UTF8) + "&" +
                URLEncoder.encode("address", UTF8) + "=" + URLEncoder.encode(currUser.getAddress(), UTF8) + "&" +
                URLEncoder.encode("address1", UTF8) + "=" + URLEncoder.encode(currUser.getAddress1(), UTF8) + "&" +
                URLEncoder.encode("city", UTF8) + "=" + URLEncoder.encode(currUser.getCity(), UTF8) + "&" +
                URLEncoder.encode("status", UTF8) + "=" + URLEncoder.encode(s, UTF8) + "&" +
                URLEncoder.encode("workAuto", UTF8) + "=" + URLEncoder.encode(currUser.getWorkAuth(), UTF8) + "&" +
                URLEncoder.encode("phone", UTF8) + "= " + URLEncoder.encode(currUser.getPhone(), UTF8);
        Log.w("shafagh",data);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx.getApplicationContext());
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("first_name",currUser.getFirstName());
        editor.putString("family_name",currUser.getLastName());
        editor.apply();
        String output = getResult(urlConnection, data);
        urlConnection.disconnect();
        return output;
    }

    private String editAcademic() throws IOException {
        final String academicEditUrl = ctx.getString(R.string.edit_academic_profile);
        URL url = new URL(academicEditUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
        String data = URLEncoder.encode("userID", UTF8) + "=" + URLEncoder.encode(paUser.getID(), UTF8) + "&" +
                URLEncoder.encode("major", UTF8) + "=" + URLEncoder.encode(paUser.getMajor(), UTF8) + "&" +
                URLEncoder.encode("gpa", UTF8) + "=" + URLEncoder.encode(paUser.getGpa(), UTF8) + "&" +
                URLEncoder.encode("appType", UTF8) + "=" + URLEncoder.encode(paUser.getApType(), UTF8) + "&" +
                URLEncoder.encode("degreeLevel", UTF8) + "=" + URLEncoder.encode(paUser.getDegreeLevel(), UTF8) + "&" +
                URLEncoder.encode("gradYear", UTF8) + "=" + URLEncoder.encode(paUser.getGraduationYear(), UTF8) + "&" +
                URLEncoder.encode("prevEdu", UTF8) + "= " + URLEncoder.encode(paUser.getPrevEdu(), UTF8);
        String output = getResult(urlConnection, data);
        urlConnection.disconnect();
        return output;
    }

    private String editProfessional() throws IOException {
        final String professionalEditUrl = ctx.getString(R.string.edit_professional_profile);
        URL url = new URL(professionalEditUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
        String data = URLEncoder.encode("userID", UTF8) + "=" + URLEncoder.encode(pfUser.getID(), UTF8) + "&" +
                URLEncoder.encode("ps", UTF8) + "=" + URLEncoder.encode(pfUser.getPS(), UTF8) + "&" +
                URLEncoder.encode("exp", UTF8) + "=" + URLEncoder.encode(pfUser.getEXP(), UTF8) + "&" +
                URLEncoder.encode("skills", UTF8) + "=" + URLEncoder.encode(pfUser.getSkills(), UTF8) + "&" +
                URLEncoder.encode("projects", UTF8) + "= " + URLEncoder.encode(pfUser.getProjects(), UTF8);
        String output = getResult(urlConnection, data);
        urlConnection.disconnect();
        return output;
    }

    private String getResult(HttpURLConnection urlConnection, String data) throws IOException {
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
        Log.w("spinner",stringBuilder.toString().trim());
        return stringBuilder.toString().trim();
    }
}
