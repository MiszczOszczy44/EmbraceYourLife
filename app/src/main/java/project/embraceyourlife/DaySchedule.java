package project.embraceyourlife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Calendar;

import com.google.android.material.navigation.NavigationView;

import java.util.Date;
import java.util.List;

import project.embraceyourlife.datatypes.Wydarzenie;

import static java.lang.String.valueOf;

public class DaySchedule extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView data_wybranego_dnia_day;
    private String data_danego_dnia;
    LinearLayout scroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayschedule);
        NavigationView navi_View = findViewById(R.id.nav_view);
        navi_View.setNavigationItemSelectedListener(this);
        this.data_wybranego_dnia_day = (TextView)findViewById(R.id.data_wybranego_dnia);
        String pattern = "dd/MM/yyyy HH:MM";
        this.data_danego_dnia =new SimpleDateFormat(pattern).format(new Date());
        this.data_wybranego_dnia_day.setText(this.data_danego_dnia);

        scroll = (LinearLayout) findViewById(R.id.scroll);
        getAllEvents(this.data_danego_dnia);

    }

    //metoda dodaje event string w parametrze dla testów
    public void addEvent(Wydarzenie wydarzenie){
        LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.layout_event, null);
        final TextView nazwa = (TextView)addView.findViewById(R.id.Nazwa);
        final TextView czas = (TextView)addView.findViewById(R.id.Czas);
        final TextView opis = (TextView)addView.findViewById(R.id.Opis);
        LinearLayout lista_wydarzen = findViewById(R.id.scroll);
        nazwa.setText(wydarzenie.getNazwa());
        nazwa.setTextColor(Color.WHITE);
        czas.setText(valueOf(wydarzenie.getCzasTrwania()));
        czas.setTextColor(Color.WHITE);
        opis.setText(wydarzenie.getOpis());
        opis.setTextColor(Color.WHITE);
        lista_wydarzen.addView(addView);
    }

    public void dodajWydarzenieDoScrollView() {
    }

    public void getAllEvents(String Data){
        List<Wydarzenie> listaWydarzen = Database.getInstance(this).getWydarzenia(Data);
        for(Wydarzenie event:listaWydarzen){
            addEvent(event);
        }
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
    }
    public void kalendarzButton(){
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