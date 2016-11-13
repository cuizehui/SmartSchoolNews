package com.example.cuizehui.smartschool.viewPagerViews;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.cuizehui.smartschool.R;

import java.util.List;

/**
 * Created by cuizehui on 2016/8/8at ${time}.
 */
public class MapPager_view extends  BasePager_view {
    //定位核心类
    private LocationClient mLocClient;
    //地图对象
    private BaiduMap mBaiduMap;
    private  boolean isFirstLoc=true;
   //位置服务设置
    private LocationClientOption option;

    private MyLocationListenner myListener;

    public MapPager_view(Context context) {
        super(context);
    }


    private MapView mMapView;

    @Override
    public void initView() {
        super.initView();
        tv_title.setText("地图");
        LayoutInflater layoutInflater=LayoutInflater.from(mainActivity);
        LinearLayout view= (LinearLayout) layoutInflater.inflate(R.layout.baidu_map,null);
        fl.addView(view);
        mMapView = (MapView) view.findViewById(R.id.bmapView);
    }

    @Override
    public void initData() {
        super.initData();


        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(mainActivity);
        myListener=new MyLocationListenner();
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();



    }



    @Override
    public void initEvent() {
        super.initEvent();
    }
    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location

            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
             }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }


}
