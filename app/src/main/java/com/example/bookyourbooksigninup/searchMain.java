package com.example.bookyourbooksigninup;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class searchMain extends AppCompatActivity {
    RecyclerView recyclerView;
    String url = "https://bookyourbook000webhost.000webhostapp.com/Booksimg/getimage.php";
    String urlSearch = "https://bookyourbook000webhost.000webhostapp.com/Booksimg/getSearchBook.php";
    List<ModelClass> imagelist;
    ModelClass modelClass;
    LinearLayoutManager linearLayoutManager;
    searchAdapter adapter;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main);

        recyclerView = findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        imagelist = new ArrayList<>();

        //getgetApplicationContext()
        adapter = new searchAdapter(this, imagelist);
        recyclerView.setAdapter(adapter);

        getimages();

        fetchSearchBook("");
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
                Toast.makeText(searchMain.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mProgressDialog = new ProgressDialog(searchMain.this);
        mProgressDialog.setTitle("Please Wait!!");
        //mProgressDialog.setMax(100);
        mProgressDialog.setMessage("Fetching....");
        mProgressDialog.setProgressStyle(mProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);

        RequestQueue requestQueue = Volley.newRequestQueue(searchMain.this);
        requestQueue.add(request);
    }

    public void fetchSearchBook(String key) {
        StringRequest request = new StringRequest(Request.Method.POST, urlSearch, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                Toast.makeText(searchMain.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", key);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(searchMain.this);
        requestQueue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchmenu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                fetchSearchBook(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                fetchSearchBook(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}