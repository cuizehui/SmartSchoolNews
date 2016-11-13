package com.example.cuizehui.smartschool.viewPagerViews;


import android.content.Context;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuizehui.smartschool.R;

import com.example.cuizehui.smartschool.Utils.JsonParser;
import com.example.cuizehui.smartschool.entity.AskAnswer;
import com.example.cuizehui.smartschool.entity.ChatBean;
import com.example.cuizehui.smartschool.entity.Weather;
import com.google.gson.Gson;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUnderstander;
import com.iflytek.cloud.TextUnderstander;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.cloud.SpeechUnderstanderListener;
import com.iflytek.cloud.UnderstanderResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by cuizehui on 2016/8/9at ${time}.
 */
public class SmartServicePager_view  extends  BasePager_view {
    private View root;
    private Button speakbutton;
    private ListView chatListView;
    private SpeechRecognizer mIat;
    //聊天列表的集合
    private List<ChatBean> chatlist;
    private Toast mToast;
    public  RecognizerDialog iatDialog;
    //保存字符串
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private SpeechUnderstander mSpeechUnderstander;
    private TextUnderstander mTextUnderstander;

    private int ret;
    ///chatlistview  适配器
    private ChatAdapter chatAdapter;

    public SmartServicePager_view(Context context) {
        super(context);


    }

    @Override
    public void initView() {
        super.initView();
        tv_title.setText("智慧服务");

        LayoutInflater layoutInflater = LayoutInflater.from(mainActivity);
        root = layoutInflater.inflate(R.layout.smartservice_layout, null);
        fl.addView(root);
        speakbutton = (Button) root.findViewById(R.id.speakBotton);
        chatListView= (ListView) root.findViewById(R.id.chatlist);

    }

    @Override
    public void initData() {
        super.initData();
        mToast = Toast.makeText(mainActivity, "", Toast.LENGTH_SHORT);
        //创建听写对象
        mIat = SpeechRecognizer.createRecognizer(mainActivity, null);
        // 创建UI
        iatDialog  = new RecognizerDialog(mainActivity,mInitListener);
        //创建服务对象
        mSpeechUnderstander = SpeechUnderstander.createUnderstander(mainActivity,null);
        mTextUnderstander = TextUnderstander.createTextUnderstander(mainActivity, mTextUdrInitListener);
        chatlist =new ArrayList();

    }

    @Override
    public void initEvent() {
        super.initEvent();

         speakbutton.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 switch (event.getAction()){
                     case MotionEvent.ACTION_DOWN:
                         speakBottonclickEvent();
                         break;
                     case MotionEvent.ACTION_UP:
                         mSpeechUnderstander.stopUnderstanding();
                          break;
                 }
                 return false;
             }
         });
        chatAdapter =new ChatAdapter();
        chatListView.setAdapter(chatAdapter);

    }
//按钮的点击事件逻辑
    private void speakBottonclickEvent() {
        startSpeakUnderstand();

    }


    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }

    /**
     * 开始听写
     */
     public void startSpeakWrite(){
         setSpeakwriteParam();

         //绑定监听  mIat.startListening(mRecoListener);
         mIat.startListening(mRecoListener);
         //ui监听
         iatDialog.setListener(recognizerDialogListener);
         iatDialog.show();
     }
    /**
     * 开始语音服务
     */
    private void startSpeakUnderstand() {
        //设置参数
        setParam();

        if(mSpeechUnderstander.isUnderstanding()){// 开始前检查状态
            mSpeechUnderstander.stopUnderstanding();

        }else {
            //
            ret = mSpeechUnderstander.startUnderstanding(mSpeechUnderstanderListener);
            if(ret != 0){
                showTip("语义理解失败,错误码:"	+ ret);
            }else {
            }
        }

    }



    /**
     * 语义音频
     */
    private InitListener mSpeechUdrInitListener = new InitListener() {

        @Override
        public void onInit(int code) {

        }
    };

    /**
     * 语义文字
     */
    private InitListener mTextUdrInitListener = new InitListener() {

        @Override
        public void onInit(int code) {

        }
    };

    /**
     * 语义理解回调监听。
     */
    private SpeechUnderstanderListener mSpeechUnderstanderListener = new SpeechUnderstanderListener() {

        @Override
        public void onResult(final UnderstanderResult result) {
            if (null != result) {

                // 显示
                String text = result.getResultString();
                if (!TextUtils.isEmpty(text)) {
                    Log.d("结果是",text+"");
                     dealData(text);

                }
            } else {
                showTip("识别结果不正确。");
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip("当前正在说话，音量大小：" + volume);
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip("结束说话");
        }

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            showTip(error.getPlainDescription(true));
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}

        }
    };
