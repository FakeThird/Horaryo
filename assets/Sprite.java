/** 
 * @class: Sprite
 * @date: December 3, 2024
 * @description:
 *      Container of a 2D Image.
 *      
 * @param: 
 *     * String imgFilename  - filename of the sprite image under the "image/" folder. 
 *     * int posX, posY      - positions of the top-left corner of the Sprite. 
 * 
 * @init: 
 *      Three cases when initializing a Sprite Object:
 *      * Sprite()   
 *              Used when constructing without position and image.
 *              Usually used in super() of inheriting class containing  
 *              animations instead of one image.
 *      
 *      * Sprite(String filename) 
 *              Used when constructing with an image, but has a default. 
 *              x-position and y-position of 0. Used when placing Sprites
 *              in the Location class which automatically sets its position.
 * 
 *      * Sprite(int posX, int posY. String filename) 
 *              Used when constructing with an image, with a set 
 *              x-position and y-position.
 * 
 * @fields:
 *     Self explanatory.
 *
 */

package assets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;


public class Sprite {
    private BufferedImage image;
    private int posX;
    private int posY;  
    private int width;
    private int height;
    private boolean visible;

    public Sprite() {
        this.visible = true;
        this.width = 160;
        this.height = 300;
    }
    public Sprite(String imgFilename) {
        this(0, 0, imgFilename);
    }
    public Sprite(int posX, int posY, String  imgFilename) {
        this.posX = posX;
        this.posY = posY;
        this.visible = true;
        
        if(imgFilename != null){
            try {
                InputStream is = getClass().getResourceAsStream("/images/" + imgFilename);
                this.image = ImageIO.read(is);
            } catch (IOException e) {
                e.printStackTrace();            
            }

            this.width = image.getWidth();
            this.height = image.getHeight();
        }
        else{
            this.width = 220;
            this.height = 440;
        }
    }

    
    public BufferedImage getImage() {
        return this.image;
    }
    
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getPosX() {
        return this.posX;
    }
    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return this.posY;
    }
    public void setPosY(int posY) {
        this.posY = posY;
    }
    
    public void moveX(int speed) {
        this.posX += speed;
    }

    public void moveY(int speed) {
        this.posY += speed;
    }

    public int getWidth() {
        return this.width;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
 
    public boolean isVisible() {
        return this.visible;
    }
    public void setVisible(boolean value) {
        this.visible = value;
    }
}
