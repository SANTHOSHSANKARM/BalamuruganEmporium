package com.example.saravanan.balamuruganemporium;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class BillCalculate extends AppCompatActivity {
    SQLiteDatabase db,db1;
    EditText et1,et2,et3,et4,et5;
    Button b1,b2;
    TextView t1,t2;

    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_calculate);
        et1=(EditText)findViewById(R.id.editText1);
        et2=(EditText)findViewById(R.id.editText2);
        et3=(EditText)findViewById(R.id.editText3);
        et4=(EditText)findViewById(R.id.editText4);
        et5=(EditText)findViewById(R.id.editText5);
        t1=(TextView)findViewById(R.id.textView5);
        t2=(TextView)findViewById(R.id.textView7);
        b1=(Button)findViewById(R.id.button1);
        b2=(Button)findViewById(R.id.button2);

        addListenerOnButton();

        db= openOrCreateDatabase("mydb", MODE_PRIVATE, null);
        db1= openOrCreateDatabase("mydb1", MODE_PRIVATE, null);
        db1.execSQL("create table if not exists t4(Bill varchar,GSTAmt varchar,BuyingItem varchar,total varchar)");
    }
    private void addListenerOnButton() {
        // TODO Auto-generated method stub

        imageButton = (ImageButton) findViewById(R.id.imageButton1);

        imageButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                String id=et1.getText().toString();

                boolean c=check(id);
                if(c)
                {
                    Toast.makeText(getApplicationContext(), "Your Item is presented", Toast.LENGTH_LONG).show();
                    Cursor c1=db.rawQuery("select * from item0 where ItemId='"+id+"' ", null);

                    c1.moveToFirst();
                    do
                    {
                        String iname=c1.getString(1);
                        String ipprice=c1.getString(3);
                        et2.append(iname);
                        et3.append(ipprice);
                    }while(c1.moveToNext());

                }

                else
                {
                    Toast.makeText(getApplicationContext(), " Not presented", Toast.LENGTH_LONG).show();
                }

            }


        });


        b1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                String id=et1.getText().toString();
                String  price=et3.getText().toString();
                String buy=et4.getText().toString();
                String bill=et5.getText().toString();
                if(buy.length()>0 ){
                    int a=Integer.parseInt(price);
                    int b=Integer.parseInt(buy);
                    int c=a*b;

                    String c1=String.valueOf(c);
                    double amount = Double.parseDouble(c1);
                    double gst = (amount / 100.0f) * 12;

                    boolean ch=check1(bill);
                    if((ch)){
                        Toast.makeText(getApplicationContext(), "('_')Already  Entered Bill ID ('_')  ", Toast.LENGTH_LONG).show();
                    }
                    else
                    {

                        Cursor c2=db.rawQuery("select * from item0 where ItemId='"+id+"' ", null);
                        c2.moveToFirst();
                        do
                        {
                            String ic=c2.getString(2);
                            int count1=Integer.parseInt(ic);

                            if(count1>=b){
                                db1.execSQL("insert into t4 values('"+bill+"','"+gst+"','"+buy+"','"+c1+"')");
                                Toast.makeText(getApplicationContext(), "Your Bill details stored", Toast.LENGTH_LONG).show();


                                Toast.makeText(getApplicationContext(), "GST amount"+gst, Toast.LENGTH_LONG).show();
                                int  sub=count1-b;
                                String st=String.valueOf(sub);
                                pass(st,id);
                                Intent i=new Intent(getApplicationContext(),Bill.class);
                                startActivity(i);


                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Available Stock is "+count1, Toast.LENGTH_LONG).show();
                                //et1.setText("");
                                et2.setText("");
                                et3.setText("");
                                et4.setText("");
                                et5.setText("");
                            }
                        }while(c2.moveToNext());

                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please Enter Your Item Count",Toast.LENGTH_LONG).show();
                    //et1.setText("");
                    et2.setText("");
                    et3.setText("");
                    et4.setText("");
                    et5.setText("");
                }




            }

            private boolean check1(String bill) {
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



            private void pass(String st,String id) {
                // TODO Auto-generated method stub
                Cursor c3=db.rawQuery("update item0 set ItemCount='"+st+"' where ItemId='"+id+"'", null);
                c3.moveToFirst();
                do
                {
                    t1.setText(st);
                    int st1=Integer.parseInt(st);
                    if(st1 >5 )
                    {
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Total item count is less than 5 !! \nYour Current Itemcount is"+st,Toast.LENGTH_LONG).show();
                        alert();
                    }
                }while(c3.moveToNext());
                et2.setText("");
                et3.setText("");
                et4.setText("");
            }

        });

        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i=new Intent(getApplicationContext(),Bill.class);
                startActivity(i);
            }
        });

    }
    protected void alert() {
        // TODO Auto-generated method stub

        NotificationCompat.Builder mBuilder =new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(" Notification")
                .setContentText("Your Items is Too low");
        // Gets an instance of the NotificationManager service//
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(001, mBuilder.build());



    }





    protected boolean check(String id) {
        // TODO Auto-generated method stub
        String sql = "select * from item0 where ItemId='"+id+"'";
        Cursor c=db.rawQuery(sql, null);

        if(c!=null && c.getCount() > 0){


            return true;
        }
        else
        {
            return false;
        }


    }



}
