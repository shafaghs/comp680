package com.comp680.sunlink.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.comp680.sunlink.R;

public class PersonalEditActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Values:
    UserPersonal currUser;

    //Edit Text:
    EditText fName;
    EditText mName;
    EditText lName;
    EditText nEmail;
    EditText phone;
    EditText address;
    EditText address1;
    EditText city;
    TextView geoNew;
    TextView statusNew;
    TextView workNew;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currUser = (UserPersonal) getIntent().getSerializableExtra("user");
        setContentView(R.layout.activity_profile_edit_personal);

        //First Name:
        fName = (EditText) findViewById(R.id.edit_firstname_field);
        fName.setText(currUser.getFirstName(), TextView.BufferType.EDITABLE);

        //Middle Name:
        mName = (EditText) findViewById(R.id.edit_middlename_field);
        mName.setText(currUser.getMiddleName(), TextView.BufferType.EDITABLE);

        //Last Name:
        lName = (EditText) findViewById(R.id.edit_lastname_field);
        lName.setText(currUser.getLastName(), TextView.BufferType.EDITABLE);

        //Email

        //Phone:
        phone = (EditText) findViewById(R.id.edit_phone_field);
        phone.setText(currUser.getPhone(), TextView.BufferType.EDITABLE);


        //Address:
        address = (EditText) findViewById(R.id.edit_address_field);
        address.setText(currUser.getAddress(), TextView.BufferType.EDITABLE);

        address1 = (EditText) findViewById(R.id.edit_address1_field);
        address1.setText(currUser.getAddress1(), TextView.BufferType.EDITABLE);

        city = (EditText) findViewById(R.id.edit_city_field);
        city.setText(currUser.getCity(), TextView.BufferType.EDITABLE);

        //Status:
        statusNew = (TextView) findViewById(R.id.status_text_view);
        statusNew.setText(currUser.getStatus());
        Spinner statusspinner = (Spinner) findViewById(R.id.status_spinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.status, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusspinner.setAdapter(adapter1);

        statusspinner.setOnItemSelectedListener(this);

        //Work Authorization:
        workNew = (TextView)findViewById(R.id.work_auth_text_view);
        workNew.setText(currUser.getWorkAuth());
        Spinner workAspinner = (Spinner) findViewById(R.id.workauth_spinner);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.work_auth_array, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workAspinner.setAdapter(adapter3);
        workAspinner.setOnItemSelectedListener(this);


        //Save Button:
        save = (Button) findViewById(R.id.save_personal);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                Intent intent = new Intent(PersonalEditActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    public void save() {
        currUser.setFirstName(fName.getText().toString().trim());
        currUser.setMiddleName(mName.getText().toString().trim());
        currUser.setLastName(lName.getText().toString().trim());
        currUser.setPhone(phone.getText().toString().trim());
        currUser.setAddress(address.getText().toString().trim());
        currUser.setAddress1(address1.getText().toString().trim());
        currUser.setCity(city.getText().toString().trim());
        currUser.setStatus(statusNew.getText().toString().trim());
        ProfileEditBgTask bgTask = new ProfileEditBgTask(getApplicationContext());
        bgTask.setUser(currUser);
        bgTask.execute("editPersonalFragment");
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()) {
            case R.id.status_spinner:
                currUser.setStatus(spinner.getItemAtPosition(pos).toString());
                statusNew.setText(currUser.getStatus());
                break;
            case R.id.workauth_spinner:
                currUser.setWorkAuth(spinner.getItemAtPosition(pos).toString());
                workNew.setText(currUser.getWorkAuth());
                break;
            default:
                break;
        }
    }


    public void onNothingSelected(AdapterView<?> parent) {
    }
}
