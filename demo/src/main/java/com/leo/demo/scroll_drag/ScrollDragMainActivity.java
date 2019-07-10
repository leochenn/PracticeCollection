package com.leo.demo.scroll_drag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.leo.demo.R;

/**
 * Created by Leo on 2019/7/10.
 */
public class ScrollDragMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_drag_main);
    }

    private void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(this, cls));
    }

    public void Scroll_Drag(View view) {
        startActivity(ScrollDragActivity.class);
    }

    public void View_Drag_Helper(View view) {
        startActivity(ViewDragHelperActivity.class);
    }

    public void Navigation_Drawer(View view) {
        startActivity(NavigationDrawActivity.class);
    }
}
