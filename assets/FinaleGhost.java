package assets;

import java.awt.Rectangle;

public class FinaleGhost extends Ghost {    

    private Player player;
    private static final int FINALE_GHOST_HITBOX_WIDTH = 150;
    private static final int FINALE_GHOST_HITBOX_HEIGHT = 335;
    private static final int SPEED = 6;
    private static final int RUNNING_SPEED = 12;
    private int state ;

    public FinaleGhost(Player player, int state) {
        super(0, 0, new Animation("MM/Munting Monster", 1F), 
                        new Animation("MM/Munting Monster - Reflected", 1F));
        this.player = player;
        this.state = state ;
    }  

    @Override
    public void updateGhostPathing() {
        if(state<=20){
            if (player.getHitbox().x > getHitbox().x) {
                moveX(RUNNING_SPEED);
                setGhostRightAnimation();
            } else {
                moveX(-RUNNING_SPEED);
                setGhostLeftAnimation();
            }
        } else{
            if (player.getHitbox().x > getHitbox().x) {
                moveX(SPEED);
                setGhostRightAnimation();
            } else {
                moveX(-SPEED);
                setGhostLeftAnimation();
            }
        }
    } 

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(getPosX() + (getWidth() - FINALE_GHOST_HITBOX_WIDTH) / 2, 
                    getPosY() + (getHeight() - FINALE_GHOST_HITBOX_HEIGHT),
                    FINALE_GHOST_HITBOX_WIDTH, FINALE_GHOST_HITBOX_HEIGHT);
    }

    public boolean isRunning(){
        return (state<=20) ;
    }
   
}