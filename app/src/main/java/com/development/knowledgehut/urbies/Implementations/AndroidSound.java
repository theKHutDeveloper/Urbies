package com.development.knowledgehut.urbies.Implementations;

import android.media.SoundPool;

import com.development.knowledgehut.urbies.Frameworks.Sound;

public class AndroidSound implements Sound{
    private SoundPool soundPool;
    private int soundId;


    AndroidSound(SoundPool soundPool, int soundId) {
        this.soundPool = soundPool;
        this.soundId = soundId;
    }

    @Override
    public void play(float volume) {
        soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

    @Override
    public void dispose() {
        soundPool.unload(soundId);
    }
}
