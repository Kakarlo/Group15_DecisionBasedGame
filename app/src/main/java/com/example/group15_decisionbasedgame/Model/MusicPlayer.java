package com.example.group15_decisionbasedgame.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

import com.example.group15_decisionbasedgame.R;

import java.util.Random;

public class MusicPlayer {
    private final Context context;
    private final SharedPreferences sp;

    public MusicPlayer (Context context, SharedPreferences sharedPreferences) {
        this.context = context;
        this.sp = sharedPreferences;
    }

    private MediaPlayer music;
    private int musicID;

    private boolean allowMusic = true;

    public void toggle() {
        //Checks if the music was toggled in settings
        if (sp.getBoolean("toggleMusic", false)) {
            if (allowMusic) {
                stopMusic();
                allowMusic = false;
            } else {
                allowMusic = true;
                startMusic();
            }
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("toggleMusic", false);
            editor.apply();
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
            if (music == null) {
                Thread thread = new Thread(() -> {
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
                });
                thread.start();
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
