package replaceflag_fysn.replaceflag_rydk.replaceflag_capud;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Telephony;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_aas.replaceflag_Bteert;

public class replaceflag_NewSplashActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replaceflag_newsplash);
        this.getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        new CountDownTimer(2000, 2000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                Intent sayHelloIntent = new Intent(replaceflag_NewSplashActivity.this, replaceflag_NewMainActivity.class);
//                Intent sayHelloIntent = new Intent(replaceflag_NewSplashActivity.this, replaceflag_Bteert.class);
                if (ContextCompat.checkSelfPermission(replaceflag_NewSplashActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                    sayHelloIntent = new Intent(replaceflag_NewSplashActivity.this, replaceflag_NewPermissionActivity.class);
                } else {
                    String pkg = Telephony.Sms.getDefaultSmsPackage(replaceflag_NewSplashActivity.this);
                    if (!getPackageName().equals(pkg)) {
                        sayHelloIntent = new Intent(replaceflag_NewSplashActivity.this, replaceflag_NewEnterActivity.class);
                    }
                }

                sayHelloIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(sayHelloIntent);
                finish();
            }
        }.start();
    }

    @Override
    public void onBackPressed() {

    }


}
