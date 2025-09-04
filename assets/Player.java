package assets;

import java.awt.Rectangle;
import java.util.ArrayList;
import main.Game;

public class Player extends Entity {
    private ArrayList<Animation> animationList; 
    private Animation currentAnimation;
    private int animationType;
    private int frameIndex;
    private int animationTick;

    private int speed;
    private int stamina;

    private boolean running;
    private boolean moving;
    private boolean movingLeft;
    private boolean movingRight;
    private boolean crouching;
    private boolean interacting;
    private boolean activeFlashlight ;
    private ArrayList<CollectibleEntity> inventory;
    
    private final int FLASHLIGHT_OFF = 0 ;
    public final int FLASHLIGHT_ON1 = 3 ;
    public final int FLASHLIGHT_ON2 = 400 ;
    public final int FLASHLIGHT_ON3 = 60 ;
    public int lightUnits = FLASHLIGHT_OFF ; 
    public int lightWidth = FLASHLIGHT_OFF ; 
    public int lightDiameter = FLASHLIGHT_OFF;
    private int battery ;

    public static final int MAX_BATTERY = 12001;
    public static final int MIN_BATTERY = 1199 ;
    
    public static final int IDLE_RIGHT = 0;
    public static final int IDLE_LEFT = 1;
    public static final int IDLE_RIGHT_FLASHLIGHT = 2;
    public static final int IDLE_LEFT_FLASHLIGHT = 3;
    public static final int WALK_RIGHT = 4;
    public static final int WALK_LEFT = 5;
    public static final int WALK_RIGHT_FLASHLIGHT = 6;
    public static final int WALK_LEFT_FLASHLIGHT = 7;
    public static final int RUN_RIGHT = 8;
    public static final int RUN_LEFT = 9;
    public static final int RUN_RIGHT_FLASHLIGHT  = 10;
    public static final int RUN_LEFT_FLASHLIGHT  = 11;
    public static final int CROUCHING_RIGHT_DOWN = 12;
    public static final int CROUCHING_LEFT_DOWN = 13;
    public static final int CROUCHING_RIGHT_DOWN_FLASHLIGHT = 14;
    public static final int CROUCHING_LEFT_DOWN_FLASHLIGHT = 15;
    public static final int CROUCHING_RIGHT_UP = 16;
    public static final int CROUCHING_LEFT_UP = 17;

    public static final int LOOKING_LEFT = 1;
    public static final int LOOKING_RIGHT = 0;

    public static final int HITBOX_WIDTH = 68;
    public static final int STANDING_HITBOX_HEIGHT = 200;
    public static final int CROUCHING_HITBOX_HEIGHT = 153;
    public static final int RUNNING_SPEED = 8;
    public static final int WALKING_SPEED = 3;
    public static final int NO_SPEED = 0;    
    public static final int STAMINA_FULL = 200;
    public static final int STAMINA_EMPTY = 0;
    public static final int STAMINA_RECHARGE_RATE = 1;


    public Player(Animation ...animations) {
        super();

        this.animationList = new ArrayList<Animation>();
        for (Animation animation: animations) {
            this.animationList.add(animation);
        }
        
        this.inventory = new ArrayList<CollectibleEntity>();
        this.currentAnimation = animationList.get(IDLE_RIGHT);
        this.setWidth(currentAnimation.getWidth());
        this.setHeight(currentAnimation.getHeight());
        this.setImage(currentAnimation.getAnimation().get(frameIndex));
        this.stamina = STAMINA_FULL;
        this.activeFlashlight = false ;
        this.battery = MAX_BATTERY ;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    } 
    public int getSpeed() {
        return this.speed;
    } 

    public void decreaseStamina() {
        if (stamina > STAMINA_EMPTY) {
            stamina -= STAMINA_RECHARGE_RATE;
        }
    }
    public void increaseStamina() {
        if (stamina < STAMINA_FULL) {
            stamina += STAMINA_RECHARGE_RATE;
        }
    }

    public void decreaseBattery() {
        if(battery > MIN_BATTERY){
            battery--;
        };
    }
    public int getBattery() {
        return this.battery;
    }

