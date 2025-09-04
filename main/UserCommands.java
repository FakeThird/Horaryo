package main;

public class UserCommands {
    private boolean toggleMovePlayerLeft;
    private boolean toggleMovePlayerRight;
    private boolean togglePlayerRunning;
    private boolean togglePlayerFlashlight;
    private boolean togglePlayerInteraction;
    private boolean togglePlayerCrouching;

    public boolean isMoveLeftToggled() {
        return this.toggleMovePlayerLeft;
    }
    public void setMoveLeftToggle(boolean toggle) {
        this.toggleMovePlayerLeft = toggle;
    }

    public boolean isMoveRightToggled() {
        return this.toggleMovePlayerRight;
    }
    public void setMoveRightToggle(boolean toggle) {
        this.toggleMovePlayerRight = toggle;
    }

    public boolean isRunningToggled() {
        return this.togglePlayerRunning;
    }
    public void setRunningToggle(boolean toggle) {
        this.togglePlayerRunning = toggle;
    }

    public boolean isCrouchingToggled() {
        return this.togglePlayerCrouching;
    }
    public void setCrouchingToggle(boolean toggle) {
        this.togglePlayerCrouching = toggle;
    }
    public void setPlayerFlashlightToggle(boolean value) {
        this.togglePlayerFlashlight = value;
    }
    public boolean isPlayerFlashlightToggled() {
        return this.togglePlayerFlashlight;
    }

    // To Work On 
    public void setPlayerInteractionToggle(boolean value) {
        this.togglePlayerInteraction = value;
    }
    public boolean isPlayerInteractionToggled() {
        return this.togglePlayerInteraction;
    }
}

