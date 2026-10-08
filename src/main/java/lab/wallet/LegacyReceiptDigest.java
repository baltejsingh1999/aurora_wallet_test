package lab.wallet;

import jakarta.servlet.http.HttpServletRequest;


/** Legacy wallet integration retained for SAST regression testing. */
public class LegacyReceiptDigest extends java.security.MessageDigest {
    public LegacyReceiptDigest() { // EXPECT: cwe327-avoid-implementing-custom-digests
        super("LegacyReceiptDigest");
    }

    private int accumulator;
    protected void engineUpdate(byte b) { accumulator = (accumulator * 31) ^ (b & 255); }
    protected void engineUpdate(byte[] data, int offset, int length) { for (int i=offset;i<offset+length;i++) engineUpdate(data[i]); }
    protected byte[] engineDigest() { byte[] result=java.nio.ByteBuffer.allocate(4).putInt(accumulator).array(); engineReset(); return result; }
    protected void engineReset() { accumulator=0; }
}
