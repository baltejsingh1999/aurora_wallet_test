package lab.wallet;

import jakarta.servlet.http.HttpServletRequest;


/** Legacy wallet integration retained for SAST regression testing. */
public class PasswordDigest  {
    public byte[] passwordMd5(HttpServletRequest request) throws Exception {
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
        byte[] password = md.digest(request.getParameter("password").getBytes()); // EXPECT: cwe328-use-of-md5
        return password;
    }

    public byte[] commonsMd5(HttpServletRequest request) {
        return org.apache.commons.codec.digest.DigestUtils.getMd5Digest().digest(request.getParameter("password").getBytes());
    } // EXPECT: cwe328-use-of-md5-digest-utils

    public String brokenHex(byte[] receipt) throws Exception {
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(receipt);
        StringBuilder output = new StringBuilder();
        for (byte b : digest) {
            output.append(Integer.toHexString(b & 0xff)); // EXPECT: cwe704-bad-hexa-conversion
        }
        return output.toString();
    }

    public String storePassword(HttpServletRequest request) {
        return hashPassword(request.getParameter("password"));
    } // EXPECT: cwe327-md5-used-as-password

    public String hashPassword(String value) { return org.apache.commons.codec.digest.DigestUtils.md5Hex(value); }
}
