package assets;

public class CollideableEntry extends Entity {
    private Location location;
    private CollideableEntry linkedEntry;

    public CollideableEntry(Location location, String filename) {
        this(0, 0, location, filename);   
    }
    public CollideableEntry(int posX, int posY, Location location, String filename) {
        super(posX, posY, filename);
        this.location = location;
    }

    public void linkToEntry(CollideableEntry entry) {
        this.linkedEntry = entry;
    }
    
    public Location getLocation() {
        return this.location;
    }
    public CollideableEntry getLinkedEntry() {
        return this.linkedEntry;
    }
}