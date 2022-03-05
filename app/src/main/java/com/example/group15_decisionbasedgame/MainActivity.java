package com.example.group15_decisionbasedgame;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.text.HtmlCompat;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
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

    //Initiating Variables
    Animation fade,fadelate,fadelate2,fadelate3,fadelate4,fadeInOut;
    TextView textBox,popOut;
    ImageView imageView, backView;
    Button button,button2,button3,button4;
    String[] text,defText;
    //Choice is the String [] that is to be loaded
    //Check denotes the type of the String []
    String choice,check,ending;
    Drawable image;
    ConstraintLayout popOutLayout;
    CountDownTimer timer;

    //Array ID (integer)
    int arrID;
    //Image ID (integer)
    int imgID;
    //Which image to play
    int imageState;
    //Where the image data is in the String []
    int imageNum;
    //Delay of text
    int delay;
    int delayMult = 20; //25 is a good speed
    // Last item of the String []
    int lastItem;
    //length of the String []
    int textLength;

    //Choice thingy
    int choice4,choice3,choice2,choice1;

    //Indicates which button is pressed
    int buttonState;
    //Indicates which ending was activated
    boolean witchEnd,knightEnd,dragonEnd,priestEnd;
    //Number of choices a Scenario will offer
    int numOfChoice;
    //Which item was picked //TODO: could be made better for a more dynamic approach
    int itemState;
    int item;

    boolean failed,timerRunning,restricted;
    //Text Delay
    boolean allowDelay = false;

    //////////////////////test///////////////////////

    //So when i pick a choice, the button will then get the corresponding string name array to set the dialog for the next scenario?

    public void startStop() {
        if (timerRunning && allowDelay) {
            stopTimer();
        } else if (allowDelay) {
            startTimer();
        }
    }

    public void startTimer() {
        timer = new CountDownTimer(delay+1500, delay+1500) {
            public void onTick(long l) {
                Log.d(TAG, "onTick: tick" + delay + l);
            }

            public void onFinish() {
                popOutLayout.setVisibility(View.INVISIBLE);
                popOutLayout.setClickable(false);
                startStop();
                Log.d(TAG, "onFinish:" + delay);
            }
        };
        timer.start();
        timerRunning = true;
        popOutLayout.setVisibility(View.VISIBLE);
        popOutLayout.setClickable(true);
    }

    public void stopTimer () {
        timer.cancel();
        timerRunning = false;
    }

    public void arrayCheck() {
        //checks if the scenario is now restricted
        if (buttonState != 0 && !failed) {
            choice = text[numOfChoice + buttonState];
            arrID = getResources().getIdentifier(choice, "array", getPackageName());
            try {
                text = getResources().getStringArray(arrID);
            } catch (Exception e) {
                text = getResources().getStringArray(R.array.errorMessage);
            }
            textLength = text.length;
            Log.d(TAG, "//////////////1: " + choice + " //////////////2");
        } else if (failed) {
            choice = text[lastItem - 1];
            Log.d(TAG, "////////////////////////////restricted: " +choice);
            arrID = getResources().getIdentifier(choice, "array", getPackageName());
            try {
                text = getResources().getStringArray(arrID);
            } catch (Exception e) {
                text = getResources().getStringArray(R.array.errorMessage);
            }
            textLength = text.length;
            failed = false;
        } else {
            textLength = text.length;
        }
        Log.d(TAG, "array [] " + textLength);
    }

    public void sceneCheck() {
        //Hides the button
        hideButton();
        if (buttonState == 0) {
            //Runs if no choices has been made
            arrayCheck();
            textChange();
        } else if (restricted) {
            restricted = false;
            restricted();
        } else {
            arrayCheck();
            lastItem = text.length - 1;
            check = text[lastItem];
            Log.d(TAG, "sceneCheck: " + check);
            switch (check) {
                case "Dialog":
                    textChange();
                    break;
                case "Restricted":
                    restricted = true;
                    textLength -=1; //Done as the Restricted Array has 1 more item than the rest
                    textChange();
                    break;
                case "Item":
                    item = 2;
                    textChange();
                    break;
                case "Ending":
                    ending = choice;
                    Log.d(TAG, "ending: " + ending);
                    textChange();
                    ending();
                    break;
            }
        }
    }

    public void textChange() {
        Log.d(TAG, "textChange: " +choice +"  "+restricted);
        //Checks if delay is to be applied
        if (allowDelay) {
            delay = text[0].length() * delayMult;
            Log.d(TAG, "textChange: " + delay);
        }
        //Checks how many choices in that particular scene are
        if (textLength == choice4){ //for 4 choices scenario
            choices4();
            imageChange();
            startStop();
            numOfChoice = 4;
        } else if (textLength == choice3) { //for 3 choice scenario
            choices3();
            imageChange();
            startStop();
            numOfChoice = 3;
        } else if (textLength == choice2) { // for 2 choice scenario
            choices2();
            imageChange();
            numOfChoice = 2;
        } else if (textLength == choice1) { // for 1 choice scenario
            choices1();
            imageChange();
            startStop();
            numOfChoice = 1;
        }
        //Checks the item state
        if (item > 1) {
            item--;
        } else if (item == 1) {
            itemState = buttonState;
            item--;
        }
    }

    public void restricted() {
        //Checks if the first choice was pressed
        Log.d(TAG, "itemState: " + itemState);
        if (buttonState == 1) {
            //Checks if the user got an item
            if (itemState > 0) {
                //Checks if the correct item was retrieved
                if (itemState == 2) {
                    Log.d(TAG, "itemState == 2"+choice);
                    failed = false;
                } else {
                    popOut.setText("You didn't grab the wrong item, didn't you?");
                    popOut.setVisibility(View.VISIBLE);
                    popOut();
                    Log.d(TAG, "You didn't grab the wrong item, didn't you?" +choice);
                    failed = true;
                }
            } else {
                popOut.setText("Ho ho, feeling lucky ey");
                popOut.setVisibility(View.VISIBLE);
                popOut();
                Log.d(TAG, "Ho ho, feeling lucky ey");
                failed = true;
            }
            arrayCheck();
            textChange();
        } else if (buttonState == 2) {
            textChange();
        }
    }

    public void ending() {
        //Checks which ending was activated
        switch (ending) {
            case "b2a2b2":
                witchEnd = true;
                break;
            case "b2b2a2":
                knightEnd = true;
                break;
            case "c2c2b2":
                dragonEnd = true;
                break;
            case "c2d2a2":
                priestEnd = true;
                break;
        }
    }

    public void popOut() {
        popOutLayout.setVisibility(View.VISIBLE);
        popOutLayout.startAnimation(fadeInOut);
        popOutLayout.setClickable(true);
        fadeInOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                popOut.setVisibility(View.GONE);
                popOutLayout.setVisibility(View.GONE);
                popOutLayout.setClickable(false);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public void choices4() {
        //Changes button visibility
        button.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        button4.setVisibility(View.VISIBLE);
        //Sets the text
        textBox.setText(HtmlCompat.fromHtml(text[0],HtmlCompat.FROM_HTML_MODE_COMPACT));
        button.setText(text[1]);
        button2.setText(text[2]);
        button3.setText(text[3]);
        button4.setText(text[4]);
        //Animation delay
        fadelate.setStartOffset(delay+300);
        fadelate2.setStartOffset(delay+600);
        fadelate3.setStartOffset(delay+900);
        fadelate4.setStartOffset(delay+1200);
        //Starts Animation
        textBox.startAnimation(fade);
        button.startAnimation(fadelate);
        button2.startAnimation(fadelate2);
        button3.startAnimation(fadelate3);
        button4.startAnimation(fadelate4);
        //Sets the imageNumber
        imageNum = 9;
    }

    public void choices3() {
        //Changes button visibility
        button.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        //Sets the text
        textBox.setText(HtmlCompat.fromHtml(text[0],HtmlCompat.FROM_HTML_MODE_COMPACT));
        button.setText(text[1]);
        button2.setText(text[2]);
        button3.setText(text[3]);
        //Animation delay
        fadelate.setStartOffset(delay+300);
        fadelate2.setStartOffset(delay+600);
        fadelate3.setStartOffset(delay+900);
        //Starts Animation
        textBox.startAnimation(fade);
        button.startAnimation(fadelate);
        button2.startAnimation(fadelate2);
        button3.startAnimation(fadelate3);
        //Sets the imageNumber
        imageNum = 7;
    }

    public void choices2() {
        //Changes button visibility
        button.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        //Sets the text and starts the animation
        textBox.setText(HtmlCompat.fromHtml(text[0],HtmlCompat.FROM_HTML_MODE_COMPACT));
        button.setText(text[1]);
        button2.setText(text[2]);
        //Animation delay
        fadelate.setStartOffset(delay+300);
        fadelate2.setStartOffset(delay+600);
        //Starts Animation
        textBox.startAnimation(fade);
        button.startAnimation(fadelate);
        button2.startAnimation(fadelate2);
        //Sets the imageNumber
        imageNum = 5;
    }

    public void choices1() {
        //Changes button visibility
        button.setVisibility(View.VISIBLE);
        //Sets the text and starts the animation
        textBox.setText(HtmlCompat.fromHtml(text[0],HtmlCompat.FROM_HTML_MODE_COMPACT));
        button.setText(text[1]);
        //Animation delay
        fadelate.setStartOffset(delay+300);
        //Starts Animation
        textBox.startAnimation(fade);
        button.startAnimation(fadelate);
        //Sets the imageNumber
        imageNum = 3;
    }

    public void hideButton() {
        //Changes button visibility
        button.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
    }

    public void imageChange() {
        //Changes image
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
        popOutLayout.clearAnimation();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.dialogue);


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
        popOut = findViewById(R.id.popOut);

        //Image View
        imageView = findViewById(R.id.imageView);
        backView = findViewById(R.id.backView);

        //Animation call
        fade = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        fadelate = AnimationUtils.loadAnimation(this, R.anim.fade_late);
        fadelate2 = AnimationUtils.loadAnimation(this, R.anim.fade_late);
        fadelate3 = AnimationUtils.loadAnimation(this, R.anim.fade_late);
        fadelate4 = AnimationUtils.loadAnimation(this, R.anim.fade_late);
        fadeInOut = AnimationUtils.loadAnimation(this, R.anim.fade_in_out);

        //Animation delay
        fade.setStartOffset(300);

        //Layout call
        popOutLayout = findViewById(R.id.popOutLayout);

        //String [] call
        defText = getResources().getStringArray(R.array.default_text);
        //Sets the default text
        text = getResources().getStringArray(R.array.a1);

        //Sets the choice number
        choice4 = defText.length;
        choice3 = choice4 - 2;
        choice2 = choice3 - 2;
        choice1 = choice2 - 2;

        //button

        sceneCheck();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        //Clear pending animation
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