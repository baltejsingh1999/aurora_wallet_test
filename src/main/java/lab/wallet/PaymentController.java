package lab.wallet;
import java.util.*;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService service;
    public PaymentController(PaymentService service) { this.service=service; }
    public record CreateRequest(String account, String merchant, int units) {}
    public record TransitionRequest(long expectedVersion) {}
    @PostMapping
    @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public Payment create(@RequestBody CreateRequest body) { return service.create(body.account(),body.merchant(),body.units()); }
    @GetMapping public List<Payment> list() { return service.list(); }
    @GetMapping("/{id}") public Payment get(@PathVariable UUID id) { return service.get(id); }
    @PostMapping("/{id}/settle") public Payment settle(@PathVariable UUID id, @RequestBody TransitionRequest body) { return service.settle(id,body.expectedVersion()); }
}
