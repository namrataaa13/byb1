package com.example.bookyourbooksigninup;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.StringRequest;

public class forgetpass extends AppCompatActivity {
EditText emailId, userName;
Button send;
StringRequest stringRequest;
String URL = "https://bookyourbook000webhost.000webhostapp.com/LoginAndRegister-forgetpassword.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);

        emailId=findViewById(R.id.emailId);
        userName = findViewById(R.id.userName);

        send=findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=userName.getText().toString();
                String email=emailId.getText().toString();

                if(email.equals("") || name.equals("")) {
                    Toast.makeText(forgetpass.this, "All fields required!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    String task = "forgetpassword";
                    BackgroundTask backgroundTask = new BackgroundTask(forgetpass.this);

                    emailId.setText("");
                    userName.setText("");

                    //execute the task
                    //pases the paras as backgroundTask parameters (para[0], para[1], para[2])
                    backgroundTask.execute(task, name, email);

                }


//                stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        if(response.equals("SUCCESS")){
//                            Toast.makeText(forgetpass.this, "Email successfully sent please check your mail inbox. ", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            Toast.makeText(forgetpass.this, "Failed !! ", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                },new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(forgetpass.this, "Please check connection!!", Toast.LENGTH_SHORT).show();
//                    }
//                }){
//                    protected Map<String, String> getParams() throws AuthFailureError{
//                        Map<String,String> params = new HashMap<>();
//                        params.put("name",name);
//                        params.put("email",email);
//
//                        return super.getParams();
//                    }
//                };
            }

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}