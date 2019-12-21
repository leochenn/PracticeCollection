package com.leo.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.leo.app.api.WanAndroidService;
import com.leo.app.data.Article;
import com.leo.app.data.ResponseData;
import com.leo.app.data.ResponseDataFunc;
import com.leo.app.retrofit.RetrofitHelper;
import com.leo.utils.log.LogUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void testRetrofit() {
        WanAndroidService service = RetrofitHelper.create(WanAndroidService.class);
        Call<ResponseData<Article>> call = service.getArticleList();
        call.enqueue(new Callback<ResponseData<Article>>() {
            @Override
            public void onResponse(Call<ResponseData<Article>> call,
                                   Response<ResponseData<Article>> response) {
                ResponseData<Article> responseData = response.body();
                LogUtil.d(TAG, "getArticleList", responseData);

            }

            @Override
            public void onFailure(Call<ResponseData<Article>> call, Throwable t) {
                LogUtil.e(t, TAG, "getArticleList");
            }
        });
    }

    Disposable disposable;
    private void testRx() {
        WanAndroidService service = RetrofitHelper.create(WanAndroidService.class);
        Observable<ResponseData<Article>> observable = service.getArticleListByRx();
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map(new ResponseDataFunc()).subscribe(new Observer<Article>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                LogUtil.d(TAG, "onSubscribe", disposable.isDisposed());
            }

            @Override
            public void onNext(Article article) {
                LogUtil.d(TAG, "onNext", article, disposable.isDisposed());
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(e, TAG, "onError", disposable.isDisposed());
            }

            @Override
            public void onComplete() {
                LogUtil.d(TAG, "onComplete", disposable.isDisposed());
            }
        });

        Observable.timer(50, TimeUnit.MILLISECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtil.d(TAG, "timer onSubscribe", d.isDisposed(), disposable.isDisposed());
            }

            @Override
            public void onNext(Long aLong) {
                LogUtil.d(TAG, "timer onNext1", disposable.isDisposed());
                disposable.dispose();
                LogUtil.d(TAG, "timer onNext2", disposable.isDisposed());
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.d(TAG, "timer onError", disposable.isDisposed());
            }

            @Override
            public void onComplete() {
                LogUtil.d(TAG, "timer onComplete", disposable.isDisposed());
            }
        });
    }

    private void testRx2() {
        WanAndroidService service = RetrofitHelper.create(WanAndroidService.class);
        Observable<ResponseData<Article>> observable = service.getArticleListByRx();
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map(new ResponseDataFunc()).subscribe(new Consumer<Article>() {
            @Override
            public void accept(Article o) throws Exception {
                LogUtil.d(TAG, "testRx2", o.getTotal());
            }
        });
    }

    private void testRxError() {
        // 当服务端返回的数据异常，如空返回，格式异常，不能通过校验等等
        // 有几种处理办法 https://www.jianshu.com/p/436cb79eace5
        // 1.定义全局的错误捕捉
        // RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
        //            @Override
        //            public void accept(Throwable throwable) throws Exception {
        //
        //            }
        //        });
        // 2.subscribe(Consumer onNext, Consumer onError)或者subscribe(Observer observer)替换subscribe(Consumer)
        // 3.重载GsonConvertFactory 在ResponseBodyConverter中处理各类错误 抛出异常
        // 4.subscribe(Observer observer),重载Observer 在onNext 或onError中处理各类错误抛出异常

        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                LogUtil.e(throwable, TAG, "setErrorHandler");
            }
        });

        WanAndroidService service = RetrofitHelper.create(WanAndroidService.class);
        Observable<ResponseData<Article>> observable = service.getErrorData();
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map(new ResponseDataFunc()).subscribe(new Consumer<Article>() {
            @Override
            public void accept(Article o){
                LogUtil.d(TAG, "testRx2", o.getTotal());
            }
        }, new Consumer<Throwable>() {

            @Override
            public void accept(Throwable throwable) {
                LogUtil.e(throwable, TAG, "subscribe Consumer<Throwable>");
            }
        });
    }

    public void Retrofit(View view) {
        testRetrofit();
    }
}
