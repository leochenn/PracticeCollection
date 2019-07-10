package com.leo.demo.scroll_drag;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.leo.demo.R;

public class ViewDragHelperActivity extends AppCompatActivity {

    ViewDragHelperLayout viewDragHelperLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drag_helper);
        viewDragHelperLayout = findViewById(R.id.view_drag_helper_layout);
    }

    public void open(View view) {
        Toast.makeText(view.getContext(), ((TextView)view).getText(), Toast.LENGTH_SHORT).show();
        viewDragHelperLayout.openDrawer();
    }

    public void close(View view) {
        Toast.makeText(view.getContext(), ((TextView)view).getText(), Toast.LENGTH_SHORT).show();
        viewDragHelperLayout.closeDrawer();
    }
}
