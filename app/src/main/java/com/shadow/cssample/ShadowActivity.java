package com.shadow.cssample;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;

public class ShadowActivity extends Activity {

    private String img_path = "http://img1.3lian.com/2015/w7/98/d/22.jpg";
    private String[] img_paths = {"http://img2.3lian.com/2014/c7/12/d/77.jpg", "http://img1.3lian.com/2015/w7/98/d/22.jpg", "http://pic3.bbzhi.com/fengjingbizhi/gaoqingkuanpingfengguangsheyingps/show_fengjingta_281299_11.jpg"};
    private ImageView mShowImage;
    private Context mContext;
    private Random radom;
    HandlerThread mHandlerThread = new HandlerThread("download-bitmap");
    Handler handler ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadow);

        mContext = this;
        Button btnShowImage;
        btnShowImage = (Button) findViewById(R.id.btn_show_image);
        final ImageView mShowImage = (ImageView) findViewById(R.id.iv_image);
        mHandlerThread.start();
        handler = new Handler(mHandlerThread.getLooper());
        btnShowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                绕过StrictMode在主线程执行联网操作
//                HttpClient httpCt = new DefaultHttpClient();
//                HttpGet httpGet = new HttpGet(img_path);
//                HttpResponse httpResponse = null;
//
//                try{
//                    StrictMode.ThreadPolicy old = StrictMode.getThreadPolicy();
//                    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(old).permitNetwork().build());
//
//                    httpResponse = httpCt.execute(httpGet);
//
//                    Bitmap bm= null;
//                    if(httpResponse.getStatusLine().getStatusCode() == 200){
//                        byte[] data = EntityUtils.toByteArray(httpResponse.getEntity());
//                        bm = BitmapFactory.decodeByteArray(data, 0, data.length);
//                    }
//
//                    StrictMode.setThreadPolicy(old);
//                    if(bm != null){
//
//                        showImage.setImageBitmap(bm);
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
                //////////////
//                final Handler activityHandler = new Handler();
//
//                //Runable方式直接调用联网并在联网线程中直接更新UI
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        HttpClient httpCt = new DefaultHttpClient();
//                        int idx = (int)(Math.random()*3);
//                        String imgPath = img_paths[idx];
//                        HttpGet httpGet = new HttpGet(imgPath);
//                        try {
//                            HttpResponse httpResponse = httpCt.execute(httpGet);
//                            Bitmap bm = null;
//                            if (httpResponse.getStatusLine().getStatusCode() == 200) {
//                                byte[] data = EntityUtils.toByteArray(httpResponse.getEntity());
//                                bm = BitmapFactory.decodeByteArray(data, 0, data.length);
//                            }
//                            if (bm != null) {
////                                mShowImage.setImageBitmap(fBm);
//                                //使用runOnUiThread方法把UI更新放到UI线程中
////                                final Bitmap fBm = bm;
////                                ((Activity)mContext).runOnUiThread(new Runnable() {
////                                    @Override
////                                    public void run() {
////                                        mShowImage.setImageBitmap(fBm);
////                                    }
////                                });
//
//                                //使用Handle方式将子线程中下载完成的图片传入UI线程中再进行更新
//                                final Bitmap fBm = bm;
//                                Looper.prepare();
//                                Looper.loop();
//                                Handler handler = new Handler();//使用这个Handle是执行不成功的，因为它是在子线程中创建的
//                                handler.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        mShowImage.setImageBitmap(fBm);
//                                    }
//                                });
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
///////////////
//                //Callalbe方式下载图片，获取图片后用FutureTask返回Bitmap在主线程中更新UI
//                //把Callable包装进FutureTask中
//                FutureTask<Bitmap> bitmapFutureTask = new FutureTask<Bitmap>(new Callable<Bitmap>() {
//                    @Override
//                    public Bitmap call() throws Exception {
//                        Bitmap bmp = downloadBitmap(img_path);
//                        return bmp;
//                    }
//                });
//                //新启线程并start
//                Thread t = new Thread(bitmapFutureTask);
//                t.start();
//                Bitmap map = null;
//                //下载完毕返回图片对象并更新UI
//                try {
//                    Object obj = bitmapFutureTask.get();
//                    if(null != obj && obj instanceof Bitmap){
//                        map = (Bitmap)obj;
//                        mShowImage.setImageBitmap(map);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//

//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        int idx = (int)(Math.random()*3);
//                        String imgPath = img_paths[idx];
//                        final Bitmap bm = downloadBitmap(imgPath);
//                        if(null != bm){
//
//                                //使用runOnUiThread方法把UI更新放到UI线程中
//                                ((Activity)mContext).runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        mShowImage.setImageBitmap(bm);
//                                    }
//                                });
//                        }
//                    }
//                });
            }
        });
    }

    public Bitmap downloadBitmap(String url){
        HttpClient httpCt = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        Bitmap bm = null;
        try {
            HttpResponse httpResponse = httpCt.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                byte[] data = EntityUtils.toByteArray(httpResponse.getEntity());
                bm = BitmapFactory.decodeByteArray(data, 0, data.length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;
    }

    public class callableTest implements Callable<String>{

        @Override
        public String call() throws Exception {
            String str1 = "callable ";
            String str2 = "test!!";
            Thread.currentThread().sleep(6000l);
            Log.v("callbale", "called !!!");
            return str1 + str2;
        }
    }

    public class ImageThread extends Thread {

        @Override
        public void run() {
            HttpClient httpCt = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(img_path);
            try {
                HttpResponse httpResponse = httpCt.execute(httpGet);
                Bitmap bm = null;
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    byte[] data = EntityUtils.toByteArray(httpResponse.getEntity());
                    bm = BitmapFactory.decodeByteArray(data, 0, data.length);
                }
                if (bm != null && mShowImage != null) {

                    mShowImage.setImageBitmap(bm);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
