package com.comp680.sunlink.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.comp680.sunlink.R;

public class AcademicEditActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    //Values:
    UserAcademic currUser;

    //Edit Text:
    EditText major;
    EditText gpa;
    EditText graduationYear;
    EditText prevEdu;
    EditText degreeLevel;
    TextView aType;
    TextView dLevel;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currUser = (UserAcademic) getIntent().getSerializableExtra("user");
        setContentView(R.layout.activity_profile_edit_academic);

        major = (EditText) findViewById(R.id.edit_major_field);
        major.setText(currUser.getMajor(), TextView.BufferType.EDITABLE);

        gpa = (EditText) findViewById(R.id.edit_gpa_field);
        gpa.setText(currUser.getGpa(), TextView.BufferType.EDITABLE);

        graduationYear = (EditText) findViewById(R.id.edit_grad_year_field);
        graduationYear.setText(currUser.getGraduationYear(), TextView.BufferType.EDITABLE);

        prevEdu = (EditText) findViewById(R.id.edit_prev_degree_field);
        prevEdu.setText(currUser.getPrevEdu(), TextView.BufferType.EDITABLE);


        //Save Button:
        save = (Button) findViewById(R.id.save_academic);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                Intent intent = new Intent(AcademicEditActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        //Applicant type
        aType = (TextView) findViewById(R.id.edit_application_type_field);
        aType.setText(currUser.getApType());
        Spinner appTypeSpinner = (Spinner) findViewById(R.id.application_type_spinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.applicant_type, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appTypeSpinner.setAdapter(adapter1);
        appTypeSpinner.setOnItemSelectedListener(this);

        //Degree Level
        dLevel = (TextView) findViewById(R.id.edit_degree_level_field);
        dLevel.setText(currUser.getDegreeLevel());
        Spinner degreeLevelSpinner = (Spinner) findViewById(R.id.degree_level_spinner);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.degree_level, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        degreeLevelSpinner.setAdapter(adapter3);
        degreeLevelSpinner.setOnItemSelectedListener(this);
    }

    private void save() {
        currUser.setMajor(major.getText().toString().trim());
        currUser.setGpa(gpa.getText().toString().trim());
        currUser.setGraduationYear(graduationYear.getText().toString().trim());
        currUser.setPrevEdu(prevEdu.getText().toString().trim());
        currUser.setAppType(aType.getText().toString().trim());
        currUser.setDegreeLevel(dLevel.getText().toString().trim());
        ProfileEditBgTask bgTask = new ProfileEditBgTask(getApplicationContext());
        bgTask.setAcademic(currUser);
        bgTask.execute("editAcademicFragment");
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()) {
            case R.id.application_type_spinner:
                currUser.setAppType(spinner.getItemAtPosition(pos).toString());
                aType.setText(currUser.getApType());
                break;
            case R.id.degree_level_spinner:
                currUser.setDegreeLevel(spinner.getItemAtPosition(pos).toString());
                dLevel.setText(currUser.getDegreeLevel());
                break;
            default:
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }
}
