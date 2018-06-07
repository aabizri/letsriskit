import org.jetbrains.annotations.NotNull;

public class NoUnitOnTerritoryException extends Exception {
    private Territory subject;

    NoUnitOnTerritoryException(@NotNull Territory t) {
        assert (t != null);
        this.subject = t;
    }

    @NotNull
    public Territory getSubject() {
        return subject;
    }

    @Override
    @NotNull
    public String getMessage() {
        return "No territory on Territory: " + subject.getName();
    }
}
