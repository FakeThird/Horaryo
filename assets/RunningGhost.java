package assets;

import java.awt.Rectangle;

public class RunningGhost extends Ghost {    

    private Player player;
    private static final int FLYING_GHOST_HITBOX_WIDTH = 40;
    private static final int FLYING_GHOST_HITBOX_HEIGHT = 220;
    private static final int SPEED = 8;
    private static final int LIFETIME = 100 ;
    private int life ;

    public RunningGhost(Player player) {
        super(0, 0, new Animation("JULIA/Just Unified Lists Into Arrays", 1F), 
                        new Animation("JULIA/Just Unified Lists Into Arrays - Reflected", 1F));
        this.player = player;
        this.life = LIFETIME ;
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

    public void updateLifetime() {
        this.life--;
    }
    public boolean isPresent() {
        return (this.life > 0);
    }

        
}