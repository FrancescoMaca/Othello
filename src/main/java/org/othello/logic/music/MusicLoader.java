package org.othello.logic.music;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicLoader {

    private static MusicLoader instance;

    private Clip clip;
    private FloatControl control;

    private MusicLoader() {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void loadMusic(String filename) {
        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(filename));
            clip.close();
            clip.open(ais);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            control = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            stop();

        } catch(UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void setVolume(float volume) {

        if (volume < 0) {
            volume = 0;
        }
        else if (volume > 1) {
            volume = 1;
        }

        float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
        control.setValue(dB);
    }

    public void start() {
        clip.start();
    }

    public void stop() {
        clip.stop();
    }

    public static MusicLoader getInstance() {
        if (instance == null) {
            instance = new MusicLoader();
        }

        return instance;
    }

    public static void playSound(File clipFile) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream stream = AudioSystem.getAudioInputStream(clipFile);
            clip.open(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
