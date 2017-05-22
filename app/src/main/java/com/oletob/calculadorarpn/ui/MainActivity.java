package com.oletob.calculadorarpn.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.oletob.calculadorarpn.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // Member variables
    private TextView panelTextView;

    private Button btnClicked;
    private String numberEntered;

    private int [] calcButtons = {
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
            R.id.btn8, R.id.btn9, R.id.btnDevide, R.id.btnMultiply, R.id.btnSubtract, R.id.btnSum,
            R.id.btnEnter, R.id.btnDot, R.id.btnSymbol, R.id.btnClear, R.id.btnDelete
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        panelTextView = (TextView)findViewById(R.id.panelTextView);

        // Set reset buttons click event
        for(int id : this.calcButtons){
            findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnClear:
                panelTextView.setText("0");
                break;
            case R.id.btnDelete:
                // Remove the last number entered
                this.numberEntered = this.panelTextView.getText().toString();
                this.numberEntered = this.numberEntered.substring(0, this.numberEntered.length() -1);

                // If there is no numbers inside the panelTextView, zero will be the default value
                if(this.numberEntered.length() == 0){
                    this.numberEntered = "0";
                }

                this.panelTextView.setText(this.numberEntered);
                break;
            case R.id.btnEnter:
                this.panelTextView.append("\n0");
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
                // Handler numeric buttons

                /* A Button is also a View, so I'll cast the View parameter called v that onClick
                   method receive into a Button object, this way I can get properties of the button
                   clicked, for example the button's text */
                this.btnClicked = (Button) v;

                //Checks if the panelTextView is set to zero
                if(this.panelTextView.length() == 1 && Integer.parseInt(this.panelTextView.getText().toString()) == 0){
                    this.panelTextView.setText(this.btnClicked.getText());
                }else{
                    this.panelTextView.append(this.btnClicked.getText());
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
