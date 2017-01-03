package com.example.cuizehui.smartschool.newcenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.nfc.Tag;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cuizehui.smartschool.R;
import com.example.cuizehui.smartschool.Utils.BitmapLoader;
import com.example.cuizehui.smartschool.Utils.Connect;
import com.example.cuizehui.smartschool.Utils.NewContextData;
import com.example.cuizehui.smartschool.Utils.NewsCenterData;
import com.example.cuizehui.smartschool.Utils.SPtools;
import com.example.cuizehui.smartschool.activitys.MainActivity;
import com.example.cuizehui.smartschool.activitys.NewsActivity;
import com.example.cuizehui.smartschool.net.RetrofitFactory;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.xutils.ImageManager;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.app.ParamsBuilder;
import org.xutils.x;

import java.security.Permission;
import java.security.Policy;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by cuizehui on 2016/8/22at ${time}.
 */
public class Tip_newsCenter  {
    private static final String TAG = "Tag";
    private final ImageManager bitmapUtils;
    private View root ;
    MainActivity mainActivity;
    private NewsCenterData.DataBean.ChildrenBean childrenBean;
    private ViewPager viewPager;
    private ListView newslistview;
    private Handler handler;
    //轮播图 信息集
    private List<NewContextData.DataBean.TopnewsBean> topnews=new ArrayList<>();
    //轮播Adapter
    private LunboAdapter lunboAdapter;
    //新闻列表
    private newsListAdapter newsListAdapter;
    private TextView vpTV;

    private List<NewContextData.DataBean.NewsBean> newsListData=new ArrayList<>();

    private View headview;
    private Gson gson;
    //新闻列表的数据

    public Tip_newsCenter(MainActivity mainActivity, NewsCenterData.DataBean.ChildrenBean childrenBean) {
        this.mainActivity= mainActivity;
        this.childrenBean=childrenBean;
        bitmapUtils= x.image();
        initView();
        initData();
        initEvent();
    }

    public void initView(){
        //思路：1先做出viewpager
        root=View.inflate(mainActivity, R.layout.news_center_context,null);
        headview=View.inflate(mainActivity,R.layout.newslist_head,null);
        //viewPager引用
        viewPager= (ViewPager) headview.findViewById(R.id.lunbovp);
        //textView引用
        vpTV= (TextView)headview.findViewById(R.id.tv_tpi_news_pic_desc);
        //Listview引用
        newslistview= (ListView) root.findViewById(R.id.lv_TPI_news);
    }
    public void initData(){
        //思路2：做界面数据
        Log.d("地址",Connect.BASE_SEVICE + childrenBean.getUrl());
        lunboAdapter=new LunboAdapter();
        viewPager.setAdapter(lunboAdapter);
        //将轮播图加到listView头部
        newslistview.addHeaderView(headview);

        newsListAdapter=new newsListAdapter();

        newslistview.setAdapter(newsListAdapter);
        //做listview的数据：

        String CACHEDATA=SPtools.getsetsp(mainActivity,Connect.BASE_SEVICE+""+childrenBean.getUrl());
        Log.d("Tag",""+childrenBean.getUrl());
        if(!TextUtils.isEmpty(CACHEDATA)){
              procressData(CACHEDATA);
        }

        final Call<String> lunBocall = RetrofitFactory.getStringService().getLunboData("/zhbj/"+childrenBean.getUrl());

        lunBocall.enqueue(new retrofit2.Callback<String>() {
              @Override
              public void onResponse(Call<String> call, Response<String> response) {

                  if (response.isSuccessful() && response.errorBody() == null) {
                      SPtools.savesetsp(mainActivity,Connect.BASE_SEVICE+""+childrenBean.getUrl(), response.body().toString());
                      Log.d(TAG, "data:" + response.body().toString());
                      //解析数据

                     procressData(response.body().toString());
                  } else {
                      Log.d(TAG, "error code:" + response.code());
                      Log.d(TAG, "error message:" + response.message());
                  }


              }

              @Override
              public void onFailure(Call<String> call, Throwable t) {

              }
          });


    }

//处理获取的数据
    private void procressData(String result) {
        Log.d("输出数据","开始"+"结束");
        if(gson==null)
        gson = new Gson();

        NewContextData newsCenterData = gson.fromJson(result, NewContextData.class);
        //轮播数据获取了

        topnews=newsCenterData.getData().getTopnews();
        //新闻列表数据
        newsListData=newsCenterData.getData().getNews();

        lunboAdapter.notifyDataSetChanged();
        newsListAdapter.notifyDataSetChanged();
    }

