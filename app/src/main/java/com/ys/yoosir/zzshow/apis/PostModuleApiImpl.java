package com.ys.yoosir.zzshow.apis;

import android.util.Log;

import com.ys.yoosir.zzshow.apis.common.HostType;
import com.ys.yoosir.zzshow.apis.interfaces.PostModuleApi;
import com.ys.yoosir.zzshow.apis.listener.RequestCallBack;
import com.ys.yoosir.zzshow.mvp.modle.toutiao.ArticleData;
import com.ys.yoosir.zzshow.mvp.modle.toutiao.ArticleResult;
import com.ys.yoosir.zzshow.utils.httputil.RetrofitManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 * Created by Yoosir on 2016/10/20 0020.
 */
public class PostModuleApiImpl{

    private static final String TAG = PostModuleApiImpl.class.getSimpleName();

    private PostModuleApi service;
    private HashMap<String,String> params;

    public static PostModuleApiImpl getInstance(){
        return new PostModuleApiImpl();
    }

    private PostModuleApiImpl(){
        service = RetrofitManager.getInstance(HostType.TOUTIAO_PHOTO).createService(PostModuleApi.class);
    }

    public void getArticles(final RequestCallBack<ArticleResult<List<ArticleData>>> callBack,long maxBehotTime){

        service.getArticles(getParams(maxBehotTime))               //获取 Observable 对象
                .subscribeOn(Schedulers.newThread())    //请求在新的线程中执行
                .observeOn(AndroidSchedulers.mainThread())             //请求完成后在 io 线程中执行
                .subscribe(new Subscriber<ArticleResult<List<ArticleData>>>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                        Log.i(TAG,"onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                        Log.i(TAG,"onError - error = " + e.getMessage());
                        if(callBack != null) {
                            callBack.onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(ArticleResult<List<ArticleData>> listArticleResult) {
                        System.out.println("onNext");
                        Log.i(TAG,"onNext");
                        Log.d(TAG,listArticleResult.toString());
                        if(callBack != null) {
                            callBack.success(listArticleResult);
                        }
                    }
                });
    }


   private Map<String,String> getParams(long maxBehotTime){
       if(params == null) {
           params = new HashMap<>();
           params.put("source", 2 + "");
           params.put("count", 20 + "");
           params.put("category", "gallery_detail");
           params.put("utm_source", "toutiao");
           params.put("device_platform", "web");
           params.put("offset", "0");
           params.put("as", "A1F558D0E8153A2");
           params.put("cp", "58085523EA727E1");
           params.put("max_create_time", "1471017973");
           params.put("_", "1476940675594");
       }
       params.put("max_behot_time", "" + maxBehotTime);
       return  params;
   }
}