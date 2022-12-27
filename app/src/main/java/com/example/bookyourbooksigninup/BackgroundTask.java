package com.example.bookyourbooksigninup;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class BackgroundTask extends AsyncTask<String,Void,String> {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    ProgressDialog mProgressDialog;
   UserManagment userManagment;
    Context context;

    BackgroundTask(Context ctx){
        this.context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        preferences = context.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("flag","0");
        editor.commit();

        String urlRegistration = "https://bookyourbook000webhost.000webhostapp.com/LoginAndRegister-register.php";
        String urlLogin = "https://bookyourbook000webhost.000webhostapp.com/LoginAndRegister-login.php";
        String urlForgetpass ="https://bookyourbook000webhost.000webhostapp.com/LoginAndRegister-forgetpassword.php";
        String urlInsertBookData = "https://bookyourbook000webhost.000webhostapp.com/Booksimg/index.php?add=333";

        String task = params[0];

        if(task.equals("register")){
            String regName = params[1];
            String regEmail = params[2];
            String regPassword = params[3];

            try {
                URL url = new URL(urlRegistration);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoOutput(true);
                OutputStream outputStream = httpsURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String myData = URLEncoder.encode("identifier_name","UTF-8")+"="+URLEncoder.encode(regName,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_email","UTF-8")+"="+URLEncoder.encode(regEmail,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_password","UTF-8")+"="+URLEncoder.encode(regPassword,"UTF-8")+"&";
                bufferedWriter.write(myData);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = httpsURLConnection.getInputStream();
                inputStream.close();

                editor.putString("flag","register");
                editor.commit();
                return "Successfully Registered " + regName;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(task.equals("login")){
            String loginEmail = params[1];
            String loginPassword = params[2];

            try {
                URL url = new URL(urlLogin);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoOutput(true);
                httpsURLConnection.setDoInput(true);

                //send the email and password to the database
                OutputStream outputStream = httpsURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String myData = URLEncoder.encode("identifier_loginEmail","UTF-8")+"="+URLEncoder.encode(loginEmail,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_loginPassword","UTF-8")+"="+URLEncoder.encode(loginPassword,"UTF-8");
                bufferedWriter.write(myData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //get response from the database
                InputStream inputStream = httpsURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String dataResponse = "";
                String inputLine = "";
                while ((inputLine = bufferedReader.readLine()) != null){
                    dataResponse += inputLine;
                }
                bufferedReader.close();
                inputStream.close();
                httpsURLConnection.disconnect();

                System.out.println("!!!!!!!!!!"+dataResponse);
                System.out.println(dataResponse);

                editor.putString("flag","login");
                editor.commit();
                return dataResponse;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(task.equals("forgetpassword")){
            String userName = params[1];
            String emailId = params[2];

            try {
                URL url = new URL(urlForgetpass);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoOutput(true);
                httpsURLConnection.setDoInput(true);

                //send the username and email  to the database
                OutputStream outputStream = httpsURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String myData = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(userName,"UTF-8")+"&"
                        +URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(emailId,"UTF-8");
                bufferedWriter.write(myData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //get response from the database
                InputStream inputStream = httpsURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String dataResponse = "";
                String inputLine = "";
                while ((inputLine = bufferedReader.readLine()) != null){
                    dataResponse += inputLine;
                }
                bufferedReader.close();
                inputStream.close();
                httpsURLConnection.disconnect();

                System.out.println("!!!!!!!!!!"+dataResponse);
                System.out.println(dataResponse);

                editor.putString("flag","forgetpassword");
                editor.commit();
                return dataResponse;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(task.equals("insertBookData")){
            String bkName = params[1];
            String bkId = params[2];
            String bkCat = params[3];
            String bkPin = params[4];
            String bkEmail = params[5];
            String bkPh = params[6];
            String bkCty = params[7];
            String bkPic = params[8];
            Log.d(TAG, "bkPic"+bkPic);

            try {
                URL url = new URL(urlInsertBookData);

                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoOutput(true);
                OutputStream outputStream = httpsURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String myData = URLEncoder.encode("t1","UTF-8")+"="+URLEncoder.encode(bkName,"UTF-8")+"&"
                        +URLEncoder.encode("t2","UTF-8")+"="+URLEncoder.encode(bkId,"UTF-8")+"&"
                        +URLEncoder.encode("t3","UTF-8")+"="+URLEncoder.encode(bkCat,"UTF-8")+"&"
                        +URLEncoder.encode("t4","UTF-8")+"="+URLEncoder.encode(bkPin,"UTF-8")+"&"
                        +URLEncoder.encode("t5","UTF-8")+"="+URLEncoder.encode(bkEmail,"UTF-8")+"&"
                        +URLEncoder.encode("t6","UTF-8")+"="+URLEncoder.encode(bkPh,"UTF-8")+"&"
                        +URLEncoder.encode("t7","UTF-8")+"="+URLEncoder.encode(bkCty,"UTF-8")+"&"
                        +URLEncoder.encode("upload","UTF-8")+"="+URLEncoder.encode(bkPic,"UTF-8");
                Log.d(TAG, "mydata"+myData);
                bufferedWriter.write(myData);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = httpsURLConnection.getInputStream();
                inputStream.close();

                editor.putString("flag","insertBookData");
                editor.commit();
                return "Successfully Registered " + bkName;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //This method will be called when doInBackground completes.... and it will return the completion string which
    //will display this toast.
    @Override
    protected void onPostExecute(String s) {
        String flag = preferences.getString("flag","0");

        if(flag.equals("register")){
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        }

        if(flag.equals("login")) {
            if (flag.equals("login")) {
                String test = "false";
                String name = "";
                String email = "";
                String[] serverResponse = s.split("[,]");
                test = serverResponse[0];
                name = serverResponse[1];
                email = serverResponse[2];

                userManagment =new UserManagment(context);
                userManagment.UserSessionMange(name,email);

                mProgressDialog = new ProgressDialog(context);
                mProgressDialog.setTitle("Please Wait!!");
                //mProgressDialog.setMax(100);
                mProgressDialog.setMessage("Logging in !!");
                mProgressDialog.setProgressStyle(mProgressDialog.STYLE_SPINNER);
                mProgressDialog.show();
                mProgressDialog.setCancelable(false);

                if (test.equals("true")) {
//                    editor.putString("name", name);
//                    editor.commit();
//                    editor.putString("email", email);
//                    editor.commit();

                    mProgressDialog.dismiss();
                    Toast.makeText(context,"Login Successfully!!",Toast.LENGTH_SHORT);
                    Intent intentMA = new Intent(context, MainActivity.class);
                    context.startActivity(intentMA);
                } else {
                    mProgressDialog.dismiss();
                    Toast.makeText(context, "Login Failed.... That email and password do not match our records :(.", Toast.LENGTH_SHORT).show();
                    //display("Login Failed....", "That email and password do not match our records :(.");
                }
            }
            else {
                mProgressDialog.dismiss();
                Toast.makeText(context, "Login Failed.... Something went wrong :(.", Toast.LENGTH_SHORT).show();
                //display("Login Failed....", "Something went wrong :(.");
            }
        }

        if(flag.equals("forgetpassword")) {
            if (flag.equals("forgetpassword")) {
                String test = "false";
                String status = "";

                String[] serverResponse = s.split("[,]");
                test = serverResponse[0];
                status = serverResponse[1];

                if (test.equals("true")) {
                    editor.putString("status", status);
                    editor.commit();

                    Toast.makeText(context, "Email successfully sent please check your mail inbox.", Toast.LENGTH_SHORT).show();
                    Intent intentMA = new Intent(context, com.example.bookyourbooksigninup.SignIn.class);
                    context.startActivity(intentMA);
                } else {
                    Toast.makeText(context, "Failed.... That username and email do not match our records :(.", Toast.LENGTH_SHORT).show();
                    //display("Login Failed....", "That email and password do not match our records :(.");
                }
            } else {
                Toast.makeText(context, "Failed.... Something went wrong :(.", Toast.LENGTH_SHORT).show();
                //display("Login Failed....", "Something went wrong :(.");
            }
        }

        if(flag.equals("insertBookData")){
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}
