package com.example.saravanan.balamuruganemporium;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Bill extends AppCompatActivity {
    SQLiteDatabase db,db1;
    EditText et1,et2;
    TextView t7,t8,t9,t10,t11,t12;
    Button b1,b2,b3;
    String st1,st2,id1,bill,Date;
    int c;
    Calendar calander;
    SimpleDateFormat simpledateformat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);


        t8=(TextView)findViewById(R.id.textView8);
        t9=(TextView)findViewById(R.id.textView9);
        t10=(TextView)findViewById(R.id.textView10);
        t11=(TextView)findViewById(R.id.textView11);
        t12=(TextView)findViewById(R.id.textView12);

        et1=(EditText)findViewById(R.id.editText1);
        et2=(EditText)findViewById(R.id.editText2);
        b1=(Button)findViewById(R.id.button1);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        db= openOrCreateDatabase("mydb", MODE_PRIVATE, null);
        db1= openOrCreateDatabase("mydb1", MODE_PRIVATE, null);


        calander = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date = simpledateformat.format(calander.getTime());


        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                t12.setText(Date);
                id1=et1.getText().toString();
                bill=et2.getText().toString();

                if(id1.length()>0 &&  bill.length()>0)
                {	 t8.setText("");
                    t9.setText("");
                    t10.setText("");
                    t11.setText("");
                    Cursor c1=db.rawQuery("select * from item0 where ItemId='"+id1+"' ", null);

                    c1.moveToFirst();
                    do
                    {
                        String iname=c1.getString(1);
                        String st=c1.getString(2);

                        t8.append(iname);
                        st1=st;
                    }while(c1.moveToNext());


                    boolean ch=check(bill);
                    if(ch){
                        Cursor c2=db1.rawQuery("select * from t4 where Bill ='"+bill+"' ", null);
                        c2.moveToFirst();
                        do
                        {

                            String gst=c2.getString(1);
                            String buy=c2.getString(2);
                            String t=c2.getString(3);
                            t9.append(gst);
                            t10.append(buy);
                            t11.append(t);
                            st2=buy;
                        }while(c2.moveToNext());
                    }
                    else
                    {
                        Toast.makeText(getBaseContext(), "!! Your Bill Id Not Found !!", Toast.LENGTH_LONG).show();
                    }
                    int a=Integer.parseInt(st1);
                    int b=Integer.parseInt(st2);
                    c=a+b;

                }
                else
                {
                    Toast.makeText(getBaseContext(), "!! Please Enter Bill Id and Item Id !!", Toast.LENGTH_LONG).show();
                }
            }

            protected boolean check(String bill) {
                // TODO Auto-generated method stub

                String sql = "select * from t4 where Bill='"+bill+"'";
                Cursor c=db1.rawQuery(sql, null);

                if(c!=null && c.getCount() > 0){


                    return true;
                }
                else
                {
                    return false;
                }

            }

        });
        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String bill=et2.getText().toString();


                if(bill.length()>0){
                    db1.rawQuery("delete from t4 where Bill='"+bill+"'",null);
                    Cursor c3=db.rawQuery("update item0 set ItemCount='"+c+"' where ItemId='"+id1+"'", null);
                    c3.moveToFirst();
                    do
                    {

                    }while(c3.moveToNext());
                    et2.setText("");
                    Toast.makeText(getBaseContext(), "your Bill was deleted & updated", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Please Enter Bill Id", Toast.LENGTH_LONG).show();
                }
                t8.setText("");
                t9.setText("");
                t10.setText("");
                t11.setText("");
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i=new Intent(getApplicationContext(),MainActivity1.class);
                startActivity(i);
            }
        });



    }

}