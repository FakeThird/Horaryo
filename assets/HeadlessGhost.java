package assets;

import java.awt.Rectangle;

public class HeadlessGhost extends Ghost {    
    private int horizontalPathSpan;
    private int horizontalPosition;
    private int horizontalSpeed;

    private static final int FLYING_GHOST_HITBOX_WIDTH = 40;
    private static final int FLYING_GHOST_HITBOX_HEIGHT = 220;


    public HeadlessGhost(int horizontalPathSpan,
                    int horizontalSpeed) {
        this(0, 0, horizontalPathSpan, horizontalSpeed);
    }
    public HeadlessGhost(int posX, int posY, 
                    int horizontalPathSpan,  
                    int horizontalSpeed) {

        super(posX, posY, new Animation("/CLYDE/Creating Lukewarm Yucky Dried Earthworms", 1F), 
                        new Animation("/CLYDE/Creating Lukewarm Yucky Dried Earthworms - Reflected", 1F));
        this.horizontalPathSpan = horizontalPathSpan;
        this.horizontalSpeed = horizontalSpeed;
    }  

    @Override
    public void updateGhostPathing() {
        horizontalPosition += horizontalSpeed;
        if (horizontalPosition >= horizontalPathSpan ||
            horizontalPosition <= 0) {
            
            horizontalSpeed *= -1;
        }

        if (horizontalSpeed > 0) {
            setGhostRightAnimation();
        }
        if (horizontalSpeed < 0) {
            setGhostLeftAnimation();
        }

        moveX(horizontalSpeed);
    }     

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(getPosX() + (getWidth() - FLYING_GHOST_HITBOX_WIDTH) / 2, 
                    getPosY() + (getHeight() - FLYING_GHOST_HITBOX_HEIGHT),
                    FLYING_GHOST_HITBOX_WIDTH, FLYING_GHOST_HITBOX_HEIGHT);
    }
    
}