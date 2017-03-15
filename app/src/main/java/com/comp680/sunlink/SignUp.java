package com.comp680.sunlink;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    private EditText signUpEmail;
    private EditText signUpPassword;
    private TextView signUpEmailError;
    private TextView signUpPasswordError;
    private TextView signUpConfirmPassword;
    private String signUpEmailText;
    private String signUpPasswordText;
    private String signUpConfirmPasswordText;
    private Context ctx;
    private View rootView;
    private Pattern pattern;
    private static final String EMAILPATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "my.csun.edu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ctx = this.getApplicationContext();
        rootView = findViewById(android.R.id.content);

        signUpEmail = (EditText) findViewById(R.id.sign_up_email);
        signUpPassword = (EditText) findViewById(R.id.sign_up_password);
        signUpConfirmPassword = (EditText) findViewById(R.id.sign_up_confirm_password);
        signUpEmailError = (TextView) findViewById(R.id.sign_up_email_error);
        signUpPasswordError = (TextView) findViewById(R.id.sign_up_password_error);

        Button signUpSubmit = (Button) findViewById(R.id.sign_up_submit);

        signUpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pattern = Pattern.compile(EMAILPATTERN);
                signUpEmailText = signUpEmail.getText().toString().trim();
                signUpPasswordText = signUpPassword.getText().toString().trim();
                signUpConfirmPasswordText = signUpConfirmPassword.getText().toString().trim();

                checkValues(signUpPasswordText,signUpConfirmPasswordText,signUpEmailText,ctx,rootView);
            }
        });
    }

    private void checkValues(String signUpPasswordText,String signUpConfirmPasswordText,
                     String signUpEmailText, Context ctx,View rootView){
        boolean checkEmailFormat = false;

        if(signUpEmailText.isEmpty()){
            signUpEmailError.setVisibility(View.VISIBLE);
            signUpEmailError.setText(R.string.empty_email_error_message);
            signUpEmail.setBackgroundResource(R.drawable.error);
        }else {
            checkEmailFormat = checkFormat(signUpEmailText);
            if (!checkEmailFormat) {
                signUpEmailError.setVisibility(View.VISIBLE);
                signUpEmailError.setText(R.string.email_format_error);
                signUpEmail.setBackgroundResource(R.drawable.error);
            } else {
                signUpEmail.setBackgroundResource(R.drawable.no_error);
                signUpEmailError.setVisibility(View.GONE);
            }
        }

        if(signUpPasswordText.isEmpty()){
            signUpPasswordError.setVisibility(View.VISIBLE);
            signUpPasswordError.setText(R.string.empty_password_error_message);
            signUpPassword.setBackgroundResource(R.drawable.error);
        }else{
            signUpPassword.setBackgroundResource(R.drawable.no_error);
            signUpPasswordError.setVisibility(View.GONE);
        }

        if(signUpConfirmPasswordText.isEmpty()){
            signUpPasswordError.setVisibility(View.VISIBLE);
            signUpPasswordError.setText(R.string.empty_password_error_message);
            signUpConfirmPassword.setBackgroundResource(R.drawable.error);
        }else{
            signUpConfirmPassword.setBackgroundResource(R.drawable.no_error);
            signUpPasswordError.setVisibility(View.GONE);
        }

        if(!signUpPasswordText.isEmpty() && !signUpConfirmPasswordText.isEmpty() &&
                !signUpEmailText.isEmpty() && checkEmailFormat){
            if(signUpPasswordText.equals(signUpConfirmPasswordText)){
                signUpEmail.setBackgroundResource(R.drawable.normal);
                signUpPassword.setBackgroundResource(R.drawable.normal);
                String method = "signUp";
                BgTask bgTask = new BgTask(ctx,rootView);
                bgTask.execute(method,signUpEmailText,signUpPasswordText);
            }
            else{
                signUpPasswordError.setVisibility(View.VISIBLE);
                signUpPasswordError.setText(R.string.same_value);
            }
        }
    }

    boolean checkFormat(String emailText){
        Matcher matcher = pattern.matcher(emailText);
        return matcher.matches();
    }
}
