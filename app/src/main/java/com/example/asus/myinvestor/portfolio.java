package com.example.asus.myinvestor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class portfolio extends AppCompatActivity {
    String Income,Gold,Property,Risk,Term,Expense,Email,Region;
    Button Ok;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);
        Spinner sp1 = findViewById(R.id.termoptions);
        Spinner sp = findViewById(R.id.riskoptions);
        EditText edit = findViewById(R.id.income);
        EditText region = findViewById(R.id.propertiesregio);
        Region = region.getText().toString();
        Income = edit.getText().toString();
        EditText edit1 = findViewById(R.id.goldinfo);
        Gold = edit1.getText().toString();
        EditText edit2 = findViewById(R.id.propertiesinfo);
        Property = edit2.getText().toString();
        EditText edit3 = findViewById(R.id.expenses);
        Expense = edit3.getText().toString();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(portfolio.this);
        if (acct != null) {
            Email = acct.getEmail();

            //Toast.makeText(getApplicationContext(), Email, Toast.LENGTH_SHORT).show();
        }
        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendInfo();
            }
        });
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1: Risk = "0";//Low Risk";
                    case 2: Risk = "1";//High Risk";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1: Term = "0";//shortterm
                    case 2: Term = "1";//longterm
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void sendInfo() {

        HashMap<String, String> getData = new HashMap<String, String>();
        getData.put("email", Email);
        getData.put("income", Income);
        getData.put("gold", Gold);
        getData.put("property", Property);
        getData.put("region",Region);
        getData.put("expenses", Expense);
        getData.put("term", Term);
        getData.put("risk", Risk);
        PostResponseAsyncTask task2 = new PostResponseAsyncTask(portfolio.this, getData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if (!(s.isEmpty())) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                    Intent intent2 = new Intent(portfolio.this,dashboard.class);
                    startActivity(intent2);


                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong! Try later!", Toast.LENGTH_SHORT).show();

                }

            }
        });
        task2.execute("http://192.168.0.101/News/portfolio.php");

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