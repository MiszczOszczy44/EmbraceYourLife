package project.embraceyourlife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        statystyki.setOnClickListener(v -> {statystykiListener();});
        plan_tygodnia.setOnClickListener(v -> {plan_tygodniaListener();});
        dodaj_trening.setOnClickListener(v -> {dodaj_treningListener();});
    }

    public void statystykiListener(){

    }

    public void plan_tygodniaListener(){

    }

    public void dodaj_treningListener(){

    }

    public void setData_wybranego_dnia_gym(String data){
        data_wybranego_dnia_gym = findViewById(R.id.wybrana_data);
        data_wybranego_dnia_gym.setText(data);
    }

}