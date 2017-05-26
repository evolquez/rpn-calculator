package com.oletob.rpncalc.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.oletob.rpncalc.R;
import com.oletob.rpncalc.model.HistoryAdapter;
import com.oletob.rpncalc.model.HistoryHolder;
import com.oletob.rpncalc.model.Rpn;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Set action bar title to "History"
        getSupportActionBar().setTitle("History");

        SharedPreferences history = getSharedPreferences(Rpn.KEY, Context.MODE_PRIVATE);

        ArrayList<HistoryHolder> historyItems =  Rpn.loadHistoryInArray(history.getString(Rpn.KEY, "NONE"));

        HistoryAdapter adapter = new HistoryAdapter(this, historyItems);

        // Set the adapter to the listView
        ((ListView) findViewById(R.id.listHistory)).setAdapter(adapter);

    }
}
