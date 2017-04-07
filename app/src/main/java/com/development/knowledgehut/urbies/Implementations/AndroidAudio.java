package com.development.knowledgehut.urbies.Implementations;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import com.development.knowledgehut.urbies.Frameworks.Audio;
import com.development.knowledgehut.urbies.Frameworks.Music;
import com.development.knowledgehut.urbies.Frameworks.Sound;

import java.io.IOException;


public class AndroidAudio implements Audio{
    private AssetManager assetManager;
    //private SoundPool soundBuilder;
    private SoundPool soundPool;

    public AndroidAudio(Activity activity){
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        this.assetManager = activity.getAssets();
        this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
    }

    @Override
    public Music newMusic(String filename) {
        try {
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd(filename);
            return new AndroidMusic(assetFileDescriptor);
        } catch (IOException e) {
            throw new RuntimeException("Could not load music '" + filename  +"'");
        }
    }

    @Override
    public Sound newSound(String filename) {
        try {
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd(filename);
            int soundId = soundPool.load(assetFileDescriptor, 0);
            return  new AndroidSound(soundPool, soundId);
        } catch (IOException e) {
            throw new RuntimeException("Could not load sound '" + filename  +"'");
        }
    }

}
