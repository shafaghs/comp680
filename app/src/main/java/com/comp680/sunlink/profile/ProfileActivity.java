package com.comp680.sunlink.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.comp680.sunlink.R;

public class ProfileActivity extends AppCompatActivity {

    String currID;
    //Fragments
    FragmentTransaction transaction;
    ProfilePersonalFragment personalF;
    ProfileProfessionalFragment profF;
    ProfileAcademicFragment academicF;

    UserPersonal currPersonal;
    UserProfessional currPF;
    UserAcademic currPA;

    View profileView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        profileView = findViewById(android.R.id.content);

        //Fragments:
        personalF = new ProfilePersonalFragment();
        academicF = new ProfileAcademicFragment();
        profF = new ProfileProfessionalFragment();

        FragmentManager manager=getSupportFragmentManager();//create an instance of fragment manager
        transaction=manager.beginTransaction();//create an instance of Fragment-transaction

        transaction.add(R.id.personal_frame_layout, personalF, "Personal");
        transaction.add(R.id.academic_frame_layout,academicF, "Academic");
        transaction.add(R.id.professional_frame_layout, profF, "Professional");
        transaction.commit();

        //TOP BAR:
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String fName = pref.getString("first_name","");
        String lName = pref.getString("family_name","");
        String name = fName + " " +lName;
        String email = pref.getString("user_email","");
        currID = pref.getString("user_id","");
        TextView newName = (TextView) findViewById(R.id.header_name_profile);
        newName.setText(name);
        TextView newEmail = (TextView) findViewById(R.id.header_degree_profile);
        newEmail.setText(email);

        //Edit Buttons
        TextView editPersonal =(TextView)findViewById(R.id.personal_edit);
        editPersonal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editPersonal();
                }

        });

        TextView editAcademic =(TextView)findViewById(R.id.academic_edit);
        editAcademic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAcademic();
            }

        });

        TextView editProfessional =(TextView)findViewById(R.id.professional_edit);
        editProfessional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfessional();
            }

        });
    }

    public void editPersonal() {
        currPersonal = personalF.getUserCurr();
        currPersonal.setUserID(currID);
        Intent intent = new Intent(ProfileActivity.this, PersonalEditActivity.class);
        intent.putExtra("user", currPersonal);
        startActivity(intent);
    }

    public void editAcademic() {
        currPA = academicF.getUserCurrAc();
        currPA.setUserID(currID);
        Intent intent = new Intent(ProfileActivity.this, AcademicEditActivity.class);
        intent.putExtra("user", currPA);
        startActivity(intent);
    }

    public void editProfessional() {
        currPF = profF.getUserCurrPF();
        currPF.setUserID(currID);
        Intent intent = new Intent(ProfileActivity.this, ProfessionalEditActivity.class);
        intent.putExtra("user", currPF);
        startActivity(intent);
    }
}