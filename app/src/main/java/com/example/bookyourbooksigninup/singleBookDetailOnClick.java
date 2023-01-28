package com.example.bookyourbooksigninup;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class singleBookDetailOnClick extends AppCompatActivity {

    TextView dOneName, dOneId, dOneCat, dOneCatid, dOnePin, dOneEmail, dOnePhone, dOneCity, dOneOpt;
    ImageView dOnePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_book_detail_on_click);

        dOneName = findViewById(R.id.dOneName);
        dOneId = findViewById(R.id.dOneId);
        dOneCat = findViewById(R.id.dOneCategory);
        dOneCatid = findViewById(R.id.dOneCategoryid);
        dOnePin = findViewById(R.id.dOnePincode);
        dOneEmail = findViewById(R.id.dOneEmail);
        dOnePhone = findViewById(R.id.dOneMobile);
        dOneCity = findViewById(R.id.dOneCity);
        dOneOpt = findViewById(R.id.dOneOptions);
        dOnePic = findViewById(R.id.dOnePic);

        Glide.with(singleBookDetailOnClick.this).load(getIntent().getStringExtra("Picture")).into(dOnePic);
        //   dOnePic.setImageResource(getIntent().getIntExtra("Picture",0));
        dOneName.setText("Name:  " + getIntent().getStringExtra("Name"));
        dOneId.setText("Id:  " + getIntent().getStringExtra("Id"));
        dOneCat.setText("Category:  " + getIntent().getStringExtra("Category"));
        dOneCatid.setText("Category Id:  " + getIntent().getStringExtra("Categoryid"));
        dOnePin.setText("Pincode:  " + getIntent().getStringExtra("Pincode"));
        dOneEmail.setText("Email Id:  " + getIntent().getStringExtra("Email"));
        dOnePhone.setText("Phone:  " + getIntent().getStringExtra("Phone"));
        dOneCity.setText("City:  " + getIntent().getStringExtra("City"));
        dOneOpt.setText("Option:  " + getIntent().getStringExtra("Option"));

    }
}

