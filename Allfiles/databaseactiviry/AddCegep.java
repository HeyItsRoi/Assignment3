package com.example.heyitsroi.databaseactiviry;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by HeyItsRoi on 11/12/2016.
 */

public class AddCegep extends Activity {
    Button friendDB, addCegep;
    DBHelper db;
    Spinner cegepName;
    TextView cityName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_cegep);

        cegepName = (Spinner) findViewById(R.id.cegepName);
        cityName = (TextView) findViewById(R.id.cityName);

        db = new DBHelper(this);

        addCegep = (Button) findViewById(R.id.addCegep);
        addCegep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db.open();
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }
                db.insertCegepContact(cegepName.getSelectedItem().toString(), cityName.getText().toString());
                db.close();
                cityName.setText("");
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
                Cursor c = db.getAllCegepContacts();
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
                        "Cegep: " + c.getString(1) + "\n" +
                        "City: " + c.getString(2),
                Toast.LENGTH_LONG).show();
    }
}
