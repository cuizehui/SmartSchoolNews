package com.example.cuizehui.smartschool.Utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.util.Log;

import android.widget.ImageView;

import com.example.cuizehui.smartschool.activitys.MainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by cuizehui on 2016/9/5at ${time}.
 */
public class BitmapLoader {
    MainActivity context;
    //动态获取jvm的内存
    LruCache<String, Bitmap> lruCache;

    private File cacheDir ;
    public static final Executor THEAD_POOL_EXECUTOR= Executors.newFixedThreadPool(3);
    private Map<Bitmap, String> urlImageViewDatas = new HashMap<>();


    public BitmapLoader(MainActivity context) {
          this.context=context;
          cacheDir = context.getCacheDir();
          int maxSize = (int) (Runtime.getRuntime().freeMemory() /2);
          lruCache = new LruCache<String, Bitmap>(Integer.MAX_VALUE) {

            @Override
            protected int sizeOf(String key, Bitmap value) {
                // TODO Auto-generated method stub
                return value.getRowBytes() * value.getHeight();
            }

        };


        //存储缓存

    }


//
    private Bitmap loadBitmap(String url) {
        //从内存获取
        Bitmap bitmap =loadFromMemCache(url);
      //  Log.d("加载","从内存中获取");
        if(bitmap!=null){
            Log.d("加载","从内存中获取");
            return bitmap;
        }

        //从存储内存中获取
        bitmap=loadFromFileCache(url);

        if(bitmap!=null){
            Log.d("加载","从文件中获取");
            return bitmap;
        }
          else {
            //从网络获取
            bitmap=downLoadFromNet(url);

            Log.d("加载","从网络中获取");
            return  bitmap;
        }


    }

    //显示 开启一个线程池 先得到图片，然后在主线程，将图片绑定
    public void  displayBitmap(final String url, final ImageView imageView){

        Runnable loadBitmapTask=new Runnable() {
            @Override
            public void run() {
                final   Bitmap bitmap=loadBitmap(url);

                   //在此处将URL记录下来
                // 3. 从网络取
                urlImageViewDatas.put(bitmap,url);//保留最后一次访问的url

                if(bitmap!=null){
                    //获取到主线程 重新在主线程中调用该方法！！这里是这样处理线程不能改变界面的问题的！
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //在这之前监测URL是否发生了改变？
                            if (url.equals(urlImageViewDatas.get(bitmap))) {
                                //自己的数据
                                imageView.setImageBitmap(bitmap);
                             }

                        }
                    });
                }
            }

        };
        //在线程池中加载图片
        THEAD_POOL_EXECUTOR.execute(loadBitmapTask);


    }
    //加密
    public  void   putToMemCache(String url,Bitmap bitmap){
        //把Url做Md5加密处理
        lruCache.put(url,bitmap);
    }


    //不带算法的文件存储方式
    public void saveBitmapToCacheFile(Bitmap bitmap,String ivUrl) {
        File file = new File(cacheDir,Md5Utils.md5(ivUrl));
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /***
     * 从内存中获取
     * @param key
     * @return
     */
    public Bitmap loadFromMemCache(String  key){
        //将url进行解密处理，然后获取
        Bitmap bitmap = lruCache.get(key);
        if(bitmap!=null){
            Log.d("获取非数据","11");
        }
         Log.d("获取空数据","11"+key);

        return bitmap;

    }

    /**
     * @param ivUrl
     *     当做缓存图片的名字
     * @return
     * 从文件中获取
     */
    public Bitmap loadFromFileCache(String ivUrl){
        Log.d("执行从文件中获取的方法了","！！！");
        //把ivUrl转换MD5值，再把md5 做文件名
        File file = new File(cacheDir,Md5Utils.md5(ivUrl));
        if (file != null  && file.exists()) {
            //文件存在
            //把文件转换成bitmap
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

            if(bitmap!=null){
                Log.d("写入","写入成功"+ivUrl);
                putToMemCache(ivUrl, bitmap);
            }else{
                Log.d("写入","写入空数据");
            }
            //再往内存写


            return bitmap;
        } else {
            return null;
        }

    }

    /**
     *
     * @param bPurl
     * @return
     * 从网络中获取
     */
    public  Bitmap downLoadFromNet(final String bPurl) {
        //开启线程！ 获取Bitmap
        //这里可以用线程池处理？
        Bitmap bitmap=null;
        HttpURLConnection connection = null;
        try {

            URL url1 = new URL(bPurl);
            connection = (HttpURLConnection) url1.openConnection();
            connection.setReadTimeout(8000);
            connection.setRequestMethod("GET");
            InputStream inputStrearms = connection.getInputStream();

            bitmap = BitmapFactory.decodeStream(inputStrearms);
            //本地存储
            saveBitmapToCacheFile(bitmap,bPurl);

            putToMemCache(bPurl,bitmap);

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(connection!=null){
                connection.disconnect();
            }
        }


        return bitmap;

    }



}
