package com.example.bookyourbooksigninup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    ImageView searchMain, catMain, insertMain, viewMain;

    UserManagment userManagment;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userManagment = new UserManagment(this);
        userManagment.checkLogin();

        preferences = this.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);

//        String mName = preferences.getString("name","Error in getting name!!");
//        String mEmail = preferences.getString("email","Error in getting email!!");

        drawerLayout=findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.navigationView);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.menu_open,R.string.menu_close); //three dash menu
        drawerLayout.addDrawerListener(actionBarDrawerToggle); //joining three dash with drawer
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        HashMap<String,String> user = userManagment.userDetails();
        String mName = user.get(userManagment.NAME);
        String mEmail = user.get(userManagment.EMAIL);

        searchMain=findViewById(R.id.searchMain);
        searchMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent=new Intent(MainActivity.this, searchMain.class);
                startActivity(searchIntent);
            }
        });

        catMain = findViewById(R.id.catMain);
        catMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent catIntent = new Intent(MainActivity.this, category.class);
                catIntent.putExtra("mEmail",mEmail);
                startActivity(catIntent);
            }
        });

        insertMain= findViewById(R.id.insertMain);
        insertMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent insertIntent = new Intent(MainActivity.this, insertData.class);
                insertIntent.putExtra("mEmail",mEmail);
                startActivity(insertIntent);
            }
        });

        viewMain = findViewById(R.id.viewMain);
        viewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewIntent = new Intent(MainActivity.this, viewData.class);
                viewIntent.putExtra("mEmail",mEmail);
                startActivity(viewIntent);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override   //click on menu item
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_profile:
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;


                    case R.id.nav_settings:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_logout:
                        drawerLayout.closeDrawer(GravityCompat.START);

                        userManagment.logout();
                        Toast.makeText(MainActivity.this, "Log out Successfully!!", Toast.LENGTH_SHORT).show();
//                        Intent intent=new Intent(MainActivity.this,SignIn.class);
//                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }
}