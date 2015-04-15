package com.example.qing;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ActionBarActivity {

	ListView li = null;
	EditText in = null;
	Button fi = null;
	SQLiteDatabase db;
	ListAdapter ad;
	Cursor c;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.setTitle("联系人");
        li = (ListView)findViewById(R.id.listView1);
        in = (EditText)findViewById(R.id.input);
        fi = (Button)findViewById(R.id.find);
        
        db = openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null); 
        db.execSQL("CREATE TABLE IF NOT EXISTS person (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, tel VARCHAR)");        
    }
    
    @Override
    protected void onResume(){
    	super.onResume();

    	c = db.rawQuery("SELECT * FROM person", null);
    	disp();
    	
        fi.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {				
				c = db.rawQuery("SELECT * FROM person WHERE name = ? OR tel = ?", new String[]{in.getText().toString(),in.getText().toString()});
				if(in.getText().toString().length()==0){
					c = db.rawQuery("SELECT * FROM person", null);
				}
				disp(c);				
			}        	
        });              
        
        li.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {        
				c.moveToPosition(arg2);
				String tel = c.getString(c.getColumnIndexOrThrow("tel"));
				Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + tel));             
	            startActivity(intent);  
			}        	
        });
        
        li.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				c.moveToPosition(arg2);
				Intent i = new Intent(MainActivity.this, EditInfo.class);
				long id = arg3;
				String name = c.getString(c.getColumnIndexOrThrow("name"));
				String tel = c.getString(c.getColumnIndexOrThrow("tel"));
				Bundle b = new Bundle();
				b.putLong("id", id);
				b.putString("name", name);
				b.putString("tel", tel);
				i.putExtras(b);
				startActivityForResult(i, 998);
				return true;
			}        	
        });
        
    }
    
    @Override
    protected void onDestroy(){
    	super.onDestroy();
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add) {
        	Intent intent = new Intent(this, EditInfo.class);
        	startActivityForResult(intent, 999);
            return true;
        }
        if(id==R.id.about){
        	new AlertDialog
        	.Builder(this)
        	.setTitle("ABOUT")
        	.setMessage("SanKo\n2015-4-9")
        	.setPositiveButton("OK", null)
        	.show();
        }
        if(id==R.id.init){
        	db.execSQL("DROP TABLE IF EXISTS person");
        	db.execSQL("CREATE TABLE person (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, tel VARCHAR)");
        	db.execSQL("INSERT INTO person VALUES (NULL, ?, ?)", new Object[]{"sanko", "647621"});
        	db.execSQL("INSERT INTO person VALUES (NULL, ?, ?)", new Object[]{"Qing", "637613"});  
        	db.execSQL("INSERT INTO person VALUES (NULL, ?, ?)", new Object[]{"Daniel", "15757827999"});
        	db.execSQL("INSERT INTO person VALUES (NULL, ?, ?)", new Object[]{"尼克", "057488889999"});  
        	disp();
        	new AlertDialog
        	.Builder(this)
        	.setMessage("已重置")
        	.show();
        }
        return super.onOptionsItemSelected(item);
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	super.onActivityResult(requestCode, resultCode, intent);
    	
    	if(resultCode==0);
    	else if(resultCode==499){
    		Bundle ex = intent.getExtras();
    		long id = ex.getLong("id");
    		delete(id);
    	}    	
    	else if(requestCode==999){
    		Bundle ex = intent.getExtras();
    		String name = ex.getString("name");
    		String tel = ex.getString("tel");
    		add(name,tel);
    	}
    	else if(requestCode==998){
    		Bundle ex = intent.getExtras();
    		String name = ex.getString("name");
    		String tel = ex.getString("tel");
    		long id = ex.getLong("id");
    		modify(name,tel,id);
    	}
    	
    	disp();
    }
    
    private void delete(long id) {		
    	db.delete("person", "_id = "+id, null);
	}

	private void modify(String name, String tel,long id) {
    	ContentValues cv = new ContentValues();  
        cv.put("name", name);
        cv.put("tel", tel);
        db.update("person", cv, "_id = "+id, null);
	}

	private void disp(){
    	Cursor c = db.rawQuery("SELECT * FROM person", null); 
    	disp(c);
    }
    
    private void disp(Cursor c) {
    	int d = c.getCount();
        in.setHint("共 "+d+" 位联系人");
    	ad = new SimpleCursorAdapter(
        		this,
        		android.R.layout.simple_expandable_list_item_2,
        		c,
        		new String[]{"name","tel"},
        		new int[]{android.R.id.text1,android.R.id.text2});        
        li.setAdapter(ad);
	}

	public void add(String name,String tel){		
		ContentValues cv = new ContentValues();  
        cv.put("name", name); 
        cv.put("tel", tel);
        db.insert("person", null, cv);
    }
    
}