    public void initEvent(){
        //设置定时！
        //这个handler本来就是在主线成创建的所以执行还是在主线程
        handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(viewPager.getAdapter().getCount()!=0){
                    viewPager.setCurrentItem((viewPager.getCurrentItem()+1)%viewPager.getAdapter().getCount());
                    handler.postDelayed(this,1000);
                }
                }
        },1000);
        //思路3：做界面数据
        //给小圆点 和Textveiw 设置数据 和对应项
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //这样处理第一个界面的数据怎么办？
                vpTV.setText(topnews.get(position).getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
         //点击跳转至新闻界面
        newslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(mainActivity, NewsActivity.class);

                mainActivity.startActivity(intent);
            }
        });


    }

    public View getRoot() {
        return root;
    }
    //如果没数据也能显示 架子已经写好

    class LunboAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
             //获取图片集合 放到view中
            ImageView lunbo_imageview=new ImageView(mainActivity);
            //这里就需要解析网络图片了
            NewContextData.DataBean.TopnewsBean topnewsdata = topnews.get(position);
            String topnewsimageURL = topnewsdata.getTopimage();

            //ImagerLoader
            //直接将网络图片给添加到组件上了 异步处理已经封装好
            bitmapUtils.bind(lunbo_imageview,topnewsimageURL);

            container.addView(lunbo_imageview);

             return lunbo_imageview;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    class newsListAdapter extends BaseAdapter{
           @Override
           public int getCount() {
               return newsListData.size();
           }

           @Override
           public Object getItem(int position) {
               //带头的怎么处理
               return null;
           }

           @Override
           public long getItemId(int position) {
               return 0;
           }

           @Override
           public View getView(int position, View convertView, ViewGroup parent) {
               LayoutInflater layoutInflater=LayoutInflater.from(mainActivity);

                         NewsHolder holder=null;
               if(convertView==null){
                   holder=new NewsHolder();
                  convertView=   layoutInflater.inflate(R.layout.newslist_item,null);
                   holder.textView = (TextView) convertView.findViewById(R.id.tv_tpi_news_listview_item_title);
                   holder.iv_newspic = (ImageView) convertView.findViewById(R.id.iv_tpi_news_listview_item_pic);
                   holder.tv_time = (TextView) convertView.findViewById(R.id.tv_tpi_news_listview_item_time);
                   convertView.setTag(holder);
               }
               else {
                   holder= (NewsHolder) convertView.getTag();
               }

                  holder.textView.setText(newsListData.get(position).getTitle());
                  holder.tv_time.setText(newsListData.get(position).getPubdate());


               // bitmapUtils.bind(holder.iv_newspic,newsListData.get(position).getListimage());

               DisplayImageOptions options = new DisplayImageOptions.Builder()
                      .resetViewBeforeLoading(false)  // default
                       .delayBeforeLoading(1000)
                       .cacheInMemory(false) // default
                       .cacheOnDisk(false) // default
                       .considerExifParams(false) // default
                       .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                       .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                       .displayer(new SimpleBitmapDisplayer()) // default
                       .handler(new Handler()) // default
                       .build();
               ImageSize mImageSize = new ImageSize(100, 100);

               //显示图片的配置
               DisplayImageOptions optionss = new DisplayImageOptions.Builder()
                       .cacheInMemory(true)
                       .cacheOnDisk(true)
                       .bitmapConfig(Bitmap.Config.RGB_565)
                       .build();

               final NewsHolder finalHolder = holder;

               ImageLoader.getInstance().loadImage(newsListData.get(position).getListimage(),mImageSize, optionss,  new SimpleImageLoadingListener(){

                   @Override
                   public void onLoadingComplete(String imageUri, View view,
                                                 Bitmap loadedImage) {
                       super.onLoadingComplete(imageUri, view, loadedImage);
                       finalHolder.iv_newspic.setImageBitmap(loadedImage);
                   }

               });

               return convertView;
           }
       }
    class NewsHolder{

        TextView textView;
        ImageView iv_newspic;
        TextView tv_time;
    }

}
