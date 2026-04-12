package assets;

import java.awt.Rectangle;

public class HeadlessGhost extends Ghost {    
    private int leftBound;
    private int rightBound;
    private int horizontalSpeed;

    private static final int FLYING_GHOST_HITBOX_WIDTH = 40;
    private static final int FLYING_GHOST_HITBOX_HEIGHT = 220;


    public HeadlessGhost(int leftBound, int rightBound, int horizontalSpeed) {
        this(0, 0, leftBound, rightBound, horizontalSpeed);
    }
    public HeadlessGhost(int posX, int posY, int leftBound, int rightBound, int horizontalSpeed) {
        super(posX, posY, new Animation("/CLYDE/Creating Lukewarm Yucky Dried Earthworms", 1F), 
                        new Animation("/CLYDE/Creating Lukewarm Yucky Dried Earthworms - Reflected", 1F));
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.horizontalSpeed = horizontalSpeed;
    } 

    @Override
    public void updateGhostPathing() {
        moveX(horizontalSpeed);

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
                    getPosY() + (getHeight() - FLYING_GHOST_HITBOX_HEIGHT),
                    FLYING_GHOST_HITBOX_WIDTH, FLYING_GHOST_HITBOX_HEIGHT);
    }
    
}