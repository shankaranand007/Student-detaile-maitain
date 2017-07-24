package com.shankar.stddetailes;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements android.view.View.OnClickListener {
	
	Button add,delete,modify,view,all;
	EditText name,roll,arriyar;
	SQLiteDatabase db;
	Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add=(Button)findViewById(R.id.button1);
        delete=(Button)findViewById(R.id.button2);
        modify=(Button)findViewById(R.id.button3);
        view=(Button)findViewById(R.id.button4);
        all=(Button)findViewById(R.id.button5);
        name=(EditText)findViewById( R.id.name);
        roll=(EditText)findViewById(R.id.roll);
        arriyar=(EditText)findViewById(R.id.editText3);
         
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        modify.setOnClickListener(this);
        view.setOnClickListener(this);
        all.setOnClickListener(this);
        
        db=openOrCreateDatabase("StudentDB", CONTEXT_IGNORE_SECURITY, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno VARCHAR,name VARCHAR,arriyar VARCHAR);");
        
       
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==add)
		{
			if(name.getText().toString().trim().length()==0 || roll.getText().toString().trim().length()==0||arriyar.getText().toString().trim().length()==0)
			{
				showmessage("Wrong");
				return;
			}
			db.execSQL("INSERT INTO student VALUES ('"+name.getText()+"','"+roll.getText()+"','"+arriyar.getText()+"');");
			showmessage("success");
			cleartext();
		}
		if(v==delete)
		{
			if(roll.getText().toString().trim().length()==0)
			{
				showmessage("wrong");
				return;
			}
			c=db.rawQuery("SELECT * FROM student WHERE rollno='"+roll.getText()+"'",null);
			
			if(c.moveToFirst())
			{
				db.execSQL("DELETE FROM student WHERE rollno='"+roll.getText()+"'");
				showmessage("success");
			}
			else{
				showmessage("No Values");
				
			}
			cleartext();
		}
		if(v==modify)
		{
			if(roll.getText().toString().trim().length()==0)
			{
				showmessage("wroong");
				return;
			}
			c=db.rawQuery("SELECT * FROM student WHERE rollno='"+roll.getText()+"'", null);
			
			if(c.moveToFirst())
			{
				db.execSQL("UPDATE FROM student SET rollno='"+roll.getText()+"',name='"+name.getText()+"',arriyar='"+arriyar.getText()+"'");
				showmessage("success");
			}
			else{
				showmessage("No Values");
				cleartext();
			}
		}
		if(v==all)
		{
			c=db.rawQuery("SELECT * FROM student", null);
			if(c.getCount()==0)
			{
				showmessage("Error");
			}
			StringBuffer strb = new StringBuffer();
			while(c.moveToNext())
			{
				strb.append("Rollno:" +c.getString(0)+"\n");
				strb.append("Name:" +c.getString(1)+"\n");
				strb.append("Arrier:" +c.getString(2)+"\n");
			}
			showmessage(strb.toString());
		}
	}


	private void cleartext() {
		// TODO Auto-generated method stub
		
		roll.setText("");
		name.setText("");
		arriyar.setText("");
		roll.requestFocus();
		
	}


	private void showmessage(String stringm) {
		// TODO Auto-generated method stub
		Builder builder = new Builder(this);
		builder.setMessage(stringm);
		builder.setCancelable(true);
		builder.show();
		
		
	}
    
}
