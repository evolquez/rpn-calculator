package com.oletob.rpncalc.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.oletob.rpncalc.R;
import com.oletob.rpncalc.model.HistoryAdapter;
import com.oletob.rpncalc.model.HistoryHolder;
import com.oletob.rpncalc.model.Rpn;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private SharedPreferences history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Set action bar title to "History"
        getSupportActionBar().setTitle("History");

        this.loadHistory();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.clear_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.btnClear:
                SharedPreferences.Editor editor = this.history.edit();
                editor.clear();

                if(editor.commit()){
                    this.loadHistory();
                    Toast.makeText(this, "History cleared!", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadHistory(){
        history = getSharedPreferences(Rpn.KEY, Context.MODE_PRIVATE);

        ArrayList<HistoryHolder> historyItems =  Rpn.loadHistoryInArray(history.getString(Rpn.KEY, "NONE"));

        HistoryAdapter adapter = new HistoryAdapter(this, historyItems);

        // Set the adapter to the listView
        ((ListView) findViewById(R.id.listHistory)).setAdapter(adapter);
    }
}
