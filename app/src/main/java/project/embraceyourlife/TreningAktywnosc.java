package project.embraceyourlife;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class TreningAktywnosc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_training);
    }
}