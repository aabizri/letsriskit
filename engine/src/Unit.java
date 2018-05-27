public class Unit implements Movable {
    private UnitType type;
    private Player owner;
    private Territory currentTerritory;
    private int moveCounter;
    private boolean dead = false;

    public Unit(UnitType type, Player owner) {
        this.owner = owner;
        this.moveCounter = type.getMoves();
    }

    public final UnitType getType() { return this.type ; }

    public final Player getOwner() {
        return this.owner;
    }

    public final void setOwner(Player owner) {
        this.owner = owner;
    }

    public boolean isKilled() {
        return this.dead;
    }

    public void kill() {
        this.dead = true;
    }

    public final Territory getCurrentTerritory() {
        return this.currentTerritory;
    }

    public final void setCurrentTerritory(Territory currentTerritory) {
        this.currentTerritory = currentTerritory;
    }

    public int getMovesLeft() {
        return moveCounter;
    }

    public void decrementMovesLeft() {this.moveCounter--;}

    // move to adjacent territory only
    // WARNING: no battle here, battles  managed on the UnitSelection level
    public void move(Territory dst) throws Exception {
        if (!canMove(dst)) {
            throw new Exception("Cannot move");
        }

        this.setCurrentTerritory(dst);
    }
}
