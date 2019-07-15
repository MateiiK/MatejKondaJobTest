package matekgames.com.feelifjobtest;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.speech.tts.TextToSpeech;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    int x1c;
    int x1n;
    int y1c;
    int y1n;
    int x2c;
    int x2n;
    int y2c;
    int y2n;
    int x3c;
    int x3n;
    int y3c;
    int y3n;

        private TextToSpeech textToSpeech;
        private EditText inputText;
        Button switchView;
        String[] langDropdown={"Angleščina","Nemščina","Francoščina","Italjanščina"};
        int ttsLang;
        int height;
        int width;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

            setContentView(R.layout.activity_main);

            switchView = findViewById(R.id.switchView);
            Button textToSpeechBtn = findViewById(R.id.textToSpeechBtn);
            inputText = findViewById(R.id.inputText);

            ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.spinner_item,langDropdown);
            arrayAdapter.setDropDownViewResource(R.layout.spinner_item);

            Spinner spinner = findViewById(R.id.spinner);
            spinner.setSelection(0);
            spinner.setAdapter(arrayAdapter);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            height = displayMetrics.heightPixels / 100;
            width = displayMetrics.widthPixels / 100;

            ConstraintLayout main = findViewById(R.id.main);

            main.setOnTouchListener(new ConstraintLayout.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if( event.getPointerCount() == 3) {
                        int action = event.getActionMasked();
                        switch (action) {
                            case MotionEvent.ACTION_POINTER_DOWN:
                                x1c = (int) event.getX(0);
                                y1c = (int) event.getY(0);
                                x2c = (int) event.getX(1);
                                y2c = (int) event.getY(1);
                                x3c = (int) event.getX(2);
                                y3c = (int) event.getY(2);
                                Log.d("start","y1:"+y1c+", y2:"+y2c);
                                break;
                            case MotionEvent.ACTION_MOVE:
                                x1n = (int) event.getX(0);
                                y1n = (int) event.getY(0);
                                x2n = (int) event.getX(1);
                                y2n = (int) event.getY(1);
                                x3n = (int) event.getX(2);
                                y3n = (int) event.getY(2);
                                float diffX1 = x1c - x1n;
                                float diffX2 = x2c - x2n;
                                float diffX3 = x3c - x3n;
                                float diffY1 = y1c - y1n;
                                float diffY2 = y2c - y2n;
                                float diffY3 = y3c - y3n;
                                //preveri ce je bil swipe gor / dol
                                if (Math.abs(diffX1) < Math.abs(diffY1) && Math.abs(diffX2) < Math.abs(diffY2) && Math.abs(diffX3) < Math.abs(diffY3)) {
                                    //preveri ce je bil swipe navzdol
                                    if (diffY1 < 0 && diffY3 < 0) {
                                        //je bil swipe iz vrha zaslona in vsaj 20% zaslona
                                        if (Math.abs(diffY1) > (height * 20) && (y1c < height * 20 || y2c < height * 20)) {
                                            finishAffinity();
                                        }
                                    }
                                }
                        }

                    }
                    return true;
                }
            });

            switchView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shapes();
                }
            });

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int number=parent.getSelectedItemPosition();
                    switch (number){
                        case 0:
                            ttsLang = textToSpeech.setLanguage(Locale.US);
                        break;
                        case 1:
                            ttsLang = textToSpeech.setLanguage(Locale.GERMANY);
                        break;
                        case 2:
                            ttsLang = textToSpeech.setLanguage(Locale.FRANCE);
                        break;
                        case 3:
                            textToSpeech.setLanguage(Locale.ITALY);
                        break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                }
            });

            textToSpeechBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    String data = inputText.getText().toString();
                    int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null,null);
                    if (speechStatus == TextToSpeech.ERROR) {
                        Log.e("TTS", "Error in converting Text to Speech!");
                    }
                }
            });
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            if (textToSpeech != null) {
                textToSpeech.stop();
                textToSpeech.shutdown();
            }
        }

    public void shapes() {
        Intent intent = new Intent(this, Shapes.class);
        startActivity(intent);
    }

    public void onBackPressed(){
    }
}