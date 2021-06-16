package project.embraceyourlife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GymActivity extends AppCompatActivity {
    private Button statystyki;
    private Button plan_tygodnia;
    private Button dodaj_trening;
    private TextView data_wybranego_dnia_gym;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym);

        statystyki = findViewById(R.id.stat);
        plan_tygodnia = findViewById(R.id.stat);
        dodaj_trening = findViewById(R.id.dodaj_trening);

    }

    public void statystykiListener(View v){
        Intent i = new Intent(this, GymStatsActivity.class);
        startActivity(i);
    }

    public void plan_dniaListener(View v){
        Intent i = new Intent(this, DaySchedule.class);
        startActivity(i);
    }

    public void dodaj_treningListener(View v){
        Intent i = new Intent(this, TworzenieTreningu.class);
        startActivity(i);
    }

    public void dodaj_cwiczenieListener(View v){
        Intent i = new Intent(this, CreateExerciseActivity.class);
        startActivity(i);
    }

    public void setData_wybranego_dnia_gym(String data){
        data_wybranego_dnia_gym = findViewById(R.id.wybrana_data);
        data_wybranego_dnia_gym.setText(data);
    }

}