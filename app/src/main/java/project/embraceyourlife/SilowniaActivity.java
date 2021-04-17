package project.embraceyourlife;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SilowniaActivity extends AppCompatActivity {

    private TextView odbyteTreningiTydzien, odbyteTreningiMiesiac;
    private TextView opuszczoneTreningiTydzien, opuszczoneTreningiMiesiac;
    private TextView czasPoswieconyTydzien, czasPoswieconyMiesiac;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silownia);

        Button statystykiCwiczen = (Button)findViewById(R.id.statystykiCwiczenButton);
        Button statystykiOgolne = (Button)findViewById(R.id.statystykiOgolneButton);

        statystykiCwiczen.setOnClickListener((v) -> {statystykiCwiczenListener();});
        statystykiOgolne.setOnClickListener((v) -> {statystykiOgolneListener();});

        odbyteTreningiTydzien = (TextView)findViewById(R.id.odbyteTreningiTydzienTextView);
        odbyteTreningiMiesiac = (TextView)findViewById(R.id.odbyteTreningiMiesiacTextView);
        opuszczoneTreningiTydzien = (TextView)findViewById(R.id.opuszczoneTreningiTydzienTextView);
        opuszczoneTreningiMiesiac = (TextView)findViewById(R.id.opuszczoneTreningiMiesiacTextView);
        czasPoswieconyTydzien = (TextView)findViewById(R.id.czasPoswieconyTydzienTextView);
        czasPoswieconyMiesiac = (TextView)findViewById(R.id.czasPoswieconyMiesiacTextView);
    }

    public void statystykiCwiczenListener()
    {

    }

    public void statystykiOgolneListener()
    {

    }
}