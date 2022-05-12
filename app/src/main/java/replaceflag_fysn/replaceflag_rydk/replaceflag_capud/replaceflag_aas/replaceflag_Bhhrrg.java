package replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_aas;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.R;
import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio;
import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty;

public class replaceflag_Bhhrrg extends Activity {
    private String mPkgName;

    private ImageView mIcon;
    private TextView mName;
    private TextView mVersion;
    private TextView mPkgTv;
    private TextView mRemove;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replaceflag_activitydetageq);
        mIcon = findViewById(R.id.img);
        mName = findViewById(R.id.name);
        mVersion = findViewById(R.id.version);
        mPkgTv = findViewById(R.id.pkg_tv);
        mRemove = findViewById(R.id.remove);
        mPkgName = getIntent().getStringExtra("pkg");
        getInfo(mPkgName);
        mRemove.setOnClickListener(v -> {
            showInstalledAppDetails(this, mPkgName);
        });

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        registerReceiver(new McReceiver(), intentFilter);
    }

    private void showInstalledAppDetails(Context context, String packageName) {
        Uri packageURI = Uri.parse("package:" + packageName);
        // 创建Intent意图
        Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
        // 执行卸载程序
        context.startActivity(intent);
    }

    private void getInfo(String pakgename) {
        PackageManager pm = getPackageManager();
        try {
            ApplicationInfo appInfo = pm.getApplicationInfo(pakgename, PackageManager.GET_META_DATA);
            String name = pm.getApplicationLabel(appInfo).toString();
            Drawable appIcon = pm.getApplicationIcon(appInfo);
            mIcon.setBackground(appIcon);
            mName.setText(name);
            mVersion.setText(getString(R.string.replaceflag_tip41) + " " + pm.getPackageInfo(pakgename, 0).versionName);
            mPkgTv.setText(mPkgName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private class McReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())){
                replaceflag_Styuty.putBoolean(replaceflag_Cskuio.SP_SHOW_DANGER, false);
                finish();
            }
        }
    }
}
