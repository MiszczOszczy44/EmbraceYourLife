package project.embraceyourlife;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    CalendarView calendarView;
    TextView data;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = (CalendarView) findViewById(R.id.kalendarz);
        data = (TextView) findViewById(R.id.data);
        calendarView.setOnDateChangeListener(changeDate);

    }

    public CalendarView.OnDateChangeListener changeDate = new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            String year_string = String.valueOf(year);
            String month_string = String.valueOf(month+1);
            String day_string = String.valueOf(dayOfMonth);
            if(month_string.length() == 1) month_string = "0"+ month_string;
            if(day_string.length() == 1) day_string = "0" + day_string;
            String temp_data = (day_string + "/" + month_string + "/" + year_string);
            data.setText(temp_data);

            Intent i = new Intent(context, DaySchedule.class);
            i.putExtra("data", temp_data);
            startActivity(i);
        }
    };


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
