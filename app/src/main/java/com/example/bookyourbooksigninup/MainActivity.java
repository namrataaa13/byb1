package com.example.bookyourbooksigninup;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
Button logout;
    UserManagment userManagment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userManagment = new UserManagment(this);
        userManagment.checkLogin();

        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userManagment.logout();
                Toast.makeText(MainActivity.this, "Log out Successfully!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}