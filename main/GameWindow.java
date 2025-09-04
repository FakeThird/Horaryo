/** 
 * @class: GameWindow 
 * @date: December 3, 2024
 * @description:
 *      Extends JFrame.
 *      A container class that contains the panel for the game.    
 *      
 * @param: 
 *     * Game game  - injected game that will be displayed.
 *        
 * @fields:
 *     * GraphicsEnvironment graphics  -  gets the local current device's graphics environment which is 
 *                                        a container for all GraphicsDevice Objects.
 *     * GraphicsDevice device         -  stores the local current device's screen which is used 
 *                                        to set the game window to its full screen with 
 *                                        device.setFullScreenWindow(this);.
 */

package main;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class GameWindow extends JFrame {
    private GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();        
    private GraphicsDevice device = graphics.getDefaultScreenDevice();
    private static Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int GAME_WIDTH  = (int) size.getWidth();
    public static final int GAME_HEIGHT  = (int) size.getHeight();
    private Game game;


    public GameWindow(Game game) {
        this.game = game;
    }

    public void initialize() { 
        this.add(game);     
        this.setUndecorated(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                                                                                                    
        this.setVisible(true);   
        device.setFullScreenWindow(this);          
    }
}

