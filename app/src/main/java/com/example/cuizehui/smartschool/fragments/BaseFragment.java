package com.example.cuizehui.smartschool.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cuizehui.smartschool.R;
import com.example.cuizehui.smartschool.activitys.MainActivity;

public abstract class BaseFragment extends Fragment {
    protected MainActivity mainActivity;//上下文

    // XXXDao dao = new XXXDao(getActivity);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();//获取fragment所在Activity;
    }




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initview();
    }

    abstract public View  initview();
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        intiEvent();
    }
    public void initData(){

    }
    public void intiEvent(){

    }
}
