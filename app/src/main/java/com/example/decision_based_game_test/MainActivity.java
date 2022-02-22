package com.example.decision_based_game_test;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    Animation fade,fadelate,fadelate2,fadelate3,fadelate4;
    TextView textBox;
    ImageView imageView, backView;
    Button button,button2,button3,button4;
    String[] text,defText;
    String choice,check;
    Drawable image;

    int arrID;
    int imgID;
    int imageState;
    int imageNum;
    int lastItem;

    int choice4,choice3,choice2,choice1;

    int buttonState;
    int numOfChoice;//basta the number of choice will be determined by the function for textchange

    //////////////////////test///////////////////////

    //so when i pick a choice, the button will then get the corresponding string name array to set the dialog for the next scenario?

    public void sceneCheck() {
        //Sets the default text
        if (numOfChoice == 0) {
            text = getResources().getStringArray(R.array.first_scene);
        }
        lastItem = text.length - 1;
        check = text[lastItem];
        switch (check) {
            case "Dialog":
                textChange();
                break;
            case "Restricted":
                //restChange();
                break;
            case "Item":
                //restChange();
                break;
        }

    }

    public void textChange() {
        //Checks which String Array should be used
        choice = text[numOfChoice+buttonState];
        arrID = getResources().getIdentifier(choice, "array", getPackageName());
        text = getResources().getStringArray(arrID);
        //Checks how many choices in that particular scene are
        if (text.length == choice4){ //for 4 choices scenario
            choices4();
            imageChange();
            numOfChoice = 4;
        } else if (text.length == choice3) { //for 3 choice scenario
            choices3();
            imageChange();
            numOfChoice = 3;
        } else if (text.length == choice2) { // for 2 choice scenario
            choices2();
            imageChange();
            numOfChoice = 2;
        } else if (text.length == choice1) { // for 1 choice scenario
            choices1();
            imageChange();
            numOfChoice = 1;
        }
    }

    public void choices4() {
        //Changes button visibility
        button.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        button4.setVisibility(View.VISIBLE);
        //Sets the buttons functionality
        button.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        //Sets the text and starts the animation
        textBox.setText(text[0]);
        textBox.startAnimation(fade);
        button.setText(text[1]);
        button.startAnimation(fadelate);
        button2.setText(text[2]);
        button2.startAnimation(fadelate2);
        button3.setText(text[3]);
        button3.startAnimation(fadelate3);
        button4.setText(text[4]);
        button4.startAnimation(fadelate4);
        //sets the imageNumber
        imageNum = 9;
    }

    public void choices3() {
        //Changes button visibility
        button.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        button4.setVisibility(View.INVISIBLE);
        //Sets the buttons functionality
        button.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(false);
        //Sets the text and starts the animation
        textBox.setText(text[0]);
        textBox.startAnimation(fade);
        button.setText(text[1]);
        button.startAnimation(fadelate);
        button2.setText(text[2]);
        button2.startAnimation(fadelate2);
        button3.setText(text[3]);
        button3.startAnimation(fadelate3);
        imageState = Integer.parseInt(text[7].replaceAll("[\\D]", ""));
        //sets the imageNumber
        imageNum = 7;
    }

    public void choices2() {
        //Changes button visibility
        button.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
        //Sets the buttons functionality
        button.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(false);
        button4.setEnabled(false);
        //Sets the text and starts the animation
        textBox.setText(text[0]);
        textBox.startAnimation(fade);
        button.setText(text[1]);
        button.startAnimation(fadelate);
        button2.setText(text[2]);
        button2.startAnimation(fadelate2);
        //sets the imageNumber
        imageNum = 5;
    }

    public void choices1() {
        //Changes button visibility
        button.setVisibility(View.VISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
        //Sets the buttons functionality
        button.setEnabled(true);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        //Sets the text and starts the animation
        textBox.setText(text[0]);
        textBox.startAnimation(fade);
        button.setText(text[1]);
        button.startAnimation(fadelate);
        //sets the imageNumber
        imageNum = 3;
    }

    public void imageChange() {
        imageState = Integer.parseInt(text[imageNum].replaceAll("[\\D]", ""));
        if (imageState > 0) {
            imgID = getResources().getIdentifier("umaru" + imageState, "drawable", getPackageName());
            image = ResourcesCompat.getDrawable(getResources(), imgID, null);
            imageView.setImageDrawable(image);
        }
    }

    public void clearAnim() {
        textBox.clearAnimation();
        button.clearAnimation();
        button2.clearAnimation();
        button3.clearAnimation();
        button4.clearAnimation();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);


        //Button call
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        //Button listener
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        //TextView Call
        textBox = findViewById(R.id.txtBox);
        imageView = findViewById(R.id.imageView);
        backView = findViewById(R.id.backView);

        //Animation call
        fade = AnimationUtils.loadAnimation(this,R.anim.fade);
        fade.setStartOffset(300);
        fadelate = AnimationUtils.loadAnimation(this, R.anim.fade_late);
        fadelate.setStartOffset(300);
        fadelate2 = AnimationUtils.loadAnimation(this, R.anim.fade_late);
        fadelate2.setStartOffset(600);
        fadelate3 = AnimationUtils.loadAnimation(this, R.anim.fade_late);
        fadelate3.setStartOffset(900);
        fadelate4 = AnimationUtils.loadAnimation(this, R.anim.fade_late);
        fadelate4.setStartOffset(1200);

        //String [] call
        defText = getResources().getStringArray(R.array.default_text);
        choice4 = defText.length;
        choice3 = choice4 - 2;
        choice2 = choice3 - 2;
        choice1 = choice2 - 2;

        sceneCheck();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        clearAnim();
        switch (v.getId()) {
            case R.id.button:
                buttonState = 1;
                sceneCheck();
                break;
            case R.id.button2:
                buttonState = 2;
                sceneCheck();
                break;
            case R.id.button3:
                buttonState = 3;
                sceneCheck();
                break;
            case R.id.button4:
                buttonState = 4;
                sceneCheck();
                break;
        }
    }

}