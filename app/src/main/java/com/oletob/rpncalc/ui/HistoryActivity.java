package com.oletob.rpncalc.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.oletob.rpncalc.R;
import com.oletob.rpncalc.model.HistoryAdapter;
import com.oletob.rpncalc.model.HistoryHolder;
import com.oletob.rpncalc.model.Rpn;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private SharedPreferences history;
    private int totalHistory = 0;

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


    public void clearHistory(){
        SharedPreferences.Editor editor = this.history.edit();
        editor.clear();

        if(editor.commit()){

            this.loadHistory();
            Toast.makeText(this, "History cleared!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.btnClear:
                AlertDialogFragment alert = new AlertDialogFragment();

                alert.show(getFragmentManager(), "");


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(totalHistory == 0){
            MenuItem itemClear = menu.findItem(R.id.btnClear);
            itemClear.setEnabled(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    private void loadHistory(){
        history = getSharedPreferences(Rpn.KEY, Context.MODE_PRIVATE);

        ArrayList<HistoryHolder> historyItems = Rpn.loadHistoryInArray(history.getString(Rpn.KEY, "NONE"));
        totalHistory = historyItems.size();

        if(totalHistory == 0){
            invalidateOptionsMenu();

            findViewById(R.id.historyEmptyTextView).setVisibility(View.VISIBLE);
            findViewById(R.id.listHistory).setVisibility(View.GONE);

        }else{
            findViewById(R.id.historyEmptyTextView).setVisibility(View.GONE);
            findViewById(R.id.listHistory).setVisibility(View.VISIBLE);

            HistoryAdapter adapter = new HistoryAdapter(this, historyItems);

            // Set the adapter to the listView
            ((ListView) findViewById(R.id.listHistory)).setAdapter(adapter);

        }
    }
}