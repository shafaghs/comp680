package com.comp680.sunlink;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignIn extends AppCompatActivity {
    private EditText signInUserName;
    private EditText signInPassword;
    private Context ctx;
    private View rootView;
    private TextView errorMessage;
    private String method;
    private String signInUserNameString;
    private String signInPasswordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        rootView = findViewById(android.R.id.content);
        ctx = this.getApplicationContext();

        Button signIn = (Button) findViewById(R.id.signin_signin);
        signInUserName = (EditText) findViewById(R.id.signin_email);
        signInPassword = (EditText) findViewById(R.id.signin_password);
        errorMessage = (TextView) findViewById(R.id.sign_in_error);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           if(checkUserName(signInUserName) && checkPassword(signInPassword)){
                    method = "logIn";
                    signInUserNameString = signInUserName.getText().toString().trim();
                    signInPasswordString = signInPassword.getText().toString().trim();
                    BgTask bgTask = new BgTask(ctx,rootView);
                    bgTask.execute(method,signInUserNameString,signInPasswordString);
                }
            }
        });
    }

    boolean checkUserName(EditText signInUserName){
        if (isEmpty(signInUserName)) {
            errorMessage.setVisibility(View.VISIBLE);
            errorMessage.setText(R.string.fill_email_field);
            signInUserName.setBackgroundResource(R.drawable.error);
            return false;
        }
        return true;
    }

    boolean checkPassword(EditText signInPassword){
        if (isEmpty(signInPassword)) {
            errorMessage.setVisibility(View.VISIBLE);
            errorMessage.setText(R.string.fill_pass_field);
            signInPassword.setBackgroundResource(R.drawable.error);
            return false;
        }
        return true;
    }
    boolean isEmpty(EditText text) {
        return text.getText().toString().trim().length() == 0;
    }
}
