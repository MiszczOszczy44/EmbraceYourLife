package project.embraceyourlife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class DaySchedule extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView data_wybranego_dnia_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayschedule);

        NavigationView navi_View = findViewById(R.id.nav_view);
        navi_View.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.akcje:
                akcjeButton();
                return true;
            case R.id.kalendarz:
                kalendarzButton();
                return true;
            case R.id.wolne_terminy:
                wolne_terminyButton();
                return true;
            case R.id.silownia:
                silowniaButton();
                return true;
            case R.id.dodaj_wydarzenie:
                dodaj_wydarzenieButton();
                return true;
        }
        return false;
    }

    public void akcjeButton(){

    }
    public void kalendarzButton(){

    }
    public void wolne_terminyButton(){

    }
    public void silowniaButton(){

    }
    public void dodaj_wydarzenieButton(){

    }

    public void setData_wybranego_dnia(String data){
        data_wybranego_dnia_day = findViewById(R.id.data_wybranego_dnia);
        data_wybranego_dnia_day.setText(data);
    }

}