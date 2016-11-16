package com.example.heyitsroi.databaseactiviry;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cstuser on 11/10/2016.
 */
public class FragmentActivity extends Activity{

    Button enterFriend, enterCegep, editFriend, editCegep, deleteFriend, deleteCegep, searchFriend, searchCegep, manual, automatic, done;
    TextView friendID, friendName, friendAge, cegepID, cID, cegepName, cegepCity, pictSoundTitle;
    ImageView friendPic;
    ToggleButton soundOn;
    Cursor c;
    MediaPlayer mp;

    //this counter is to keep track of which pic we are displaying right now
    //it can go from 1 to 6
    int count = 1;

    //count for the automatic going through the array of images
    int count2 = 0;


    DBHelper db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_activiry);

        friendPic = (ImageView) findViewById(R.id.friendPic);

        cID = (TextView) findViewById(R.id.cID);
        cegepName = (TextView) findViewById(R.id.cegepName);
        cegepCity = (TextView) findViewById(R.id.cegepCity);
        pictSoundTitle = (TextView) findViewById(R.id.pictSoundTitle);

        //create an array of all the sound files
        final int[] soundList = new int[]{R.raw.sound1, R.raw.sound2, R.raw.sound3, R.raw.sound4,
                R.raw.sound5, R.raw.sound6};

        //starting the audio file
        soundOn = (ToggleButton) findViewById(R.id.soundOn);
        soundOn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(soundOn.isChecked() == true){
                    mp.start();
                }
            }
        });



        //create an array of all the drawable images
        final int[] imageList = new int[]{R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4,
                R.drawable.pic5, R.drawable.pic6};
        //set first picture when creating the page
        friendPic.setImageResource(imageList[0]);

        db = new DBHelper(this);

        //Set onClick listeners
        //BOTH: DISPLAY FRIEND PICTURE AND SOUND & FRIENDDB
        //manual
        manual = (Button) findViewById(R.id.manual);
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Button previous = (Button) findViewById(R.id.previous);
                final Button next = (Button) findViewById(R.id.next);

                previous.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);

                previous.setOnClickListener(new View.OnClickListener() {
                    //previous is disabled at first
                    @Override
                    public void onClick(View view) {
                        count--;

                        //prevents null pointer exception when displaying
                        if(count > 0)
                            friendPic.setImageResource(imageList[count]);

                        //make next enabled again if the picture displayed is the second one
                        //this is so it doesnt repeat the action multiple times even though isEnabled is already set to true
                        if(count == imageList.length - 2)
                            next.setEnabled(true);

                        //if first picture, make previous disabled
                        if(count == 1)
                            previous.setEnabled(false);
                    }
                });

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        count++;

                        //prevents null pointer exception when displaying
                        if(count < imageList.length)
                            friendPic.setImageResource(imageList[count]);

                        //make previous enabled again if the picture displayed is the second one
                        //this is so it doesnt repeat the action multiple times even though isEnabled is already set to true
                        if(count == 2)
                            previous.setEnabled(true);

                        //if last picture, disable next
                        if(count == imageList.length - 1)
                            next.setEnabled(false);
                    }
                });
            }
        });

        automatic = (Button) findViewById(R.id.automatic);
        automatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (count2 < imageList.length) {
                                    friendPic.setImageResource(imageList[count2]);
                                    mp = MediaPlayer.create(FragmentActivity.this, soundList[count2]);
                                    soundOn.setChecked(false);
                                    count2++;
                                } else {
                                    cancel();
                                    finish();
                                }
                            }
                        });
                    }
                }, 0, 5000);
            }
        });


        //FRIENDDB BUTTON ONLY
        //Checks if caller is the FRIENDDB button
        Bundle extra = getIntent().getExtras();

        if(extra != null) {
            String b_id = extra.getString("b_id");

            if (b_id.equals("friendDB")) {
                //set title if FriendDB
                pictSoundTitle.setText("FriendDB");

                //Display missing layout
                LinearLayout friendInfo = (LinearLayout) findViewById(R.id.friendInfo);
                LinearLayout cegepInfo = (LinearLayout) findViewById(R.id.cegepInfo);
                friendInfo.setVisibility(View.VISIBLE);
                cegepInfo.setVisibility(View.VISIBLE);

                //Remove imageview
                ImageView friendPic = (ImageView) findViewById(R.id.friendPic);
                friendPic.setVisibility(View.GONE);

                //Display missing buttons
                enterFriend = (Button) findViewById(R.id.enterFriend);
                enterCegep = (Button) findViewById(R.id.enterCegep);
                editFriend = (Button) findViewById(R.id.editFriend);
                editCegep = (Button) findViewById(R.id.editCegep);
                deleteFriend = (Button) findViewById(R.id.deleteFriend);
                deleteCegep = (Button) findViewById(R.id.deleteCegep);
                searchFriend = (Button) findViewById(R.id.searchFriend);
                searchCegep = (Button) findViewById(R.id.searchCegep);

                enterFriend.setVisibility(View.VISIBLE);
                enterCegep.setVisibility(View.VISIBLE);
                editFriend.setVisibility(View.VISIBLE);
                editFriend.setVisibility(View.VISIBLE);
                editCegep.setVisibility(View.VISIBLE);
                deleteFriend.setVisibility(View.VISIBLE);
                deleteCegep.setVisibility(View.VISIBLE);
                searchFriend.setVisibility(View.VISIBLE);
                searchCegep.setVisibility(View.VISIBLE);

                //Remove manual and automatic buttons
                manual.setVisibility(View.GONE);
                automatic.setVisibility(View.GONE);

                //set functions of the new buttons
                friendID = (TextView) findViewById(R.id.friendID);
                friendName = (TextView) findViewById(R.id.friendName);
                friendAge = (TextView) findViewById(R.id.friendAge);
                cegepID = (TextView) findViewById(R.id.cegepID);

                //ADDING A FRIEND
                enterFriend = (Button) findViewById(R.id.enterFriend);
                enterFriend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent("com.example.heyitsroi.databaseactiviry.AddFriend"));
                    }
                });

                //ADDING A CEGEP
                enterCegep = (Button) findViewById(R.id.enterCegep);
                enterCegep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent("com.example.heyitsroi.databaseactiviry.AddCegep"));
                    }
                });

                //EDITING A FRIEND
                editFriend = (Button) findViewById(R.id.editFriend);
                editFriend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view = (LayoutInflater.from(FragmentActivity.this)).inflate(R.layout.edit_friend_input, null);

                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(FragmentActivity.this);
                        alertBuilder.setView(view);
                        final EditText userInput = (EditText) view.findViewById(R.id.friendinput);
                        final EditText newFriendName = (EditText) view.findViewById(R.id.newFriendName);
                        final EditText newFriendAge = (EditText) view.findViewById(R.id.newFriendAge);
                        final EditText newCegepID = (EditText) view.findViewById(R.id.newCegepID);

                        alertBuilder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    db.open();
                                } catch (java.sql.SQLException e) {
                                    e.printStackTrace();
                                }
                                db.updateFriendContact(Long.parseLong(userInput.getText().toString()), newFriendName.getText().toString(), Integer.parseInt(newFriendAge.getText().toString()), Integer.parseInt(newCegepID.getText().toString()));
                                db.close();
                                Toast.makeText(getBaseContext(), "Edit Complete", Toast.LENGTH_SHORT).show();
                            }
                        });
                        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {@Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getBaseContext(), "Edit Canceled", Toast.LENGTH_SHORT).show();
                        }
                        });
                        Dialog dialog = alertBuilder.create();
                        dialog.show();

                    }
                });

                //EDITING A CEGEP
                editCegep = (Button) findViewById(R.id.editCegep);
                editCegep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view = (LayoutInflater.from(FragmentActivity.this)).inflate(R.layout.edit_cegep_input, null);

                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(FragmentActivity.this);
                        alertBuilder.setView(view);
                        final EditText userInput = (EditText) view.findViewById(R.id.cegepinput);
                        final EditText newCegep = (EditText) view.findViewById(R.id.newCegep);
                        final EditText newCity = (EditText) view.findViewById(R.id.newCity);

                        alertBuilder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    db.open();
                                } catch (java.sql.SQLException e) {
                                    e.printStackTrace();
                                }
                                db.updateCegepContact(Long.parseLong(userInput.getText().toString()), newCegep.getText().toString(), newCity.getText().toString());
                                db.close();
                                Toast.makeText(getBaseContext(), "Edit Complete", Toast.LENGTH_SHORT).show();
                            }
                        });
                        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {@Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getBaseContext(), "Edit Canceled", Toast.LENGTH_SHORT).show();
                        }
                        });
                        Dialog dialog = alertBuilder.create();
                        dialog.show();
                    }
                });

                //DELETING A FRIEND
                deleteFriend = (Button) findViewById(R.id.deleteFriend);
                deleteFriend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view = (LayoutInflater.from(FragmentActivity.this)).inflate(R.layout.delete_friend_input, null);

                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(FragmentActivity.this);
                        alertBuilder.setView(view);
                        final EditText userInput = (EditText) view.findViewById(R.id.friendinput);

                        alertBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    db.open();
                                } catch (java.sql.SQLException e) {
                                    e.printStackTrace();
                                }
                                db.deleteFriendContact(userInput.getText().toString());
                                db.close();
                            }
                        });
                        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {@Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getBaseContext(), "Delete Canceled", Toast.LENGTH_SHORT).show();
                        }
                        });
                        Dialog dialog = alertBuilder.create();
                        dialog.show();

                    }
                });

                //DELETING A CEGEP
                deleteCegep = (Button) findViewById(R.id.deleteCegep);
                deleteCegep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view = (LayoutInflater.from(FragmentActivity.this)).inflate(R.layout.delete_cegep_input, null);

                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(FragmentActivity.this);
                        alertBuilder.setView(view);
                        final EditText userInput = (EditText) view.findViewById(R.id.cegepinput);


                        alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    db.open();
                                } catch (java.sql.SQLException e) {
                                    e.printStackTrace();
                                }
                                db.deleteCegepContact(userInput.getText().toString());
                                db.close();
                            }
                        });

                        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {@Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getBaseContext(), "Delete Canceled", Toast.LENGTH_SHORT).show();
                        }
                        });
                        Dialog dialog = alertBuilder.create();
                        dialog.show();

                    }
                });

                //DELETING A FRIEND
                searchFriend = (Button) findViewById(R.id.searchFriend);
                searchFriend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view = (LayoutInflater.from(FragmentActivity.this)).inflate(R.layout.search_friend_input, null);

                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(FragmentActivity.this);
                        alertBuilder.setView(view);
                        final EditText userInput = (EditText) view.findViewById(R.id.friendinput);

                        alertBuilder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    db.open();
                                } catch (java.sql.SQLException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    c = db.getFriendContact(userInput.getText().toString());
                                } catch (java.sql.SQLException e) {
                                    e.printStackTrace();
                                }

                                friendID.setText(c.getString(0));
                                friendName.setText(c.getString(1));
                                friendAge.setText(c.getString(2));
                                cegepID.setText(c.getString(3));
                                db.close();
                            }
                        });
                        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {@Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getBaseContext(), "Search Canceled", Toast.LENGTH_SHORT).show();
                        }
                        });
                        Dialog dialog = alertBuilder.create();
                        dialog.show();

                    }
                });

                //SEARCHING A CEGEP
                searchCegep = (Button) findViewById(R.id.searchCegep);
                searchCegep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view = (LayoutInflater.from(FragmentActivity.this)).inflate(R.layout.search_cegep_input, null);

                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(FragmentActivity.this);
                        alertBuilder.setView(view);
                        final EditText userInput = (EditText) view.findViewById(R.id.cegepinput);

                        alertBuilder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    db.open();
                                } catch (java.sql.SQLException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    c = db.getCegepContact(userInput.getText().toString());
                                } catch (java.sql.SQLException e) {
                                    e.printStackTrace();
                                }
                                cID.setText(c.getString(0));
                                cegepName.setText(c.getString(1));
                                cegepCity.setText(c.getString(2));
                                db.close();
                            }
                        });
                        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {@Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getBaseContext(), "Search Canceled", Toast.LENGTH_SHORT).show();
                        }
                        });
                        Dialog dialog = alertBuilder.create();
                        dialog.show();

                    }
                });
            }
        }

        //RETURNING TO MAIN MENU
        done = (Button) findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void DisplayContact(Cursor c)
    {
         friendID.setText(c.getString(0));
         friendName.setText(c.getString(1));
         friendAge.setText(c.getString(2));
         cegepID.setText(c.getString(3));
    }
}
