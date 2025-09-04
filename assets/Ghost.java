package assets;

import java.awt.Rectangle;
import main.Game;

public abstract class Ghost extends Entity {
    private Animation movingLeftAnimation;
    private Animation movingRightAnimation;
    private Animation currentAnimation;
    private int frameIndex;
    private int animationTick;

    public Ghost(int posX, int posY, Animation animationRight, Animation animationLeft) {    
        super();
        this.movingRightAnimation = animationRight;
        this.movingLeftAnimation = animationLeft;
        this.currentAnimation = animationRight;

        this.setWidth(currentAnimation.getWidth());
        this.setHeight(currentAnimation.getHeight());
        this.setImage(currentAnimation.getAnimation().get(frameIndex));
    }

    public void updateAnimationFrames() {
        this.animationTick++;
        if (animationTick >= Game.FPS / (currentAnimation.getCyclesPerSec() * currentAnimation.getAnimationLength())) {
            this.frameIndex = (frameIndex + 1) % currentAnimation.getAnimationLength();
            this.animationTick = 0;
            this.setImage(currentAnimation.getAnimation().get(frameIndex));
        }

    }

    public void setGhostLeftAnimation() {
        this.currentAnimation = movingLeftAnimation;
    }
    public void setGhostRightAnimation() {
        this.currentAnimation = movingRightAnimation;
    }

    public abstract void updateGhostPathing();
    
    @Override
    public abstract Rectangle getHitbox();
}





