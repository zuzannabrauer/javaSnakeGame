package org.example;
import javax.sound.sampled.*;
import java.io.*;
public class MusicPlayer {
    private Clip clip;
    boolean looping;

    public MusicPlayer(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void play() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
            if (looping) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
    }
}
