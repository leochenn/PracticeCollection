package com.leo.demo.custom_view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.leo.demo.R;
import com.leo.demo.scroll_drag.NavigationDrawActivity;
import com.leo.demo.scroll_drag.ScrollDragActivity;
import com.leo.demo.scroll_drag.ViewDragHelperActivity;

/**
 * Created by Leo on 2019/7/10.
 */
public class CustomViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_main);
    }

    private void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(this, cls));
    }

    public void Label_View(View view) {
        startActivity(LabelViewActivity.class);
    }

    public void Screen_Shot(View view) {
        startActivity(GlobeScreenShotActivity.class);
    }
}
