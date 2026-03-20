package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Menu for selecting what you want to convert from
    Spinner sourceUnit;
    //Menu for selecting what you want to convert to
    Spinner destUnit;
    //Button to toggle conversion
    Button convertButton;
    //Text Field to enter value
    EditText sourceValue;
    //Source Number
    Double sourceValueDouble;
    //Final Result
    Double result;
    //Decimal Formatting for result (I can't believe I need this)
    DecimalFormat numberFormat = new DecimalFormat("#0.000");
    //Display for final result
    TextView destValue;
    //Selected position in adapter array of sourceUnit - used in switch
    Integer pos1;
    //Selected position in adapter array of destUnit
    Integer pos2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        convertButton = findViewById(R.id.convertButton);
        sourceValue = findViewById(R.id.sourceValue);
        destValue = findViewById(R.id.destValue);

        sourceUnit = findViewById(R.id.sourceUnit);
        destUnit = findViewById(R.id.destUnit);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.conversion_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceUnit.setAdapter(adapter);
        destUnit.setAdapter(adapter);

        sourceUnit.setOnItemSelectedListener(this);
        destUnit.setOnItemSelectedListener(this);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sourceValueDouble = Double.valueOf(sourceValue.getText().toString());
                calculatorSwitch(pos1, pos2, sourceValueDouble);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == sourceUnit) {
            pos1 = position;
        }
        else {
            pos2 = position;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void calculatorSwitch(int pos1, int pos2, double value) {
        switch (pos1) {
            //USD
            case 0:
                if (pos2 == 1) {
                    result = value * 1.55;
                }
                else if (pos2 == 2) {
                    result = value * 0.92;
                }
                else if (pos2 == 3) {
                    result = value * 148.50;
                }
                else if (pos2 == 4) {
                    result = value * 0.78;
                }
                else {
                    Toast.makeText(MainActivity.this, "Error: Invalid Conversion Option.",
                            Toast.LENGTH_LONG).show();
                    destValue.setText("");
                    break;
                }

                //may need to *bound* these fucking results but it works for the hot minute
                destValue.setText(numberFormat.format(result));

                break;
            //AUD
            case 1:
                if (pos2 == 0) {
                    result = value / 1.55;
                }
                else if (pos2 == 2) {
                    result = value / 1.55;
                    result = result * 0.92;
                }
                else if (pos2 == 3) {
                    result = value / 1.55;
                    result = result * 148.50;
                }
                else if (pos2 == 4) {
                    result = value / 1.55;
                    result = result * 0.78;
                }
                else {
                    Toast.makeText(MainActivity.this, "Error: Invalid Conversion Option.",
                            Toast.LENGTH_LONG).show();
                    destValue.setText("");
                    break;
                }

                destValue.setText(numberFormat.format(result));
                break;
            //EUR
            case 2:
                if (pos2 == 0) {
                    result = value / 0.92;
                }
                else if (pos2 == 1) {
                    result = value / 0.92;
                    result = result * 1.55;
                }
                else if (pos2 == 3) {
                    result = value / 0.92;
                    result = result * 148.50;
                }
                else if (pos2 == 4) {
                    result = value / 0.92;
                    result = result * 0.78;
                }
                else {
                    Toast.makeText(MainActivity.this, "Error: Invalid Conversion Option.",
                            Toast.LENGTH_LONG).show();
                    destValue.setText("");
                    break;
                }

                destValue.setText(numberFormat.format(result));
                break;
            //JPY
            case 3:
                if (pos2 == 0) {
                    result = value / 148.50;
                }
                else if (pos2 == 1) {
                    result = value / 148.50;
                    result = result * 1.55;
                }
                else if (pos2 == 2) {
                    result = value / 148.50;
                    result = result * 0.92;
                }
                else if (pos2 == 4) {
                    result = value / 148.50;
                    result = result * 0.78;
                }
                else {
                    Toast.makeText(MainActivity.this, "Error: Invalid Conversion Option.",
                            Toast.LENGTH_LONG).show();
                    destValue.setText("");
                    break;
                }

                destValue.setText(numberFormat.format(result));
                break;
            //GBP
            case 4:
                if (pos2 == 0) {
                    result = value / 0.78;
                }
                else if (pos2 == 1) {
                    result = value / 0.78;
                    result = result * 1.55;
                }
                else if (pos2 == 2) {
                    result = value / 0.78;
                    result = result * 0.92;
                }
                else if (pos2 == 3) {
                    result = value / 0.78;
                    result = result * 148.50;
                }
                else {
                    Toast.makeText(MainActivity.this, "Error: Invalid Conversion Option.",
                            Toast.LENGTH_LONG).show();
                    destValue.setText("");
                    break;
                }

                destValue.setText(numberFormat.format(result));
                break;
            //Miles per Gallon
            case 5:
                if (pos2 == 6) {
                    result = value * 0.425;
                }
                else {
                    Toast.makeText(MainActivity.this, "Error: Invalid Conversion Option.",
                            Toast.LENGTH_LONG).show();
                    destValue.setText("");
                    break;
                }

                destValue.setText(numberFormat.format(result));
                break;
            //Kilometers per Liter
            case 6:
                if (pos2 == 5) {
                    result = value / 0.425;
                }
                else {
                    Toast.makeText(MainActivity.this, "Error: Invalid Conversion Option.",
                            Toast.LENGTH_LONG).show();
                    destValue.setText("");
                    break;
                }

                destValue.setText(numberFormat.format(result));
                break;
            //Gallon (US)
            case 7:
                if (pos2 == 8) {
                    result = value * 3.785;
                }
                else {
                    Toast.makeText(MainActivity.this, "Error: Invalid Conversion Option.",
                            Toast.LENGTH_LONG).show();
                    destValue.setText("");
                    break;
                }

                destValue.setText(numberFormat.format(result));
                break;
            //Liters
            case 8:
                if (pos2 == 7) {
                    result = value / 3.785;
                }
                else {
                    Toast.makeText(MainActivity.this, "Error: Invalid Conversion Option.",
                            Toast.LENGTH_LONG).show();
                    destValue.setText("");
                    break;
                }

                destValue.setText(numberFormat.format(result));
                break;
            //Nautical Mile
            case 9:
                if (pos2 == 10) {
                    result = value * 1.852;
                }
                else {
                    Toast.makeText(MainActivity.this, "Error: Invalid Conversion Option.",
                            Toast.LENGTH_LONG).show();
                    destValue.setText("");
                    break;
                }

                destValue.setText(numberFormat.format(result));
                break;
            //Kilometers
            case 10:
                if (pos2 == 9) {
                    result = value / 1.852;
                }
                else {
                    Toast.makeText(MainActivity.this, "Error: Invalid Conversion Option.",
                            Toast.LENGTH_LONG).show();
                    destValue.setText("");
                    break;
                }

                destValue.setText(numberFormat.format(result));
                break;
            //Celsius
            case 11:
                if (pos2 == 12) {
                    result = (value * 1.8) + 32;
                }
                else if (pos2 == 13) {
                    result = value + 273.15;
                }
                else {
                    Toast.makeText(MainActivity.this, "Error: Invalid Conversion Option.",
                            Toast.LENGTH_LONG).show();
                    destValue.setText("");
                    break;
                }

                destValue.setText(numberFormat.format(result));
                break;
            //Fahrenheit
            case 12:
                if (pos2 == 11) {
                    result = (value - 32) / 1.8;
                }
                else if (pos2 == 13) {
                    result = ((value - 32) / 1.8)+ 273.15;
                }
                else {
                    Toast.makeText(MainActivity.this, "Error: Invalid Conversion Option.",
                            Toast.LENGTH_LONG).show();
                    destValue.setText("");
                    break;
                }

                destValue.setText(numberFormat.format(result));
                break;
            //Kelvin
            case 13:
                if (pos2 == 11) {
                    result = value - 273.15;
                }
                else if (pos2 == 12) {
                    result = ((value - 273.15) * 1.8) + 32;
                }
                else {
                    Toast.makeText(MainActivity.this, "Error: Invalid Conversion Option.",
                            Toast.LENGTH_LONG).show();
                    destValue.setText("");
                    break;
                }

                destValue.setText(numberFormat.format(result));
                break;

            default:
                Toast.makeText(MainActivity.this, "Error: Invalid Conversion Option.",
                        Toast.LENGTH_LONG).show();
                destValue.setText("");
                break;
        }
    }
}