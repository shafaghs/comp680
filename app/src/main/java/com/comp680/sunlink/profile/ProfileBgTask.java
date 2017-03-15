package com.comp680.sunlink.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

class ProfileBgTask extends AsyncTask<String, Void, String> {
    private Context ctx;
    private View rootView;
    private ListView listView;
    private String searchKey;

    private UserPersonal currUser = new UserPersonal();
    private UserProfessional currPF = new UserProfessional();
    private UserAcademic currAc = new UserAcademic();
    private String currID;


    //Strings for Personal:
    private String first = "null";
    private String last = "null";
    private String middle = "null";
    private String  name = "null";

    private String street1;
    private String street2;
    private String city;
    private String email = "null";
    private String phone = "null";
    private String address = "null";
    private String statusOfJobsSearch = "null";
    private String geoPreference = "null";
    private String workAuth= "null";

    //Strings for professional:
    String pStatement = "null";
    String experience = "null";
    String skills = "null";
    String projects = "null";

    private static final String UTF8 = "UTF-8";

    ProfileBgTask(Context ctx, ListView rootView) {
        this.ctx = ctx;
        this.listView = rootView;
    }

    @Override
    protected String doInBackground(String... params) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        setUserID(pref.getString("user_id",""));
        //PHP FILEs
        final String personalUrl = ctx.getString(R.string.personal_frag_url);
        final String academicUrl = ctx.getString(R.string.academic_frag_url);
        final String professionalUrl = ctx.getString(R.string.professional_frag_url);
        String result;
        searchKey = params[0];
        switch (searchKey) {
            case "academicFragment":
                try {
                    URL url = new URL(academicUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //send request
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, UTF8));
                    String data = URLEncoder.encode("userID", UTF8) + "=" + URLEncoder.encode(this.currID, UTF8) + "&" +
                            URLEncoder.encode("academic", UTF8) + "=" + URLEncoder.encode(searchKey, UTF8);
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

            case "personalFragment":
                try {
                    URL url = new URL(personalUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //send request
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, UTF8));
                    String data = URLEncoder.encode("userID", UTF8) + "=" + URLEncoder.encode(this.currID, UTF8) + "&" +
                                  URLEncoder.encode("personal", UTF8) + "=" + URLEncoder.encode(searchKey, UTF8);
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

            case "professionalFragment":
                try {
                    URL url = new URL(professionalUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //send request
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, UTF8));
                    String data = URLEncoder.encode("userID", UTF8) + "=" + URLEncoder.encode(this.currID, UTF8) + "&" +
                            URLEncoder.encode("professional", UTF8) + "=" + URLEncoder.encode(searchKey, UTF8);
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
            default:
                break;
        }
        return null;

    }

    @Override
    protected void onPostExecute(String finalResult) {
        switch (searchKey) {
            case "academicFragment":
                academicFrag(finalResult);
                break;
            case "personalFragment":
                personalFrag(finalResult);
                break;
            case "professionalFragment":
                try {
                 JSONObject jsonObj = new JSONObject(finalResult);
                    JSONArray jsonArray = jsonObj.getJSONArray("server_res");
                    int count = 0;

                    ProfileInfoAdapter itemAdapter;
                    itemAdapter = new ProfileInfoAdapter(ctx, R.layout.row_layout);


                    while (count < jsonArray.length()) {
                        JSONObject jsonObject = jsonArray.getJSONObject(count);
                        //Personal Statement:
                        currPF.setPS(jsonObject.getString("statement"));
                        ProfileInfo newperstmt = new ProfileInfo("Personal Statement ", currPF.getPS());
                        itemAdapter.add(newperstmt);


                        //Experience:
                        currPF.setEXP(jsonObject.getString("experience"));
                        ProfileInfo exps = new ProfileInfo("Experience ", currPF.getEXP());
                        itemAdapter.add(exps);

                        //Skills:
                        currPF.setSkills(jsonObject.getString("skills"));
                        ProfileInfo skill = new ProfileInfo("Skills ", currPF.getSkills());
                        itemAdapter.add(skill);

                        //Projects:
                        currPF.setProjects(jsonObject.getString("projects"));
                        ProfileInfo pr = new ProfileInfo("Projects ", currPF.getProjects());
                        itemAdapter.add(pr);

                        count++;
                    }
                    listView.setAdapter(itemAdapter);
                    setListViewHeightBasedOnItsChildren(listView);

                } catch (JSONException e) {
                    LOGGER.jsonInfo(e);
                }

                break;
            }

    }

    public String constructNameString(String f, String l, String m) {
        String fullName = null;
        if (f != null)
            fullName = f;
        if ( m != null) {
            if (fullName == null)
                fullName = m;
            else
                fullName += " " + m;

        }
        if (l != null) {
            if(fullName == null)
                fullName = l;
            else {
                fullName += " " + l;
            }
        }
         return fullName;
    }


    //Method to make sure all information is shown for each adapter
    private static void setListViewHeightBasedOnItsChildren(ListView listView) {
        if (listView.getAdapter() == null) {
            // pre-condition adaptershould not be null
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
            View listItem = listView.getAdapter().getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        //set layout params for listview
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listView.getAdapter()
                .getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void academicFrag(String finalResult){
        try {
            JSONObject jsonObj = new JSONObject(finalResult);
            JSONArray jsonArray = jsonObj.getJSONArray("server_res");
            int count = 0;
            ProfileInfoAdapter itemAdapter;
            itemAdapter = new ProfileInfoAdapter(ctx, R.layout.row_layout);
            while (count < jsonArray.length()) {
                JSONObject jsonObject = jsonArray.getJSONObject(count);
                //Major:
                String major = jsonObject.getString("major");
                currAc.setMajor(major);
                if (major != null){
                    ProfileInfo newInfo = new ProfileInfo("Major", major);
                    itemAdapter.add(newInfo);}
                else {
                    ProfileInfo newInfo = new ProfileInfo("Major ", " ");
                    itemAdapter.add(newInfo);}
                //GPA:
                if (jsonObject.getString("gpa") != null) {
                    String gpa = jsonObject.getString("gpa");
                    currAc.setGpa(gpa);
                    ProfileInfo newInfo = new ProfileInfo("GPA ", gpa);
                    itemAdapter.add(newInfo);}
                else {
                    ProfileInfo newInfo = new ProfileInfo("GPA ", " ");
                    itemAdapter.add(newInfo);}

                //Graduation Date:
                if (jsonObject.getString("graduation_date")!= null) {
                    String graduationYear = jsonObject.getString("graduation_date");
                    currAc.setGraduationYear(graduationYear);
                    ProfileInfo newInfo = new ProfileInfo("Graduating in ", graduationYear);
                    itemAdapter.add(newInfo);}
                else {
                    ProfileInfo newInfo = new ProfileInfo("Graduating in ", " ");
                    itemAdapter.add(newInfo);}

                //Graduation Date:
                if (jsonObject.getString("previous_education") != null) {
                    String prevEdu = jsonObject.getString("previous_education");
                    currAc.setPrevEdu(prevEdu);
                    ProfileInfo newInfo = new ProfileInfo("Previous Education ", prevEdu);
                    itemAdapter.add(newInfo);}
                else {
                    ProfileInfo newInfo = new ProfileInfo("Previous Education ", " ");
                    itemAdapter.add(newInfo);}

                //Applicant Type:
                if (jsonObject.getString("a_t_titles") != null) {
                    String apType = jsonObject.getString("a_t_titles");
                    currAc.setAppType(apType);
                    ProfileInfo newInfo = new ProfileInfo("Applicant Type ", apType);
                    itemAdapter.add(newInfo);
                }
                else {
                    ProfileInfo newInfo = new ProfileInfo("Applicant Type ", " ");
                    itemAdapter.add(newInfo);
                }
                // Degree Level
                if (!jsonObject.getString("d_l_title").equals("null")) {
                    String degreeLevel = jsonObject.getString("d_l_title");
                    currAc.setDegreeLevel(degreeLevel);
                    ProfileInfo newInfo = new ProfileInfo("Degree Level ", degreeLevel);
                    itemAdapter.add(newInfo);}
                else {
                    ProfileInfo newInfo = new ProfileInfo("Degree Level ", " ");
                    itemAdapter.add(newInfo);}
                count++;
            }
            listView.setAdapter(itemAdapter);
            setListViewHeightBasedOnItsChildren(listView);
        } catch (JSONException e) {
            LOGGER.jsonInfo(e);
        }

    }

    private void personalFrag(String finalResult){
        try {
            JSONObject jsonObj = new JSONObject(finalResult);
            JSONArray jsonArray = jsonObj.getJSONArray("server_res");
            int count = 0;

            ProfileInfoAdapter itemAdapter;
            itemAdapter = new ProfileInfoAdapter(ctx, R.layout.row_layout);

            while (count < jsonArray.length()) {
                JSONObject jsonObject = jsonArray.getJSONObject(count);
                //Name:
                currUser.setFirstName(jsonObject.getString("first_name"));
                currUser.setLastName(jsonObject.getString("family_name"));
                currUser.setMiddleName(jsonObject.getString("middle_name"));
                this.name = constructNameString(currUser.getFirstName(), currUser.getLastName(), currUser.getMiddleName());
                ProfileInfo newInfoName = new ProfileInfo("Name ", this.name);
                itemAdapter.add(newInfoName);

                //Phone Number:
                currUser.setPhone(jsonObject.getString("user_phone_number"));
                ProfileInfo newInfoPhone = new ProfileInfo("Phone ", currUser.getPhone());
                itemAdapter.add(newInfoPhone);


                currUser.setAddress(jsonObject.getString("street"));
                ProfileInfo newInfoAddress = new ProfileInfo("Street ", currUser.getAddress());
                itemAdapter.add(newInfoAddress);

                currUser.setAddress1(jsonObject.getString("street2"));
                ProfileInfo newInfoAddress1 = new ProfileInfo("Street2 ", currUser.getAddress1());
                itemAdapter.add(newInfoAddress1);

                currUser.setCity(jsonObject.getString("city_name"));
                ProfileInfo newInfoCity = new ProfileInfo("City ", currUser.getCity());
                itemAdapter.add(newInfoCity);

                //Status:
                currUser.setStatus(jsonObject.getString("status_title"));
                ProfileInfo newInfoStatus = new ProfileInfo("Status ", currUser.getStatus());
                itemAdapter.add(newInfoStatus);

                // Geo Preference

                //Work Authorization:
                currUser.setWorkAuth(jsonObject.getString("w_a_title"));
                ProfileInfo newInfo = new ProfileInfo("Work \nAuthorization ", currUser.getWorkAuth());
                itemAdapter.add(newInfo);
                count++;
            }
            listView.setAdapter(itemAdapter);
            setListViewHeightBasedOnItsChildren(listView);
        } catch (JSONException e) {
            LOGGER.jsonInfo(e);
        }

    }

    //Methods to return UserPersonalinfo:
    UserPersonal getUserPersonal(){
        return this.currUser;
    }

    UserProfessional getUserProfessional() {
        return this.currPF;
    }

    UserAcademic getUserAcademic() {
        return this.currAc;
    }

    private void setUserID(String newID) {
        this.currID = newID;
    }
}
