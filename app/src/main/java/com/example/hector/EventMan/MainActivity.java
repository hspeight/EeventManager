package com.example.hector.EventMan;
// Code courtesy of http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class MainActivity extends ActionBarActivity {

    int maxAllowableEvents = 15; // not yet in use

    MyDBHandler dbHandler;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    public String rowID []; // Array of _id column form database
    public ImageView bin = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHandler = new MyDBHandler(this, null, null, 1);
        setContentView(R.layout.main_activity);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        // get the listview
        //expListView = (ExpandableListView) findViewById(R.id.lvExp);
        //llo = (LinearLayout) findViewById(R.id.parentLayout);
        //llo.setBackgroundColor(0xffffff);
        // preparing list data
        setGroupParents();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;

            @Override   // collapse list
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousItem )
                    expListView.collapseGroup(previousItem );
                previousItem = groupPosition;

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                //String selected = ((TextView) v.findViewById(R.id.textViewRowID)).getText().toString();
                Intent intent = new Intent(getBaseContext(), EventEditor.class);
                intent.putExtra("ROW_ID",rowID[groupPosition]);
                startActivity(intent);
/*
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + rowID[groupPosition]
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
*/
                return false;
            }
        });
/*
        bin = (ImageView)findViewById(R.id.imageViewBin);
        bin.setOnItemClickListener(new View.OnClickListener(){

            public void onite(View view) {
                Toast.makeText(getApplicationContext(),
                        "bin clicked", Toast.LENGTH_SHORT)
                        .show();
            }});
*/

    }

    // method to add parent & child events
    public void setGroupParents() {

        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        String evstring = dbHandler.getEventIDs();
        //System.out.println("!!- " + "*" + evstring + "*");
        String[] foods = evstring.split(":");
        rowID = new String[foods.length]; // set number of array elements equal to number of events in database
        //System.out.println("!!- " + "^" + foods.length + "^");
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        int i;
        if (evstring.length() > 0) { // need to display something if there are no events
            for (i = 0; i < foods.length; i++) {
                //System.out.println("!!- " + i + foods.length);
                Events myEvent = dbHandler.getMyEvent(foods[i]);
                listDataHeader.add(myEvent.get_eventname());
                List<String> child = new ArrayList<>();
                child.add(formatDateTime(myEvent.get_evtime(), myEvent.get_direction()));
                listDataChild.put(listDataHeader.get(i), child);
                rowID[i] = foods[i]; // store _id from database
            }
        } else {
            Intent intent = new Intent(this, NoEventsExist.class);
            startActivity(intent);
            //System.out.println("!!- " + "here");
        }
    }

    public String formatDateTime(int eventTime,int direction){
        String[] d = new String[] {"up from","down to"};
        long millis = eventTime;
        millis *= 1000;
        DateTime dt = new DateTime(millis, DateTimeZone.forOffsetHours(0)); // needs to be a local date
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd MMM yyyy HH:mm");

        return "Count " + d[direction] + " " + dtf.print(dt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

// Not yet implemented
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent addAct = new Intent(MainActivity.this, EventEditor.class);
                startActivity(addAct);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        setGroupParents();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

    }

}