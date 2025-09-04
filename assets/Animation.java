package assets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Animation {
    private ArrayList<BufferedImage> animation;
    private float cyclesPerSecond;
    private int animationLength;
    private int width;
    private int height;
    
    public Animation(String filename, float cyclesPerSecond) {
        this.animation = new ArrayList<BufferedImage>();
        File[] frames = new File("animations/" + filename).listFiles();
        InputStream is;

        try {
            for (File frame: frames) {
                is = getClass().getResourceAsStream("/" + frame.getPath());
                animation.add(ImageIO.read(is));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.cyclesPerSecond = cyclesPerSecond;
        this.animationLength = animation.size();
        this.width = animation.get(0).getWidth();
        this.height = animation.get(0).getHeight();
    }

    public ArrayList<BufferedImage> getAnimation() {
        return this.animation;
    }

    public float getCyclesPerSec() {
        return this.cyclesPerSecond;
    }

    public int getAnimationLength() {
        return this.animationLength;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}