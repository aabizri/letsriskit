/**
 * Should be thread-safe
 *
 * @param <T>
 * @param <R>
 */
public interface EventWithReaction<T, R> extends Event {
    R react(T param);
}
