package assets;

import java.awt.Rectangle;

public class VampireGhost extends Ghost {    

    private Player player;
    private static final int FLYING_GHOST_HITBOX_WIDTH = 40;
    private static final int FLYING_GHOST_HITBOX_HEIGHT = 220;
    private static final int SPEED = 4;

    public VampireGhost(Player player) {
        super(0, 0, new Animation("NEYRO/Nobody Enjoys Yielding Rodent Overpopulation", 1F), 
                        new Animation("NEYRO/Nobody Enjoys Yielding Rodent Overpopulation - Reflected", 1F));
        this.player = player;
    }  

    @Override
    public void updateGhostPathing() {
        if (player.getHitbox().x > getHitbox().x) {
            moveX(SPEED);
            setGhostRightAnimation();
        } else {
            moveX(-SPEED);
            setGhostLeftAnimation();
        }
    } 

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(getPosX() + (getWidth() - FLYING_GHOST_HITBOX_WIDTH) / 2, 
                    getPosY() + (getHeight() - FLYING_GHOST_HITBOX_HEIGHT),
                    FLYING_GHOST_HITBOX_WIDTH, FLYING_GHOST_HITBOX_HEIGHT);
    }

        
}