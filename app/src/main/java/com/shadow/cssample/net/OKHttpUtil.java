package com.shadow.cssample.net;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OKHttpUtil {

    static OkHttpClient mOkHttpClient;

    public interface httpResponse {

        public void onResult(String result);
    }

    public static void get(String url, final httpResponse httpResponse){

        Request request = new Request.Builder()
                .url(url)
                .build();
        //创建默认的OkHttp对象
//        mOkHttpClient = new OkHttpClient();

        //创建自定义OKHttp对象
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(1);
        mOkHttpClient = new OkHttpClient.Builder()
        .dispatcher(dispatcher)
        .build();

        Call call = mOkHttpClient.newCall(request);

        //串行调用
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                httpResponse.onResult(response.body().string());
            }
        });
        //并行调用
        try {
            httpResponse.onResult(call.execute().body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
