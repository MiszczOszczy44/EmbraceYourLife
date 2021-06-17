package project.embraceyourlife;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

public class TworzenieTreningu extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private boolean czy_wydarzenie;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtraining);
        powtarzalnoscSpinner();

        Intent i = getIntent();
        this.czy_wydarzenie = i.getBooleanExtra("czy_wydarzenie", false);
        ScrollView scroll = findViewById(R.id.ListaCwiczenWTreninguScroll);
        TextView opis_wydarzenia = findViewById(R.id.OpisWydarzeniaMultiLine);
        FloatingActionButton plusik = findViewById(R.id.add);

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

            LinearLayout lista = findViewById(R.id.CwiczeniaWTreninguScrollViewLayout);
            Database baza = new Database(this);
            List<Database.Cwiczenie> lista_cwiczen = baza.getCwiczenie();
            for (Database.Cwiczenie cwiczenie: lista_cwiczen) {
                TextView cwiczenieTekst = new TextView(this);
                cwiczenieTekst.setText(cwiczenie.Nazwa);
                cwiczenieTekst.setId(cwiczenie.ID);
                cwiczenieTekst.setClickable(true);
                cwiczenieTekst.setOnClickListener(dodajSpecyfikacjeCwiczenia);

                cwiczenieTekst.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                lista.addView(cwiczenieTekst);
            }
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

            widok_dodawania_cwiczen.setVisibility(View.VISIBLE);
            widok_cwiczen.setVisibility(View.INVISIBLE);

            String nazwa_cwiczenia = ((TextView)v).getText().toString();
            ((TextView)findViewById(R.id.NoweCwiczenieWTreninguNazwa)).setText(nazwa_cwiczenia);
        }
    };

    public void schowajOkno(View v){
        View widok_cwiczen = findViewById(R.id.OknoCwiczenWTreningu);
        View widok_dodawania_cwiczen = findViewById(R.id.OknoNowegoCwiczeniaWTreningu);
        Toast.makeText(this, "Dzialam!", Toast.LENGTH_SHORT).show();
        if(widok_dodawania_cwiczen.getVisibility() == View.VISIBLE){
            widok_dodawania_cwiczen.setVisibility(View.INVISIBLE);
            widok_cwiczen.setVisibility(View.VISIBLE);
        }
        else if(widok_cwiczen.getVisibility() == View.VISIBLE){
            widok_cwiczen.setVisibility(View.INVISIBLE);
        }
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
        }
        else{
            i = new Intent(this, GymActivity.class);
            //z lista cwiczen bedziemy sie pierdolic, jak juz bedzie widok
        }

        startActivity(i);
    }

}
