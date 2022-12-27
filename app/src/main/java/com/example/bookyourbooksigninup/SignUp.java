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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    TextInputEditText signupUsername, signupEmail, signupEnPassword, signupReEnPassword;
    Button signupBtn;
    TextView signupBtnsignin;
    String name, email, password, repassword;
boolean passwordVisible, passwordReVisible;
//    DBHelperSignUp dbHelperSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

//        dbHelperSignUp=new DBHelperSignUp(this);

        signupUsername = findViewById(R.id.signupUsername);
        signupEmail = findViewById(R.id.signupEmail);
        signupEnPassword = findViewById(R.id.signupEnPassword);
        signupReEnPassword = findViewById(R.id.signupReEnPassword);

        signupBtnsignin = findViewById(R.id.signupBtnsignIn);

        signupBtn = findViewById(R.id.signupBtn);

        name = signupUsername.getText().toString();
        email = signupEmail.getText().toString();
        password = signupEnPassword.getText().toString();
        repassword = signupReEnPassword.getText().toString();

        signupBtnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, com.example.bookyourbooksigninup.SignIn.class);
                startActivity(intent);
            }
        });

        signupEnPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right=2;
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX() >= signupEnPassword.getRight() - signupEnPassword.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = signupEnPassword.getSelectionEnd();
                        if(passwordVisible){
                            //set drawable image here
                            signupEnPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24,0);

                            //for hide password
                            signupEnPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible=false;

                        }else {
                            //set drawable image here
                            signupEnPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_24,0);

                            //for hide password
                            signupEnPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=true;
                        }
                        signupEnPassword.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        signupReEnPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right=2;
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX() >= signupReEnPassword.getRight() - signupReEnPassword.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = signupReEnPassword.getSelectionEnd();
                        if(passwordReVisible){
                            //set drawable image here
                            signupReEnPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24,0);

                            //for hide password
                            signupReEnPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordReVisible=false;

                        }else {
                            //set drawable image here
                            signupReEnPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_24,0);

                            //for hide password
                            signupReEnPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordReVisible=true;
                        }
                        signupReEnPassword.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = signupUsername.getText().toString();
                email = signupEmail.getText().toString();
                password = signupEnPassword.getText().toString();
                repassword = signupReEnPassword.getText().toString();
                Pattern pattern1 = Pattern.compile( "^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+");

                Matcher matcher1 = pattern1.matcher(email);

                if (signupUsername.getText().toString().equals("")) {
                    Toast.makeText(SignUp.this, "Enter username!!", Toast.LENGTH_SHORT).show();
                } else if (signupEmail.getText().toString().equals("")) {
                    Toast.makeText(SignUp.this, "Enter email!!", Toast.LENGTH_SHORT).show();
                }
                else if (!matcher1.matches()) {
                    Toast.makeText(SignUp.this, "Invalid Email ID!!", Toast.LENGTH_SHORT).show();
                }
                else if (signupEnPassword.getText().toString().equals("")) {
                    Toast.makeText(SignUp.this, "Enter password!!", Toast.LENGTH_SHORT).show();
                } else if (signupReEnPassword.getText().toString().equals("")) {
                    Toast.makeText(SignUp.this, "Re-enter password!!", Toast.LENGTH_SHORT).show();
                } else if (!signupEnPassword.getText().toString().equals(signupReEnPassword.getText().toString())) {
                    Toast.makeText(SignUp.this, "Password Mis-match!!", Toast.LENGTH_SHORT).show();
                } else {
                    String task = "register";
                    com.example.bookyourbooksigninup.BackgroundTask backgroundTask = new com.example.bookyourbooksigninup.BackgroundTask(SignUp.this);

                    //execute the task
                    //pases the paras as backgroundTask parameters (para[0], para[1], para[2])

                    backgroundTask.execute(task, name, email, password);
                    finish();
                }
            }
        });
    }

//    private void register(HashMap<String, String> params) {
//
//        final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
//        progressDialog.setTitle("Please wait");
//        progressDialog.setMessage("Registering...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//
//        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
//        Call<RegistrationResponseModel> registerCall = networkService.register(params);
//        registerCall.enqueue(new Callback<RegistrationResponseModel>() {
//            @Override
//            public void onResponse(@NonNull Call<RegistrationResponseModel> call, @NonNull Response<RegistrationResponseModel> response) {
//                RegistrationResponseModel responseBody = response.body();
//                if (responseBody != null) {
//                    if (responseBody.getSuccess().equals("1")) {
//                        Toast.makeText(SignUp.this, responseBody.getMessage(), Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(SignUp.this, SignIn.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        Log.d(TAG, "onResponse: "+responseBody.getMessage());
//                        Toast.makeText(SignUp.this, responseBody.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<RegistrationResponseModel> call, @NonNull Throwable t) {
//                progressDialog.dismiss();
//            }
//        });
//    }
}