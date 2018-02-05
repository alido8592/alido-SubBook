/*
 * Copyright 2018 Team Me. CMPUT 301. University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at University of Alberta.
 * You may find a copy of the license in this project. Otherwise please contact alido@ualberta.ca.
 */

package comalido8592.github.alido_subbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "subscriptions.sav";
    private TextView monthlyTotal;
    private ArrayList<Subscription> subscriptionsList;
    private ArrayAdapter<Subscription> adapter;
    private ListView subList;
    static final int ADD_SUB_ACTIVITY = 1;
    static final int EDIT_SUB_ACTIVITY = 2;
    private float monthlySum = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //based off of LonelyTwitter lab for CMPUT 301 at University of Alberta
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subList = (ListView) findViewById(R.id.SubscriptionsL);
        Button addButton = (Button) findViewById(R.id.add);
        monthlyTotal = (TextView) findViewById(R.id.totalVal);

        addButton.setOnClickListener(new View.OnClickListener() { // send user to sub add
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SingleSubscription.class);
                Subscription newSub = new Subscription();
                intent.putExtra("Selected_Sub",newSub);
                intent.putExtra("position",-1);
                startActivityForResult(intent,ADD_SUB_ACTIVITY);
            }
        });

        subList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Subscription selectSub = (Subscription) subList.getItemAtPosition(position);
                subscriptionsList.remove(selectSub);
                adapter.notifyDataSetChanged();

                monthlySum = 0;

                for (int i = 0; i < subList.getCount(); i++) {
                    Subscription sub = (Subscription) subList.getItemAtPosition(i);
                    monthlySum = monthlySum + sub.getRate();
                }

                monthlyTotal.setText(Float.toString(monthlySum));
                saveData();
                return false;
            }
        });

        subList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // views item selected
                                                                              // through sub_add
            // updating replaces the subscription and places it as the newest
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Subscription selectSub = (Subscription) subList.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(),SingleSubscription.class);
                intent.putExtra("Selected_Sub",selectSub);
                intent.putExtra("position",position);
                startActivityForResult(intent,EDIT_SUB_ACTIVITY);
            }
        });

    }

    @Override
    protected void onActivityResult(int activityCode, int resultCode, Intent intent){
        if ((activityCode==ADD_SUB_ACTIVITY)&&(resultCode==RESULT_OK)){ // adds sub
            Subscription newSub =
                    (Subscription) intent.getExtras().getSerializable("Subscription_New");
            subscriptionsList.add(newSub);
            adapter.notifyDataSetChanged();
            saveData();
        }

        else if ((activityCode==EDIT_SUB_ACTIVITY)&&(resultCode==RESULT_OK)){ // replaces sub
            Subscription newSub =
                    (Subscription) intent.getExtras().getSerializable("Subscription_New");
            int position = intent.getIntExtra("position",-1);
            subscriptionsList.remove(position);
            subscriptionsList.add(position,newSub);
            adapter.notifyDataSetChanged();
            saveData();

        }

    }

    @Override
    protected void onStart(){
        super.onStart();
        loadData();
        adapter = new ArrayAdapter<Subscription>(this,
                R.layout.subscription_item,R.id.subName, subscriptionsList);
        subList.setAdapter(adapter);

        monthlySum = 0;

        for (int i = 0; i < subList.getCount(); i++) {
            Subscription sub = (Subscription) subList.getItemAtPosition(i);
            monthlySum = monthlySum + sub.getRate();
        }

        monthlyTotal.setText(Float.toString(monthlySum));

    }

    private void saveData() { //taken from LonelyTwitter lab sample for CMPUT 301
                             // at the University of Alberta
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(subscriptionsList, out);
            out.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private String[] loadData() { //taken from LonelyTwitter lab sample for CMPUT 301
                                 // at the University of Alberta
        ArrayList<String> subs = new ArrayList<String>();
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();
            subscriptionsList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            subscriptionsList = new ArrayList<Subscription>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return subs.toArray(new String[subs.size()]);
    }

}
