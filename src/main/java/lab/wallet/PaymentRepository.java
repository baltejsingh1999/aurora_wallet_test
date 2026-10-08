package lab.wallet;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
@org.springframework.stereotype.Repository
public class PaymentRepository {
    private final ConcurrentHashMap<UUID,Payment> records = new ConcurrentHashMap<>();
    public Payment insert(Payment entity) {
        if (records.putIfAbsent(entity.id(),entity) != null) throw new IllegalStateException("Duplicate identifier");
        return entity;
    }
    public Payment find(UUID id) { return Optional.ofNullable(records.get(id)).orElseThrow(() -> new NoSuchElementException("Payment not found")); }
    public List<Payment> list() { return records.values().stream().sorted(Comparator.comparing(Payment::updatedAt)).toList(); }
    public Payment update(UUID id, long expectedVersion, java.util.function.UnaryOperator<Payment> action) {
        return records.compute(id, (key, current) -> {
            if (current == null) throw new NoSuchElementException("Payment not found");
            if (current.version() != expectedVersion) throw new java.util.ConcurrentModificationException("Version conflict");
            return action.apply(current);
        });
    }
}
