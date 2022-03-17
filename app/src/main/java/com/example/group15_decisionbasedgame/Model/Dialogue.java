package com.example.group15_decisionbasedgame.Model;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.SharedPreferences;
import android.util.Log;

public class Dialogue {
    private final SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public Dialogue(SharedPreferences sharedPreferences) {this.sp = sharedPreferences;}

    private String[] txt;
    private String[] firstTxt;
    private String choice;
    private String ending;
    //Delay of text
    private int delay;
    private final int delayMult = 20; //25 is a good speed
    // Last item of the String []
    private int lastItem;
    //length of the String []
    private int textLength;
    //Choice thingy
    private final int defTextLength = 12;
    private final int choice4 = defTextLength;
    private final int choice3 = choice4 - 2;
    private final int choice2 = choice3 - 2;
    private final int choice1 = choice2 - 2;
    //Indicates which button is pressed
    private int buttonState;
    //Indicates which ending was activated
    private boolean witchEnd,knightEnd,dragonEnd,priestEnd;
    //Number of choices a Scenario will offer
    private int numOfChoice;
    //Number of buttons to show
    private int buttonNum;
    //Number of clicks to skip text animation
    private int textSkip;
    //Which item was picked //TODO: could be made better for a more dynamic approach
    public int getItemState() {return sp.getInt("ItemState", 0);} //Which item was picked
    private int item;
    boolean failed,restricted,locked,ended;
    private int popOutText;
    //Text Delay
    boolean allowDelay = true;

    public void sceneCheck(int state) {
        buttonState = state;
        if (restricted) {
            restricted = false;
            restricted();
        } else {
            arrayCheck();
        }
    }

    private void arrayCheck() {
        //checks if the scenario is now restricted
        if (buttonState != 0 && !failed) {
            choice = txt[numOfChoice + buttonState];
        } else if (failed) {
            choice = txt[lastItem - 1];
            failed = false;
        }
    }

    private void caseCheck() {
        textLength = txt.length;
        lastItem = txt.length - 1;
        String check = txt[lastItem].replaceAll("[^a-zA-Z]","");
        switch (check) {
            case "Dialog":
                //Doing nothing special for now
                break;
            case "Restricted":
                restricted = true;
                textLength -=1; //Done as the Restricted Array has 1 more item than the rest
                break;
            case "Item":
                item = 2;
                break;
            case "Ending":
                ending = choice;
                ending();
                break;
            case "Locked":
                locked = true;
                break;
            case "Reset":
                ended = true;
                Reset();
                break;
        }
    }

    public void textChange() {
        caseCheck();
        //Checks if delay is to be applied
        if (allowDelay) {
            delay = txt[0].length() * delayMult;
        }
        //Checks how many choices in that particular scene are
        if (textLength == choice4){ //for 4 choices scenario
            numOfChoice = 4;
            buttonNum = 4;
        } else if (textLength == choice3) { //for 3 choice scenario
            numOfChoice = 3;
            buttonNum = 3;
        } else if (textLength == choice2) { // for 2 choice scenario
            numOfChoice = 2;
            buttonNum = 2;
        } else if (textLength == choice1) { // for 1 choice scenario
            numOfChoice = 1;
            buttonNum = 1;
        }
        //Checks the item state
        if (item > 1) {
            item--;
        } else if (item == 1) {
            editor = sp.edit();
            editor.putInt("ItemState" ,buttonState);
            editor.apply();
            item--;
        }
        if (locked){
            locked();
            locked = false;
        }
    }

    private void restricted() {
        if (choice != null) {
            String a = txt[lastItem].replaceAll("[^0-9]", "");
            int b = Integer.parseInt(a);
            Log.d(TAG, "locked: " + b);
            switch (b) {
                case 1:
                    //Clue for Unarmed path
                    if (!lock1()) {
                        failed = true;
                    }
                    break;
                case 2:
                    if (!lock2()) {
                        failed = true;
                    }
                    break;
                case 3:
                    //For the Sword and Shield
                    editor = sp.edit();
                    editor.putBoolean(String.valueOf(sp.getInt("ItemState", 0)),true);
                    editor.apply();
                    if(!lock3()) {
                        failed = true;
                    } else {
                        editor = sp.edit();
                        editor.putBoolean("1" ,false);
                        editor.putBoolean("2" ,false);
                        editor.putBoolean("3" ,false);
                        editor.putBoolean("4" ,false);
                        editor.apply();
                    }
                    break;
            }
        }
        arrayCheck();
    }

