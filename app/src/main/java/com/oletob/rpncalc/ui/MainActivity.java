package com.oletob.rpncalc.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.oletob.rpncalc.R;
import com.oletob.rpncalc.model.Rpn;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // Member variables
    private TextView panelTextView;
    private String[] strSplitted;
    private String input;
    private Button btnClicked;
    private boolean lastIsZero          = false;
    private boolean operationPerformed  = false;

    private SharedPreferences historyStore;

    private Rpn rpn = new Rpn();

    private int [] calcButtons  = {
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
            R.id.btn8, R.id.btn9, R.id.btnDevide, R.id.btnMultiply, R.id.btnSubtract, R.id.btnSum,
            R.id.btnEnter, R.id.btnDot, R.id.btnSymbol, R.id.btnClear, R.id.btnDelete
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        panelTextView = (TextView)findViewById(R.id.panelTextView);

        this.historyStore = getSharedPreferences(Rpn.KEY, Context.MODE_PRIVATE);

        // Set reset buttons click event
        for(int id : this.calcButtons){
            findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.btnHistory:
                Intent i = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(i);
                return true;
            case R.id.btnAbout:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        // Split the content of the panelTextView into an string array
        this.strSplitted = this.panelTextView.getText().toString().split("\n");

        switch(v.getId()){
            case R.id.btnClear:
                this.operationPerformed = false;
                panelTextView.setText("0");
                break;
            case R.id.btnDelete:

                // Remove the last number entered
                String newInput = this.rpn.delete(this.panelTextView.getText().toString());

                if(newInput.length() == 0){
                    newInput = "0";
                }
                this.panelTextView.setText(newInput);

                break;
            case R.id.btnEnter:
                if(this.panelTextView.getText().toString().length() > 1 || Double.parseDouble(this.panelTextView.getText().toString()) != 0){

                    this.operationPerformed = false;
                    this.panelTextView.setText(this.rpn.formatInput(this.strSplitted)); //Format input
                    this.panelTextView.append("\n0"); // Append new line with a default zero in the text view
                    this.lastIsZero = true;

                }
                break;
            case R.id.btn0:
            case R.id.btn1:
            case R.id.btn2:
            case R.id.btn3:
            case R.id.btn4:
            case R.id.btn5:
            case R.id.btn6:
            case R.id.btn7:
            case R.id.btn8:
            case R.id.btn9:
            case R.id.btnDot:
                // Handler numeric buttons

                /* A Button is also a View, so I'll cast the View parameter called v that onClick
                   method receive into a Button object, this way I can get properties of the button
                   clicked, for example the button's text */
                this.btnClicked = (Button) v;

                // Check if is the first input of the row
                if(this.panelTextView.getText().toString().length() == 1 &&
                        Double.parseDouble(this.panelTextView.getText().toString()) == 0 && v.getId() != R.id.btnDot){
                    this.panelTextView.setText(this.btnClicked.getText());
                }else{

                    if(this.lastIsZero && v.getId() != R.id.btnDot){

                        this.panelTextView.setText(this.rpn.delete(this.panelTextView.getText().toString()));
                    }

                    if(this.operationPerformed){
                        this.panelTextView.append("\n");
                        this.operationPerformed =  false;
                    }
                    // Avoid insert more than one zero per input
                    if(v.getId() == R.id.btnDot && !this.strSplitted[this.strSplitted.length - 1].contains(".")){
                        this.panelTextView.append(".");
                    }else if(v.getId() != R.id.btnDot){

                        this.panelTextView.append(this.btnClicked.getText()); // Append input to textview
                    }

                    this.lastIsZero = false;
                }

                break;
            case R.id.btnSymbol:
                // Change symbol of last input

                String input = this.rpn.changeInputSymbol(this.strSplitted);
                if(input.length() > 0){
                    this.panelTextView.setText(input);
                }

                break;
            case R.id.btnDevide:
            case R.id.btnSum:
            case R.id.btnMultiply:
            case R.id.btnSubtract:
                // Handler operator buttons
                this.btnClicked = (Button) v;
                if(this.strSplitted.length > 1){
                    this.operationPerformed = true;
                    this.input = this.rpn.proccess(this.strSplitted, this.btnClicked.getText().toString(), this.historyStore);
                    this.panelTextView.setText(this.input);
                }

                break;

        }
    }
}