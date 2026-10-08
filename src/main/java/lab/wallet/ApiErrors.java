package lab.wallet;
@org.springframework.web.bind.annotation.RestControllerAdvice
public class ApiErrors {
    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
    public java.util.Map<String,String> invalid() { return java.util.Map.of("error", "Invalid request"); }
    @org.springframework.web.bind.annotation.ExceptionHandler(java.util.NoSuchElementException.class)
    @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
    public java.util.Map<String,String> missing() { return java.util.Map.of("error", "Resource not found"); }
    @org.springframework.web.bind.annotation.ExceptionHandler({IllegalStateException.class,java.util.ConcurrentModificationException.class})
    @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.CONFLICT)
    public java.util.Map<String,String> conflict() { return java.util.Map.of("error", "State or version conflict"); }
}
