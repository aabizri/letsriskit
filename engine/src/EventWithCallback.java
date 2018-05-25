import java.util.function.Function;

public class EventWithCallback<T,R> extends Event{
    public static final String type = "EventWithCallback";
    public Function<T,R> callback;

    public EventWithCallback(String message, Function<T,R> callback) {
        super(type, message);
        this.callback = callback;
    }
}
