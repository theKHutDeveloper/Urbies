package com.development.knowledgehut.urbies.Frameworks;

import com.development.knowledgehut.urbies.Implementations.AndroidRenderView;

public interface Game {
    public FileIO getFileIO();
    public Graphics getGraphics();
    public Audio getAudio();
    public void setScreen(Screen screen);
    public Screen getCurrentScreen();
    public Screen getStartScreen();
    public AndroidRenderView getRenderView();
}
