package com.leo.app.api;

import com.leo.app.data.Article;
import com.leo.app.data.MovieSubject;
import com.leo.app.data.ResponseData;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Leo on 2019/7/26.
 */
public interface WanAndroidService {

    // // https://www.jianshu.com/p/73216939806a

    @GET("wxarticle/chapters/json")
    Call<MovieSubject> getTop250ByGet(@Query("start") int start, @Query("count") int count);

    @FormUrlEncoded
    @POST("top250")
    Call<MovieSubject> getTop250ByPost(@Field("start") int start, @Field("count") int count);

    // 动态路径, 如果path参数包括斜杠 ,不加encoded 会出现转义
    @GET("{pathParmas}")
    Call<MovieSubject> getTop250ByPath(@Path(value = "pathParmas", encoded = true) String path, @Query("start") int start,
                                       @Query("count") int count);

    @GET()
    Call<MovieSubject> getTop250ByUrl(@Url String path, @Query("start") int start,
                                      @Query("count") int count);
    @GET("article/list/1/json")
    Call<ResponseData<Article>> getArticleList();

    @GET("article/list/1/json")
    Observable<ResponseData<Article>> getArticleListByRx();

    @GET("banner/json")
    Observable<ResponseData<Article>> getErrorData();
}
