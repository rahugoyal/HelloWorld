package com.example.hellocicd;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          // test webhook trigger --

        TextView greeting = new TextView(this);

        // Use Greeting helper to make the text available to unit tests
        greeting.setText("hello");
        greeting.setTextSize(24);
        greeting.setGravity(Gravity.CENTER);

        setContentView(greeting);
    }
}
