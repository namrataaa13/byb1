package com.example.bookyourbooksigninup;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class insertData extends AppCompatActivity {
    public ImageView bookPic;
    public EditText bookName, bookId, bookCategoryid, bookPincode, bookPhone, bookCity;
    TextView bookEmail;
    Button bookUploadbtn;
    int flagbkpic = 0;
    ProgressDialog mProgressDialog;

    String email;
    Spinner bookOptionsBar;
    int bookOptPosition;
    String[] bookOpt = {"Select Book options", "Sell", "Lend", "Rent", "Free"};
    ArrayAdapter arrayAdapter;

    public int progress = 0;

    Bitmap bitmap;
    String encodeImage;
    public static final String url = "https://bookyourbook000webhost.000webhostapp.com/Booksimg/index.php";
    public String encodeImageString = "noimage";

    //trial category spinner

    Spinner bookCategory;
    ArrayList<String> catList = new ArrayList<>();

    ArrayAdapter<String> catAdapter;


    String urlCat;
    String cat;
   // String mEmail = "a@gmail.com";
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);

        bookEmail = findViewById(R.id.bookEmail);
        email =getIntent().getExtras().getString("mEmail").toString().trim();
        bookEmail.setText("Email: "+email);

        bookPic = findViewById(R.id.bookPic);

        bookUploadbtn = findViewById(R.id.bookUploadbtn);

        bookOptionsBar = findViewById(R.id.bookOptionsBar);
        arrayAdapter = new ArrayAdapter(insertData.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, bookOpt);
        bookOptionsBar.setAdapter(arrayAdapter);

        //trialcategory
        urlCat = "https://bookyourbook000webhost.000webhostapp.com/Booksimg/fetchCategoryForInserdata.php";

        bookCategory = (Spinner) findViewById(R.id.bookCategory);
        getCategory();
        //

        bookOptionsBar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bookOptPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(insertData.this, "Select Book option!!", Toast.LENGTH_SHORT).show();
            }
        });

        bookPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withActivity(insertData.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Browse Image"), 1);
                                flagbkpic = 1;
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        bookUploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploaddatatodb();

            }
        });
    }

    private void getCategory() {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                urlCat, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String catName = jsonObject.optString("bookCategoryName");
                        catList.add(catName);
                        catAdapter = new ArrayAdapter<>(insertData.this,
                                android.R.layout.simple_spinner_dropdown_item, catList);
                       // catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        bookCategory.setAdapter(catAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(insertData.this, ""+error.getCause(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "errorpvs"+error.getCause());
            }
        });
        requestQueue.add(jsonObjectRequest);
       // bookCategory.setOnItemSelectedListener(this);

        bookCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cat = adapterView.getSelectedItem().toString();
                Toast.makeText(insertData.this, "Category Selected by default: "+cat, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                bookPic.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            } catch (Exception ex) {
                Toast.makeText(this, "Books Image required!!", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        encodeImageString = Base64.encodeToString(bytesofimage, Base64.DEFAULT);

    }

    private void uploaddatatodb() {
        bookName = findViewById(R.id.bookName);
        bookId = findViewById(R.id.bookId);
//        bookCategory = findViewById(R.id.bookCategory);
        bookCategoryid = findViewById(R.id.bookCategoryid);
        bookPincode = findViewById(R.id.bookPincode);
        // bookEmail = findViewById(R.id.bookEmail);
        bookPhone = findViewById(R.id.bookMobile);
        bookCity = findViewById(R.id.bookCity);


        final String name = bookName.getText().toString().trim();
        final String id = bookId.getText().toString().trim();
        cat = bookCategory.getSelectedItem().toString().trim();

        final String catid = bookCategoryid.getText().toString().trim();
        final String pin = bookPincode.getText().toString().trim();

        final String phone = bookPhone.getText().toString().trim();
        final String city = bookCity.getText().toString().trim();


        if (flagbkpic > 0 && name.length() > 0 && id.length() > 0 && catid.length() > 0 && pin.length() > 0 && phone.length() > 0 && city.length() > 0) {
            if (phone.length() == 10) {
                if (pin.length() >= 6) {
                    if (bookOptPosition != 0) {
                        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("Book Uploaded Successfully!!")) {
                                    mProgressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Book Uploaded Successfully!!", Toast.LENGTH_LONG).show();
                                    bookPic.setImageResource(R.drawable.ic_baseline_menu_book_24);
                                    bookName.setText("");
                                    bookId.setText("");
                                    bookCategoryid.setText("");
                                    bookPincode.setText("");
                                    bookEmail.setText("");
                                    bookPhone.setText("");
                                    bookCity.setText("");
                                } else {
                                    mProgressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), ":(" + response.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                mProgressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("t1", name);
                                map.put("t2", id);
                                map.put("t3",cat);
                                map.put("t4", catid);
                                map.put("t5", pin);
                                map.put("t6", email);
                                map.put("t7", phone);
                                map.put("t8", city);
                                map.put("opts", bookOpt[bookOptPosition]);
                                map.put("upload", encodeImageString);
                                Log.d(TAG, "checkpvs "+map);
                                return map;
                            }
                        };
                        mProgressDialog = new ProgressDialog(insertData.this);
                        mProgressDialog.setTitle("Please Wait!!");
                        //mProgressDialog.setMax(100);
                        mProgressDialog.setMessage("Uploading....");
                        mProgressDialog.setProgressStyle(mProgressDialog.STYLE_SPINNER);
                        mProgressDialog.show();
                        mProgressDialog.setCancelable(false);

                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        queue.add(request);
                        flagbkpic = 0;

                    } else {
                        Toast.makeText(insertData.this, "Select Book option!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(insertData.this, "Invalid Pincode!!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(insertData.this, "Invalid Mobile Number must be greater than 10!!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(insertData.this, "All fields required!!", Toast.LENGTH_SHORT).show();
        }
    }

}

//Populate Spinner from MySQL Database in Android Studio

