package com.example.mahjongtiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final ImageView[] iv = new ImageView[14];
    private final Map<String, Integer> tileTypes = new HashMap<>(); //ключ - имя тайла, совпадающее с именем файла, значение - количество тайлов одного имени в руке
    private short i, j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //скрывает панель уведомлений на активности

        iv[0] = findViewById(R.id.iv00);
        iv[1] = findViewById(R.id.iv01);
        iv[2] = findViewById(R.id.iv02);

        iv[3] = findViewById(R.id.iv10);
        iv[4] = findViewById(R.id.iv11);
        iv[5] = findViewById(R.id.iv12);

        iv[6] = findViewById(R.id.iv20);
        iv[7] = findViewById(R.id.iv21);
        iv[8] = findViewById(R.id.iv22);

        iv[9] = findViewById(R.id.iv30);
        iv[10] = findViewById(R.id.iv31);
        iv[11] = findViewById(R.id.iv32);

        iv[12] = findViewById(R.id.iv40);
        iv[13] = findViewById(R.id.iv41);

        initializationOfCollection();
        takeTilesInHand();

        Log.d("mytag", "The number of tile types: " + tileTypes.size() + "\n "); //логи для отслеживания правильности выполнения программы во вкладке logcat
    }

    private void takeTilesInHand() {
        String tileName;
        List<String> tileNames = new ArrayList<>(tileTypes.keySet());
        i = 0;

        while (iv[13].getDrawable() == null) { //работает до тех пор, пока последний ImageView в массиве не заполнен
            tileName = tileNames.get(new Random().nextInt(tileNames.size()));
            Log.d("mytag", "while, tileName = " + tileName);
            if (tileTypes.get(tileName) < 4) { //если тайлов такого типа в руке уже 4, то не срабатывает
                Log.d("mytag", "if, tileName = " + tileName + ", quantity = " + (tileTypes.get(tileName) + 1));
                tileTypes.put(tileName, tileTypes.get(tileName) + 1);

                //поиск файла по имени
                Resources resources = getResources();
                int resID = resources.getIdentifier(tileName, "drawable", getPackageName());
                Drawable drawable = resources.getDrawable(resID);
                iv[i].setImageDrawable(drawable);

                i++;
            }
        }
    }

    private void initializationOfCollection() { //функция заполняет коллекцию строками, соответствующими названиям файлов с изображениями тайлов
        for (j = 0; j < 4; j++)
            for (i = 1; i <= 9; i++) {
                tileTypes.put("dots_" + i, 0);
                tileTypes.put("bamboo_" + i, 0);
                tileTypes.put("characters_" + i, 0);
            }
        tileTypes.put("winds_east", 0);
        tileTypes.put("winds_south", 0);
        tileTypes.put("winds_west", 0);
        tileTypes.put("winds_north", 0);
        tileTypes.put("dragons_red", 0);
        tileTypes.put("dragons_green", 0);
        tileTypes.put("dragons_white", 0);
    }

    public void onClick(View view) { //заставляет экран перерисоваться при нажатии на кнопку
        Intent i = new Intent(MainActivity.this, MainActivity.class);
        finish();
        overridePendingTransition(0, 0); //убирает анимацию перерисовки экрана
        startActivity(i);
        overridePendingTransition(0, 0);
    }
}