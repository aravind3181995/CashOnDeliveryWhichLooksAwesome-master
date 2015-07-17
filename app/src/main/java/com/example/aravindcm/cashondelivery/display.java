package com.example.aravindcm.cashondelivery;


import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.w3c.dom.Text;


public class display extends ActionBarActivity {
    TextView txt;
    String result="";
    String name="";
    String gender="";
    String yob="";
    String co="";
    String address="";
    EditText password;
    EditText username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.navigationBarColor));
        }
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        Intent i=getIntent();
        name=i.getStringExtra("name");
        gender=i.getStringExtra("gender");
        yob=i.getStringExtra("yob");
        co=i.getStringExtra("co");
        address=i.getStringExtra("address");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display, menu);
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
    public void done(View v)
    {
        final String usernametxt = username.getText().toString();
        final String passwordtxt = password.getText().toString();
        Log.e("cm", "" + usernametxt + passwordtxt);
        ParseUser user = new ParseUser();
        user.setUsername(usernametxt);
        user.setPassword(passwordtxt);
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Show a simple Toast message upon successful registration
                    Toast.makeText(getApplicationContext(),
                            "Successfully Signed up, please log in.",
                            Toast.LENGTH_LONG).show();
                            ParseObject aadharUser=new ParseObject("Aadhar");
                            aadharUser.put("Name",name);
                            aadharUser.put("Gender",gender);
                            aadharUser.put("YOB",yob);
                            aadharUser.put("Address",address);
                            aadharUser.put("SonOf",co);
                            aadharUser.put("username",usernametxt);
                            aadharUser.saveInBackground();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Username already exists. Choose a different one.", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        Intent i=new Intent(this,LoginSignupActivity.class);
        startActivity(i);
    }

}
