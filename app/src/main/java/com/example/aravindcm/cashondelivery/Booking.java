package com.example.aravindcm.cashondelivery;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Booking extends ActionBarActivity {
    int index;
    String phonenumber;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        Intent j=getIntent();
        index=j.getIntExtra("index",0);
        Log.e("cm", "" + index);
        /*ParseQuery<ParseObject> query = ParseQuery.getQuery("packages");
        query.whereEqualTo("Booked", false);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> packageList, ParseException e) {
                if (e == null) {
                    String phonenumber=packageList.get(index).getNumber("PhoneNumber").toString();
                    Log.e("cm",""+phonenumber);
                } else {
                    Log.d("Size", "Error: " + e.getMessage());
                }
            }
        });
        ParseUser currentUser = ParseUser.getCurrentUser();
        String name=currentUser.getString("Name");*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_booking, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void sendMail(View view)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("packages");
        query.whereEqualTo("Booked", false);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> packageList, ParseException e) {
                if (e == null) {
                    phonenumber=packageList.get(index).getNumber("PhoneNumber").toString();
                    email=packageList.get(index).getString("emailid");
                    Log.e("cm",""+phonenumber);
                    //send();
                } else {
                    Log.d("Size", "Error: " + e.getMessage());
                }
            }
        });

    }
    /*public void send()
    {
        Map<String, String> params = new HashMap<>();
        params.put("text", "Your Package will be Delivered by today. You can contact "+phonenumber+" . Thank you :*. I love you :*.");
        params.put("subject", "Amazon Delivery");
        params.put("fromEmail", "aravind3181995@gmail.com");
        params.put("fromName", "Anonymous");
        params.put("toEmail", email);
        params.put("toName", "fgt");
        ParseCloud.callFunctionInBackground("sendMail", params, new FunctionCallback<Object>() {
            @Override
            public void done(Object response, ParseException exc) {
                Log.e("cloud code example", "response: " + response);
            }
        });
    }*/
}
