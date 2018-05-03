package com.example.saravanan.balamuruganemporium;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity1 extends AppCompatActivity {
    Button b1,b2,b3;
    TextView t1;
    SQLiteDatabase db;
    int id=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        b1=(Button)findViewById(R.id.button1);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        t1=(TextView)findViewById(R.id.textView1);
        db= openOrCreateDatabase("mydb", MODE_PRIVATE, null);
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i=new Intent(MainActivity1.this,AddYourItems.class);
                startActivity(i);

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i=new Intent(MainActivity1.this,BillCalculate.class);
                startActivity(i);

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub


                StringBuffer buffer=new StringBuffer();
                Cursor c1=db.rawQuery("select * from item0  ", null);
                if(c1.getCount() ==0)
                {
                    showMessage1("welcome","Not found");
                    Toast.makeText(getApplicationContext(), "Please ADD your Items",Toast.LENGTH_LONG).show();
                }

                c1.moveToFirst();
                do
                {
                    buffer.append(c1.getColumnName(0)+"\t\t"+c1.getString(0)+"\n");
                    buffer.append(c1.getColumnName(1)+"\t\t"+c1.getString(1)+"\n");
                    buffer.append(c1.getColumnName(2)+"\t\t"+c1.getString(2)+"\n");
                    buffer.append(c1.getColumnName(3)+"\t\t"+c1.getString(3)+"\n\n");

                    buffer.append("______________________\n");
                }while(c1.moveToNext());

                showMessage("Item List",buffer.toString());


            }


            private void showMessage(String string, String string2) {
                // TODO Auto-generated method stub

                AlertDialog.Builder builder=new  AlertDialog.Builder(MainActivity1.this);
                builder.setCancelable(true);
                builder.setTitle(string);

                builder.setMessage(string2);

                builder.show();
            }
            private void showMessage1(String string, String string2) {
                // TODO Auto-generated method stub

                AlertDialog.Builder builder=new  AlertDialog.Builder(MainActivity1.this);
                builder.setCancelable(true);
                builder.setTitle(string);

                builder.setMessage(string2);

                builder.show();
            }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}

