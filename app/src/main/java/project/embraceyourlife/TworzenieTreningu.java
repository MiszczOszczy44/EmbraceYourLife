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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class TworzenieTreningu extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtraining);

        powtarzalnoscSpinner();
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

    public void dodajCwiczenie(View v){
        View widok_cwiczen = findViewById(R.id.OknoCwiczenWTreningu);
        int widocznosc_cwiczen = widok_cwiczen.getVisibility();
        if(widocznosc_cwiczen == View.VISIBLE){
            widok_cwiczen.setVisibility(View.INVISIBLE);
        }
        else{
            int i = 1;
            widok_cwiczen.setVisibility(View.VISIBLE);
            LinearLayout lista = (LinearLayout)findViewById(R.id.CwiczeniaWTreninguScrollViewLayout);
            Database baza = new Database(this);
            List<Database.Cwiczenie> lista_cwiczen = baza.getCwiczenie();
            for (Database.Cwiczenie cwiczenie: lista_cwiczen) {
                TextView cwiczenieTekst = new TextView(this);
                cwiczenieTekst.setText(cwiczenie.Nazwa);
                cwiczenieTekst.setId(i);
                cwiczenieTekst.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                lista.addView(cwiczenieTekst);
                ++i;
            }
        }
    }

    public void noweCwiczenie(View v){

    }

    public void dodajSpecyfikacjeCwiczenia(View v){
        View widok_dodawania_cwiczen = findViewById(R.id.OknoNowegoCwiczeniaWTreningu);
        View widok_cwiczen = findViewById(R.id.OknoCwiczenWTreningu);
        widok_dodawania_cwiczen.setVisibility(View.VISIBLE);
        widok_cwiczen.setVisibility(View.INVISIBLE);

    }

    public void schowajOkno(View v){
        View widok_cwiczen = findViewById(R.id.OknoCwiczenWTreningu);
        View widok_dodawania_cwiczen = findViewById(R.id.OknoNowegoCwiczeniaWTreningu);
        if(widok_dodawania_cwiczen.getVisibility() == View.VISIBLE){
            widok_dodawania_cwiczen.setVisibility(View.INVISIBLE);
            widok_cwiczen.setVisibility(View.VISIBLE);
        }
        else if(widok_cwiczen.getVisibility() == View.VISIBLE){
            widok_cwiczen.setVisibility(View.INVISIBLE);
        }
        //Mozliwe, ze do ponizszego kodu trzeba bedzie zrobic nowa metoda.
        //Nie mam pojecia, czy do przyrowanie ma jakikolwiek sens.
        /*Button przycisk = findViewById(R.id.NoweCwiczeniePrzyciskDodaj);
        if((Button)v == przycisk)
        {
            //zapisz do bazy
        }*/
    }


    public void wrocDoGym(View v){
        //Powrot do aktywnosci silowni
        //Trzeba bedzie jeszcze zapisac trening do bazy
        Intent i = new Intent(this, GymActivity.class);
        startActivity(i);
    }

}
