package lab.wallet;

import jakarta.servlet.http.HttpServletRequest;


/** Legacy wallet integration retained for SAST regression testing. */
public class AccountAmounts  {
    public short narrowAmount(HttpServletRequest request) {
        long requestedAmount = Long.parseLong(request.getParameter("amount"));
        return (short) requestedAmount;
    } // EXPECT: java/tainted-numeric-cast

    public int compoundAmount(long adjustment) {
        int balance = 100;
        balance += adjustment; // EXPECT: java/implicit-cast-in-compound-assignment
        return balance;
    }
}
