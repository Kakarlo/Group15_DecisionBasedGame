package com.example.group15_decisionbasedgame.Model;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.group15_decisionbasedgame.R;

import java.util.Random;

public class MusicPlayer {
    private final Context context;

    public MusicPlayer (Context context) {
        this.context = context;
    }

    private MediaPlayer music;
    private int musicID;

    public void setAllowMusic(boolean allowMusic) {this.allowMusic = allowMusic;}

    private boolean allowMusic = true;

    public void toggle() {
        if(allowMusic) {
            stopMusic();
            allowMusic = false;
        } else {
            allowMusic = true;
            startMusic();
        }
    }

    public void startMusic () {
        if (allowMusic) {
            Random random = new Random();
            byte index = (byte) random.nextInt(3);
            switch (index) {
                case 0:
                    musicID = R.raw.timber_town;
                    break;
                case 1:
                    musicID = R.raw.night_elf_village;
                    break;
                case 2:
                    musicID = R.raw.fairy_dust_town;
                    break;
                case 3:
                    musicID = 0;
                    break;
                case 4:
                    musicID = 0;
                    break;
                case 5:
                    musicID = 0;
                    break;
                case 6:
                    musicID = 0;
                    break;
                case 7:
                    musicID = 0;
                    break;
            }
            //condition in case no musicID had no value
            if (musicID != 0) {
                music = MediaPlayer.create(context, musicID);
                music.setOnPreparedListener(mp -> {
                    music.setVolume(0.5f, 0.5f);
                    music.start();
                });

                //Plays another song
                music.setOnCompletionListener(mediaPlayer -> {
                    stopMusic();
                    startMusic();
                });
            }
        }
    }

    public void pauseMusic() {
        if (allowMusic) {
            if (music.isPlaying()) {
                music.pause();
            }
        }
    }

    public void resumeMusic() {
        if (allowMusic) {
            if (music != null) {
                music.start();
            }
        }
    }

    public void stopMusic () {
        if (allowMusic) {
            if (music != null) {
                music.release();
                music = null;
            }
        }
    }
}
