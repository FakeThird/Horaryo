package main;

import assets.Player;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class LightManager {
    private Game game; 
    private Player player;
    private BufferedImage darknessFilter;
    private Graphics2D filterG2D;
    private int opacity ;

    // private final int DEFAULT_DARKNESS_FILTER_OPACITY = 220;

    public LightManager(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    public void manageBackgroundLighting() {    
        darknessFilter = new BufferedImage(GameWindow.GAME_WIDTH, GameWindow.GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        filterG2D = darknessFilter.createGraphics();
        
        if (opacity > 220) {
            opacity--;
        } 

        filterG2D.setColor(new Color(0, 0, 0, opacity));
        
        filterG2D.fillRect(0, 0, GameWindow.GAME_WIDTH, GameWindow.GAME_HEIGHT);
        filterG2D.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 1.0F));


            // Put All With LightComponent Here 
        if (opacity == 220) {
            if(player.getAnimationType() % 2 ==Player.LOOKING_LEFT){
                // Creating Light Shape going Left
                filterG2D.fillPolygon(new int[]{player.getHitbox().x-13, player.getHitbox().x-463, player.getHitbox().x-463},
                                        new int[]{player.getHitbox().y+55, player.getHitbox().y-480, player.getHitbox().y+590}, 
                                            player.getLightUnits()) ;
                filterG2D.fillArc(player.getHitbox().x-660, player.getHitbox().y-465, player.getLightWidth(), 1050, 90, 180) ;
                //filterG2D.fillOval(player.getHitbox().x-14,player.getHitbox().y+15,player.getLightDiameter(),player.getLightDiameter() ) ;
            } else if(player.getAnimationType() % 2==Player.LOOKING_RIGHT) {
                // Creating Light Shape going Right
                filterG2D.fillPolygon(new int[]{player.getHitbox().x+70, player.getHitbox().x+520, player.getHitbox().x+520},
                                        new int[]{player.getHitbox().y+55, player.getHitbox().y-480, player.getHitbox().y+590}, 
                                            player.getLightUnits()) ;
                filterG2D.fillArc(player.getHitbox().x+320, player.getHitbox().y-465, player.getLightWidth(), 1050, 90, -180) ; 
                //filterG2D.fillOval(player.getHitbox().x-5,player.getHitbox().y+15,player.getLightDiameter(),player.getLightDiameter() ) ;
            }        
        } 

        filterG2D.dispose();
    }
    
    public BufferedImage getBackgroundLightDisplay() {
        return this.darknessFilter;
    }
    public void doDarkenEffect() {
        this.opacity = 255;
    }
    public void lightUp() {
        this.opacity = 0;
    }

}