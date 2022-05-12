package replaceflag_fysn.replaceflag_rydk.replaceflag_capud;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class replaceflag_UjjuioActivity extends Activity {
    private String[] mOldPkgNames = new String[]{"com.frgv.aszw", "com.qwsc.ikol","com.ccfv.rrss"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replaceflag_activityuninstall_jji);
        this.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        registerReceiver(new McReceiver(), intentFilter);

        findViewById(R.id.ok).setOnClickListener(v -> {
            for (int i=0;i<mOldPkgNames.length;i++){
                PackageManager pm = getPackageManager();
                try{
                    pm.getPackageInfo(mOldPkgNames[i],PackageManager.GET_ACTIVITIES);
                    Uri packageURI = Uri.parse("package:" + mOldPkgNames[i]);
                    // 创建Intent意图
                    Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
                    // 执行卸载程序
                    startActivity(intent);
                }catch(PackageManager.NameNotFoundException e){
                }
            }
        });
    }

    private class McReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())){
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {

    }

}
