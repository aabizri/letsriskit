package engine;

import java.util.Optional;

interface Movable {
    Territory getCurrentTerritory();

    void setCurrentTerritory(Territory dst);

    int getMovesLeft();

    boolean canMove(Territory dst);

    Optional<Battle> move(Territory dst) throws CantMoveException;
}
