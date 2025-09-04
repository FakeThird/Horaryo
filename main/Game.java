/** 
 * @class: Game
 * @date: December 5, 2024
 * @description:
 *      Extends JPanel
 *      A container class that contains the game and its displays.    
 */

package main; 

import assets.* ;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable {
    /** 
     * Following fields tackle time and the Game Loop
     * Time will be measured by NANOSECONDS, for much precise 
     * keeping of time
     * 
     * GAME_START and GAME_RESET are states depending on the  
     * state of the game. They are already descripitve :>
     */
    private Thread gameLoopThread;      
    public static final int FPS = 30;       
    public static final int UPS = 50;
    public static final int GAME_START = 0; 
    public static final int GAME_RESET = 1; 
    private static final double SECONDS_IN_NANOSECONDS = 1000000000.0;
    private static final double TIME_PER_FRAME = SECONDS_IN_NANOSECONDS / FPS;
    private static final double TIME_PER_UPDATE = SECONDS_IN_NANOSECONDS / UPS;


    /** 
     * Following fields tackle are game managers which inject
     * this class and manages different parts of the game. A
     * conveyer/assembly line way of managing states.
     * 
     */ 
    private KeyInputs keyInput;
    private UserCommands userCommands;
    private StateManager statemanager;
    private Camera camera;
    private LightManager lightManager;
    private CollisionManager collisionManager;


    /** 
     * Following fields tackle events and main parts
     * of the game. 
     */
    private Location[] allLocations;
    private CollectibleEntity[] coloredBooks;
    private boolean[] placedBook; 
    private Random random = new Random();
    private Location randomLocation;
    private boolean spawnedHansen = false;
    private boolean spawnedClyde = false;
    private boolean spawnedNeyro = false;
    private boolean spawnedJulia = false;
    private boolean spawnedMM = false;

    private FinaleGhost muntingMonster ;

    private boolean end = false;

    //Music
    private Sound ambience = new Sound("ambience.wav") ;
    private Sound music = new Sound("music.wav") ;
    private Sound death = new Sound("awake.wav") ;
    private Sound congrats = new Sound("congrats.wav") ;
    private Sound mmnoise = new Sound("mimic.wav") ;
    private Sound poynoise = new Sound("poy.wav") ;

    
    Location mainLobby;
    Location emptyHallway;
    Location stairwayUp ;
    Location backHallway ;
    Location innerGarden ;
    Location stairwayDown  ;
    Location upperFrontHallway; ;
    Location roomHallway ;
    Location upperBackHallway; ;
    Location nenaRoom ;

    Location currentLocation;
    Player player;

    DoorEntity roomHToRoom ;
    DoorEntity lobbyToGarden ;
    DoorEntity backHLToGarden ;
    DoorEntity gardenToLobby ;
    DoorEntity gardenToBHL ;
        

    InputStream is ;
    private int shouldDrawBatteryImage;

    public Game() {
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(1200, 700));
        this.setFocusable(true);
    }
    
    public void loadImages(int gameOperation) {
        switch (gameOperation) {
        case Game.GAME_START:
            this.player = new Player(
                new Animation("player/0 Breathing Sigma", 1.5F),
                new Animation("player/1 Breathing Sigma - Reflected", 1.5F),
                new Animation("player/2 Breathing Sigma Mogging", 1.5F),
                new Animation("player/3 Breathing Sigma Mogging - Reflected", 1.5F),
                new Animation("player/4 Walking Beta", 0.95F),
                new Animation("player/5 Walking Beta - Reflected", 0.95F),
                new Animation("player/6 Walking Beta Mogging", 0.95F),
                new Animation("player/7 Walking Beta Mogging - Reflected", 0.95F),
                new Animation("player/8 Running Edger", 1.75F),
                new Animation("player/9 Running Edger - Reflected", 1.75F),
                new Animation("player/10 Running Edger Mogging", 1.75F),
                new Animation("player/11 Running Edger Mogging - Reflected", 1.75F),
                new Animation("player/12 Crouching Rizzler Down", 1.75F),
                new Animation("player/13 Crouching Rizzler Down - Reflected", 1.75F),
                new Animation("player/14 Crouching Rizzler Mogging", 1.75F),
                new Animation("player/15 Crouching Rizzler Mogging - Reflected", 1.75F),
                new Animation("player/16 Crouching Rizzler Up", 0.75F),
                new Animation("player/17 Crouching Rizzler Up - Reflected", 0.75F)
            );    
            /* Falls Through */

        case Game.GAME_RESET:    
        nenaRoom = new Location(0, 350, "Room.png");
        roomHallway = new Location(0, 350, "HallwayUp.png");
        upperFrontHallway = new Location(0, 350, "FrontUp.png");
        stairwayDown = new Location(0, 350, "Stairwaydown.png");
        stairwayUp = new Location(0, 350, "Stairway.png");
        backHallway = new Location(0, 350, "Back.png");
        innerGarden = new Location(0, 350, "Garden.png");
        mainLobby = new Location(0, 350, "Lobby.png");
        emptyHallway = new Location(0, 350, "Hallway.png");
        upperBackHallway = new Location(0, 350, "BackUp.png");
    

        currentLocation = mainLobby;

        allLocations = new Location[]{mainLobby, roomHallway, upperFrontHallway, stairwayDown, stairwayUp, backHallway, emptyHallway, upperBackHallway};
        coloredBooks = new CollectibleEntity[] { 
                        new CollectibleEntity("whitebook.png", "book"), 
                        new CollectibleEntity("bluebook.png", "book"), 
                        new CollectibleEntity("greenbook.png", "book"),
                        new CollectibleEntity("orangebook.png", "book"), 
                        new CollectibleEntity("pinkbook.png", "book"), };
        placedBook  = new boolean[] {false, false, false, false, false};

        roomHToRoom = new DoorEntity(nenaRoom, null);

        DoorEntity roomToRH = new DoorEntity(roomHallway, null);
        DoorEntity roomHToFHU = new DoorEntity(upperFrontHallway, null);
        DoorEntity roomHToBHU = new DoorEntity(upperBackHallway, null);

        DoorEntity backHUToRH = new DoorEntity(roomHallway, null);
        DoorEntity backHUToSD = new DoorEntity(stairwayDown, null);

        DoorEntity frontHUToRH = new DoorEntity(roomHallway, null);
        DoorEntity frontHUToSD = new DoorEntity(stairwayDown, null);

        DoorEntity stairwayDToFHU = new DoorEntity(upperFrontHallway, null);
        DoorEntity stairwayDToSU = new DoorEntity(stairwayUp, null);
        DoorEntity stairwayDToBHU = new DoorEntity(upperBackHallway, null);

        DoorEntity stairwayUToSD = new DoorEntity(stairwayDown, null);
        DoorEntity stairwayUToLobby = new DoorEntity(mainLobby, null);
        DoorEntity stairwayUToBHL = new DoorEntity(backHallway, null);

        lobbyToGarden = new DoorEntity(innerGarden, null);
        DoorEntity lobbyToSU = new DoorEntity(stairwayUp, null);
        DoorEntity lobbyToEH = new DoorEntity(emptyHallway, null);
        
        backHLToGarden = new DoorEntity(innerGarden, null);
        DoorEntity backHLToSU = new DoorEntity(stairwayUp, null);
        DoorEntity backHLToEH = new DoorEntity(emptyHallway, null);

        gardenToLobby = new DoorEntity(mainLobby, null);
        gardenToBHL = new DoorEntity(backHallway, null);
        
        DoorEntity emptyHToLobby = new DoorEntity(mainLobby, null);
        DoorEntity emptyHToBHL = new DoorEntity(backHallway, null);

        roomToRH.linkToDoor(roomHToRoom);

        roomHToBHU.linkToDoor(backHUToRH);
        roomHToFHU.linkToDoor(frontHUToRH);

        gardenToBHL.linkToDoor(backHLToGarden);
        gardenToLobby.linkToDoor(lobbyToGarden);

        backHLToSU.linkToDoor(stairwayUToBHL);
        backHLToEH.linkToDoor(emptyHToBHL);

        lobbyToSU.linkToDoor(stairwayUToLobby);
        lobbyToEH.linkToDoor(emptyHToLobby);

        backHUToRH.linkToDoor(roomHToBHU);
        backHUToSD.linkToDoor(stairwayDToBHU);

        frontHUToRH.linkToDoor(roomHToFHU);
        frontHUToSD.linkToDoor(stairwayDToFHU);

        stairwayDToFHU.linkToDoor(frontHUToSD);
        stairwayDToBHU.linkToDoor(backHUToSD);
        stairwayDToSU.linkToDoor(stairwayUToSD);

        stairwayUToSD.linkToDoor(stairwayDToSU);
        stairwayUToBHL.linkToDoor(backHLToSU);
        stairwayUToLobby.linkToDoor(lobbyToSU);
        
        emptyHToBHL.linkToDoor(backHLToEH);
        emptyHToLobby.linkToDoor(lobbyToEH);

        roomHToRoom.linkToDoor(roomToRH);
        lobbyToGarden.linkToDoor(gardenToLobby);
        backHLToGarden.linkToDoor(gardenToBHL);



        player.setPosX((GameWindow.GAME_WIDTH - player.getWidth()) / 2);
        player.setPosY(440);
        
        // lobby.setImageAtGrid(1, 2, new FlyingGhost(1000, 10, 1, 1));
        // lobby.setImageAtGrid(3, 10, new HeadlessGhost(300, 3));

        nenaRoom.setImageAtGrid(3, 3, roomToRH);

        roomHallway.setImageAtGrid(3, 32, roomHToFHU);
        roomHallway.setImageAtGrid(3, 0, roomHToBHU);
        roomHallway.setImageAtGrid(3, 10, roomHToRoom);
        
        innerGarden.setImageAtGrid(3, 18, gardenToLobby);
        innerGarden.setImageAtGrid(3, 0, gardenToBHL);

        upperFrontHallway.setImageAtGrid(3, 0, frontHUToRH);
        upperFrontHallway.setImageAtGrid(3, 32, frontHUToSD);

        upperBackHallway.setImageAtGrid(3, 0, backHUToSD);
        upperBackHallway.setImageAtGrid(3, 32, backHUToRH);

        stairwayDown.setImageAtGrid(3, 0, stairwayDToFHU);
        stairwayDown.setImageAtGrid(3, 15, stairwayDToSU);
        stairwayDown.setImageAtGrid(3, 32, stairwayDToBHU);

        emptyHallway.setImageAtGrid(3, 0, emptyHToBHL);
        emptyHallway.setImageAtGrid(3, 32, emptyHToLobby);

        mainLobby.setImageAtGrid(3, 7, lobbyToEH);
        mainLobby.setImageAtGrid(3, 27, lobbyToGarden);
        mainLobby.setImageAtGrid(3, 38, lobbyToSU);

        backHallway.setImageAtGrid(3, 0, backHLToSU);
        backHallway.setImageAtGrid(3, 12, backHLToGarden);
        backHallway.setImageAtGrid(3, 32, backHLToEH);

        stairwayUp.setImageAtGrid(3, 0, stairwayUToLobby);
        stairwayUp.setImageAtGrid(3, 15, stairwayUToSD);
        stairwayUp.setImageAtGrid(3, 32, stairwayUToBHL);

        //Designs
        this.nenaRoom.setImageAtGrid(3, 8, new Sprite("DogTrash.png"));
        this.roomHallway.setImageAtGrid(3, 3, new Sprite("door.png"));
        this.roomHallway.setImageAtGrid(3, 9, new Sprite("door.png"));
        this.roomHallway.setImageAtGrid(3, 14, new Sprite("door.png"));
        this.roomHallway.setImageAtGrid(3, 20, new Sprite("door.png"));
        this.roomHallway.setImageAtGrid(3, 26, new Sprite("door.png"));
        this.roomHallway.setImageAtGrid(3, 31, new Sprite("door.png"));
        this.upperFrontHallway.setImageAtGrid(3, 7, new Sprite("door.png"));
        this.upperFrontHallway.setImageAtGrid(3, 13, new Sprite("door.png"));
        this.upperFrontHallway.setImageAtGrid(3, 19, new Sprite("door.png"));
        this.upperFrontHallway.setImageAtGrid(3, 25, new Sprite("door.png"));
        this.upperBackHallway.setImageAtGrid(3, 7, new Sprite("door.png"));
        this.upperBackHallway.setImageAtGrid(3, 13, new Sprite("door.png"));
        this.upperBackHallway.setImageAtGrid(3, 19, new Sprite("door.png"));
        this.upperBackHallway.setImageAtGrid(3, 25, new Sprite("door.png"));
        this.stairwayDown.setImageAtGrid(3, 27, new Sprite("Reff.png"));
        this.stairwayDown.setImageAtGrid(3, 30, new Sprite("Waters2.png"));
        this.stairwayDown.setImageAtGrid(3, 6, new Sprite("door.png"));
        this.stairwayDown.setImageAtGrid(3, 24, new Sprite("DogTrash.png"));
        this.emptyHallway.setImageAtGrid(2, 30, new Sprite("Hydrant.png"));
        this.emptyHallway.setImageAtGrid(2, 27, new Sprite("Occupants.png"));
        this.emptyHallway.setImageAtPixel(1490, 430, new Sprite("Reff.png"));
        this.emptyHallway.setImageAtPixel(1750, 430, new Sprite("Waters2.png"));
        this.emptyHallway.setImageAtGrid(3, 16, new Sprite("door.png"));
        this.emptyHallway.setImageAtGrid(3, 10, new Sprite("door.png"));
        this.emptyHallway.setImageAtGrid(3, 4, new Sprite("door.png"));
        this.emptyHallway.setImageAtGrid(3, 28, new Sprite("DogTrash.png"));

        this.mainLobby.setImageAtPixel(1700, 430, new Sprite("Waters.png"));
        this.mainLobby.setImageAtGrid(3, 19, new Sprite("Coffeee.png"));
        this.mainLobby.setImageAtGrid(3, 32, new Sprite("Microwave.png"));
        this.mainLobby.setImageAtPixel(200, 400, new Sprite("Frames.png"));
        this.backHallway.setImageAtGrid(3, 6, new Sprite("door.png"));
        this.backHallway.setImageAtGrid(3, 16, new Sprite("door.png"));
        this.backHallway.setImageAtGrid(3, 22, new Sprite("door.png"));
        this.backHallway.setImageAtGrid(3, 28, new Sprite("door.png"));
        this.stairwayUp.setImageAtGrid(3, 4, new Sprite("door.png"));
        this.stairwayUp.setImageAtGrid(3, 10, new Sprite("door.png"));
        this.stairwayUp.setImageAtGrid(3, 26, new Sprite("door.png"));
        default:
            break;  
        }
    }

    public void reset() {
        death.startSound(); 
        loadImages(Game.GAME_RESET);
        lightManager.doDarkenEffect();
        player.emptyInventory();
        player.resetStamina();
        userCommands.setMoveRightToggle(false);
        userCommands.setMoveLeftToggle(false);
        userCommands.setRunningToggle(false);
        userCommands.setPlayerFlashlightToggle(false);
        this.spawnedClyde = false ;
        this.spawnedHansen = false ;
        this.spawnedNeyro = false;      
        this.spawnedJulia = false ;
        this.spawnedMM = false ;
        
    }


    public void checkGameEvents() {

        //BOOKS
        if (player.getInventory().size() == 0 && !placedBook[0]) {
            randomLocation = allLocations[generateRandomLocation()];
            mainLobby.setImageAtGrid(2, generateRandomPosition(randomLocation), coloredBooks[0]);
            placedBook[0] = true;    
        }
        if (player.getInventory().size() == 1 && !placedBook[1]) {
            randomLocation = allLocations[generateRandomLocation()];
            mainLobby.setImageAtGrid(2, generateRandomPosition(randomLocation), coloredBooks[1]);
            placedBook[1] = true;
        }
        if (player.getInventory().size() == 2 && !placedBook[2]) {
            randomLocation = allLocations[generateRandomLocation()];
            mainLobby.setImageAtGrid(2, generateRandomPosition(randomLocation), coloredBooks[2]);
            placedBook[2] = true;
        }
        if (player.getInventory().size() == 3 && !placedBook[3]) {
            randomLocation = allLocations[generateRandomLocation()];
            mainLobby.setImageAtGrid(2, generateRandomPosition(randomLocation), coloredBooks[3]);
            placedBook[3] = true;

        }
        if (player.getInventory().size() == 4 && !placedBook[4]) {
            for (Location location: allLocations) {
                ArrayList<Ghost> ghosts = new ArrayList<Ghost>();  
                for (Entity entity: location.getEntities()) {
                    if (entity instanceof VampireGhost || entity instanceof RunningGhost || entity instanceof FlyingGhost || entity instanceof HeadlessGhost) {
                        ghosts.add((Ghost) entity);
                    }
                }

                for (Ghost ghost: ghosts) {
                    ghost.setVisible(false);
                    location.getEntities().remove(ghost);
                }
            }
            ambience.stopSound();
            music.stopSound();
            mainLobby.setImageAtGrid(2, 5, coloredBooks[4]);
            mainLobby.setImageAtGrid(2, 10, new CollectibleEntity("room14key.png", "key"));
            placedBook[4] = true;
        }    
        if (player.getInventory().size() == 6 && !end) {
            FinaleGhost mm = null;
            OUTER_LOOP: for (Location location: allLocations) {
                for (Entity entity: location.getEntities()) {
                    if (entity instanceof FinaleGhost) {
                        mm = (FinaleGhost) entity;
                        break OUTER_LOOP;
                    }  
                }
            }

            if (mm != null) {
                muntingMonster.setVisible(false);
                currentLocation.getEntities().remove(muntingMonster);
                spawnedMM =  false ;

            }
            currentLocation.setImageAtGrid(3, 10, new Poy());
            lightManager.lightUp() ;
            congrats.startSound();
            
            end = true ;
        }    
        if (player.getInventory().size() == 6) {
            poynoise.startProximity(500, player.getHitbox().x, 800);
        }    

    



        //GHOSTS
        if(player.getInventory().size() == 1 && !spawnedHansen) {
            for (Location location: allLocations) {
                location.setImageAtGrid(2, 1, new FlyingGhost(2000, 0, 6, 3));       
            }
            this.spawnedHansen = true ;
        }

        if(player.getInventory().size() == 2 && !spawnedClyde) {
            for (Location location: allLocations) {
                location.setImageAtGrid(3, 4, new HeadlessGhost(2000, 5));
            }
            this.spawnedClyde = true ;
        }

        if (random.nextInt(200) < 1 && !spawnedNeyro && player.getInventory().size() == 3) {
            int direction = (random.nextInt(2) == 0) ? 1: -1;     
            currentLocation.setImageAtPixel(player.getRect().x + direction * 500, 450, new VampireGhost(player)); 
            this.spawnedNeyro = true;
        }    

        if (random.nextInt(200) < 1 && !spawnedJulia && player.getInventory().size() == 3) {
            int direction = (random.nextInt(2) == 0) ? 1: -1;     
            currentLocation.setImageAtPixel(player.getRect().x + direction * 500, 450, new RunningGhost(player)); 
            this.spawnedJulia = true;
        }    
        if (random.nextInt(100) < 1 && !spawnedMM && player.getInventory().size() == 5) {
            muntingMonster = new FinaleGhost(player, random.nextInt(100)) ;
            int direction = (random.nextInt(2) == 0) ? 1: -1;     
            currentLocation.setImageAtPixel(player.getRect().x + direction * 500, 365, muntingMonster); 
            this.spawnedMM = true;
        }   
        if (spawnedMM) {
            mmnoise.startProximity(500, player.getHitbox().x, muntingMonster.getHitbox().x);
        }  


    }

    public void resetNeyroSpawn() {
        this.spawnedNeyro = false;
    }

    public void resetJuliaSpawn() {
        this.spawnedJulia = false;
    }
    public void resetMMSpawn() {
        this.spawnedMM = false;
    }

    public int generateRandomLocation() {
        int rand = random.nextInt(allLocations.length);
        return rand;
    }
    public int generateRandomPosition(Location location) {
        int rand = random.nextInt(location.getColumns()); 
        return rand;
    }



    public void initialize() {
        this.keyInput = new KeyInputs(this, player);
        this.addKeyListener(keyInput);
        this.userCommands = new UserCommands();
        this.statemanager = new StateManager(this, player);
        this.camera = new Camera(this, player);
        this.lightManager = new LightManager(this, player);
        this.lightManager.doDarkenEffect();
        this.collisionManager = new CollisionManager(this, player);
    }

    public UserCommands getUserCommands() {
        return this.userCommands;
    }

    public Location getGameLocation() {
        return this.currentLocation;
    }

    public Location[] getLocations() {
        return this.allLocations;
    }

    public void setGameLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }


    /** 
     * @method: startGameLoop()
     * @desciption:
     *      Calls run() method to start the game loop.
     */
    public void startGameLoop() {
        this.gameLoopThread = new Thread(this);
        this.ambience.startSound() ;
        this.ambience.startLoop() ;
        this.music.startSound();
        this.music.startLoop() ;
        this.music.setVolume(0.10f);
        gameLoopThread.start();                           
    }


    /** 
     * @method: run()
     * @desciption:
     *      Runs the game loop. 
     *      Game Loop checks the amount of time elapsed per 
     *      frame and update, and updates the display and game 
     *      states, respectively, if it reached the time delay
     *      per frame and state updates. 
     */
    @Override
    public void run() {
        long currentTime;
        long previousTime = System.nanoTime();
        long lastCheck = System.nanoTime();
 
        double deltaUPS = 0;
        double deltaFPS = 0;

        int updates = 0;
        int frames = 0;

        while (true) {
            currentTime = System.nanoTime();
            deltaUPS += (currentTime - previousTime) / TIME_PER_UPDATE;
            deltaFPS += (currentTime - previousTime) / TIME_PER_FRAME;
            previousTime = currentTime;

            if (deltaUPS >= 1) {
                checkGameEvents();
                keyInput.updateKeyInputCooldown();
                statemanager.updatePlayerState();
                camera.updateFocus();
                camera.move();
                statemanager.updateGhostPathing();
                statemanager.updateGhostStates();
                statemanager.updatePlayerAnimationType();
                collisionManager.managePlayerEntityCollisions();
                collisionManager.managePlayerBorderCollisions();
                deltaUPS--;
                updates++;
                
            }
            
            if (deltaFPS >= 1) {
                repaint();
                player.updateAnimationFrames();
                statemanager.updateGhostAnimationFrames();
                deltaFPS--;
                frames++;
            }

            if (System.nanoTime() - lastCheck >= SECONDS_IN_NANOSECONDS) {
                lastCheck = System.nanoTime();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
            
        }
    }

    public void setShouldDrawBatteryImage(int value) {
        this.shouldDrawBatteryImage = value  ;
    }

    @Override 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(currentLocation.getImage(), currentLocation.getPosX(), currentLocation.getPosY(), null);

        
        for (Sprite sprite: currentLocation.getSprites()) {
            if (sprite.isVisible()){
                g2d.drawImage(sprite.getImage(), sprite.getPosX(), sprite.getPosY(), null);
            }
        }   


        g2d.drawImage(player.getImage(), player.getPosX(), player.getPosY(), null);

        lightManager.manageBackgroundLighting();
        g2d.drawImage(lightManager.getBackgroundLightDisplay(), 0, 0, null);
        InputStream os = getClass().getResourceAsStream("/images/phoneOFF.png");
        try {
            g2d.drawImage(ImageIO.read(os), 1400, 800, null);
        } catch (IOException e) {
            e.printStackTrace(); // Optionally print the stack trace for debugging
        }

        switch (shouldDrawBatteryImage) {
            case 1: //12000 -- 10800
            // Logic for when shouldDrawBatteryImage is 1
            is = getClass().getResourceAsStream("/images/oneBatt.png");
            try {
                g2d.drawImage(ImageIO.read(is), 1670,810, null);
            } catch (IOException e) {
                e.printStackTrace(); // Optionally print the stack trace for debugging
            }
            break;
        case 2: // 10800 -- 9600
            is = getClass().getResourceAsStream("/images/twoBatt.png");
            // Logic for when shouldDrawBatteryImage is 2
            try {
                g2d.drawImage(ImageIO.read(is), 1670, 810, null);
            } catch (IOException e) {
                e.printStackTrace(); // Optionally print the stack trace for debugging
            }
            break;
        case 3: // 9600 -- 8400
            // Logic for when shouldDrawBatteryImage is 3
            is = getClass().getResourceAsStream("/images/threeBatt.png");
            try {
                g2d.drawImage(ImageIO.read(is), 1670, 810, null);
            } catch (IOException e) {
                e.printStackTrace(); // Optionally print the stack trace for debugging
            }
            break;
        case 4: // 8400 -- 7200
            // Logic for when shouldDrawBatteryImage is 4
            is = getClass().getResourceAsStream("/images/fourBatt.png");
            try {
                g2d.drawImage(ImageIO.read(is), 1670, 810, null);
            } catch (IOException e) {
                e.printStackTrace(); // Optionally print the stack trace for debugging
            }
            break;
        case 5: // 7200 -- 6000
            // Logic for when shouldDrawBatteryImage is 5
            is = getClass().getResourceAsStream("/images/fiveBatt.png");
            try {
                g2d.drawImage(ImageIO.read(is), 1670, 810, null);
            } catch (IOException e) {
                e.printStackTrace(); // Optionally print the stack trace for debugging
            }
            break;
        case 6: // 6000 -- 4800
            // Logic for when shouldDrawBatteryImage is 6
            is = getClass().getResourceAsStream("/images/sixBatt.png");
            try {
                g2d.drawImage(ImageIO.read(is), 1670, 810, null);
            } catch (IOException e) {
                e.printStackTrace(); // Optionally print the stack trace for debugging
            }
            break;
        case 7: // 4800 -- 3600
            // Logic for when shouldDrawBatteryImage is 7
            is = getClass().getResourceAsStream("/images/sevenBatt.png");
            try {
                g2d.drawImage(ImageIO.read(is), 1670, 810, null);
            } catch (IOException e) {
                e.printStackTrace(); // Optionally print the stack trace for debugging
            }
            break;
        case 8: // 3600 -- 2400
            // Logic for when shouldDrawBatteryImage is 8
            is = getClass().getResourceAsStream("/images/eightBatt.png");
            try {
                g2d.drawImage(ImageIO.read(is), 1670, 810, null);
            } catch (IOException e) {
                e.printStackTrace(); // Optionally print the stack trace for debugging
            }
            break;
        case 9: // 2400 -- 1200
            // Logic for when shouldDrawBatteryImage is 9
            is = getClass().getResourceAsStream("/images/nineBatt.png");
            try {
                g2d.drawImage(ImageIO.read(is), 1670, 810, null);
            } catch (IOException e) {
                e.printStackTrace(); // Optionally print the stack trace for debugging
            }
            break;
        case 10: // 2400 -- 1200
            // Logic for when shouldDrawBatteryImage is 10
            is = getClass().getResourceAsStream("/images/deadBatt.png");
            try {
                g2d.drawImage(ImageIO.read(is), 1670, 810, null);
            } catch (IOException e) {
                e.printStackTrace(); // Optionally print the stack trace for debugging
            }
            break;
    
            default:
            is = getClass().getResourceAsStream("/images/oneBatt.png");
            try {
                g2d.drawImage(ImageIO.read(is), 1670,810, null);
            } catch (IOException e) {
                e.printStackTrace(); // Optionally print the stack trace for debugging
            }
            break;
            }
    }
}