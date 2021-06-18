package project.embraceyourlife;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import project.embraceyourlife.datatypes.Cwiczenie;
import project.embraceyourlife.datatypes.CwiczenieINFO;
import project.embraceyourlife.datatypes.Wydarzenie;

public class TworzenieTreningu extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private boolean czy_wydarzenie;
    LinearLayout scrollDoCwiczen;
    StringBuilder Cwiczenia;
    Context context = this.context;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtraining);
        powtarzalnoscSpinner();

        scrollDoCwiczen = (LinearLayout) findViewById(R.id.Cwiczenia);

        Intent i = getIntent();
        this.czy_wydarzenie = i.getBooleanExtra("czy_wydarzenie", false);
        ScrollView scroll = findViewById(R.id.ListaCwiczenWTreninguScroll);
        TextView opis_wydarzenia = findViewById(R.id.OpisWydarzeniaMultiLine);
        FloatingActionButton plusik = findViewById(R.id.add);

        Cwiczenia = new StringBuilder();
        //wydarzenie
        if(czy_wydarzenie) {
            scroll.setVisibility(View.INVISIBLE);
            opis_wydarzenia.setVisibility(View.VISIBLE);
            plusik.setVisibility(View.INVISIBLE);

            EditText nazwa = findViewById(R.id.editNazwa);
            nazwa.setHint("Nazwa wydarzenia");
            ((TextView)findViewById(R.id.PlanTreningu_OpisWydarzeniaText)).setText("Opis wydarzenia");
        }
        //trening
        else {
            scroll.setVisibility(View.VISIBLE);
            opis_wydarzenia.setVisibility(View.INVISIBLE);
            plusik.setVisibility(View.VISIBLE);

            stworzListeCwiczen();
        }

    }

    public void stworzListeCwiczen(){
        LinearLayout lista = (LinearLayout)findViewById(R.id.CwiczeniaWTreninguScrollViewLayout);
        ArrayList<CwiczenieINFO> lista_cwiczen = new ArrayList<CwiczenieINFO>(Database.getInstance(this).getCwiczeniaINFO_id());
        for (CwiczenieINFO cwiczenie: lista_cwiczen) {
            TextView cwiczenieTekst = new TextView(this);
            cwiczenieTekst.setText(cwiczenie.nazwa);
            cwiczenieTekst.setId(cwiczenie.id);
            cwiczenieTekst.setClickable(true);
            cwiczenieTekst.setTextSize(30);
            cwiczenieTekst.setOnClickListener(dodajSpecyfikacjeCwiczenia);
            cwiczenieTekst.setOnLongClickListener(this.usunCwiczenie);

            cwiczenieTekst.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            lista.addView(cwiczenieTekst);
        }
    }

    public void wyczyscListCwiczen() {
        LinearLayout lista = (LinearLayout)findViewById(R.id.CwiczeniaWTreninguScrollViewLayout);
        for(int j = 0; j<lista.getChildCount(); j++){
            lista.removeViewAt(j);
        }
    }

    @SuppressLint("SetTextI18n")
    public void timePicker(View v) {
        final TimePickerDialog[] timePicker = new TimePickerDialog[1];
        EditText TimeSelect = findViewById(R.id.editCzas);
        timePicker[0] = new TimePickerDialog(TworzenieTreningu.this,
                (view, hourOfDay, minute) -> TimeSelect.setText(hourOfDay + ":" + minute), 0, 0, true);
        timePicker[0].show();
    }

    // Ta komenda dodaje ćwiczenie string w parametrach jest dla testów
    public void addCwiczenie(String Nazwa,String czasLiczba, String dystansLiczba, String powtorzeniaLiczba, String ciezarLiczba){
        LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.layout_exercise, null);
        final TextView nazwacwiczenia = (TextView)addView.findViewById(R.id.nazwacwiczenia);
        final TextView czas = (TextView)addView.findViewById(R.id.czas);
        final TextView dystans = (TextView)addView.findViewById(R.id.dystans);
        final TextView powtorzenia = (TextView)addView.findViewById(R.id.powtorzenia);
        final TextView ciezar = (TextView)addView.findViewById(R.id.ciezar);
        final TextView czasliczba = (TextView)addView.findViewById(R.id.czasLiczba);
        final TextView dystansliczba = (TextView)addView.findViewById(R.id.dystansLiczba);
        final TextView powtorzenialiczba = (TextView)addView.findViewById(R.id.powtorzeniaLiczba);
        final TextView ciezarliczba = (TextView)addView.findViewById(R.id.ciezarLiczba);
        nazwacwiczenia.setText(Nazwa);
        czas.setText("Czas:");
        dystans.setText("Dystans:");
        powtorzenia.setText("Powtórzenia:");
        ciezar.setText("Ciężar:");
        czasliczba.setText(czasLiczba);
        dystansliczba.setText(dystansLiczba);
        powtorzenialiczba.setText(powtorzeniaLiczba);
        ciezarliczba.setText(ciezarLiczba);
        scrollDoCwiczen.addView(addView);
    }


    @SuppressLint("SetTextI18n")
    public void datePicker(View v){
        final DatePickerDialog[] datePicker = new DatePickerDialog[1];
        EditText DataSelect = findViewById(R.id.editData);
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date datePickerDialog dialog
        datePicker[0] = new DatePickerDialog(TworzenieTreningu.this,
                (view, year1, monthOfYear, dayOfMonth) -> DataSelect.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
        datePicker[0].show();
    }

    private void powtarzalnoscSpinner() {
        Spinner spinner = findViewById(R.id.spinnerPowtarzalnosc);
        ArrayAdapter<CharSequence> Adapter = ArrayAdapter.createFromResource(this, R.array.Powtarzalnosc, android.R.layout.simple_spinner_item);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(Adapter);
        //spinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @SuppressLint("SetTextI18n")
    public void dodajCwiczenie(View v){
        View widok_cwiczen = findViewById(R.id.OknoCwiczenWTreningu);
        int widocznosc_cwiczen = widok_cwiczen.getVisibility();
        View widok_dodawania_cwiczen = findViewById(R.id.OknoNowegoCwiczeniaWTreningu);

        if(widok_dodawania_cwiczen.getVisibility() == View.VISIBLE) return;
        if(widocznosc_cwiczen == View.VISIBLE){
            widok_cwiczen.setVisibility(View.INVISIBLE);
        }
        else{
            widok_cwiczen.setVisibility(View.VISIBLE);
        }
    }

    public void noweCwiczenie(View v){

    }

    public View.OnClickListener dodajSpecyfikacjeCwiczenia = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View widok_dodawania_cwiczen = findViewById(R.id.OknoNowegoCwiczeniaWTreningu);
            View widok_cwiczen = findViewById(R.id.OknoCwiczenWTreningu);
            CwiczenieINFO cwiczenie = Database.getInstance(context).getCwiczenieINFO(((TextView)v).getText().toString());
            ustawWidocznoscAtrybutow(cwiczenie.powtorzenia, cwiczenie.obciazenie, cwiczenie.czas, cwiczenie.dystans);

            widok_dodawania_cwiczen.setVisibility(View.VISIBLE);
            widok_cwiczen.setVisibility(View.INVISIBLE);

            String nazwa_cwiczenia = ((TextView)v).getText().toString();
            ((TextView)findViewById(R.id.NoweCwiczenieWTreninguNazwa)).setText(nazwa_cwiczenia);
        }
    };

    public View.OnLongClickListener usunCwiczenie = new View.OnLongClickListener(){
        @Override
        public boolean onLongClick(View v) {
            String nazwa_cwiczenia = ((TextView)v).getText().toString();
            Database.getInstance(context).removeCwiczenie(nazwa_cwiczenia);
            wyczyscListCwiczen();
            stworzListeCwiczen();

            return true; //true sprawia, ze zwykly onClick sie nie odpali
        }
    };

    public void ustawWidocznoscAtrybutow(boolean powtorzenia, boolean obciazenie, boolean czas, boolean dystans){
        if(powtorzenia)findViewById(R.id.RzadPowtorzen).setVisibility(View.VISIBLE);
        else findViewById(R.id.RzadPowtorzen).setVisibility(View.INVISIBLE);

        if(obciazenie)findViewById(R.id.RzadObciazenia).setVisibility(View.VISIBLE);
        else findViewById(R.id.RzadObciazenia).setVisibility(View.INVISIBLE);

        if(czas)findViewById(R.id.RzadCzasu).setVisibility(View.VISIBLE);
        else findViewById(R.id.RzadCzasu).setVisibility(View.INVISIBLE);

        if(dystans)findViewById(R.id.RzadDystansu).setVisibility(View.VISIBLE);
        else findViewById(R.id.RzadDystansu).setVisibility(View.INVISIBLE);
    }


    public void schowajOkno(View v){
        View widok_cwiczen = findViewById(R.id.OknoCwiczenWTreningu);
        View widok_dodawania_cwiczen = findViewById(R.id.OknoNowegoCwiczeniaWTreningu);
        if(widok_dodawania_cwiczen.getVisibility() == View.VISIBLE){
            widok_dodawania_cwiczen.setVisibility(View.INVISIBLE);
            widok_cwiczen.setVisibility(View.VISIBLE);
            dodajCwiczenieDoScrollView();
        }
        else if(widok_cwiczen.getVisibility() == View.VISIBLE){
            widok_cwiczen.setVisibility(View.INVISIBLE);
        }
    }

    public void dodajCwiczenieDoScrollView(){
        StringBuilder napis = new StringBuilder();
        final EditText Powtarzalnosc = (EditText)findViewById(R.id.NoweCwiczenieWTreninguIlosc1);
        final EditText Obciazenia = (EditText)findViewById(R.id.NoweCwiczenieWTreninguIlosc2);
        final EditText Czas = (EditText)findViewById(R.id.NoweCwiczenieWTreninguIlosc3);
        final EditText Dystans = (EditText)findViewById(R.id.NoweCwiczenieWTreninguIlosc4);
        final TextView NazwaView = (TextView)findViewById(R.id.NoweCwiczenieWTreninguNazwa);
        String Nazwa = NazwaView.getText().toString();

        String Pw = Powtarzalnosc.getText().toString();
        String ob = Obciazenia.getText().toString();
        String Cz = Czas.getText().toString();
        String dt = Dystans.getText().toString();

        Powtarzalnosc.setText("");
        Obciazenia.setText("");
        Czas.setText("");
        Dystans.setText("");

        napis.append(Nazwa);
        napis.append("\n");
        if(!Pw.equals("")){
            napis.append("Powtarzalnosc ");
            napis.append(Pw);
            napis.append("\n");
        }
        if(!ob.equals("")){
            napis.append("Obciazenie ");
            napis.append(ob);
            napis.append("kg\n");
        }
        if(!Cz.equals("")){
            napis.append("Czas ");
            napis.append(Cz);
            napis.append("min\n");
        }
        if(!dt.equals("")){
            napis.append("Dystans ");
            napis.append(dt);
            napis.append("m\n\n");
        }
        Cwiczenia.append(napis.toString());
        addCwiczenie(Nazwa,Cz, dt, Pw, ob);
    }



    public void wrocDoGym(View v){
        //Powrot do aktywnosci silowni lub dayschedule
        //Trzeba bedzie jeszcze zapisac trening do bazy
        Intent i;

        String nazwa= ((EditText)findViewById(R.id.editNazwa)).getText().toString();
        String powtarzalnosc = ((Spinner)findViewById(R.id.spinnerPowtarzalnosc)).getSelectedItem().toString();
        String czas = ((EditText)findViewById(R.id.editCzas)).getText().toString();
        String data = ((EditText)findViewById(R.id.editData)).getText().toString();
        //opis wydarzenia lub lista cwiczen jest w ifie

        if(this.czy_wydarzenie){
            i = new Intent(this, DaySchedule.class);
            String opis = ((EditText)findViewById(R.id.OpisWydarzeniaMultiLine)).getText().toString();
            Database.getInstance(this).insert(new Wydarzenie(nazwa, powtarzalnosc, data + " " + czas, 0, opis));
        }
        else{
            i = new Intent(this, GymActivity.class);
            Database.getInstance(this).insert(new Wydarzenie(nazwa, powtarzalnosc, data + " " + czas, 0, Cwiczenia.toString()));
        }

        List<Wydarzenie> lista_wydarzen = Database.getInstance(this).getWydarzenia(data + " " + czas);
        Wydarzenie wydarzenie = lista_wydarzen.get(0);
        startActivity(i);
    }

}
