package com.example.qing;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditInfo extends ActionBarActivity {

	EditText en = null;
	EditText et = null;
	Button bt = null;
	Button bt2 = null;
	long id = -1;
	
	@Override
	protected void onCreate(Bundle x){
		super.onCreate(x);
        setContentView(R.layout.edit);
        
        en = (EditText)this.findViewById(R.id.name);
        et = (EditText)this.findViewById(R.id.tel);
        bt = (Button)this.findViewById(R.id.submit);
        bt2 = (Button)this.findViewById(R.id.del);
        
        this.setTitle("新增联系人");
        bt2.setVisibility(View.GONE);
        
        Bundle ex = getIntent().getExtras();
        if(ex!=null){        	
        	bt2.setVisibility(View.VISIBLE);
        	this.setTitle("修改联系人");
        	en.setText(ex.getString("name"));
        	et.setText(ex.getString("tel"));
        	id = ex.getLong("id");
        }        
        
        bt.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {												
				Intent i = new Intent();
				String name = en.getText().toString();
				String tel = et.getText().toString();
				Bundle b = new Bundle();
				b.putString("name", name);
				b.putString("tel", tel);
				b.putLong("id", id);
				i.putExtras(b);
				setResult(RESULT_OK, i);
				finish();
			}
        });  
        
        bt2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent();
				Bundle b = new Bundle();
				b.putLong("id", id);
				i.putExtras(b);
				setResult(499, i);
				finish();
			}
        	
        });
	}
	
}
