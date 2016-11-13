package com.example.cuizehui.smartschool.baseCenter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuizehui.smartschool.R;
import com.example.cuizehui.smartschool.Utils.BitmapLoader;
import com.example.cuizehui.smartschool.Utils.Connect;
import com.example.cuizehui.smartschool.Utils.PhotoData;
import com.example.cuizehui.smartschool.Utils.SPtools;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.app.ParamsBuilder;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by cuizehui on 2016/8/18at ${time}.
 */
public class PicsBascenter extends  BaseCenterPaper {
    GridView gv;
    private GrAdapter grAdapter;
    List<String> photosData=new ArrayList();
    private List<String> photosTitle=new ArrayList<>();


    public PicsBascenter(Context context) {
        super(context);
    }
    @Override
   public View initView() {
     /*   TextView textView=new TextView(mainActivity);
        textView.setText("这里是组图");*/
        Log.d("执行组图方法了","1");
        LayoutInflater layoutInflater=LayoutInflater.from(mainActivity);
        View view=layoutInflater.inflate(R.layout.picsbase,null);
        gv= (GridView) view.findViewById(R.id.pic_gridview);
        return view;
    }

    @Override
    public void initData() {
        Log.d("执行组图方法了","2");
        //从网络获取数据 这里获取的是一组Josn数据，并不是根url获取的图片
        String  url= Connect.PHOTOSURL;
        String  zutudata=SPtools.getsetsp(mainActivity,"组图josn数据");
        //本地
        if(zutudata==null){
            //网络
            RequestParams params=new RequestParams(url);
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.d("执行一次",""+result);
                    //存到本地
                    SPtools.savesetsp(mainActivity,"组图josn数据",result);
                    //解析数据
                    prcessPhotosData(result);

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });

        }
        else {

            prcessPhotosData(zutudata);
        }


        grAdapter=new GrAdapter();
        gv.setAdapter(grAdapter);

    }

    private void prcessPhotosData(String result) {
        Gson gson =new Gson();
        PhotoData photoData=gson.fromJson(result, PhotoData.class);
        //获取list的数组了！
        List<PhotoData.DataBean.NewsBean> newsdata = photoData.getData().getNews();
        for (PhotoData.DataBean.NewsBean data:newsdata) {
           //将图片Url取到！
            photosData.add(data.getListimage());
            photosTitle.add(data.getTitle());
        }
    }

    class GrAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return photosData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           Holder holder=null;
            if(convertView==null){
                holder=new Holder();
                LayoutInflater inflater=LayoutInflater.from(mainActivity);
                convertView=inflater.inflate(R.layout.picsgrid_item,null);
                holder.picimageView= (ImageView) convertView.findViewById(R.id.pics_iv);
                holder.pictextview= (TextView) convertView.findViewById(R.id.tv_photos_list_item_desc);
                convertView.setTag(holder);
            }
            else{
                holder= (Holder) convertView.getTag();
            }
            //三级缓存
            BitmapLoader bitmapLoader=new BitmapLoader(mainActivity);
            bitmapLoader.displayBitmap(photosData.get(position),holder.picimageView);
               holder.pictextview.setText(photosTitle.get(position));
            return convertView;
        }
    }
    class Holder {
        ImageView picimageView;
        TextView pictextview;
    }
}
