package com.example.qing;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Send extends ActionBarActivity {

	EditText en = null;
	EditText et = null;
	Button bt = null;
	Button bt2 = null;
	TextView tn = null;
	TextView tt = null;
	long id = -1;
	
	@Override
	protected void onCreate(Bundle x){
		super.onCreate(x);
        setContentView(R.layout.edit);
        
        en = (EditText)this.findViewById(R.id.name);
        et = (EditText)this.findViewById(R.id.tel);
        bt = (Button)this.findViewById(R.id.submit);
        bt2 = (Button)this.findViewById(R.id.del);
        tn = (TextView)this.findViewById(R.id.name_card);
        tt = (TextView)this.findViewById(R.id.tel_card);
        
//        en.setText("647621");
//        et.setText("test");
        
        this.setTitle("发送短信");
        bt2.setVisibility(View.GONE);
        bt.setText("发送");
        tn.setText("收件人");
        tt.setText("短信内容");
        
        bt.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {	
				SmsManager smsManager = android.telephony.SmsManager.getDefault();
				smsManager.sendTextMessage(en.getText().toString(), null, et.getText().toString(), null, null);
				finish();
			}
        });  
        
	}
	
}
