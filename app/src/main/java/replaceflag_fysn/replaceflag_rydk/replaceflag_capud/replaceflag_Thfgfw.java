package replaceflag_fysn.replaceflag_rydk.replaceflag_capud;


import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


public class replaceflag_Thfgfw {

    private static final Context context = replaceflag_Ahrtry.getApplication();

    private static Toast toast;


    public static void show(int stringId) {
        try {
            show(context.getResources().getString(stringId));
        } catch (Exception e) {
        }
    }

    public static void show(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }
    public static void showlong(CharSequence text) {
        show(text, Toast.LENGTH_LONG);
    }
    public static void showCenterGravity(CharSequence text) {
        showCenterGravity(text, Toast.LENGTH_SHORT);
    }
    public static void showCenterGravitylong(CharSequence text) {
        showCenterGravity(text, Toast.LENGTH_LONG);
    }
    public static void showCenterGravity(CharSequence text, int duration) {
        Toast toast = show(text, duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
    }

    public static Toast show(CharSequence text, int duration) {
        try {
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(context, text, duration);
            toast.show();
        } catch (Exception e) {

        }
        return toast;
    }

}
