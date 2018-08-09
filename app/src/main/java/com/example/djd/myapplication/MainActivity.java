package com.example.djd.myapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.djd.myapplication.service.Book;
import com.example.djd.myapplication.service.IBookManager;
import com.example.djd.myapplication.service.IOnNewBookArrivedListener;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

@Route(path = PathContance.MAIN_ACTIVITY_PATH)
public class MainActivity extends AppCompatActivity {

    private ImageView mIv;
    private IBookManager iBookManager;
    private TextView mTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ARouter.getInstance().inject(this);
        mIv = (ImageView) findViewById(R.id.iv_animation_test);
        findViewById(R.id.tv_navigation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //jumpToSecondActivity();
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.example.djd.myapplication",
                        "com.example.djd.myapplication.service.MyService"));
                bindService(intent,conn,BIND_AUTO_CREATE);
            }
        });

        Handler handler = new Handler();
        //handler.post()
        mIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(mIv.getContext(),R.anim.show);
                //mIv.setAnimation(animation);
                mIv.startAnimation(animation);
            }
        });




        mTv = (TextView) findViewById(R.id.tv_anmation);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append("Hello world ha ha");
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#009ad6"));
        spannableStringBuilder.setSpan(foregroundColorSpan,0,3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTv.setText(spannableStringBuilder);
        PackageInfo packageInfo = null;
        try {
            packageInfo = this.getPackageManager().getPackageInfo("com.example.djd.myapplication",0);
            Log.i("test", "onDestroy: "+packageInfo.versionName+"code"+packageInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void onClickAnimaiton(View view){
        Intent intent = new Intent(MainActivity.this, AnimationActivity.class);
        startActivity(intent);
    }


    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iBookManager = IBookManager.Stub.asInterface(service);
            try {
                if (iBookManager == null){
                    return;
                }
                iBookManager.registerOnNewBookArrivedListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    IOnNewBookArrivedListener.Stub listener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void newBookArrived(Book book) throws RemoteException {
            Log.i("test", "newBookArrived: "+book.toString());
        }
    };

    

    private void jumpToSecondActivity(){
        ARouter.getInstance().build(PathContance.SECOND_ACTIVITY_PATH)
                .navigation(MainActivity.this, new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {
                        Log.i(PathContance.TAG, "onArrival:Group "+postcard.getGroup()+"path"+postcard.getPath());
                        test();
                    }

                    @Override
                    public void onFound(Postcard postcard) {
                        super.onFound(postcard);
                        Log.i(PathContance.TAG, "onFound:Group "+postcard.getGroup()+"path"+postcard.getPath());
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {
                        super.onInterrupt(postcard);
                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        super.onLost(postcard);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (listener != null && iBookManager!=null && iBookManager.asBinder().isBinderAlive()) {
            try {
                iBookManager.unregisterOnNewBookArrivedListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }



        unbindService(conn);
    }

    private void test(){
        Log.i("TAG", "test: "+Log.getStackTraceString(new Throwable()));
    }




}
