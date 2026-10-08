package lab.wallet;
import java.time.Instant;
import java.util.UUID;
public record Payment(UUID id, String account, String merchant, int units, Status status, long version, Instant updatedAt) {
    public enum Status { PENDING, SETTLED, CANCELLED }
    public Payment {
        java.util.Objects.requireNonNull(id);
        java.util.Objects.requireNonNull(status);
        java.util.Objects.requireNonNull(updatedAt);
        if (account == null || account.isBlank() || merchant == null || merchant.isBlank()) throw new IllegalArgumentException("Required fields are blank");
        if (units < 1 || units > 10000) throw new IllegalArgumentException("Units must be 1..10000");
        if (version < 0) throw new IllegalArgumentException("Version must be nonnegative");
    }
    public Payment advance(Instant now) {
        if (status != Status.PENDING) throw new IllegalStateException("Cannot settle a " + status + " payment");
        return new Payment(id, account, merchant, units, Status.SETTLED, version + 1, now);
    }
}
