package assets;

import java.awt.Rectangle;

public class FlyingGhost extends Ghost {    
    private int horizontalPathSpan;
    private int verticalPathSpan;
    private int horizontalPosition;
    private int verticalPosition;
    private int horizontalSpeed;
    private int verticalSpeed;

    private static final int FLYING_GHOST_HITBOX_WIDTH = 40;
    private static final int FLYING_GHOST_HITBOX_HEIGHT = 150;

    public FlyingGhost(int horizontalPathSpan, int verticalPathSpan, 
                    int horizontalSpeed, int verticalSpeed) {
        this(0, 0, horizontalPathSpan, verticalPathSpan, horizontalSpeed, verticalSpeed);
    }
    public FlyingGhost(int posX, int posY, 
                    int horizontalPathSpan, int verticalPathSpan, 
                    int horizontalSpeed, int verticalSpeed) {

        super(posX, posY, new Animation("HANSEN/How Animated Neyro_s Shit Entry Nodes", 1F), 
                        new Animation("HANSEN/How Animated Neyro_s Shit Entry Nodes - Reflected", 1F));
        this.horizontalPathSpan = horizontalPathSpan;
        this.verticalPathSpan = verticalPathSpan;
        this.horizontalSpeed = horizontalSpeed;
        this.verticalSpeed = verticalSpeed;
    }  

    @Override
    public void updateGhostPathing() {
        verticalPosition += verticalSpeed;
        horizontalPosition += horizontalSpeed;

        if (verticalPosition >= verticalPathSpan || 
            verticalPosition <= 0) {
            
            verticalSpeed *= -1; 
        }
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
        moveY(verticalSpeed);
    } 

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(getPosX() + (getWidth() - FLYING_GHOST_HITBOX_WIDTH) / 2, 
                    getPosY() + (getHeight() - FLYING_GHOST_HITBOX_HEIGHT) - 100 ,
                    FLYING_GHOST_HITBOX_WIDTH, FLYING_GHOST_HITBOX_HEIGHT);
    }

        
}