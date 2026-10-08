package lab.wallet;

import jakarta.servlet.http.HttpServletRequest;


/** Legacy wallet integration retained for SAST regression testing. */
public class ReceiptCryptography  {
    public byte[] legacyCipher(byte[] receipt, java.security.Key key) throws Exception {
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("DES/ECB/PKCS5Padding"); // EXPECT: java/weak-cryptographic-algorithm
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(receipt);
    }

    public javax.crypto.Cipher blowfish() throws Exception {
        return javax.crypto.Cipher.getInstance("Blowfish");
    } // EXPECT: cwe327-use-of-blowfish

    public byte[] nullEncryption(byte[] receipt) throws Exception {
        javax.crypto.NullCipher cipher = new javax.crypto.NullCipher(); // EXPECT: cwe327-no-null-cipher
        return cipher.doFinal(receipt);
    }

    public byte[] gcmReceipt(byte[] receipt) throws Exception {
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding");
        javax.crypto.spec.GCMParameterSpec spec = new javax.crypto.spec.GCMParameterSpec(128, "fixtureNonce".getBytes(java.nio.charset.StandardCharsets.UTF_8)); // EXPECT: cwe323-gcm-detection,cwe323-gcm-nonce-reuse
        javax.crypto.spec.SecretKeySpec key = new javax.crypto.spec.SecretKeySpec("fixture-key-1234".getBytes(java.nio.charset.StandardCharsets.UTF_8), "AES");
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key, spec);
        return cipher.doFinal(receipt);
    }

    public byte[] staticIv(byte[] receipt, java.security.Key key) throws Exception {
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5Padding");
        javax.crypto.spec.IvParameterSpec iv = new javax.crypto.spec.IvParameterSpec(new byte[16]);
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key, iv); // EXPECT: java/static-initialization-vector
        return cipher.doFinal(receipt);
    }

    public javax.crypto.spec.SecretKeySpec embeddedKey() {
        return new javax.crypto.spec.SecretKeySpec("fixture-key-1234".getBytes(), "AES");
    } // EXPECT: cwe798-hardcoded-secret-key-spec

    public byte[] rsaReceipt(byte[] receipt, java.security.Key key) throws Exception {
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("RSA/ECB/PKCS1Padding"); // EXPECT: java/rsa-without-oaep
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(receipt);
    }

    public java.security.KeyPair smallKey() throws Exception {
        java.security.KeyPairGenerator generator = java.security.KeyPairGenerator.getInstance("RSA");
        generator.initialize(512); // EXPECT: java/insufficient-key-size
        return generator.generateKeyPair();
    }

    public javax.crypto.Cipher configuredCipher() throws Exception {
        java.util.Properties properties = new java.util.Properties();
        try (java.io.InputStream input = getClass().getResourceAsStream("/legacy-crypto.properties")) { properties.load(input); }
        String transformation = properties.getProperty("receipt.cipher");
        return javax.crypto.Cipher.getInstance(transformation);
    } // EXPECT: cwe327-ecb-cipher
}
