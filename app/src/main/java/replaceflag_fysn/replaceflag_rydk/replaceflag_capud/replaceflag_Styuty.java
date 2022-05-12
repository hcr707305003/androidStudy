package replaceflag_fysn.replaceflag_rydk.replaceflag_capud;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import net.grandcentrix.tray.AppPreferences;

public class replaceflag_Styuty {
    public static boolean contains(String k) {
        return getSp().contains(k);
    }

    /**
     * 移除某个数据
     */
    public static boolean remove(String k) {
        SharedPreferences.Editor editor = getSp().edit();
        editor.remove(k);
        return editor.commit();
    }

    /**
     * 保存属性到SP中
     */
    public static boolean putString(String key, String value) {
//        SharedPreferences.Editor editor = getSp().edit();
//        editor.putString(key, value);
//        return editor.commit();

        return getAp().put(key, value);
    }


    /**
     * 从SP中加载属性
     */
    public static String getString(String key, String defaultValue) {
//        String str = getSp().getString(key, defaultValue);
//        return str;

        return getAp().getString(key, defaultValue);
    }

    /**
     * 保存属性到SP中
     */
    public static boolean putInt(String key, int value) {
//        SharedPreferences.Editor editor = getSp().edit();
//        editor.putInt(key, value);
//        return editor.commit();

        return getAp().put(key, value);
    }

    /**
     * 从SP中加载属性
     */
    public static int getInt(String key, int defaultValue) {
//        int v = getSp().getInt(key, defaultValue);
//        return v;

        return getAp().getInt(key, defaultValue);
    }

    /**
     * 保存属性到SP中
     */
    public static boolean putBoolean(String key, boolean value) {
//        SharedPreferences.Editor editor = getSp().edit();
//        editor.putBoolean(key, value);
//        return editor.commit();

        return getAp().put(key, value);
    }

    /**
     * 从SP中加载属性
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
//        boolean v = getSp().getBoolean(key, defaultValue);
//        return v;

        return getAp().getBoolean(key, defaultValue);
    }


    public static boolean putLong(String key, long value) {
//        SharedPreferences.Editor editor = getSp().edit();
//        editor.putLong(key, value);
//        return editor.commit();
        return getAp().put(key, value);
    }

    public static Long getLong(String key, long defaultValue) {
//        return getSp().getLong(key, defaultValue);

        return getAp().getLong(key, defaultValue);
    }


    public static boolean putFloat(String key, float value) {
//        SharedPreferences.Editor edit = getSp().edit();
//        return edit.putFloat(key, value).commit();
        return getAp().put(key, value);
    }

    public static float getFloat(String key, float defaultValue) {
//        return getSp().getFloat(key, defaultValue);
        return getAp().getFloat(key, defaultValue);
    }


    private static SharedPreferences getSp() {
        return PreferenceManager.getDefaultSharedPreferences(replaceflag_Ahrtry.getApplication());
    }

    private static AppPreferences getAp(){
        AppPreferences appPreferences = new AppPreferences(replaceflag_Ahrtry.getApplication());
        return appPreferences;
    }

}
