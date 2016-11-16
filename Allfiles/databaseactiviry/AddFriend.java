package com.example.heyitsroi.databaseactiviry;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by HeyItsRoi on 11/12/2016.
 */

public class AddFriend extends Activity {
    Button friendDB, addFriend;
    DBHelper db;
    TextView friendName, friendAge, cegepID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend);

        friendName = (TextView) findViewById(R.id.friendName);
        friendAge = (TextView) findViewById(R.id.friendAge);
        cegepID = (TextView) findViewById(R.id.cegepID);

        db = new DBHelper(this);

        addFriend = (Button) findViewById(R.id.addFriend);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db.open();
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }
                db.insertFriendContact(friendName.getText().toString(), Integer.parseInt(friendAge.getText().toString()),Integer.parseInt(cegepID.getText().toString()));
                db.close();
                friendName.setText("");
                friendAge.setText("");
                cegepID.setText("");
            }
        });

        friendDB = (Button) findViewById(R.id.frienddb);
        friendDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db.open();
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }
                Cursor c = db.getAllFriendContacts();
                if(c.moveToFirst()){
                    do{
                        DisplayContact(c);
                    }while (c.moveToNext());
                }
                db.close();

                //return to previous activity
                finish();
            }
        });
    }

    public void DisplayContact(Cursor c)
    {
        Toast.makeText(this,
                "id: " + c.getString(0) + "\n" +
                "Name: " + c.getString(1) + "\n" +
                "Age: " + c.getString(2) + "\n" +
                "Cegep ID: " + c.getString(3),
                Toast.LENGTH_LONG).show();
    }
}
