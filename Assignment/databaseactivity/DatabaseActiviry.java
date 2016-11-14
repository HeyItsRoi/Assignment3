package com.example.lerihan.assignment3;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class DatabaseActiviry extends Activity {

    Button display;
    Button friendDB;
    Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        display = (Button) findViewById(R.id.display);
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("com.example.lerihan.assignment3.FragmentActivity"));
            }
        });

        friendDB = (Button) findViewById(R.id.friendDB);
        friendDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.lerihan.assignment3.FragmentActivity");
                //Since additional buttons must be added, the message "friendDB" (the button's id) will be passed
                //to signal the FragmentActivity activity
                intent.putExtra("b_id", "friendDB");
                startActivity(intent);
            }
        });

        exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }

}
