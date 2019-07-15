package matekgames.com.feelifjobtest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


public class Shapes extends AppCompatActivity{
    int currSize;
    int height;
    int width;
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

    int r;
    int g;
    int b;

    Button switchView;
    ImageButton circleBtn;
    ImageButton trikotnikBtn;
    ImageButton kvadratBtn;
    ImageView trikotnik;
    ImageView circle;
    ImageView kvadrat;
    TextView procenti;
    SeekBar size;
    String active = "trikotnik";
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shapes);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        switchView = findViewById(R.id.switchView);
        size = findViewById(R.id.size);

        trikotnik = findViewById(R.id.trikotnik);
        trikotnikBtn = findViewById(R.id.trikotnikBtn);

        circle = findViewById(R.id.circle);
        circleBtn = findViewById(R.id.circleBtn);

        kvadrat = findViewById(R.id.kvadrat);
        kvadratBtn = findViewById(R.id.kvadratBtn);

        procenti = findViewById(R.id.procent);

        trikotnik.setVisibility(View.INVISIBLE);
        circle.setVisibility(View.INVISIBLE);
        kvadrat.setVisibility(View.INVISIBLE);

        final ConstraintLayout shapes = findViewById(R.id.shapes);

        size.setProgress(50);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels / 100;
        width = displayMetrics.widthPixels / 100;

        trikotnik.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                jeOutline(event);
                    return true;
                }

        });

        circle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                jeOutline(event);
                return true;
            }

        });
        kvadrat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                jeOutline(event);
                return true;
            }

        });

        switchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main();
            }
        });

        shapes.setOnTouchListener(new ConstraintLayout.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handleTouch(event);
                return true;
            }
        });

        size.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currSize = seekBar.getProgress();
                if (currSize == 0) currSize = 1;
                if (active.equals("trikotnik")) {
                    trikotnik.setDrawingCacheEnabled(false);
                    trikotnik.getLayoutParams().height = height * currSize;
                    trikotnik.requestLayout();
                } else if (active.equals("circle")) {
                    circle.setDrawingCacheEnabled(false);
                    circle.getLayoutParams().height = height * currSize;
                    circle.requestLayout();
                }else if (active.equals("kvadrat")){
                    kvadrat.setDrawingCacheEnabled(false);
                    kvadrat.getLayoutParams().height = height * currSize;
                    kvadrat.getLayoutParams().width = height * currSize;
                    kvadrat.requestLayout();
                }
                procenti.setText(currSize + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        trikotnikBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                size.setProgress(50);
                trikotnik.getLayoutParams().height = height * 50;
                trikotnik.requestLayout();
                circle.setVisibility(View.INVISIBLE);
                kvadrat.setVisibility(View.INVISIBLE);
                trikotnik.setVisibility(View.VISIBLE);
                active = "trikotnik";
            }
        });
        circleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                size.setProgress(50);
                circle.getLayoutParams().height = height * 50;
                circle.requestLayout();
                trikotnik.setVisibility(View.INVISIBLE);
                kvadrat.setVisibility(View.INVISIBLE);
                circle.setVisibility(View.VISIBLE);
                active = "circle";
            }
        });

        kvadratBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                size.setProgress(50);
                kvadrat.getLayoutParams().height = height * size.getProgress();
                kvadrat.getLayoutParams().width = kvadrat.getHeight();
                kvadrat.requestLayout();
                trikotnik.setVisibility(View.INVISIBLE);
                circle.setVisibility(View.INVISIBLE);
                kvadrat.setVisibility(View.VISIBLE);
                active = "kvadrat";
            }
        });
    }

    public void main() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void onBackPressed(){
    }
    void handleTouch(MotionEvent event){
        //ce so 3je prsti
        int pointerCount = event.getPointerCount();
        if(pointerCount == 3) {
            int action = event.getActionMasked();
            switch (action) {
                case MotionEvent.ACTION_POINTER_DOWN:
                    x1c = (int) event.getX(0);
                    y1c = (int) event.getY(0);
                    x2c = (int) event.getX(1);
                    y2c = (int) event.getY(1);
                    x3c = (int) event.getX(2);
                    y3c = (int) event.getY(2);
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
            //ce sta 2 prsta
        }else if(pointerCount == 2){
            back(event);
           }
    }

    private void reset(){
        x1c=0;
        x1n=0;
        y1c=0;
        y1n=0;
        x2c=0;
        x2n=0;
        y2c=0;
        y2n=0;
        y3c=0;
        y3n=0;

        r=0;
        g=0;
        b=0;
    }

    private void vibra() {
        if (r == 51 && g == 181 && b == 229) {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                vibrator.vibrate(100);
            }
        }
    }

    private void barvaPixla(int x1c,int y1c){
        int pixel = bitmap.getPixel(x1c,y1c);
        r = Color.red(pixel);
        g = Color.green(pixel);
        b = Color.blue(pixel);
    }

    private void trikotnik(){
        trikotnik.setDrawingCacheEnabled(true);
        trikotnik.buildDrawingCache(true);
        bitmap = trikotnik.getDrawingCache();
    }

    private void krog(){
        circle.setDrawingCacheEnabled(true);
        circle.buildDrawingCache(true);
        bitmap = circle.getDrawingCache();
    }
    private void kvadrat(){
        kvadrat.setDrawingCacheEnabled(true);
        kvadrat.buildDrawingCache(true);
        bitmap = kvadrat.getDrawingCache();
    }

    Boolean jeOutline(MotionEvent event){
        x1c = (int) event.getX();
        y1c = (int) event.getY();
        int action = event.getActionMasked();
        if (event.getPointerCount() == 1) {
            if (x1c >= 0 && y1c >= 0) {
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        switch (active){
                            case "trikotnik":
                                trikotnik();
                                break;
                            case "circle":
                                krog();
                                break;
                            case "kvadrat":
                                kvadrat();
                                break;
                        }
                        if (y1c < bitmap.getHeight() && x1c < bitmap.getWidth()) {
                            barvaPixla(x1c,y1c);
                            vibra();
                        }
                    case MotionEvent.ACTION_MOVE:
                        if (y1c < bitmap.getHeight() && x1c < bitmap.getWidth()) {
                            barvaPixla(x1c,y1c);
                            vibra();
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        reset();
                        break;
                }
            }
        }else if( event.getPointerCount() == 3){
            exit(event);
        }else if(event.getPointerCount() == 2){
            back(event);
        }
        return true;
    }

    public void back(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_POINTER_DOWN:
                x1c = (int) event.getX(0);
                y1c = (int) event.getY(0);
                x2c = (int) event.getX(1);
                y2c = (int) event.getY(1);
                break;
            case MotionEvent.ACTION_UP:
                reset();
                break;
            case MotionEvent.ACTION_MOVE:
                x1n = (int) event.getX(0);
                y1n = (int) event.getY(0);
                x2n = (int) event.getX(1);
                y2n = (int) event.getY(1);
                float diffX1 = x1c - x1n;
                float diffX2 = x2c - x2n;
                float diffY1 = y1c - y1n;
                float diffY2 = y2c - y2n;
                //preveri ce je bil swipe v levo / desno
                if (Math.abs(diffX1) > Math.abs(diffY1) && Math.abs(diffX2) > Math.abs(diffY2)) {
                    //preveri ce je bil swipe v levo
                    if (diffX1 > 0 && diffX2 > 0) {
                        //swipe vsaj 20% zaslona
                        if (Math.abs(diffX1) > (width * 20)) super.onBackPressed();
                    }
                }
        }
    }

    public void exit(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_POINTER_DOWN) {
            x1c = (int) event.getX(0);
            y1c = (int) event.getY(0);
            x2c = (int) event.getX(1);
            y2c = (int) event.getY(1);
            x3c = (int) event.getX(2);
            y3c = (int) event.getY(2);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
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
}


