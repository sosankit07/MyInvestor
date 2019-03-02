package com.example.asus.myinvestor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;

public class userprofile extends AppCompatActivity {
    String Dependents,Email,Fname,Lname,Age;
    int CheckAge;
    Button confirm;
    EditText edit;
    EditText edit1,edit2,edit3;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        confirm =findViewById(R.id.confirm);
        edit = findViewById(R.id.fname);
        edit1 = findViewById(R.id.lname);
        edit2 = findViewById(R.id.age);
        edit3 = findViewById(R.id.dependents);



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(userprofile.this);
        if (acct != null) {
            Email = acct.getEmail();

            //Toast.makeText(getApplicationContext(), Email, Toast.LENGTH_SHORT).show();
        }

        HashMap<String, String> getData = new HashMap<String, String>();
        getData.put("email", Email);

        PostResponseAsyncTask task = new PostResponseAsyncTask(userprofile.this, getData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if (!(s.isEmpty())) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    if(s=="Exists") {
                        Intent intent2 = new Intent(userprofile.this, dashboard.class);
                        startActivity(intent2);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong! Try later!", Toast.LENGTH_SHORT).show();

                }

            }
        });
        task.execute("http://192.168.43.104/News/check.php");

        task.setEachExceptionsHandler(new EachExceptionsHandler() {
            @Override
            public void handleIOException(IOException e) {
                Toast.makeText(getApplicationContext(), "Cannot connect to server!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleMalformedURLException(MalformedURLException e) {
                Toast.makeText(getApplicationContext(), "URL Error!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void handleProtocolException(ProtocolException e) {
                Toast.makeText(getApplicationContext(), "Protocol Error!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                Toast.makeText(getApplicationContext(), "Encoding Error!", Toast.LENGTH_SHORT).show();

            }
        });




        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult();
            }
        });
    }
    public void sendResult() {



        Lname = edit1.getText().toString();
        Age = edit2.getText().toString();
        Fname = edit.getText().toString();
        Dependents=edit3.getText().toString();
        CheckAge = Integer.parseInt(Age);
        if(CheckAge < 18){
            Toast.makeText(getApplicationContext(), "Can't Invest in Shares", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(userprofile.this,userprofile.class);
            startActivity(intent);

        }

        HashMap<String, String> getData = new HashMap<String, String>();
        getData.put("email", Email);
        getData.put("fname", Fname);
        getData.put("lname", Lname);
        getData.put("age", Age);

        PostResponseAsyncTask task2 = new PostResponseAsyncTask(userprofile.this, getData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if (!(s.isEmpty())) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                    Intent intent2 = new Intent(userprofile.this,portfolio.class);
                    startActivity(intent2);


                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong! Try later!", Toast.LENGTH_SHORT).show();

                }

            }
        });
        task2.execute("http://192.168.43.104/News/insert.php");

        task2.setEachExceptionsHandler(new EachExceptionsHandler() {
            @Override
            public void handleIOException(IOException e) {
                Toast.makeText(getApplicationContext(), "Cannot connect to server!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleMalformedURLException(MalformedURLException e) {
                Toast.makeText(getApplicationContext(), "URL Error!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void handleProtocolException(ProtocolException e) {
                Toast.makeText(getApplicationContext(), "Protocol Error!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                Toast.makeText(getApplicationContext(), "Encoding Error!", Toast.LENGTH_SHORT).show();

            }
        });



    }
}
