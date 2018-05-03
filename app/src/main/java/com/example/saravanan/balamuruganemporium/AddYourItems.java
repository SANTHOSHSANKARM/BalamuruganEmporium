package com.example.saravanan.balamuruganemporium;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddYourItems extends AppCompatActivity {

    SQLiteDatabase db;
    Button b1;
    EditText et1,et2,et3,et4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_your_items);

        et1=(EditText)findViewById(R.id.editText1);
        et2=(EditText)findViewById(R.id.editText2);
        et3=(EditText)findViewById(R.id.editText3);
        et4=(EditText)findViewById(R.id.editText4);
        b1=(Button)findViewById(R.id.btn1);
        db= openOrCreateDatabase("mydb", MODE_PRIVATE, null);
        db.execSQL("create table if not exists item0(ItemId varchar,ItemName varchar, ItemCount varchar,ItemPrice varchar,unique(ItemId,ItemName))");
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                String iname=et1.getText().toString();
                String icount=et2.getText().toString();
                String ipprice=et3.getText().toString();
                String iid=et4.getText().toString();

                et1.setText("");
                et2.setText("");
                et3.setText("");
                et4.setText("");
                //insert data into able
                boolean ch=check(iid);
                boolean ch1=check1(iname);


                if((ch||ch1)){
                    Toast.makeText(getApplicationContext(), "Already  Entered Id,Name  ", Toast.LENGTH_LONG).show();


                }


                else{
                    if(iname.length() > 0 && icount.length() > 0 && ipprice.length() >0 && iid.length()>0)
                    {
                        db.execSQL("insert into item0 values('"+iid+"','"+iname+"','"+icount+"','"+ipprice+"')");
                        Toast.makeText(getApplicationContext(), "!!!!!!!!!!!Your Item is Added !!!!!!!!!!", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Please Enter Your Items", Toast.LENGTH_LONG).show();
                    }
                }
            }


        });

    }



    protected boolean check(String iid) {
        // TODO Auto-generated method stub
        String sql = "select * from item0 where ItemId='"+iid+"' " ;
        Cursor c=db.rawQuery(sql, null);
        if(c!=null && c.getCount() > 0)
        {
            return true;
        }

        else
        {
            return false;
        }
    }
    protected boolean check1(String iname) {
        // TODO Auto-generated method stub
        String sql = "select * from item0 where ItemName='"+iname+"' " ;
        Cursor c=db.rawQuery(sql, null);
        if(c!=null && c.getCount() > 0)
        {
            return true;
        }

        else
        {
            return false;
        }

    }




}
