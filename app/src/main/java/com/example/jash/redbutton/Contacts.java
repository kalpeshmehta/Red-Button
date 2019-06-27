package com.example.jash.redbutton;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Contacts extends AppCompatActivity {

    Button btn;
    ImageButton b1;
    private final int PICK_CONTACT=1;
    ListView l1;
    public static ArrayList<String> array = new ArrayList();
    ArrayAdapter<String> adapter;
    public static ArrayList<String> numbers = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);
        b1=(ImageButton)findViewById(R.id.imageButton);
        l1 = findViewById(R.id.list);
        Log.e("1","entered");
        btn= findViewById(R.id.peeche);
        Log.e("2","byn");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("abc","Inside click");
                Intent i= new Intent(Contacts.this,MainActivity.class);
                startActivity(i);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callContacts(view);
            }
        });
        }

    public void callContacts(View v){
        Intent intent=new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent,PICK_CONTACT);
    }

    protected void onActivityResult(int reqCode,int resultCode,Intent data){
        super.onActivityResult(reqCode,resultCode,data);
        if(reqCode==PICK_CONTACT){
            if(resultCode== Activity.RESULT_OK){
                Uri contactData=data.getData();
                Cursor c=getContentResolver().query(contactData,null,null,null,null);
                if(c.moveToFirst()){
                    String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                    String name=c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                    Cursor phones;
                    String number;
                    phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                    while (phones.moveToNext()){
                        number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        numbers.add(number);
                    }
                    Toast.makeText(this,"You've picked :"+name,Toast.LENGTH_LONG).show();
                    array.add(name);
                    adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, array);
                    adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, numbers);
                    l1.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                }
            }
        }
    }
}
