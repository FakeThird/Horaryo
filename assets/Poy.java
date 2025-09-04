package assets;

import java.awt.Rectangle;

public class Poy extends Ghost {    

    public Poy() {

        super(0,0, new Animation("/FRANZ/Skibidi Rizzler - Reflected", 1F), 
                        new Animation("/FRANZ/Skibidi Rizzler", 1F));
    }  

    @Override
    public void updateGhostPathing() {
    }     

    @Override
    public Rectangle getHitbox() {
        return getRect() ;
    }
    
}