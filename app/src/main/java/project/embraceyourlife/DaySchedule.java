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

import com.google.android.material.navigation.NavigationView;

import java.util.Date;
import java.util.List;

import project.embraceyourlife.datatypes.Wydarzenie;
import project.embraceyourlife.parsers.TimeParser;

import static java.lang.String.valueOf;

public class DaySchedule extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView data_wybranego_dnia_day;
    private String data_danego_dnia;
    public Context context = this;
    LinearLayout scroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_dayschedule);
        NavigationView navi_View = findViewById(R.id.nav_view);
        navi_View.setNavigationItemSelectedListener(this);
        this.data_wybranego_dnia_day = (TextView)findViewById(R.id.data_wybranego_dnia);
        this.data_wybranego_dnia_day.setTextSize(30);
        Intent i = getIntent();
        String otrzymana_data = i.getStringExtra("data");

        if(otrzymana_data == null) {
            String pattern = "dd/MM/yyyy";
            this.data_danego_dnia = new SimpleDateFormat(pattern).format(new Date());
        }
        else{
            this.data_danego_dnia = otrzymana_data;
        }
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
        String godzina_poczatkowa = wydarzenie.getData().split(" ")[1];
        int czas_trwania = wydarzenie.getCzasTrwania();
        String godzina_koncowa = TimeParser.format(TimeParser.parse(godzina_poczatkowa) + czas_trwania);
        czas.setText(godzina_poczatkowa + " - " + godzina_koncowa);
        czas.setTextColor(Color.WHITE);
        opis.setText(wydarzenie.getOpis());
        opis.setTextColor(Color.WHITE);
        lista_wydarzen.addView(addView);
        //addView.setOnClickListener();
        addView.setOnLongClickListener(usunWydarzenie);
    }


    public void getAllEvents(String Data){
        List<Wydarzenie> listaWydarzen = Database.getInstance(this).getWydarzenia(Data);
        for(Wydarzenie event:listaWydarzen){
            addEvent(event);
        }
    }

    public View.OnLongClickListener usunWydarzenie = new View.OnLongClickListener(){
        @Override
        public boolean onLongClick(View v) {
            String nazwa = ((TextView)v.findViewById(R.id.Nazwa)).getText().toString();
            String czas = ((TextView)v.findViewById(R.id.Czas)).getText().toString();
            String opis = ((TextView)v.findViewById(R.id.Opis)).getText().toString();
            String godz_pocz = czas.split(" - ")[0];
            String godz_kon = czas.split(" - ")[1];
            int dlugosc = TimeParser.parse(godz_kon) - TimeParser.parse(godz_pocz);

            Database.getInstance(context).removeWydarzenie(new Wydarzenie(nazwa, "", data_danego_dnia + " " +godz_pocz, dlugosc, opis));
            ((LinearLayout)findViewById(R.id.scroll)).removeView(v);
            return true;
        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.kalendarz:
                kalendarzButton();
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

    public void kalendarzButton(){
        Intent i = new Intent(this, CalendarActivity.class);
        startActivity(i);
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