    public void resetStamina() {
        stamina = STAMINA_FULL;
    }
    public boolean isExhausted() {
        return (stamina == 0);
    }
    public int getStamina() {
        return this.stamina;
    }

    public boolean isRunning() {
        return this.running;
    } 
    public void setRunning(boolean value) {
        this.running = value;
    } 

    public boolean isMoving() {
        return this.moving;
    } 
    public void setMoving(boolean value) {
        this.moving = value;
    } 

    public boolean isMovingLeft() {
        return this.movingLeft;
    } 
    public void setMovingLeft(boolean value) {
        this.movingLeft = value;
    } 

    public boolean isMovingRight() {
        return this.movingRight;
    } 
    public void setMovingRight(boolean value) {
        this.movingRight = value;
    } 

    public void setFlashlightActive(boolean value) {
        this.activeFlashlight = value;
    }
    public boolean isFlashlightActive(){
        return this.activeFlashlight;
    }
    public Rectangle getFlashlightHitbox() {
        if (activeFlashlight && animationType % 2 == LOOKING_RIGHT) {
            return new Rectangle(getHitbox().x+50 , getRect().y + getHeight() / 2, 600, 100);
        }
        if (activeFlashlight && animationType % 2 == LOOKING_LEFT) {
            return new Rectangle(getHitbox().x - 650 + getHitbox().width , getRect().y + getHeight() / 2, 600, 100);
        }
        return new Rectangle(getHitbox().x, getRect().y, 0, 0);
    }

    

    public void flashlightON() {
        this.lightUnits = FLASHLIGHT_ON1 ;
        this.lightWidth = FLASHLIGHT_ON2 ;
        this.lightDiameter = FLASHLIGHT_ON3 ;
        this.activeFlashlight = true ;
    }

    public void flashlightOFF() {
        lightUnits = lightWidth = lightDiameter = FLASHLIGHT_OFF ;
        this.activeFlashlight = false ;
    }

    public int getLightDiameter(){
        return this.lightDiameter ;
    }

    public int getLightWidth(){
        return this.lightWidth ;
    }

    public int getLightUnits(){
        return this.lightUnits ;
    }
    public boolean isCrouching() {
        return this.crouching;
    }
    public void setCrouching(boolean value)  {
        this.crouching = value;
    }

    public void collectItem(CollectibleEntity collectibleEntity) {
        this.inventory.add(collectibleEntity);
    }
    public ArrayList<CollectibleEntity> getInventory() {
        return this.inventory;
    }
    public void emptyInventory() {
        this.inventory = new ArrayList<CollectibleEntity>();
    } 

    public void setInteracting(boolean value) {
        this.interacting = value;
    }
    public boolean isInteracting() {
        return this.interacting;
    }


    public void updateAnimationFrames() {
        this.animationTick++;

        if (animationTick >= Game.FPS / (currentAnimation.getCyclesPerSec() * currentAnimation.getAnimationLength()) && ((isCrouching() && frameIndex==3)?false:true)) {
            this.frameIndex = (frameIndex + 1) % currentAnimation.getAnimationLength();
            this.animationTick = 0;
            this.setImage(currentAnimation.getAnimation().get(frameIndex));        
        }
    }

    public void setAnimationType(int newAnimationType) {
        int previousAnimationType = animationType;

        this.animationType = newAnimationType;
        this.currentAnimation = animationList.get(animationType);

        if (previousAnimationType != animationType) {
            this.setImage(currentAnimation.getAnimation().get(0));
            frameIndex = 0;
        }
    }

    public int getAnimationType() {
        return this.animationType;
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(getPosX() + (getWidth() - HITBOX_WIDTH) / 2, 
                    this.getPosY() + (this.getHeight() - (this.isCrouching()?CROUCHING_HITBOX_HEIGHT:STANDING_HITBOX_HEIGHT)) / 2 + (this.isCrouching()?50:30) , 
                    HITBOX_WIDTH, (this.isCrouching()?CROUCHING_HITBOX_HEIGHT:STANDING_HITBOX_HEIGHT));

    }
}  