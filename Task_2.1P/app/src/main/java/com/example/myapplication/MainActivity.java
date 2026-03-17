package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Menu for selecting what you want to convert from
    Spinner sourceUnit;
    //Menu for selecting what you want to convert to
    Spinner destUnit;
    //Button to toggle conversion
    Button convertButton;
    //Text Field to enter value
    EditText sourceValue;
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
                Toast.makeText(MainActivity.this,
                        "Hello " + sourceValue.getText().toString() + " " + pos1 + " " + pos2.toString(),
                        Toast.LENGTH_LONG).show();
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
    // Next step - copy over the core spinner logic to this document, and use toasts to test that
    // the correct information is coming through (spinner position (ideally name/id but we'll
    // settle), captured text value)

    // then implement a Switch that handles the calculation logic - it will check for what the
    // second spinner provides, spit up a toast for 'lol no' if its an incompatible unit type,
    // and then push the calculated numbers to the result text field :)
}