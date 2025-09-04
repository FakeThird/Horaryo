// package assets;


// public class MuntingMonster extends Ghost {
//     private ArrayList<Animation> animationList; 
//     private Animation currentAnimation;
//     private int animationType;
//     private int frameIndex;
//     private int animationTick;

//     private int speed;

//     private boolean running;
//     private boolean moving;
//     private boolean movingLeft;
//     private boolean movingRight;
//     private boolean activeflashlight;
//     private boolean interacting;
//     private ArrayList<CollectibleEntity> inventory;

//     private int lightDiameter;
//     private final int FLASHLIGHT_OFF = 0 ;
//     public final int FLASHLIGHT_ON = 500; 

//     public static final int IDLE_RIGHT = 0;
//     public static final int IDLE_LEFT = 1;
//     public static final int IDLE_RIGHT_FLASHLIGHT = 2;
//     public static final int IDLE_LEFT_FLASHLIGHT = 3;
//     public static final int WALK_RIGHT = 4;
//     public static final int WALK_LEFT = 5;
//     public static final int WALK_RIGHT_FLASHLIGHT = 6;
//     public static final int WALK_LEFT_FLASHLIGHT = 7;
//     public static final int RUN_RIGHT = 8;
//     public static final int RUN_LEFT = 9;

//     public static final int LOOKING_LEFT = 1;
//     public static final int LOOKING_RIGHT = 0;
//     public static final int HITBOX_WIDTH = 68;
//     public static final int HITBOX_HEIGHT = 200;
//     public static final int RUNNING_SPEED = 7;
//     public static final int WALKING_SPEED = 3;
//     public static final int NO_SPEED = 0;    

//     public Player(Animation ...animations) {
//         super();

//         this.animationList = new ArrayList<Animation>();
//         for (Animation animation: animations) {
//             this.animationList.add(animation);
//         }
        
//         this.inventory = new ArrayList<CollectibleEntity>();
//         this.currentAnimation = animationList.get(IDLE_RIGHT);
//         this.setWidth(currentAnimation.getWidth());
//         this.setHeight(currentAnimation.getHeight());
//         this.setImage(currentAnimation.getAnimation().get(frameIndex));
//     }

//     public void setSpeed(int speed) {
//         this.speed = speed;
//     } 
//     public int getSpeed() {
//         return this.speed;
//     } 

//     public boolean isRunning() {
//         return this.running;
//     } 
//     public void setRunning(boolean value) {
//         this.running = value;
//     } 

//     public boolean isMoving() {
//         return this.moving;
//     } 
//     public void setMoving(boolean value) {
//         this.moving = value;
//     } 

//     public boolean isMovingLeft() {
//         return this.movingLeft;
//     } 
//     public void setMovingLeft(boolean value) {
//         this.movingLeft = value;
//     } 

//     public boolean isMovingRight() {
//         return this.movingRight;
//     } 
//     public void setMovingRight(boolean value) {
//         this.movingRight = value;
//     } 

//     public void setFlashlightActive(boolean value) {
//         this.activeflashlight = value;
//     }
//     public boolean isFlashlightActive() {
//         return this.activeflashlight ;
//     }

//     public void flashlightON() {
//         this.lightDiameter = FLASHLIGHT_ON;
//     }
//     public void flashlightOFF() {
//         this.lightDiameter = FLASHLIGHT_OFF;
//     }

//     public int getLightDiameter(){
//         return this.lightDiameter;
//     }

//     public Rectangle getHitbox() {
//         return new Rectangle(getPosX() + (getWidth() - HITBOX_WIDTH) / 2, 
//                     getPosY() + (getHeight() - HITBOX_HEIGHT),
//                     HITBOX_WIDTH, HITBOX_HEIGHT);
//     }

//     public void collectItem(CollectibleEntity collectibleEntity) {
//         this.inventory.add(collectibleEntity);
//     }
    
//     public ArrayList<CollectibleEntity> getInventory() {
//         return this.inventory;
//     }

//     public void setInteracting(boolean value) {
//         this.interacting = value;
//     }
//     public boolean isInteracting() {
//         return this.interacting;
//     }


//     public void updateAnimationFrames() {
//         this.animationTick++;

//         if (animationTick >= Game.FPS / (currentAnimation.getCyclesPerSec() * currentAnimation.getAnimationLength())) {
//             this.frameIndex = (frameIndex + 1) % currentAnimation.getAnimationLength();
//             this.animationTick = 0;
//             this.setImage(currentAnimation.getAnimation().get(frameIndex));
//         } 
//     }

//     public void setAnimationType(int newAnimationType) {
//         int previousAnimationType = animationType;

//         this.animationType = newAnimationType;
//         this.currentAnimation = animationList.get(animationType);

//         if (previousAnimationType != animationType) {
//             this.setImage(currentAnimation.getAnimation().get(0));
//             frameIndex = 0;
//         }
//     }

//     public int getAnimationType() {
//         return this.animationType;
//     }
    
// }