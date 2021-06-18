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

import project.embraceyourlife.datatypes.CwiczenieINFO;

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
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_createexercise);

    }

    /*boolean Clear = true;
    public void clear(View view) {
        if (Clear){
            _activity_name =  (EditText) findViewById(R.id.ActivityName);
            _activity_name.setText("");
            Clear = false;
        }
    }*/

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
        CwiczenieINFO Cwiczenie = new CwiczenieINFO(getActivityName(), _powtorzenia, _obciazenia, _czas_trwania, _dystans);
        Database.getInstance(this).insert(Cwiczenie);

        Intent i = new Intent(this, GymActivity.class);
        startActivity(i);
    }

    public String getActivityName(){
        _activity_name = findViewById(R.id.ActivityName);
        return _activity_name.getText().toString();
    }

}