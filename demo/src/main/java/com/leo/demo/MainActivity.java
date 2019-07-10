package com.leo.demo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.leo.demo.scroll_drag.ScrollDragActivity;
import com.leo.demo.scroll_drag.ScrollDragMainActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Scroll_Drag(View view) {
        startActivity(ScrollDragMainActivity.class);
    }

    public void Other(View view) {
    }

    private void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(this, cls));
    }
}