//处理返回的josn数据
    private void dealData(String text) {
        //缓存
        Gson gson =new Gson();

        AskAnswer chatBean=gson.fromJson(text, AskAnswer.class);
       //问的对象
        ChatBean askchatBean=new ChatBean();
        askchatBean.setType(2);
        askchatBean.setMessage(chatBean.getText());
        Log.d("生成了一个对象","添加一个");
        chatlist.add(askchatBean);

        //答的对象
        ChatBean awchatBean=new ChatBean();
        //ask2
        //answer 1
        awchatBean.setType(1);
        if (chatBean.getRc() == 0) {
            if (chatBean.getOperation().equals("ANSWER")) {
                String as = chatBean.getAnswer().getText();
                if (as != null) {
                    awchatBean.setMessage(as);
                } else {
                    awchatBean.setMessage(text);
                }
            } else if (chatBean.getService().equals("weather")) {
                Weather weatherdata = gson.fromJson(text, Weather.class);
                Weather.DataBean.ResultBean result = weatherdata.getData().getResult().get(0);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("空气质量 :" + result.getAirQuality()+"\r\n");
                stringBuilder.append("城市 :" + result.getCity()+"\r\n");
                stringBuilder.append("PM2.5 :" + result.getPm25()+"\r\n");
                stringBuilder.append("天气状况 :" + result.getWeather()+"\r\n");
                stringBuilder.append("温度 :" + result.getTempRange()+"\r\n");
                stringBuilder.append("风向 :" + result.getWind()+"\r\n");
                awchatBean.setMessage(stringBuilder.toString());
            }
              else {

            }

        } else {
            awchatBean.setMessage(text);
        }
        chatlist.add(awchatBean);
        chatAdapter.notifyDataSetChanged();
    }


    /**
     * uiäº‹ä»¶å›žè°ƒ
     */
    RecognizerDialogListener recognizerDialogListener=new RecognizerDialogListener() {
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            processResult(recognizerResult);

        }

        @Override
        public void onError(SpeechError speechError) {

        }
    };

    /***
     * 设置听写识别参数
     */
    public void setSpeakwriteParam(){
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
    }
    /**
     * 设置语义识别参数
     */
    public void setParam(){


            mSpeechUnderstander.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            mSpeechUnderstander.setParameter(SpeechConstant.DOMAIN, "iat");
            mSpeechUnderstander.setParameter(SpeechConstant.NLP_VERSION, "2.0");
            mSpeechUnderstander.setParameter(SpeechConstant.RESULT_TYPE, "json");
    }

    /**
     * 听写识别监听
     */
    private RecognizerListener mRecoListener = new RecognizerListener() {

        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d("Result:", results.getResultString());
             }

        @Override
        public void onError(SpeechError speechError) {

        }

        @Override
        public void onVolumeChanged(int i, byte[] bytes) {

        }

        public void onBeginOfSpeech() {
        }

        public void onVolumeChanged(int volume) {
        }

        public void onEndOfSpeech() {
        }

        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };



    /***
     *  初始化听写识别
     */
    InitListener mInitListener=new InitListener() {
        @Override
        public void onInit(int i) {

        }
    };

    public void processResult(RecognizerResult result){
        String text = JsonParser.parseIatResult(result.getResultString());

        String sn = null;
         try {
            JSONObject resultJson = new JSONObject(result.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }
        Toast.makeText(mainActivity,resultBuffer.toString(),Toast.LENGTH_LONG).show();
    }
    //聊天界面适配器
   class  ChatAdapter extends BaseAdapter{
       public ChatAdapter() {
           super();
       }

       @Override
       public int getCount() {
           return chatlist.size();
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
          final   Holder holder;
           if(convertView==null){
              holder=new Holder();
               LayoutInflater layoutInflater=LayoutInflater.from(mainActivity);
               convertView=layoutInflater.inflate(R.layout.chatlistview_item,null);
               holder.askTextView = (TextView) convertView.findViewById(R.id.tv_asker);
               holder.answerTextView = (TextView) convertView.findViewById(R.id.tv_answer);
               holder.answerImageView = (ImageView) convertView.findViewById(R.id.iv_answer);
               holder.answerLLayout = (LinearLayout) convertView.findViewById(R.id.ll_answer);
               convertView.setTag(holder);
           }
           else {

            holder= (Holder) convertView.getTag();
           }


               ChatBean chatBean   = chatlist.get(position);


           if (chatBean.getType() == ChatBean.ask) {
               //问
               //设置view的显示
               holder.askTextView.setVisibility(View.VISIBLE);

               //答的组件的隐藏
               holder.answerTextView.setVisibility(View.GONE);
               holder.answerImageView.setVisibility(View.GONE);
               holder.answerLLayout.setVisibility(View.GONE);
               //设置问的信息的显示
               holder.askTextView.setText(chatBean.getMessage());
           } else {
               //答
               //设置问的view的隐藏
               holder.answerTextView.setVisibility(View.GONE);

               //答的组件的显示
               holder.answerTextView.setVisibility(View.VISIBLE);
               //设置答的信息的显示
               holder.answerTextView.setText(chatBean.getMessage());
               holder.answerLLayout.setVisibility(View.VISIBLE);
               //是否有图片

               if (chatBean.getPicID() == -1) {
                   //没有图片
                   holder.answerImageView.setVisibility(View.GONE);
               } else {
                   holder.answerImageView.setVisibility(View.VISIBLE);
                   //设置答的图片
                   holder.answerImageView.setImageResource(chatBean.getPic());
               }

       }
           return convertView;

       }

        class  Holder   {
            private TextView askTextView;
            private TextView answerTextView;
            private ImageView answerImageView;
            private LinearLayout answerLLayout;
        }

    }


}
