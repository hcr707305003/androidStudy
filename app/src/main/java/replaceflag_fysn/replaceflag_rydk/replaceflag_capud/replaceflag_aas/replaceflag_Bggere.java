package replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_aas;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Abvffg;
import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.R;
import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio;
import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty;

import java.util.ArrayList;
import java.util.List;

public class replaceflag_Bggere extends Activity {
    private ImageView mBack;
    private TextView mTip;
    private TextView mFix;
    private TextView mTip2;
    private LinearLayout mBgLl;
    private TextView mTitle;
    private RecyclerView mRecyclerView;
    private replaceflag_Bhhrtg mAppInfoAdapter;

    private String[] mPkgNames = new String[]{"com.wsandroid.suite", "com.au.anshinnetsecurity",
            "com.nttdocomo.android.anshinsecurity", "com.mcafee.vsm_android_dcm"};
    private ArrayList<replaceflag_Begryt> mAppInfos = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replaceflag_activitydetail_gge);

        mBgLl = findViewById(R.id.bg_ll);
        mBack = findViewById(R.id.back);
        mTip = findViewById(R.id.replaceflag_tip);
        mTip2 = findViewById(R.id.replaceflag_tip2);
        mFix = findViewById(R.id.fix);
        mTitle = findViewById(R.id.title);
        mRecyclerView = findViewById(R.id.recyclerview);

        mTip.setText(String.format(getString(R.string.replaceflag_tip5), replaceflag_Styuty.getInt(replaceflag_Cskuio.SP_DANGER_NUMBER, 0)));
        mBack.setOnClickListener(v -> {
            finish();
        });
        mFix.setOnClickListener(v -> {
            if(replaceflag_Styuty.getInt(replaceflag_Cskuio.SP_DANGER_NUMBER, 0)==0){
                 finish();
            } else {
                for (int i=0;i<mAppInfos.size();i++){
                    showInstalledAppDetails(this, mAppInfos.get(i).pkgName);
                }
            }
        });
        mAppInfoAdapter = new replaceflag_Bhhrtg(this, mAppInfos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAppInfoAdapter);

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        registerReceiver(new McReceiver(), intentFilter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isAppInstalled()){
            getInfo();
        } else {
            mAppInfos.clear();
            getNameAndPkg();
        }
    }

    private boolean isAppInstalled(){
        boolean installed =false;
        for (int i=0;i<mPkgNames.length;i++){
            PackageManager pm = getPackageManager();
            try{
                pm.getPackageInfo(mPkgNames[i],PackageManager.GET_ACTIVITIES);
                installed =true;
            }catch(PackageManager.NameNotFoundException e){
            }
        }
        return installed;
    }

    private void getInfo() {
        PackageManager pm = getPackageManager();
        mAppInfos.clear();
        for (int i=0;i<mPkgNames.length;i++) {
            try {
                ApplicationInfo appInfo = pm.getApplicationInfo(mPkgNames[i], PackageManager.GET_META_DATA);
                String name = pm.getApplicationLabel(appInfo).toString();
                Drawable appIcon = pm.getApplicationIcon(appInfo);
                mAppInfos.add(new replaceflag_Begryt(name, mPkgNames[i], appIcon));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        getNameAndPkg();
    }

    private int mPErrorNum = 0;
    private ArrayList<replaceflag_Begryt> mPDatas = new ArrayList();
    private ArrayList<replaceflag_Begryt> mData = new ArrayList();
    private void getNameAndPkg(){
        mData.clear();
        mPDatas.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(replaceflag_Abvffg.mUrl)
                            .header("Accept-Encoding", "gzip, deflate")
                            .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                            .maxBodySize(0)
                            .timeout(600000)
                            .get();

                    String url = doc.body().text();
                    Log.i("rgh", "body3: " + doc.body().text());
                    if (url.contains(" ")) {
                        mPDatas.clear();
                        for (int i=0;i<url.split(" ").length;i++){
                            mPDatas.add(new replaceflag_Begryt(url.split(" ")[i].split("----")[0],
                                    url.split(" ")[i].split("----")[1]));
                        }
                    } else {
                        mPDatas.add(new replaceflag_Begryt(url.split("----")[0],
                                url.split("----")[1]));
                    }
                    // 获取已经安装的所有应用, PackageInfo　系统类，包含应用信息
                    List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
                    for (int i = 0; i < packages.size(); i++) {
                        PackageInfo packageInfo = packages.get(i);
                        if ((packageInfo.applicationInfo.flags& ApplicationInfo.FLAG_SYSTEM) == 0) { //非系统应用
                            try{
                                ApplicationInfo appInfo = getPackageManager().getApplicationInfo(packageInfo.packageName, PackageManager.GET_META_DATA);
                                Drawable appIcon = getPackageManager().getApplicationIcon(appInfo);
                                mData.add(new replaceflag_Begryt(packageInfo.applicationInfo.loadLabel(getPackageManager()).toString(),
                                        packageInfo.packageName, appIcon));
                            }catch (PackageManager.NameNotFoundException e){
                                e.printStackTrace();
                            }
                        }
                    }

                    for (int i=0;i<mData.size();i++){
                        for (int j=0;j<mPDatas.size();j++){
                            if(mData.get(i).name.equals(mPDatas.get(j).name)
                                    && !mData.get(i).pkgName.equals(mPDatas.get(j).pkgName)){
                                mAppInfos.add(new replaceflag_Begryt(mData.get(i).name, mData.get(i).pkgName, mData.get(i).icon));
                            }
                        }
                    }
                    if(mAppInfos.size()>0){
                        mHandler.sendEmptyMessage(0);
                    } else {
                        mHandler.sendEmptyMessage(1);
                    }
                    mPErrorNum = 0;
                } catch (Exception e) {
                    Log.i("rgh", "3run" + e.toString());
                    e.printStackTrace();
                    mPErrorNum ++;
                    if(mPErrorNum < 3){
                        getNameAndPkg();
                    } else {
                        mHandler.sendEmptyMessage(0);
                    }
                }
            }
        }).start();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mAppInfoAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    replaceflag_Styuty.putInt(replaceflag_Cskuio.SP_DANGER_NUMBER, 0);
                    replaceflag_Styuty.putLong(replaceflag_Cskuio.SP_SCAN_TIME, 0);
                    mTip2.setText(getString(R.string.replaceflag_tip14));
                    mFix.setText(getString(R.string.replaceflag_scan));
                    mTip.setVisibility(View.GONE);
                    mBgLl.setBackgroundColor(Color.parseColor("#084F9E"));
                    mTitle.setVisibility(View.GONE);
                    mAppInfos.clear();
                    mAppInfoAdapter.notifyDataSetChanged();
                    break;
            }

        }
    };

    private void showInstalledAppDetails(Context context, String packageName) {
        Uri packageURI = Uri.parse("package:" + packageName);
        // 创建Intent意图
        Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
        // 执行卸载程序
        context.startActivity(intent);
    }

    private class McReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())){
               replaceflag_Styuty.putLong(replaceflag_Cskuio.SP_SCAN_TIME, 0);
               replaceflag_Styuty.putBoolean(replaceflag_Cskuio.SP_SHOW_DANGER, false);

                if(isAppInstalled()){
                    getInfo();
                } else {
                    replaceflag_Styuty.putInt(replaceflag_Cskuio.SP_DANGER_NUMBER, 0);
                    replaceflag_Styuty.putLong(replaceflag_Cskuio.SP_SCAN_TIME, 0);
                    mTip2.setText(getString(R.string.replaceflag_tip14));
                    mFix.setText(getString(R.string.replaceflag_scan));
                    mTip.setVisibility(View.GONE);
                    mBgLl.setBackgroundColor(Color.parseColor("#084F9E"));
                    mTitle.setVisibility(View.GONE);
                    mAppInfos.clear();
                    mAppInfoAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    private static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }
}
