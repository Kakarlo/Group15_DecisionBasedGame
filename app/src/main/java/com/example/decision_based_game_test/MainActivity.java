package com.example.decision_based_game_test;

import androidx.appcompat.app.AppCompatActivity;

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
    String[] text;
    String choice;
    Drawable image;

    int arrID;
    int imgID;
    int imageState;

    int buttonState;
    int numOfChoice;//basta the number of choice will be determined by the function for textchange

    //////////////////////test///////////////////////

    //so when i pick a choice, the button will then get the corresponding string name array to set the dialog for the next scenario?

    public void textChange() {
        if (numOfChoice == 0) {
            text = getResources().getStringArray(R.array.first_scene);
        } else {
            choice = text[numOfChoice+buttonState];
            arrID = getResources().getIdentifier(choice,
                    "array", getPackageName());
            text = getResources().getStringArray(arrID);
        }
        if (text.length == 11){ //for 4 choices scenario
            button.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.VISIBLE);
            button4.setVisibility(View.VISIBLE);
            button.setEnabled(true);
            button2.setEnabled(true);
            button3.setEnabled(true);
            button4.setEnabled(true);
            textBox.setText(text[0]);
            button.setText(text[1]);
            button2.setText(text[2]);
            button3.setText(text[3]);
            button4.setText(text[4]);
            imageState = Integer.parseInt(text[9].replaceAll("[\\D]", ""));
            if (imageState > 0) {
                imgID = getResources().getIdentifier("umaru" + imageState, "drawable", getPackageName());
                image = getResources().getDrawable(imgID);
                imageView.setImageDrawable(image);
            }
            numOfChoice = 4;
            //do this
        } else if (text.length == 9) { //for 3 choice scenario
            button.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.VISIBLE);
            button4.setVisibility(View.INVISIBLE);
            button.setEnabled(true);
            button2.setEnabled(true);
            button3.setEnabled(true);
            button4.setEnabled(false);
            textBox.setText(text[0]);
            button.setText(text[1]);
            button2.setText(text[2]);
            button3.setText(text[3]);
            imageState = Integer.parseInt(text[7].replaceAll("[\\D]", ""));
            if (imageState > 0) {
                imgID = getResources().getIdentifier("umaru" + imageState, "drawable", getPackageName());
                image = getResources().getDrawable(imgID);
                imageView.setImageDrawable(image);
            }
            numOfChoice = 3;
            //do this
        } else if (text.length == 7) { // for 2 choice scenario
            button.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.INVISIBLE);
            button4.setVisibility(View.INVISIBLE);
            button.setEnabled(true);
            button2.setEnabled(true);
            button3.setEnabled(false);
            button4.setEnabled(false);
            textBox.setText(text[0]);
            button.setText(text[1]);
            button2.setText(text[2]);
            imageState = Integer.parseInt(text[5].replaceAll("[\\D]", ""));
            if (imageState > 0) {
                imgID = getResources().getIdentifier("umaru" + imageState, "drawable", getPackageName());
                image = getResources().getDrawable(imgID);
                imageView.setImageDrawable(image);
            }
            numOfChoice = 2;
            //do this
        } else if (text.length == 5) { // for 1 choice scenario
            button.setVisibility(View.VISIBLE);
            button2.setVisibility(View.INVISIBLE);
            button3.setVisibility(View.INVISIBLE);
            button4.setVisibility(View.INVISIBLE);
            button.setEnabled(true);
            button2.setEnabled(false);
            button3.setEnabled(false);
            button4.setEnabled(false);
            textBox.setText(text[0]);
            button.setText(text[1]);
            imageState = Integer.parseInt(text[3].replaceAll("[\\D]", ""));
            if (imageState > 0) {
                imgID = getResources().getIdentifier("umaru" + imageState, "drawable", getPackageName());
                image = getResources().getDrawable(imgID);
                imageView.setImageDrawable(image);
            }
            numOfChoice = 1;
            //do this
        } else {
            //return "Saag ka bai"
        }
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
        textChange();
    }

    @Override
    public void onClick(View v) {
        textBox = findViewById(R.id.txtBox);
        switch (v.getId()) {
            case R.id.button:
                buttonState = 1;
                textChange();
                break;
            case R.id.button2:
                buttonState = 2;
                textChange();
                break;
            case R.id.button3:
                buttonState = 3;
                textChange();
                break;
            case R.id.button4:
                buttonState = 4;
                textChange();
                break;
        }
    }

    public void test() {
        if (numOfChoice == 0) {
            text = getResources().getStringArray(R.array.default_text);
        } else {
            choice = text[numOfChoice+buttonState];
            arrID = getResources().getIdentifier(choice,
                    "array", getPackageName());
            text = getResources().getStringArray(arrID);
        }
        String check;
        int lastItem = text.length + 1;
        check = text[lastItem];
        switch (check) {
            case "Normal":
                textChange();
                break;
            case "Dialog":
                //restChange();
                break;
        }

    }

}