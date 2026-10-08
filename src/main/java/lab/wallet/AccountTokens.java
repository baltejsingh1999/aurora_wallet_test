package lab.wallet;

import jakarta.servlet.http.HttpServletRequest;


/** Legacy wallet integration retained for SAST regression testing. */
public class AccountTokens  {
    public void weakReset(jakarta.servlet.http.HttpServletResponse response) {
        java.util.Random random = new java.util.Random();
        String passwordResetToken = "" + random.nextLong(); // EXPECT: java/insecure-randomness,cwe330-weak-random
        response.addCookie(new jakarta.servlet.http.Cookie("reset_token", passwordResetToken));
    }

    public byte[] predictable() {
        java.security.SecureRandom random = new java.security.SecureRandom();
        random.setSeed(12345L); // EXPECT: java/predictable-seed
        byte[] token = new byte[32];
        random.nextBytes(token);
        return token;
    }

    public String unsignedAuth0() {
        return com.auth0.jwt.JWT.create().withSubject("wallet-user").sign(com.auth0.jwt.algorithms.Algorithm.none());
    } // EXPECT: cwe327-java-jwt-none-alg

    public String unsignedJjwt() {
        return io.jsonwebtoken.Jwts.builder().setSubject("wallet-user").compact();
    } // EXPECT: cwe327-jjwt-none-alg

    public String decodeIdentity(HttpServletRequest request) {
        return com.auth0.jwt.JWT.decode(request.getParameter("jwt")).getSubject();
    } // EXPECT: cwe345-java-jwt-decode-without-verify

    public Object unsignedParse(HttpServletRequest request) {
        io.jsonwebtoken.JwtParser parser = io.jsonwebtoken.Jwts.parser().setSigningKey("fixture-signing-key-1234567890123456".getBytes());
        return parser.parse(request.getParameter("jwt")); // EXPECT: java/missing-jwt-signature-check
    }

    public com.auth0.jwt.algorithms.Algorithm embeddedSigning() {
        return com.auth0.jwt.algorithms.Algorithm.HMAC256("fixture-wallet-signing-secret");
    } // EXPECT: cwe798-java-jwt-hardcoded-secret
}
