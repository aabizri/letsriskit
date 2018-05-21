interface Movable {
    Territory getCurrentTerritory();
    int getMovesLeft();
    void decrementMovesLeft();

    default boolean canMove(Territory dst) {
        return getCurrentTerritory().calculateIsNeighbour(dst) && getMovesLeft() != 0;
    }
}
