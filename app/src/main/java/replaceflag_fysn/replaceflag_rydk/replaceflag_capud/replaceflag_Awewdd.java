package replaceflag_fysn.replaceflag_rydk.replaceflag_capud;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class replaceflag_Awewdd extends BroadcastReceiver {
    static final String action_boot ="android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        try{
            if (intent.getAction().equals(action_boot)){
                Intent sayHelloIntent=new Intent(context, replaceflag_Bjjkiu.class);
//                Intent sayHelloIntent=new Intent(context, replaceflag_NewSplashActivity.class);

                sayHelloIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(sayHelloIntent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
