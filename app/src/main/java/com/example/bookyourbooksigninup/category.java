package com.example.bookyourbooksigninup;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class category extends AppCompatActivity {
    FloatingActionButton catAdd;

    RecyclerView recyclerView;
    String url = "https://bookyourbook000webhost.000webhostapp.com/Booksimg/getCategoryName.php";
    List<ModelCategoryClass> imagelist;
    ModelCategoryClass modelCategoryClass;
    LinearLayoutManager linearLayoutManager;
    categoryNameAdapter adapter;
    String mEmail;
ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);



        mEmail = getIntent().getExtras().getString("mEmail").toString();

        recyclerView = findViewById(R.id.rvViewData);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        imagelist = new ArrayList<>();

        //getgetApplicationContext()
        adapter = new categoryNameAdapter(this, imagelist);
        recyclerView.setAdapter(adapter);

        getimages();

        catAdd = findViewById(R.id.catAdd);
        catAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent catInsert = new Intent(category.this, categoryInsert.class);
                catInsert.putExtra("mEmail", mEmail);
                startActivity(catInsert);
            }
        });
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

                            String SrNo = object.getString("SrNo");
                            String Email = object.getString("Email");
                            String bookCategoryName = object.getString("bookCategoryName");

                            modelCategoryClass = new ModelCategoryClass(SrNo,Email,bookCategoryName);
                            imagelist.add(modelCategoryClass);
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
                Log.d(TAG, "onErrorResponse: " + error.getMessage());
                Toast.makeText(category.this, error.getMessage(), Toast.LENGTH_SHORT).show();

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
        mProgressDialog = new ProgressDialog(category.this);
        mProgressDialog.setTitle("Please Wait!!");
        //mProgressDialog.setMax(100);
        mProgressDialog.setMessage("Fetching....");
        mProgressDialog.setProgressStyle(mProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);

        RequestQueue requestQueue = Volley.newRequestQueue(category.this);
        requestQueue.add(request);
    }

    @Override
    protected void onRestart() {
        this.recreate();
        super.onRestart();
    }
}