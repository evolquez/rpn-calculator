package com.oletob.rpncalc.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.oletob.rpncalc.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // Member variables
    private TextView panelTextView;
    private String[] strSplitted;
    private Button btnClicked;
    private Character lastChar;
    private Character firstChar;

    //private String numberEntered;
    private ArrayList<Double> numberStack;

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

        numberStack = new ArrayList<>();

        // Set reset buttons click event
        for(int id : this.calcButtons){
            findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnClear:
                this.numberStack.clear();
                panelTextView.setText("0");
                break;
            case R.id.btnDelete:
                // Remove the last number entered

                break;
            case R.id.btnEnter:

                this.strSplitted = this.panelTextView.getText().toString().split("\n");

                /* Get last char of last input, this is to ensure that the
                   input doesn't end with a dot*/
                this.lastChar   = this.strSplitted[this.strSplitted.length - 1].substring(this.strSplitted[this.strSplitted.length - 1].length() - 1).charAt(0);

                // Add a zero by default if user doesn't complete the input
                if(this.lastChar == '.'){
                    this.panelTextView.append("0");
                }

                this.panelTextView.append("\n0"); // Append new line with a default zero in the text view

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

                    // Split the content of the panelTextView into an string array
                    this.strSplitted = this.panelTextView.getText().toString().split("\n");

                    // Avoid insert more than one zero per input
                    if(v.getId() == R.id.btnDot && !this.strSplitted[this.strSplitted.length - 1].contains(".")){
                        this.panelTextView.append(".");
                    }else if(v.getId() != R.id.btnDot){

                        // Get first char of last input
                        this.firstChar = this.strSplitted[this.strSplitted.length - 1].substring(0, 1).charAt(0);

                        // If the first char is a zero and the second one is not a dot, so I remove the leading zero
                        if(this.firstChar == '0' && !this.strSplitted[this.strSplitted.length - 1].contains(".")){
                            String inputClean = this.panelTextView.getText().toString().substring(0, this.panelTextView.getText().toString().length() - 1);
                            this.panelTextView.setText(inputClean);
                        }

                        this.panelTextView.append(this.btnClicked.getText()); // Append input to textview
                    }
                }

                break;
            case R.id.btnDevide:
            case R.id.btnSum:
            case R.id.btnMultiply:
            case R.id.btnSubtract:
                // Handler operator buttons

                break;

        }
    }
}
