package bond.memo.practice.snake;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Objects;

public class Sound {

    private Clip clip;
    private AudioInputStream audio;

    public void setSound(String soundPath) {
        try {
            InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream(soundPath));
            InputStream bufferedIn = new BufferedInputStream(inputStream);
            audio = AudioSystem.getAudioInputStream(bufferedIn);
            clip = AudioSystem.getClip();
            clip.open(audio);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void stop() throws Exception {
        audio.close();
        clip.close();
        clip.stop();
    }

    public void adjustVol(float vol) {
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(vol);
    }

    public static void main(String[] args) {

        Sound sound = new Sound();
        sound.setSound("/Users/lamle/Development/practice/java/snake/src/main/resources/food.wav");
        sound.play();
    }
}
