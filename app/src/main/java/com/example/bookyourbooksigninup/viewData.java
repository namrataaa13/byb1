package com.example.bookyourbooksigninup;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class viewData extends AppCompatActivity {

    RecyclerView recyclerView;
    String url = "https://bookyourbook000webhost.000webhostapp.com/Booksimg/getMyBooks.php";
    List<ModelClass> imagelist;
    ModelClass modelClass;
    LinearLayoutManager linearLayoutManager;
    viewDataAdapter adapter;
    String mEmail;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        
        mEmail = getIntent().getExtras().getString("mEmail").toString();

        recyclerView = findViewById(R.id.rvViewData);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        imagelist = new ArrayList<>();

        //getgetApplicationContext()
        adapter = new viewDataAdapter(this, imagelist);
        recyclerView.setAdapter(adapter);

        getimages();
    }

    private void getimages() {

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                mProgressDialog.dismiss();

                imagelist.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (success.equals("1")) {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String bookName = object.getString("bookName");
                            String bookId = object.getString("bookId");
                            String bookCategory = object.getString("bookCategory");
                            String bookCategoryid = object.getString("bookCategoryid");
                            String bookPincode = object.getString("bookPincode");
                            String bookEmail = object.getString("bookEmail");
                            String bookPhone = object.getString("bookPhone");
                            String bookCity = object.getString("bookCity");
                            String bookOption = object.getString("bookOption");
                            String url2 = object.getString("bookPic");

                            String urlimage = "https://bookyourbook000webhost.000webhostapp.com/Booksimg/images/" + url2;

                            modelClass = new ModelClass(bookName, bookId, bookCategory, bookCategoryid, bookPincode, bookEmail, bookPhone, bookCity, bookOption, urlimage);
                            imagelist.add(modelClass);
                            adapter.notifyDataSetChanged();
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(viewData.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("emailId", mEmail);
                Log.d(TAG, "Email " + mEmail);
                return map;
            }
        };

        mProgressDialog = new ProgressDialog(viewData.this);
        mProgressDialog.setTitle("Please Wait!!");
        //mProgressDialog.setMax(100);
        mProgressDialog.setMessage("Fetching....");
        mProgressDialog.setProgressStyle(mProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);

        RequestQueue requestQueue = Volley.newRequestQueue(viewData.this);
        requestQueue.add(request);
    }

}