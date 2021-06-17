package project.embraceyourlife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class DaySchedule extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView data_wybranego_dnia_day;
    LinearLayout scroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayschedule);
        NavigationView navi_View = findViewById(R.id.nav_view);
        navi_View.setNavigationItemSelectedListener(this);

        scroll = (LinearLayout) findViewById(R.id.scroll);



        addEvent("XDD"); // można usunąć
    }

    //metoda dodaje event string w parametrze dla testów
    public void addEvent(String napis){
        LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.layout_event, null);
        final TextView nazwa = (TextView)addView.findViewById(R.id.Nazwa);
        final TextView czas = (TextView)addView.findViewById(R.id.Czas);
        final TextView opis = (TextView)addView.findViewById(R.id.Opis);
        nazwa.setText("nazwa");
        czas.setText("czas");
        opis.setText("opis");
        scroll.addView(addView);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            /*case R.id.akcje:
                akcjeButton();
                return true;*/
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
            addEvent("XD");
    }
    public void kalendarzButton(){
        addEvent("XD");
    }
    public void wolne_terminyButton(){

    }
    public void silowniaButton(){
        Intent i = new Intent(this, GymActivity.class);
        startActivity(i);
    }
    public void dodaj_wydarzenieButton(){
        Intent i = new Intent(this, TworzenieTreningu.class);
        // zapewnia, ze bedzie wydarzenie zamiast treningu
        i.putExtra("czy_wydarzenie", true);
        startActivity(i);
    }

    public void setData_wybranego_dnia(String data){
        data_wybranego_dnia_day = findViewById(R.id.data_wybranego_dnia);
        data_wybranego_dnia_day.setText(data);
    }

}