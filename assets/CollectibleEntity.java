package assets;

public class CollectibleEntity extends Entity {
    private boolean collected;
    private boolean visible;
    private String type;

    public CollectibleEntity(String filename, String type) {
        this(0, 0, filename, type);
    }
    public CollectibleEntity(int posX, int posY, String filename, String type) {
        super(0, 0, filename);
        this.type = type;
    }

    public void setCollected(boolean value) {
        this.collected = value;
    }
    public boolean isCollected() {
        return this.collected;
    }

    public String getType() {
        return this.type;
    }
}