package com.leo.demo.third.volley;

import android.content.Context;

import com.leo.demo.third.volley.toolbox.StringRequest;
import com.leo.demo.third.volley.toolbox.Volley;

import java.util.Map;

/**
 * Created by Leo on 2019/7/12.
 */
public class TestVolley {

    public static void start(Context context) {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        Request<String> request = new StringRequest("https://www.v2ex.com/about", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                LogUtil.d("TestVolley onResponse", response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.d("TestVolley onErrorResponse", error);
                NetworkResponse r = error.networkResponse;
                if (r != null) {
                    LogUtil.d("TestVolley onErrorResponse", r.statusCode);
                    Map<String, String> headers = r.headers;
                    if (headers.size() > 0) {

                    }
                }
            }

        });
        requestQueue.add(request);
    }
}
