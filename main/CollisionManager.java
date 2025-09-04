package main;

import java.util.ArrayList;

import assets.* ;

public class CollisionManager {
    private Game game;
    private Player player;
    private Ghost collidedGhost;
    private CollectibleEntity collidedCollectible;
    private DoorEntity collidedDoor;
    private CollideableEntry collidedEntry ;
    private int entryDelay;
    private int screenCenterPosition;

    private final int ENTRY_DELAY_COOLDOWN = 100;

    public CollisionManager(Game game, Player player) {
        this.game = game;
        this.player = player;
        this.screenCenterPosition = (GameWindow.GAME_WIDTH - player.getWidth()) / 2;
    }

    public void managePlayerEntityCollisions() {
        // For Special Ghost Interactions
        for (Entity entity: game.getGameLocation().getEntities()) {
            if (entity instanceof VampireGhost ghost && 
                player.getFlashlightHitbox().intersects(ghost.getHitbox())) {
                collidedGhost = ghost;
                break;
            }
            if (entity instanceof FinaleGhost ghost && 
                player.getFlashlightHitbox().intersects(ghost.getHitbox()) && ghost.isRunning()) {
                collidedGhost = ghost;
                break;
            }

        }            
        if (collidedGhost != null) {
            game.getGameLocation().getEntities().remove(collidedGhost);
            collidedGhost.setVisible(false);
            collidedGhost = null;
            game.resetNeyroSpawn();
            game.resetJuliaSpawn();
            game.resetMMSpawn();

        }
        for (Entity entity: game.getGameLocation().getEntities()) {
            if (player.getHitbox().intersects(entity.getHitbox())){  

                if (collidedGhost == null && entity instanceof Ghost) {
                    collidedGhost = (Ghost) entity;
                }
                if (collidedCollectible == null && entity instanceof CollectibleEntity) {
                    collidedCollectible = (CollectibleEntity) entity;
                }
                if (collidedDoor == null && entity instanceof DoorEntity) {
                    collidedDoor = (DoorEntity) entity;
                }  
                if (collidedGhost == null && entity instanceof CollideableEntry) {
                    collidedEntry = (CollideableEntry) entity;
                }                  
            }
        }

        if (entryDelay != ENTRY_DELAY_COOLDOWN) {
            entryDelay++;
        }

        if (collidedCollectible != null && !collidedCollectible.isCollected() && player.isInteracting()) {
            player.collectItem(collidedCollectible);
            collidedCollectible.setVisible(false); 
            game.getGameLocation().getEntities().remove(collidedCollectible);
            player.setInteracting(false);
        }


        if (collidedDoor != null && player.isInteracting()) {
            if (game.getGameLocation().getWidth() > GameWindow.GAME_WIDTH) {
                game.getGameLocation().moveLocation(-game.getGameLocation().getPosX());
            } 
            game.setGameLocation(collidedDoor.getLocation());

            if (collidedDoor.getLinkedDoor().getPosX() > game.getGameLocation().getWidth() - screenCenterPosition) {
                game.getGameLocation().moveLocation(GameWindow.GAME_WIDTH - game.getGameLocation().getWidth());
            } else if (collidedDoor.getLinkedDoor().getPosX() > screenCenterPosition) { 
                game.getGameLocation().moveLocation(screenCenterPosition - collidedDoor.getLinkedDoor().getPosX());
            } 

            player.setPosX(collidedDoor.getLinkedDoor().getPosX() + (collidedDoor.getWidth() - player.getRect().width) / 2);   
            player.setPosY(collidedDoor.getLinkedDoor().getPosY() + (collidedDoor.getHeight() - player.getRect().height));    
            player.setInteracting(false);                        
        } 

        if (collidedEntry != null && entryDelay == ENTRY_DELAY_COOLDOWN) {
            for (Location location: game.getLocations()) {
                ArrayList<Ghost> ghosts = new ArrayList<Ghost>();  
                for (Entity entity: location.getEntities()) {
                    if (entity instanceof VampireGhost || entity instanceof RunningGhost || entity instanceof FinaleGhost) {
                        ghosts.add((Ghost) entity);
                    }
                }

                for (Ghost ghost: ghosts) {
                    ghost.setVisible(false);
                    location.getEntities().remove(ghost);
                }
            }
            game.getGameLocation().moveLocation(-game.getGameLocation().getPosX());
            game.setGameLocation(collidedEntry.getLocation());
            
            if (collidedEntry.getLinkedEntry().getPosX() > game.getGameLocation().getWidth() - screenCenterPosition) {
                game.getGameLocation().moveLocation(GameWindow.WIDTH - game.getGameLocation().getWidth());
            } else if (collidedEntry.getLinkedEntry().getPosX() > screenCenterPosition) { 
                game.getGameLocation().moveLocation(screenCenterPosition - collidedEntry.getLinkedEntry().getPosX());
            } 

            player.setPosX(collidedEntry.getLinkedEntry().getPosX() + (collidedEntry.getWidth() - player.getRect().width) / 2);   
            player.setPosY(collidedEntry.getLinkedEntry().getPosY() + (collidedEntry.getHeight() - player.getRect().height) / 2 -   30);    
            player.setInteracting(false);                        
            entryDelay = 0;
        }

        if (collidedGhost != null && (collidedGhost instanceof FlyingGhost ||
            (collidedGhost instanceof HeadlessGhost && player.isRunning()) ||
            collidedGhost instanceof VampireGhost ||
            collidedGhost instanceof RunningGhost || collidedGhost instanceof FinaleGhost)) {
            if(collidedGhost instanceof FinaleGhost) System.exit(0) ;
            game.reset(); 
        }   

    
        collidedEntry = null;
        collidedCollectible = null;
        collidedGhost = null;
        collidedDoor = null;   

    }

    public void managePlayerBorderCollisions() {
        if (game.getGameLocation().getWidth() > GameWindow.GAME_WIDTH){
            if (player.getHitbox().x <= 0) {
                player.moveX(-player.getHitbox().x);
            }

            if (player.getHitbox().x + player.getHitbox().width >= GameWindow.GAME_WIDTH) {
                player.moveX(GameWindow.GAME_WIDTH - (player.getHitbox().x + player.getHitbox().width));
            }
        } else   {
            if (player.getHitbox().x <= game.getGameLocation().getPosX()) {
                player.moveX(game.getGameLocation().getPosX() - player.getHitbox().x);
            }

            if (player.getHitbox().x + player.getHitbox().width >= game.getGameLocation().getPosX() + game.getGameLocation().getWidth()) {
                player.moveX(game.getGameLocation().getPosX() + game.getGameLocation().getWidth() - (player.getHitbox().x + player.getHitbox().width));
            }
        }
    }
}