package replaceflag_fysn.replaceflag_rydk.replaceflag_capud;

import android.app.Activity;
import android.app.role.RoleManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_aas.replaceflag_Bteert;

public class replaceflag_NewEnterActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replaceflag_call_screening_guide);
        this.getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        findViewById(R.id.goToNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pkg = Telephony.Sms.getDefaultSmsPackage(replaceflag_NewEnterActivity.this);
                if (!getPackageName().equals(pkg)) {
                    try {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                            RoleManager roleManager = getSystemService(RoleManager.class);
                            // check if the app is having permission to be as default SMS app boolean
                            boolean isRoleAvailable = roleManager.isRoleAvailable(RoleManager.ROLE_SMS);
                            if (isRoleAvailable) {
                                Intent roleRequestIntent = roleManager.createRequestRoleIntent(RoleManager.ROLE_SMS);
                                startActivityForResult(roleRequestIntent, 1111);
                            }
                        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                            Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, getPackageName());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            startActivity(intent);
                        }
                    } catch (Exception e) {
                    }
                } else {
                    goMain();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1111)
            goMain();
//        Log.e("=====>",""+requestCode+" "+resultCode);
    }

    private void goMain() {
        Intent sayHelloIntent = new Intent(replaceflag_NewEnterActivity.this, replaceflag_NewMainActivity.class);
//        Intent sayHelloIntent = new Intent(replaceflag_NewEnterActivity.this, replaceflag_Bteert.class);
        sayHelloIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(sayHelloIntent);
        finish();
    }

    @Override
    public void onBackPressed() {

    }

}
