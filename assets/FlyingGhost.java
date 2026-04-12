package assets;

import java.awt.Rectangle;

public class FlyingGhost extends Ghost {    
    private int leftBound;
    private int rightBound;
    private int verticalPathSpan;
    private int verticalPosition;
    private int horizontalSpeed;
    private int verticalSpeed;

    private static final int FLYING_GHOST_HITBOX_WIDTH = 40;
    private static final int FLYING_GHOST_HITBOX_HEIGHT = 150;

    public FlyingGhost(int leftBound, int rightBound, int verticalPathSpan,
                    int horizontalSpeed, int verticalSpeed) {
        this(0, 0, leftBound, rightBound, verticalPathSpan, horizontalSpeed, verticalSpeed);
    }
    public FlyingGhost(int posX, int posY, int leftBound, int rightBound,
                    int verticalPathSpan, int horizontalSpeed, int verticalSpeed) {
        super(posX, posY, new Animation("HANSEN/How Animated Neyro_s Shit Entry Nodes", 1F), 
                        new Animation("HANSEN/How Animated Neyro_s Shit Entry Nodes - Reflected", 1F));
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.verticalPathSpan = verticalPathSpan;
        this.horizontalSpeed = horizontalSpeed;
        this.verticalSpeed = verticalSpeed;
    } 
    
    @Override
    public void updateGhostPathing() {
        verticalPosition += verticalSpeed;
        if (verticalPosition >= verticalPathSpan || verticalPosition <= 0) verticalSpeed *= -1;

        moveX(horizontalSpeed);
        moveY(verticalSpeed);

        if (getPosX() + getWidth() >= rightBound || getPosX() <= leftBound) {
            horizontalSpeed *= -1;
            moveX(horizontalSpeed);
        }

        if (horizontalSpeed > 0) setGhostRightAnimation();
        if (horizontalSpeed < 0) setGhostLeftAnimation();
    }

    public void moveBounds(int speed) {
        leftBound += speed;
        rightBound += speed;
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(getPosX() + (getWidth() - FLYING_GHOST_HITBOX_WIDTH) / 2, 
                    getPosY() + (getHeight() - FLYING_GHOST_HITBOX_HEIGHT) - 100 ,
                    FLYING_GHOST_HITBOX_WIDTH, FLYING_GHOST_HITBOX_HEIGHT);
    }

        
}