    private void ending() {
        //Checks which ending was activated
        Log.d(TAG, "ending: " + choice);
        switch (ending) {
            case "b2a2b2":
                editor = sp.edit();
                editor.putBoolean("WitchEnd" ,true);
                editor.apply();
                break;
            case "b2b2a1":
                editor = sp.edit();
                editor.putBoolean("KnightEnd" ,true);
                editor.apply();
                break;
            case "c2c2b2":
                editor = sp.edit();
                editor.putBoolean("DragonEnd" ,true);
                editor.apply();
                break;
            case "c2d2a2":
                editor = sp.edit();
                editor.putBoolean("PriestEnd" ,true);
                editor.apply();
                break;
            case "d3a3d1":
                editor = sp.edit();
                editor.putBoolean("WitchEndPacifist" ,true);
                editor.apply();
                break;
            case "d3b3d1":
                editor = sp.edit();
                editor.putBoolean("KnightEndPacifist" ,true);
                editor.apply();
                break;
            case "d3c3d1":
                editor = sp.edit();
                editor.putBoolean("DragonEndPacifist" ,true);
                editor.apply();
                break;
            case "d3d3d1":
                editor = sp.edit();
                editor.putBoolean("PriestEndPacifist" ,true);
                editor.apply();
                break;
        }
    }

    private boolean lock1() {
        return sp.getBoolean("WitchEnd" ,false) && sp.getBoolean("KnightEnd" ,false)
                && sp.getBoolean("DragonEnd" ,false) && sp.getBoolean("PriestEnd" ,false);
    }

    private boolean lock2() {
        return sp.getBoolean("WitchEndPacifist" ,false) && sp.getBoolean("KnightEndPacifist" ,false)
                && sp.getBoolean("DragonEndPacifist" ,false) && sp.getBoolean("PriestEndPacifist" ,false);
    }

    private boolean lock3() {
        return sp.getBoolean("1" ,false) && sp.getBoolean("2" ,false)
                && sp.getBoolean("3" ,false) && sp.getBoolean("4" ,false);
    }

    private void locked() {
       if (choice != null) {
            String a = txt[lastItem].replaceAll("[^0-9]", "");
            int b = Integer.parseInt(a);
            Log.d(TAG, "locked: " + b);
            if (b == 2) {
                //Sword and Shield route open
                if (!lock2()) {
                    Log.d(TAG, "locked:1 " + sp.getBoolean("WitchEndPacifist" ,false));
                    Log.d(TAG, "locked:2 " + sp.getBoolean("KnightEndPacifist" ,false));
                    Log.d(TAG, "locked:3 " + sp.getBoolean("DragonEndPacifist" ,false));
                    Log.d(TAG, "locked:4 " + sp.getBoolean("PriestEndPacifist" ,false));
                    buttonNum = 1;
                } //if true, show text
            } else if (b == 1) {
                //Unarmed route open
                if (!lock1()) {
                    Log.d(TAG, "locked:1 " + sp.getBoolean("WitchEnd" ,false));
                    Log.d(TAG, "locked:2 " + sp.getBoolean("KnightEnd" ,false));
                    Log.d(TAG, "locked:3 " + sp.getBoolean("DragonEnd" ,false));
                    Log.d(TAG, "locked:4 " + sp.getBoolean("PriestEnd" ,false));
                    buttonNum = 1;
                } //if true, show text
            }
        } else {
           buttonNum = 1;
       }
    }

    public void Save() {
        if (choice != null) {
            editor = sp.edit();
            editor.putString("textID", choice);
            editor.apply();
        }
    }

    public void Reset() {
        choice = null;
        editor = sp.edit();
        editor.putString("textID", "null");
        editor.putInt("ItemState" ,0);
        editor.putBoolean("WitchEnd" ,false);
        editor.putBoolean("KnightEnd" ,false);
        editor.putBoolean("DragonEnd" ,false);
        editor.putBoolean("PriestEnd" ,false);
        editor.putBoolean("WitchEndPacifist" ,false);
        editor.putBoolean("KnightEndPacifist" ,false);
        editor.putBoolean("DragonEndPacifist" ,false);
        editor.putBoolean("PriestEndPacifist" ,false);
        editor.putBoolean("1" ,false);
        editor.putBoolean("2" ,false);
        editor.putBoolean("3" ,false);
        editor.putBoolean("4" ,false);
        editor.apply();
    }

    //Getters
    public String[] getFirstTxt() {return firstTxt;}
    public String getTxtString(int index) {return txt[index];}
    public String getChoice() {return choice;}
    public String getBackNum() {return this.txt[txt.length - 2];}
    public int getDelay() {return delay;}
    public int getButtonNum() {return buttonNum;}
    public int getTextSkip() {return textSkip;}
    public int getPopOutText() {return popOutText;}
    public boolean isFailed() {return failed;}
    public boolean isEnded() {return ended;}

    //Setters
    public void setFirstTxt(String[] firstTxt) {this.firstTxt = firstTxt;}
    public void setTxt(String[] text) {this.txt = text;}
    public void setTextSkip(int textSkip) {this.textSkip = textSkip;}
    public void setEnded(boolean state) {this.ended = state;}

}
