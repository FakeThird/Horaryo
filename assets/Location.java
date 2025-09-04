package assets;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Location extends Sprite {
    private int rows;
    private int columns;
    private int leftBorder;
    private int rightBorder;

    private ArrayList<Sprite> spriteList;
    private ArrayList<Entity> entityList;
    private Rectangle[][] gridMap;

    private final int TILE_SIZE = 80;

    public Location(String filename) {
        this(0, 0, filename);
    }
    public Location(int posX, int posY, String filename) {
        super(posX, posY, filename);
        this.rows = getHeight() / TILE_SIZE;
        this.columns = getWidth() / TILE_SIZE;
        this.leftBorder = 0;
        this.rightBorder = getWidth();
        this.spriteList = new ArrayList<Sprite>();
        this.entityList = new ArrayList<Entity>();
        this.gridMap = new Rectangle[rows][columns]; 

        int offsetX = getPosX();
        int offsetY = getPosY() + (getHeight() - rows * TILE_SIZE);
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.gridMap[i][j] = 
                    new Rectangle(j * TILE_SIZE + offsetX, 
                                i * TILE_SIZE + offsetY,
                                TILE_SIZE, TILE_SIZE); 
            }
        }
    }

    public int getColumns() {
        return this.columns;
    }
    public int getBorderLeft() {
        return this.leftBorder;
    }
    
    public int getBorderRight() {
        return this.rightBorder;
    }

    public ArrayList<Sprite> getSprites() {
        return this.spriteList;
    }

    public ArrayList<Entity> getEntities() {
        return this.entityList;
    }
    
    public Rectangle[][] getGrid() {
        return this.gridMap;
    }

    public void setImageAtGrid(int row, int col, Sprite sprite) {
        sprite.setPosX(gridMap[row][col].x + (TILE_SIZE - sprite.getWidth()) / 2);
        sprite.setPosY(gridMap[row][col].y + (TILE_SIZE - sprite.getHeight()));                      
        this.spriteList.add(sprite);
        
        if (sprite instanceof Entity entity) {
            this.entityList.add(entity);
        }
    }

    public void setImageAtPixel(int posX, int posY, Sprite sprite) {
        sprite.setPosX(posX);
        sprite.setPosY(posY);
        this.spriteList.add(sprite);

        if (sprite instanceof Entity entity) {
            this.entityList.add(entity);
        }
    }

    public void moveLocation(int speed) {
        leftBorder += speed;
        rightBorder += speed;
        
        moveX(speed);
        for (Sprite sprite: spriteList) {
            sprite.moveX(speed);
        }

        for (Rectangle[] rectRows: gridMap) {
            for (Rectangle rect: rectRows) {
                rect.x += speed;
            } 
        }
    }
}