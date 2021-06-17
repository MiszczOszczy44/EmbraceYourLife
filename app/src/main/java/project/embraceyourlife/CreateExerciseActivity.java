package project.embraceyourlife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class CreateExerciseActivity extends AppCompatActivity {

    private EditText _activity_name;
    private Spinner _activity_category;
    private Boolean _powtorzenia = false;
    private Boolean _obciazenia = false;
    private Boolean _czas_trwania = false;
    private Boolean _dystans = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createexercise);

        this._activity_category = (Spinner) findViewById(R.id.category);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(CreateExerciseActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Kategoria));
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this._activity_category.setAdapter(Adapter);
    }

    boolean Clear = true;
    public void clear(View view) {
        if (Clear){
            _activity_name =  (EditText) findViewById(R.id.ActivityName);
            _activity_name.setText("");
            Clear = false;
        }
    }

    public void zmienPowtorzenia(View v){
        this._powtorzenia = !this._powtorzenia;
    }

    public void zmienObciazenia(View v){
        this._obciazenia = !this._obciazenia;
    }

    public void zmienCzasTrwania(View v){
        this._czas_trwania = !this._czas_trwania;
    }

    public void zmienDystans(View v){
        this._dystans = !this._dystans;
    }

    public void anulujButtonListener(View v){
        Intent i = new Intent(this, GymActivity.class);
        startActivity(i);
    }

    public void gotoweButtonListener(View v){
//        Database baza = new Database(this);
//        baza.insertIntoCwiczenie(getActivityName(), getActivityCategory(), _powtorzenia, _obciazenia, _czas_trwania, _dystans);
//
//        Intent i = new Intent(this, GymActivity.class);
//        startActivity(i);
    }

    public String getActivityName(){
        _activity_name =  (EditText) findViewById(R.id.ActivityName);
        return _activity_name.getText().toString();
    }

    public String getActivityCategory(){
        _activity_category = (Spinner)findViewById(R.id.category);
        return _activity_category.getSelectedItem().toString();
    }
}