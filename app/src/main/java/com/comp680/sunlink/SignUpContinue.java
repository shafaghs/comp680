package com.comp680.sunlink;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.comp680.sunlink.mail.MailSender;

public class SignUpContinue extends AppCompatActivity {
    String firstNameText;
    String familyNameText;
    String middleNameText;
    String signUpEmailText;
    String signUpPasswordText;
    TextView error;
    EditText firstName;
    EditText familyName;
    EditText middleName;
    private Context ctx;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_continue);

        rootView = findViewById(android.R.id.content);
        ctx = this.getApplicationContext();

        Bundle extras = getIntent().getExtras();

        TextView email = (TextView) findViewById(R.id.sign_up_con_email);
        if (extras != null) {
            email.setText(extras.getString("EmailAddress"));
            signUpEmailText = extras.getString("EmailAddress");
            signUpPasswordText = extras.getString("Password");
        }

        Button submitButton = (Button) findViewById(R.id.sign_up_con_submit);
        firstName = (EditText) findViewById(R.id.sign_up_con_first_name);
        familyName = (EditText) findViewById(R.id.sign_up_con_family_name);
        middleName = (EditText) findViewById(R.id.sign_up_con_middle_name);
        error = (TextView) findViewById(R.id.sign_up_con_error);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstNameText = firstName.getText().toString().trim();
                familyNameText = familyName.getText().toString().trim();
                middleNameText = middleName.getText().toString().trim();

                if (firstNameText.isEmpty() || familyNameText.isEmpty()) {
                    error.setVisibility(View.VISIBLE);
                    error.setText(R.string.mandatory_field);
                    firstName.setBackgroundResource(R.drawable.error);
                    familyName.setBackgroundResource(R.drawable.error);
                } else {
                    if(middleNameText.isEmpty()){
                        middleNameText = "0";
                    }
                    String method = "register";
                    new MailSender(signUpEmailText);
                    BgTask bgTask = new BgTask(ctx, rootView);
                    bgTask.execute(method, signUpEmailText, signUpPasswordText,firstNameText,familyNameText,middleNameText);
                }
            }
        });
    }
}
