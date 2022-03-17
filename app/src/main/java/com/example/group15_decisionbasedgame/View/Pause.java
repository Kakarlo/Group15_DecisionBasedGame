package com.example.group15_decisionbasedgame.View;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.group15_decisionbasedgame.R;

public class Pause extends AppCompatActivity implements View.OnClickListener{

    Button btn1,btn2,btn3,btn4,btn5,btn6;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    ConstraintLayout confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Transparent);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pause);

        overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);

        //Shared Preference
        sp = getSharedPreferences("StoredData", Context.MODE_PRIVATE);
        editor = sp.edit();

        //Layout Call
        confirm = findViewById(R.id.confirmation);

        //Button Call
        btn1 = findViewById(R.id.returnToPrevious);
        btn2 = findViewById(R.id.returnToMenu);
        btn3 = findViewById(R.id.musicToggle);
        btn4 = findViewById(R.id.resetProgress);
        btn5 = findViewById(R.id.yes);
        btn6 = findViewById(R.id.no);

        //Button listener
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
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
                editor.putBoolean("toggleMusic", true);
                editor.apply();
                Log.d(TAG, "onClick: " + sp.getBoolean("allowMusic", false));
                finish();
                break;
            case R.id.resetProgress:
                confirm.setVisibility(View.VISIBLE);
                break;
            case R.id.yes:
                confirm.setVisibility(View.GONE);
                resetProgress();
            case R.id.no:
                confirm.setVisibility(View.GONE);
        }
    }

    private void resetProgress() {
        editor.putBoolean("ResetProgress", true);
        editor.apply();
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent start = new Intent(this, Menu.class);
        startActivity(start);
        finish();
    }
}
