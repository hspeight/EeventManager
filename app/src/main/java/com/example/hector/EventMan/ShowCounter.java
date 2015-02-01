package com.example.hector.EventMan;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ShowCounter extends ActionBarActivity {

    //DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    Calendar c = Calendar.getInstance();
    long secs;
    long modSecs; //need to learn how to cast
    long mins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_counter);

        final TextView textSecs = (TextView)findViewById(R.id.textSecs);
        final TextView textMins = (TextView)findViewById(R.id.textMins);
        final TextView textHour = (TextView)findViewById(R.id.textHour);
        final TextView textDays = (TextView)findViewById(R.id.textDays);
        final TextView textStartDate = (TextView)findViewById(R.id.textStartDate);
        //final TextView textView2 = (TextView)findViewById(R.id.textView2);
        Button myButton;

        String myDate = "2014-12-29 04:00:08 +0000";
        Date startDate = null;
        try {
            startDate = df.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        textStartDate.setText(myDate);
        // final long timeDiff = Math.abs(startDate.getTime() - System.currentTimeMillis());
        final long timeDiff = (Math.abs(startDate.getTime() - System.currentTimeMillis()) / 1000); // need to do this every min to keep it accurate

        //double timeDiff2 = Math.abs(dateOne.getTime() - System.currentTimeMillis());
        //System.out.println("difference:" + df.format(c.getTime()) + "!" + timeDiff + " " + System.currentTimeMillis());   // difference: 0
        //System.out.println("!!- " + timeDiff);   // difference: 0

        new CountDownTimer(20000000, 1000){ // 1st value is seconds in 1 year
            public void onTick(long millisUntilFinished){
                //textView.setText("seconds remaining: " + (timeDiff - (millisUntilFinished / 1000)));
                // String d = df.format(c.getTime());
                //System.out.println(">" + System.currentTimeMillis());
                //Date resultdate = new Date(System.currentTimeMillis());
                //

                //fmDate = String.valueOf(df.format(timeDiff - (millisUntilFinished / 1000)));
                secs = (timeDiff - (millisUntilFinished / 1000)) + 20000;
                modSecs = secs % 60;
                mins = (secs / 60) % 60;
                long hours = TimeUnit.SECONDS.toHours(secs) % 24;
                long days = TimeUnit.SECONDS.toDays(secs);
                //System.out.println("!-" + hours);
                //textSecs.setText(String.valueOf((timeDiff - (millisUntilFinished / 1000)) + 20000));
                textSecs.setText(String.valueOf(modSecs));
                textMins.setText(String.valueOf(mins));
                textHour.setText(String.valueOf(hours));
                textDays.setText(String.valueOf(days));
                //textMins.setText(String.valueOf((timeDiff - (millisUntilFinished / 1000)) + 20000));
                //System.out.println("-!:" + String.valueOf(timeDiff - (millisUntilFinished / 1000)));
                //textView.setText(fmDate);
                //textView.setText("" + (timeDiff - (millisUntilFinished / 1000)));

            }
            public void onFinish(){
                textSecs.setText("done!");
            }
        }.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            //    return true;
            //}
            //else if (id == R.id.action_add) {
            /** Called when the user clicks the add button */
            Intent intent = new Intent(this, ShowCounter.class);
            startActivity(intent);

            /* Context context = getApplicationContext();
            CharSequence text = "Add action pressed!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show(); */
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
