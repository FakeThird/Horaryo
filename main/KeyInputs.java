package main;

import assets.Player;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInputs extends KeyAdapter {
    
    private Game game;
    private Player player;
    private int flashlightDelayTimer;
    private int interactionDelayTimer;
    private int crouchingDelayTimer;
    private final int DELAY_DURATION = 50;
    private Sound lightswitch = new Sound("flashlight.wav") ;

    public KeyInputs(Game game, Player player) {
        this.game = game;
        this.player = player;
        this.flashlightDelayTimer = 50;
        this.interactionDelayTimer = 50;
        this.crouchingDelayTimer = 50;
    }

    public void updateKeyInputCooldown() {
        if (flashlightDelayTimer != DELAY_DURATION) {
            this.flashlightDelayTimer++;
        }
        if (interactionDelayTimer != DELAY_DURATION) {
            this.interactionDelayTimer++;
        }
        if (crouchingDelayTimer != DELAY_DURATION) {
            this.crouchingDelayTimer++;
        }
    }
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_C && crouchingDelayTimer == DELAY_DURATION) {
            game.getUserCommands().setCrouchingToggle(
                !game.getUserCommands().isCrouchingToggled());
            crouchingDelayTimer = 0;
        } else if (!player.isCrouching()){
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                game.getUserCommands().setMoveLeftToggle(true);
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                game.getUserCommands().setMoveRightToggle(true);  
            }
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                game.getUserCommands().setRunningToggle(true);
            }
            if (e.getKeyCode() == KeyEvent.VK_E && this.interactionDelayTimer == DELAY_DURATION) {
                game.getUserCommands().setPlayerInteractionToggle(true);
                player.setInteracting(true);
                interactionDelayTimer = 0;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_F && this.flashlightDelayTimer == DELAY_DURATION) {
            lightswitch.startSound() ; 
            game.getUserCommands().setPlayerFlashlightToggle(
                !game.getUserCommands().isPlayerFlashlightToggled());  
            flashlightDelayTimer = 0; 
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            game.getUserCommands().setMoveLeftToggle(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            game.getUserCommands().setMoveRightToggle(false);            
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            game.getUserCommands().setRunningToggle(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_E) {
            player.setInteracting(false);
        }
    }
}