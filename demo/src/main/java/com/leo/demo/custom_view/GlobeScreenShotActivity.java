package com.leo.demo.custom_view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.leo.demo.R;

public class GlobeScreenShotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_globe_screen_shot);
    }

    public void takeScreenShot(View view) {
        GlobalScreenshot screenshot = new GlobalScreenshot(this);
        
        View contentView = findViewById(android.R.id.content);
        screenshot.takeScreenshot(contentView, new Runnable() {
            @Override
            public void run() {
                Toast.makeText(GlobeScreenShotActivity.this, "截图完成", Toast.LENGTH_SHORT).show();
            }
        }, true, true);
    }
}
