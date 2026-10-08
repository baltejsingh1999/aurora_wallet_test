package lab.wallet;
import java.time.*;
import java.util.*;
@org.springframework.stereotype.Service
public class PaymentService {
    private final PaymentRepository repository;
    private final Clock clock;
    @org.springframework.beans.factory.annotation.Autowired
    public PaymentService(PaymentRepository repository) { this(repository, Clock.systemUTC()); }
    public PaymentService(PaymentRepository repository, Clock clock) { this.repository = repository; this.clock = clock; }
    public Payment create(String account, String merchant, int units) {
        return repository.insert(new Payment(UUID.randomUUID(), account, merchant, units, Payment.Status.PENDING, 0, clock.instant()));
    }
    public Payment settle(UUID id, long expectedVersion) { return repository.update(id, expectedVersion, current -> current.advance(clock.instant())); }
    public Payment get(UUID id) { return repository.find(id); }
    public List<Payment> list() { return repository.list(); }
}
