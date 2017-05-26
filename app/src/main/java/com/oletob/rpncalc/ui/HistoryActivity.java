package com.oletob.rpncalc.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.oletob.rpncalc.R;
import com.oletob.rpncalc.model.Rpn;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        SharedPreferences history = getSharedPreferences(Rpn.KEY, Context.MODE_PRIVATE);

        Toast.makeText(this, history.getString(Rpn.KEY, "NONE"), Toast.LENGTH_LONG).show();
    }
}
