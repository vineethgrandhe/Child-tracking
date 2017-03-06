package com.appiclauncher;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

public class FormActivity extends AppCompatActivity {

    private String type;
    private String id, name, parent, gender, age, mobile, address;

    // Views
    private EditText etId, etName, etParent, etGender, etAge, etMobile, etAddress;
    private Button bSubmit;
    private ImageView kids;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_activity);
        init();
    }

    // init()
    private void init() {
        etId = (EditText) findViewById(R.id.et_id);
        etName = (EditText) findViewById(R.id.et_name);
        etParent = (EditText) findViewById(R.id.et_parent);
        etGender = (EditText) findViewById(R.id.et_gender);
        etAge = (EditText) findViewById(R.id.et_age);
        etMobile = (EditText) findViewById(R.id.et_mobile);
        etAddress = (EditText) findViewById(R.id.et_address);

        bSubmit = (Button) findViewById(R.id.submit1);
        kids = (ImageView) findViewById(R.id.kids);

        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InternetDetector.detectInternet(FormActivity.this)) {
                    if (InternetDetector.detectInternet(FormActivity.this)) {
                        populate();
                        if (mobile.equals("")) {
                            Toast.makeText(FormActivity.this, "Please provide mobile number", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        new LoadForm().execute();
                    } else {
                        Toast.makeText(FormActivity.this, "Please connect to internet", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        String type = getIntent().getStringExtra(MenuActivity.TYPE);
        if (type.equals("Senior")) {
            kids.setImageResource(R.drawable.senior);
        } else if (type.equals("Kids")) {
            kids.setImageResource(R.drawable.children);
        }
    }

    // populate()
    private void populate() {
        id = etId.getText().toString().trim();
        Log.d("id", id);
        name = etName.getText().toString().trim();
        Log.d("name", name);
        parent = etParent.getText().toString().trim();
        Log.d("parent", parent);
        gender = etGender.getText().toString().trim();
        Log.d("gender", gender);
        age = etAge.getText().toString().trim();
        Log.d("age", age);
        mobile = etMobile.getText().toString().trim();
        Log.d("mobile", mobile);
        address = etAddress.getText().toString().trim();
        Log.d("address", address);
    }

    int success = 0;

    private class LoadForm extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(FormActivity.this);
            pd.setTitle("Please wait");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            HashMap<String, String> map = new HashMap<>();
            final String URL = "https://pushkaralu-ee88d.firebaseio.com/";
            map.put("V_Number", id);
            map.put("Name", name);
            map.put("ParentName", parent);
            map.put("Gender", gender);
            map.put("Age", age);
            map.put("PhoneNumber", mobile);
            map.put("Address", address);

            try {
                JSONObject json = JSONParser.POST(URL, map);
                success = json.getInt("success");
                Log.d("success", String.valueOf(success));
            } catch (Exception e) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                if (pd != null) pd.dismiss();
                if (success == 1) {
                    Toast.makeText(FormActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getBaseContext(), MenuActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
            } catch (Exception e) {
            }
        }
    }
}
