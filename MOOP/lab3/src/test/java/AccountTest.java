import A5.Account;
import A5.TransferException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AccountTest {

    private Account account1;
    private Account account2;

    public final static double eps = 10e-6;

    @Before
    public void init() {
        account1 = new Account(232, "Oleg");
        account2 = new Account(432, "Olga");
        account1.incBalance(150);
        account2.incBalance(190);
    }

    @Test
    public void testIncBalancesInAFewThreads() throws InterruptedException {
        double oldBalance = account1.getBalance();
        Thread t1 = new Thread(() -> {
            incBalance(1_000_000);
        });
        Thread t2 = new Thread(() -> {
            incBalance(500_000);
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        Assert.assertEquals(account1.getBalance(), oldBalance + 15_000_000, eps);
    }

    private void incBalance(int times) {
        for (int i = 0; i < times; i++){
            account1.incBalance(10);
        }
    }

    @Test
    public void testLowBalancesInAFewThreads() throws InterruptedException {
        double oldBalance = account1.getBalance();
        account1.incBalance(60_000_000);
        Thread t1 = new Thread(() -> {
            lowBalance(4_000_000);
        });
        Thread t2 = new Thread(() -> {
            lowBalance(2_000_000);
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        Assert.assertEquals(account1.getBalance(), oldBalance, eps);
    }

    private void lowBalance(int times) {
        for (int i = 0; i < times; i++){
            try {
                account1.lowBalance(10);
            } catch (TransferException e) {
                e.printStackTrace();
            }
        }
    }


    @Test(expected = TransferException.class)
    public void lowBalanceExceptionTest() throws TransferException {
        Account account = new Account(34,"Alex");
        account.lowBalance(10);
    }

    @Test
    public void paymentTest() throws TransferException {
        double oldB2 = account2.getBalance();
        account1.incBalance(10);
        account1.new Payment(10, account2).run();
        Assert.assertEquals(oldB2 + 10, account2.getBalance(), 0.0001);
    }

    @Test
    public void receiptTest() throws TransferException {
        double oldB1 = account1.getBalance();
        account2.incBalance(10);
        account1.new Receipt(10, account2).run();
        Assert.assertEquals(account1.getBalance(), oldB1 + 10, 0.0001);
    }

    @Test
    public void withdrawalTest() throws TransferException {
        double oldB1 = account1.getBalance();
        account1.new Withdrawal(10).run();
        Assert.assertEquals(account1.getBalance(), oldB1 - 10, 0.0001);
    }

    @Test
    public void createAndRunFewOperations() throws TransferException {
        List<Account.Operation> opList = new ArrayList<>();
        account1.incBalance(10);
        opList.add(account2.new Receipt(10, account1));
        opList.add(account2.new Withdrawal(5));
        opList.add(account2.new Payment(5, account1));
        for (Account.Operation operation : opList) {
            operation.run();
        }
    }
}
