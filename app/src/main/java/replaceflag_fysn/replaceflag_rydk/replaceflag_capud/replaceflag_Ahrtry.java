package replaceflag_fysn.replaceflag_rydk.replaceflag_capud;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;

import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_aas.replaceflag_Begryt;


public class replaceflag_Ahrtry extends Application {

    private static Application mApplication;
    public static ArrayList<replaceflag_Begryt> mPDatasOnly = new ArrayList();

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        CrashReport.initCrashReport(getApplicationContext(), "fe41da6255", false);
        replaceflag_Abvffg.init(this);
    }

    private boolean isMainProcess(Context context) {
        int pid = android.os.Process.myPid();
        String currentProcessName = "";
        ActivityManager manager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
            if (process.pid == pid) {
                currentProcessName = process.processName;
            }
        }

        if (currentProcessName == null || "".equals(currentProcessName)) {
            return true;
        }

        return context.getApplicationContext().getPackageName().equals(currentProcessName);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        replaceflag_Styuty.putBoolean(replaceflag_Cskuio.SP_SHOW_DANGER, false);
        replaceflag_Abvffg.removeHandler();
    }

    public static Application getApplication(){
        return mApplication;
    }
}
