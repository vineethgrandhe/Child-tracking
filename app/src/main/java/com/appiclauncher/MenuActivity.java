package com.appiclauncher;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    public static final String TYPE = "type";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
    }

    public void LaunchKids(View view) {
        Intent i = new Intent(this, FormActivity.class);
        i.putExtra(TYPE, "Kids");
        startActivity(i);
    }

    public void LaunchSenior(View view) {
        Intent i = new Intent(this, FormActivity.class);
        i.putExtra(TYPE, "Senior");
        startActivity(i);

    }
    public void website (View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("URL which cannot be shown"));
        startActivity(intent);
    }
}
