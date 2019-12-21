package com.leo.app.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Leo on 2019/7/26.
 */
public class ResponseData<T> {
    @SerializedName("errorMsg")
    public String msg;
    @SerializedName("errorCode")
    public int code;
    @SerializedName("data")
    public T t;
}
