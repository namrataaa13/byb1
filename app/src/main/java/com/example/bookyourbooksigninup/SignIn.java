package com.example.bookyourbooksigninup;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class SignIn extends AppCompatActivity {
    TextInputEditText signinEmail, signinPassword;
Button signinBtn;
TextView signinBtnsignup, signinBtnforgetpass;

    boolean passwordVisible;
//DBHelperSignUp dbHelperSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

//        dbHelperSignUp=new DBHelperSignUp(this);

        signinEmail=findViewById(R.id.signinEmail);
        signinPassword=findViewById(R.id.signinPassword);

        signinBtnsignup=findViewById(R.id.signinBtnsignup);

        signinBtnforgetpass = findViewById(R.id.signinBtnforgetpass);

        signinBtn=findViewById(R.id.signinBtn);

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = signinEmail.getText().toString();
                String pass = signinPassword.getText().toString();

                if(email.equals("") || pass.equals("")) {
                    Toast.makeText(SignIn.this, "Please enter credentials!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    String task = "login";
                    BackgroundTask backgroundTask = new BackgroundTask(SignIn.this);

                    signinEmail.setText("");
                    signinPassword.setText("");

                    //execute the task
                    //pases the paras as backgroundTask parameters (para[0], para[1], para[2])

                    backgroundTask.execute(task, email, pass);

                }
            }
        });

        signinBtnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup=new Intent(SignIn.this,SignUp.class);
                startActivity(signup);
            }
        });

        signinBtnforgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgetpassIntent = new Intent(SignIn.this, forgetpass.class);
                startActivity(forgetpassIntent);
            }
        });

        signinPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right=2;
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX() >= signinPassword.getRight() - signinPassword.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = signinPassword.getSelectionEnd();
                        if(passwordVisible){
                            //set drawable image here
                            signinPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24,0);

                            //for hide password
                            signinPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible=false;

                        }else {
                            //set drawable image here
                            signinPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_24,0);

                            //for hide password
                            signinPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=true;
                        }
                        signinPassword.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }
}