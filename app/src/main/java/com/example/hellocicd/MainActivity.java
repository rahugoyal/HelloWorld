package com.example.hellocicd;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // test webhook trigger

        TextView greeting = new TextView(this);

        greeting.setText("Hello World  from Android CI/CD Radhe Radhe");
        greeting.setTextSize(24);
        greeting.setGravity(Gravity.CENTER);

        setContentView(greeting);
    }
}
