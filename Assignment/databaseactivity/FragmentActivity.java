package com.example.lerihan.assignment3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by cstuser on 11/10/2016.
 */
public class FragmentActivity extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_activiry);

        //Set onClick listeners //TODO: combine with what Roi did
        //BOTH: DISPLAY FRIEND PICTURE AND SOUND & FRIENDDB
        //manual
        Button manual = (Button) findViewById(R.id.manual);
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button previous = (Button) findViewById(R.id.previous);
                Button next = (Button) findViewById(R.id.next);

                previous.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);

                previous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Previous Clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Next Clicked", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //auto
        Button automatic = (Button) findViewById(R.id.automatic);

        //done
        Button done = (Button) findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        //FRIENDDB BUTTON ONLY
        //Checks if caller is the FRIENDDB button
        Button friendDB = (Button) findViewById(R.id.friendDB);
        Bundle extra = getIntent().getExtras();

        if(extra != null) {
            String b_id = extra.getString("b_id");

            if (b_id.equals("friendDB")) {
                //Display missing layout
                LinearLayout friendInfo = (LinearLayout) findViewById(R.id.friendInfo);
                friendInfo.setVisibility(View.VISIBLE);

                //Display missing buttons
                Button enterFriend = (Button) findViewById(R.id.enterFriend);
                Button enterCegep = (Button) findViewById(R.id.enterCegep);
                Button editFriend = (Button) findViewById(R.id.editFriend);
                Button editCegep = (Button) findViewById(R.id.editCegep);
                Button deleteFriend = (Button) findViewById(R.id.deleteFriend);
                Button deleteCegep = (Button) findViewById(R.id.deleteCegep);
                Button searchFriend = (Button) findViewById(R.id.searchFriend);
                Button searchCegep = (Button) findViewById(R.id.searchCegep);

                enterFriend.setVisibility(View.VISIBLE);
                enterCegep.setVisibility(View.VISIBLE);
                editFriend.setVisibility(View.VISIBLE);
                editFriend.setVisibility(View.VISIBLE);
                editCegep.setVisibility(View.VISIBLE);
                deleteFriend.setVisibility(View.VISIBLE);
                deleteCegep.setVisibility(View.VISIBLE);
                searchFriend.setVisibility(View.VISIBLE);
                searchCegep.setVisibility(View.VISIBLE);
            }
        }
    }
}
