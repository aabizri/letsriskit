package engine;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class CantAttackException extends CantMoveException {
    CantAttackException(@NotNull Movable subject, @NotNull Territory destination, @Nullable String info) {
        super(subject, destination, "");
    }

    static private String formatInfo(String info) {
        return "Cannot Attack: " + info;
    }
}
