package com.development.knowledgehut.urbies.Implementations;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;
import com.development.knowledgehut.urbies.Frameworks.Music;

public class AndroidMusic implements Music, MediaPlayer.OnCompletionListener{
    private MediaPlayer mediaPlayer;
    private boolean isPrepared = false;

    public AndroidMusic(AssetFileDescriptor assetFileDescriptor) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),
                    assetFileDescriptor.getStartOffset(),
                    assetFileDescriptor.getLength());
            mediaPlayer.prepare();
            isPrepared = true;
            mediaPlayer.setOnCompletionListener(this);
        } catch (IOException e) {
            throw new RuntimeException("Could not load music '" );
        }
    }

    @Override
    public void play() {
        if(mediaPlayer.isPlaying()){
            return;
        }
        try{
            synchronized (this){
                if(!isPrepared) mediaPlayer.prepare();
                mediaPlayer.start();
            }
        } catch (IllegalStateException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        mediaPlayer.stop();
        synchronized (this){
            isPrepared = false;
        }
    }

    @Override
    public void pause() {
        if(mediaPlayer.isPlaying())mediaPlayer.pause();
    }

    @Override
    public void setLooping(boolean looping) {
        mediaPlayer.setLooping(isLooping());
    }

    @Override
    public void setVolume(float volume) {
        mediaPlayer.setVolume(volume, volume);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public boolean isStopped() {
        return  !isPrepared;
    }

    @Override
    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

    @Override
    public void dispose() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        synchronized (this){
            isPrepared = false;
        }
    }
}
