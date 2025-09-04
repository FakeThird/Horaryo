package assets;

import java.awt.Rectangle;

public class Entity extends Sprite {
    
    public Entity() {
        super();
    }
    public Entity(String filename) {
        this(0, 0, filename);
    }
    public Entity(int posX, int posY, String filename) {    
        super(posX, posY, filename);
    }   

    public Rectangle getRect() {
        return new Rectangle(this.getPosX(), this.getPosY(), this.getWidth(), this.getHeight());
    }

    public Rectangle getHitbox() {
        return getRect();
    }
}



