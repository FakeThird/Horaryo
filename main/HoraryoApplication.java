/** 
 * @class: HoraryoApplication 
 * @date: December 3, 2024
 * @description:
 *     Starting point of the Horaryo Application/Game.
 *     To start the process, enter java main/HoraryoApplicaion 
 *  
*/

package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HoraryoApplication {

    public static void main(String[] args) {
        // Create a new Game object
        Game game = new Game();
        // Create and show the main menu window
        MainWindow mainWindow = new MainWindow(game);
        mainWindow.setVisible(true);

        // // Action listener for the "Start Game" button (in MainClass)
        mainWindow.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        //         // Transition to game initialization when "Start Game" is clicked
                mainWindow.dispose(); // Close the main menu
                startGame(game); // Start the game
                game.reset();   
            }
        });
    }

    private static void startGame(Game game) {
        // Initialize the game window
        game.loadImages(Game.GAME_START);
        game.initialize();
        
        GameWindow gameWindow = new GameWindow(game);
        gameWindow.initialize();
        
        game.startGameLoop();
    }
}


