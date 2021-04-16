package com.example.createexerciseactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText _activity_name;
    private Switch powtorzenia;
    private Switch obciazenia;
    private Switch czas_trwania;
    private Switch dystans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner Category = (Spinner) findViewById(R.id.category);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.category));
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Category.setAdapter(Adapter);

        Button anuluj = findViewById(R.id.Anuluj);
        Button gotowe = findViewById(R.id.Gotowe);

        anuluj.setOnClickListener((v) -> {anulujButtonListener();});
        gotowe.setOnClickListener((v) -> {gotoweButtonListener();});
    }

    boolean Clear = true;
    public void clear(View view) {
        if (Clear){
            _activity_name =  (EditText) findViewById(R.id.ActivityName);
            _activity_name.setText("");
            Clear = false;
        }
    }

    public void anulujButtonListener(){
    }

    public void gotoweButtonListener(){

    }

    public String getActivityName(){
        _activity_name =  (EditText) findViewById(R.id.ActivityName);
        return _activity_name.getText().toString();
    }

    public void getCategory(){

    }

    public boolean[] getSwitches(){
        powtorzenia = findViewById(R.id.switch1);
        obciazenia = findViewById(R.id.switch2);
        czas_trwania = findViewById(R.id.switch3);
        dystans = findViewById(R.id.switch4);
        boolean array[] = new boolean[4];
        array[0] = powtorzenia.isChecked();
        array[1] = obciazenia.isChecked();
        array[2] = czas_trwania.isChecked();
        array[3] = dystans.isChecked();
        return array;
    }
}