package com.example.decision_based_game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textBox;
    ImageView imageView, backView;
    Button button,button2,button3,button4;
    String[] text,defText;
    Drawable image,backImage;

    int treeState;
    int arrID;
    int imgID;
    int backID;
    int finalID;
    int num1;
    int num2;
    int imageState;
    int backState;

    int buttonState;
    int decisionCounter;
    int maxDecision = 3;

    public void changeText() {
        decisionCounter++;
        //changing the text
        if (decisionCounter < maxDecision) {
            //formula to find the specific dialogue of a scenario
            num1 = (int) Math.pow(4, decisionCounter-1);
            num2 = buttonState * num1;
            treeState += num2;
            arrID = getResources().getIdentifier("dialog_"+decisionCounter+"_"+treeState,
                    "array", getPackageName());// this is equal to R.array.dialog_1_1
            text = getResources().getStringArray(arrID);

            textBox.setText(text[0]);
            button.setText(text[1]);
            button2.setText(text[2]);
            button3.setText(text[3]);
            button4.setText(text[4]);
            //backState = Integer.parseInt(text[6].replaceAll("[\\D]", ""));

            if (text.length > 5) {
                imageState = Integer.parseInt(text[5].replaceAll("[\\D]", ""));
                if (imageState > 0) {
                    imgID = getResources().getIdentifier("umaru" + imageState, "drawable", getPackageName());
                    image = getResources().getDrawable(imgID);
                    imageView.setImageDrawable(image);
                }
            }

        } else if(decisionCounter == maxDecision){
            finalID = decisionCounter-1;
            arrID = getResources().getIdentifier("dialog_"+finalID+"_"+treeState+"_"+buttonState,
                    "string", getPackageName());
            textBox.setText(arrID);
            button.setText(R.string.reset);
            button2.setText(R.string.reset);
            button3.setText(R.string.reset);
            button4.setText(R.string.reset);
        }else {
            textBox.setText(defText[0]);
            button.setText(defText[1]);
            button2.setText(defText[2]);
            button3.setText(defText[3]);
            button4.setText(defText[4]);
            decisionCounter=0;
            treeState=0;
            num1=0;
            num2=0;
        }

    }

    public void changeBack() {

        backState = Integer.parseInt(text[6].replaceAll("[\\D]", ""));
        switch (backState){
            case 1:
                backView.setImageResource(R.drawable.background1);
                break;
            case 2:
                backView.setImageResource(R.drawable.background2);
                break;
        }

        /**if (backState > 0) {
            backID = getResources().getIdentifier("background" + backState, "drawable", getPackageName());
            backImage = getResources().getDrawable(backID);
            backView.setImageDrawable(backImage);
        }**/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        textBox = findViewById(R.id.txtBox);
        imageView = findViewById(R.id.imageView);
        backView = findViewById(R.id.backView);
        defText = getResources().getStringArray(R.array.default_text);

    }

    @Override
    public void onClick(View v) {
        textBox = findViewById(R.id.txtBox);
        switch (v.getId()) {
            case R.id.button:
                buttonState = 1;
                changeText();
                break;
            case R.id.button2:
                buttonState = 2;
                changeText();
                break;
            case R.id.button3:
                buttonState = 3;
                changeText();
                break;
            case R.id.button4:
                buttonState = 4;
                changeText();
                break;
        }
    }

}