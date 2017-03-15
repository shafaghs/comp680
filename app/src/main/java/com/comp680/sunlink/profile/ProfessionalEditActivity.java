package com.comp680.sunlink.profile;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.comp680.sunlink.R;

public class ProfessionalEditActivity extends AppCompatActivity {

    //Values:
    UserProfessional currUser;

    //Edit Text:
    EditText ps;
    EditText exp;
    EditText skills;
    EditText projects;

    Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currUser = (UserProfessional) getIntent().getSerializableExtra("user");
        setContentView(R.layout.activity_profile_edit_professional);

        //Personal Statmenet:
        ps = (EditText) findViewById(R.id.edit_ps_field);
        ps.setText(currUser.getPS(), TextView.BufferType.EDITABLE);

        //Experience:
        exp = (EditText) findViewById(R.id.edit_exp_field);
        exp.setText(currUser.getEXP(), TextView.BufferType.EDITABLE);

        //Skills:
        skills = (EditText) findViewById(R.id.edit_skills_field);
        skills.setText(currUser.getSkills(), TextView.BufferType.EDITABLE);

        //Projects:
        projects= (EditText) findViewById(R.id.edit_projects_field);
        projects.setText(currUser.getProjects(), TextView.BufferType.EDITABLE);


        //Save Button:
        save = (Button) findViewById(R.id.save_proff);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                Intent intent = new Intent(ProfessionalEditActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    public void save() {

        currUser.setPS(ps.getText().toString());
        currUser.setEXP(exp.getText().toString());
        currUser.setProjects(projects.getText().toString());
        currUser.setSkills(skills.getText().toString());
        ProfileEditBgTask bgTask = new ProfileEditBgTask(getApplicationContext());
        bgTask.setProfessional(currUser);
        bgTask.execute("editProfessional");


    }
}

