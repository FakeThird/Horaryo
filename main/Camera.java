package main;

import assets.Location;
import assets.Player;

public class Camera {
    private Game game;
    private Player player;
    private Object focus;
    private int screenCenterPosition;

    public Camera(Game game, Player player) {
        this.game = game;
        this.player = player;   
        this.screenCenterPosition = (GameWindow.GAME_WIDTH - player.getWidth()) / 2;
    }

    public void updateFocus() {
        if ((player.isMovingLeft() && (player.getPosX() > screenCenterPosition || game.getGameLocation().getBorderLeft() > 0)) ||  
            (player.isMovingRight() && (player.getPosX() < screenCenterPosition || game.getGameLocation().getBorderRight() < GameWindow.GAME_WIDTH))) {                
    
            focus = player;
        } else {
            focus = game.getGameLocation();
        }
    }

    public void move() {
        int speed = 0;
        if (player.isMovingLeft()) {
            speed = -player.getSpeed();
        } else {
            speed = +player.getSpeed();
        }

        if (focus instanceof Player playerVar) {
            player.moveX(speed);
        } 
        if (focus instanceof Location locationVar) {
            locationVar.moveLocation(-speed);
        }
    }
}