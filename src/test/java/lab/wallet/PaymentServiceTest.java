package lab.wallet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PaymentServiceTest {
    private PaymentService service() { return new PaymentService(new PaymentRepository(),java.time.Clock.fixed(java.time.Instant.parse("2025-01-01T00:00:00Z"),java.time.ZoneOffset.UTC)); }
    @Test void advancesStateAndVersion() {
        var service=service(); var created=service.create("test-account","test-merchant",2);
        var advanced=service.settle(created.id(),0);
        assertEquals(Payment.Status.SETTLED,advanced.status()); assertEquals(1,advanced.version());
        assertEquals(advanced,service.get(created.id()));
    }
    @Test void rejectsStaleWritesWithoutChangingState() {
        var service=service();var created=service.create("a","b",1);
        assertThrows(java.util.ConcurrentModificationException.class,()->service.settle(created.id(),4));
        assertEquals(created,service.get(created.id()));
    }
    @Test void rejectsRepeatTransition() {
        var service=service(); var created=service.create("a","b",1);service.settle(created.id(),0);
        assertThrows(IllegalStateException.class,()->service.settle(created.id(),1));
    }
    @Test void rejectsInvalidInput() { assertThrows(IllegalArgumentException.class,()->service().create("", "b", 0)); }
}
