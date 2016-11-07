package com.example.cstuser.cegepdatabase;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.sql.SQLException;


public class DatabaseActivity extends Activity {

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        DBHelper db = new DBHelper(this);

//        try {
//            db.open();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        long id = db.insertCegepContact("Vanier", "Saint-Laurent");
//        db.close();
//        Toast.makeText(this, "inserting", Toast.LENGTH_LONG).show();

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cursor c = db.getAllCegepContacts();
        if(c.moveToFirst()){
            do{
                DisplayContact(c);
            }while (c.moveToNext());
        }
        db.close();

//        try {
//            db.open();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        db.deleteCegepContact(2);
//        db.deleteCegepContact(3);
//        db.deleteCegepContact(4);
//        db.close();
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


