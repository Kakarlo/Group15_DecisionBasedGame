package com.example.group15_decisionbasedgame.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group15_decisionbasedgame.R;

import java.util.Objects;

public class Menu extends AppCompatActivity implements View.OnClickListener{

    Button btn1,btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.menu);

        //Button Call
        btn1 = findViewById(R.id.start);
        btn2 = findViewById(R.id.setting);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                Intent start = new Intent(this, DialogueView.class);
                startActivity(start);
                break;
            case R.id.setting:
                Intent sett = new Intent(this, Pause.class);
                startActivity(sett);
                break;
        }
    }
}
