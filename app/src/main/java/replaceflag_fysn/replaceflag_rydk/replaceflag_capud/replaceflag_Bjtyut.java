package replaceflag_fysn.replaceflag_rydk.replaceflag_capud;

import android.content.Context;
import android.provider.Settings;

import java.net.NetworkInterface;
import java.util.HashMap;
import java.util.UUID;

public class replaceflag_Bjtyut {

    public static String get(Context context) {

        // 如果本地已经存储了设备唯一id, 直接使用
        String storedUniqueId = replaceflag_Styuty.getString(replaceflag_Cskuio.SP_DEVICE_UNIQUE_ID, "");
        if (!replaceflag_Snnjki.isEmpty(storedUniqueId)) {
            return storedUniqueId;
        }

        String uniqueId = null;
        StringBuilder uniqueIdSb = new StringBuilder();

        String prefix = "";


        HashMap<String, String> map = new HashMap<>();
        // 获取Android ID
        uniqueId = getAndroidId(context);
        map.put("devices_id", uniqueId);

        if (!replaceflag_Snnjki.isEmpty(uniqueId)) {
            uniqueIdSb.append(uniqueId);
            prefix += "A-";
        } else {
            prefix += "0-";
        }

        //获取IMEI(也就是常说的DeviceId)
//        uniqueId = getIMIEStatus(context);
//        if (!StringUtil.isEmpty(uniqueId)) {
//            uniqueIdSb.append(uniqueId);
//            prefix += "I-";
//        } else {
//            prefix += "0-";
//        }

        // 获取MAC地址
        uniqueId = getLocalMac(context);
        map.put("mac_id", uniqueId);

        if (!replaceflag_Snnjki.isEmpty(uniqueId)) {
            uniqueIdSb.append(uniqueId);
            prefix += "M-";
        } else {
            prefix += "0-";
        }

        // 如果以上都没获取到则在本地生成唯一ID, 并保存到SP中
        if (uniqueIdSb.length() == 0) {
            UUID uuid = UUID.randomUUID();
            uniqueId = uuid.toString().replace(" ", "");
            uniqueIdSb.append(uniqueId);
            prefix += "U-";
        } else {
            prefix += "0-";
        }

        String uniqueIdSbMD5 = replaceflag_Ennjkl.encryptMD5ToString(uniqueIdSb.toString());
        String result = prefix + uniqueIdSbMD5;
        replaceflag_Styuty.putString(replaceflag_Cskuio.SP_DEVICE_UNIQUE_ID, result);

        map.put("device_unique_id", result);
//        Stat.get().reportKVEvent(StatConstant.DEVICE_UNIQUE_ID, map);
        return result;
    }

    private static String getAndroidId(Context context) {
        // 获取 Android ID, 部分厂商会屏蔽, 无法获取, 或者获取到无效的android id
        String ai = Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (replaceflag_Snnjki.isEmpty(ai)) {
            return null;
        }

        // 无效 ID
        if (replaceflag_Snnjki.equals(ai, "9774d56d682e549c")) {
            return null;
        }

        return ai;
    }

    /**
     * 获取设备的DeviceId(IMES) 这里需要相应的权限<br/>
     * 需要 READ_PHONE_STATE 权限
     *
     * @param context
     * @return
     */
    /*public static String getIMIEStatus(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String deviceId = tm.getDeviceId();
            return deviceId;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }*/

    /**
     * 获取设备MAC 地址 由于 6.0 以后 WifiManager 得到的 MacAddress得到都是 相同的没有意义的内容
     * 所以采用以下方法获取Mac地址
     *
     * @param context
     * @return
     */
    private static String getLocalMac(Context context) {
//        WifiManager wifi = (WifiManager) context
//                .getSystemService(Context.WIFI_SERVICE);
//        WifiInfo info = wifi.getConnectionInfo();
//        return info.getMacAddress();
        String macAddress = null;
        StringBuilder buf = new StringBuilder();
        NetworkInterface networkInterface = null;
        try {
            networkInterface = NetworkInterface.getByName("eth1");
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName("wlan0");
            }
            if (networkInterface == null) {
                return "";
            }
            byte[] addr = networkInterface.getHardwareAddress();


            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            macAddress = buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return macAddress;
    }

}
