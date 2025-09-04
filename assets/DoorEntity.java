package assets;

public class DoorEntity extends Entity {
    private Location location;
    private DoorEntity linkedDoor;
    
    public DoorEntity(Location location,  String filename) {
        this(0, 0, location, filename);   
    }
    public DoorEntity(int posX, int posY, Location location, String filename) {
        super(posX, posY, filename);
        this.location = location;
    }

    public void linkToDoor(DoorEntity otherDoor) {
        this.linkedDoor = otherDoor;
    }
    
    public Location getLocation() {
        return this.location;
    }
    public DoorEntity getLinkedDoor() {
        return this.linkedDoor;
    }
}