package com.example.qing;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.WindowManager;
import android.widget.Toast;

public class SMSRecv extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		
		Bundle bundle = arg1.getExtras();  
        SmsMessage msg = null;  
        if (null != bundle) {  
            Object[] smsObj = (Object[]) bundle.get("pdus");
            for (Object object : smsObj) {
                msg = SmsMessage.createFromPdu((byte[]) object); 
                Date date = new Date(msg.getTimestampMillis());//ʱ��  
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
                String receiveTime = format.format(date);  
                 String re = "������:" + msg.getOriginatingAddress()  
                         + "\n\n��������:" + msg.getDisplayMessageBody() + "\n\nʱ��:"  
                         + receiveTime;
                if (msg.getOriginatingAddress().equals("10086")) {  
                    Toast.makeText(arg0, "�����ض���", Toast.LENGTH_LONG).show();
                }  else{
            		AlertDialog al = new AlertDialog
            		    	.Builder(arg0)
            		    	.setTitle("����")
            		    	.setMessage(re)
            		    	.setPositiveButton("OK", null)
            		    	.create();            				
            		al.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            		al.show();
                }
            }  
        }  
	}

}
