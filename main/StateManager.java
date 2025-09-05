package main;

import assets.* ;


public class StateManager {
    private Game game;
    private Player player;

    private Sound walk = new Sound("walk.wav", 27) ;
    private int stateBattery=0;

    public StateManager(Game game, Player player) {
        this.game = game;
        this.player = player;
    }
    

    public void updatePlayerState() {   
        player.setCrouching(game.getUserCommands().isCrouchingToggled());
        player.setMovingLeft(game.getUserCommands().isMoveLeftToggled());
        player.setMovingRight(game.getUserCommands().isMoveRightToggled());
    
        player.setMoving(game.getUserCommands().isMoveLeftToggled() ^
                            game.getUserCommands().isMoveRightToggled());

        player.setRunning(game.getUserCommands().isRunningToggled() && 
                        player.isMoving() && !player.isExhausted());
        
        player.setFlashlightActive(game.getUserCommands().isPlayerFlashlightToggled());

         //Updates the battery state
        if (player.isFlashlightActive()){
            player.decreaseBattery();
                if (player.getBattery()<=player.MIN_BATTERY) {
                    player.flashlightOFF();
                }
        }
        if (player.getBattery()%1200==0) {
            stateBattery++;
            game.setShouldDrawBatteryImage(stateBattery);  // Signal Game to draw the battery image
        }

        player.setSpeed(Player.NO_SPEED);
        player.increaseStamina();

        if (player.isCrouching()) {
            player.setSpeed(Player.NO_SPEED);
            player.increaseStamina();
        }else if (player.isRunning()) {
            player.setSpeed(Player.RUNNING_SPEED);
            player.decreaseStamina();
            walk.startTimer(20) ;
        } else if (player.isMoving()) {
            player.setSpeed(Player.WALKING_SPEED);
            walk.startTimer(28) ;
        } else {
            player.setSpeed(Player.NO_SPEED);
            player.increaseStamina();
            walk.setTimeStart(27);
        }
        if (player.isFlashlightActive()) {
            player.flashlightON();
        } else {
            player.flashlightOFF();
            
        }
    }  

    // NEW (updates all ghosts in all locations)
    public void updateGhostPathing() {
        for (Location location: game.getLocations()) {
            for (Entity entity: location.getEntities()) {
                if (entity instanceof Ghost ghost) {
                    ghost.updateGhostPathing();
                }
            }
        }
    }

    // NEW (updates all ghosts in all locations)
    public void updateGhostAnimationFrames() {
        for (Location location: game.getLocations()) {
            for (Entity entity: location.getEntities())  {
                if (entity instanceof Ghost ghost) {
                    ghost.updateAnimationFrames();
                }
            }
        }
    }

    // NEW (updates all ghosts in all locations)
    public void updateGhostStates() {
        RunningGhost ghost = null;
        for (Location location: game.getLocations()) {
            for (Entity entity: location.getEntities()) {
                if (entity instanceof RunningGhost julia) {
                    julia.updateLifetime();
                    ghost = julia;
                }
            }        
        }

        if (ghost != null && !ghost.isPresent()) {
            ghost.setVisible(false);
            for (Location location: game.getLocations()) {
                if (location.getEntities().contains(ghost)) {
                    location.getEntities().remove(ghost);
                }                
            }
            game.resetJuliaSpawn();
        }
    }
    public void updatePlayerAnimationType() {
        int prevAnimation = player.getAnimationType();

        if (player.isCrouching()) { 
            if (prevAnimation % 2 == Player.LOOKING_LEFT && player.isFlashlightActive()) {
                player.setAnimationType(Player.CROUCHING_LEFT_DOWN_FLASHLIGHT);
            } else if (prevAnimation % 2 == Player.LOOKING_LEFT) {
                player.setAnimationType(Player.CROUCHING_LEFT_DOWN);
            }
            if (prevAnimation % 2 == Player.LOOKING_RIGHT  && player.isFlashlightActive()) {
                player.setAnimationType(Player.CROUCHING_RIGHT_DOWN_FLASHLIGHT);
            } else if (prevAnimation % 2 == Player.LOOKING_RIGHT) {
                player.setAnimationType(Player.CROUCHING_RIGHT_DOWN);
            }

        } else{
            if (prevAnimation == Player.CROUCHING_LEFT_DOWN || prevAnimation == Player.CROUCHING_RIGHT_DOWN || prevAnimation == Player.CROUCHING_LEFT_DOWN_FLASHLIGHT || prevAnimation == Player.CROUCHING_RIGHT_DOWN_FLASHLIGHT) { 
                if (prevAnimation == Player.CROUCHING_LEFT_DOWN || prevAnimation == Player.CROUCHING_LEFT_DOWN_FLASHLIGHT) {
                    player.setAnimationType(Player.CROUCHING_LEFT_UP);
                }
                if (prevAnimation == Player.CROUCHING_RIGHT_DOWN || prevAnimation == Player.CROUCHING_RIGHT_DOWN_FLASHLIGHT) {
                    player.setAnimationType(Player.CROUCHING_RIGHT_UP);
                }
    
            } else if (player.isRunning()) { 
                if (player.isMovingLeft() && player.isFlashlightActive()) {
                    player.setAnimationType(Player.RUN_LEFT_FLASHLIGHT);
                } else if (player.isMovingLeft()) {
                    player.setAnimationType(Player.RUN_LEFT);
                }
                if (player.isMovingRight() && player.isFlashlightActive()){
                    player.setAnimationType(Player.RUN_RIGHT_FLASHLIGHT);
                } else if (player.isMovingRight()) {
                    player.setAnimationType(Player.RUN_RIGHT);
                }
            } else if (player.isMoving()) {
                if (player.isMovingLeft() && player.isFlashlightActive()) { 
                    player.setAnimationType(Player.WALK_LEFT_FLASHLIGHT);
                }
                else if (player.isMovingLeft()) {   
                    player.setAnimationType(Player.WALK_LEFT);
                } 
                if (player.isMovingRight() && player.isFlashlightActive()) { 
                    player.setAnimationType(Player.WALK_RIGHT_FLASHLIGHT);
                }
                else if (player.isMovingRight()) { 
                    player.setAnimationType(Player.WALK_RIGHT);
                }

            } else {

                if (prevAnimation % 2 == Player.LOOKING_LEFT && player.isFlashlightActive()) { 
                    player.setAnimationType(Player.IDLE_LEFT_FLASHLIGHT);
                }
                else if (prevAnimation % 2 == Player.LOOKING_LEFT) {
                    player.setAnimationType(Player.IDLE_LEFT);
                }
                if (prevAnimation % 2 == Player.LOOKING_RIGHT && player.isFlashlightActive()) { 
                    player.setAnimationType(Player.IDLE_RIGHT_FLASHLIGHT);
                }
                else if (prevAnimation % 2 == Player.LOOKING_RIGHT) {
                    player.setAnimationType(Player.IDLE_RIGHT);
                }
            }
        }
    }
}