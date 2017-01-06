package com.example.cuizehui.smartschool.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cuizehui.smartschool.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

//Dagger2

public class NewsActivity extends AppCompatActivity {
    @InjectView(R.id.newsActivity_Vb)
    WebView webView;
    @InjectView(R.id.newsActivity_newsdtail_loading)
    ProgressBar loadingbar;
    private View root;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        initData();
        initEvent();

    }

    private void initEvent() {
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                loadingbar.setVisibility(View.GONE);
            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_news);
        ButterKnife.inject(this);

     }


    private void initData() {
          String  newsUrl =getIntent().getStringExtra("newsUrl");
          Log.d("url",""+newsUrl);
          webView.loadUrl(newsUrl);
    }
}
