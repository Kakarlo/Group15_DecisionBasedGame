package com.example.group15_decisionbasedgame.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group15_decisionbasedgame.R;

public class Pause extends AppCompatActivity implements View.OnClickListener{

    Button btn1,btn2,btn3,btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Transparent);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pause);

        //Button Call
        btn1 = findViewById(R.id.returnToPrevious);
        btn2 = findViewById(R.id.returnToMenu);
        btn3 = findViewById(R.id.musicToggle);
        btn4 = findViewById(R.id.resetProgress);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }

    //TODO:add the toggle and reset
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.returnToPrevious:
                finish();
                break;
            case R.id.returnToMenu:
                Intent start = new Intent(this, Menu.class);
                startActivity(start);
                finish();
                break;
            case R.id.musicToggle:

                finish();
                break;
            case R.id.resetProgress:

                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent start = new Intent(this, Menu.class);
        startActivity(start);
        finish();
    }
}
