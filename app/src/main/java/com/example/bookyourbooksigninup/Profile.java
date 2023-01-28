package com.example.bookyourbooksigninup;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {
TextView name, email;
ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);

        back=findViewById(R.id.back);

        String mName= "Username:  "+getIntent().getExtras().getString("mName").toString();
        String mEmail=getIntent().getExtras().getString("mEmail").toString();

        name.setText(mName);
        email.setText(mEmail);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
//<TextView
//        android:id="@+id/name"
//                android:layout_width="199dp"
//                android:layout_height="57dp"
//                android:layout_marginTop="500dp"
//                android:background="@drawable/textviewbg"
//                android:gravity="center"
//                android:text="name"
//                android:textSize="20dp"
//                />
//
//<TextView
//        android:id="@+id/email"
//                android:layout_width="185dp"
//                android:layout_height="39dp"
//                android:layout_marginBottom="172dp"
//                android:background="@drawable/textviewbg"
//                android:gravity="center"
//                android:text="Email"
//                android:textSize="20dp"
//                />