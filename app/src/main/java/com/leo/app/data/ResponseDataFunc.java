package com.leo.app.data;


import io.reactivex.functions.Function;

/**
 * Created by Leo on 2019/7/27.
 */
public class ResponseDataFunc<T> implements Function<ResponseData<T>, T> {

    //进行数据转换，并且进行判断，activity只需要拿到真实的数据或者处理错误
    @Override
    public T apply(ResponseData<T> tResponseData) throws Exception {
        if (tResponseData.code != 0) {
            throw new Exception("abc");
        }
        return tResponseData.t;
    }
}
