package A5;

public class Account {
    private int id;
    private String username;
    private double balance;

    public Account(int id, String username) {
        this.id = id;
        this.username = username;
        this.balance = 0.0;
    }

    public synchronized void lowBalance(double amount) throws TransferException {
        if (this.balance - amount >= 0.0) {
            this.balance -= amount;
        } else {
            throw new TransferException("There is not enough money on the account\n");
        }
    }


    public synchronized void incBalance(double amount) {
        this.balance += amount;
    }

    @FunctionalInterface
    interface Operation {
        void run() throws TransferException;
    }

    abstract class MoneyTransfer implements Operation {
        final double transferAmount;
        boolean initiatorOfOperation;
        Account interactiveAccount;

        private MoneyTransfer(double transferAmount, Account interactiveAccount, boolean initiatorOfOperation) {
            this.interactiveAccount = interactiveAccount;
            this.initiatorOfOperation = initiatorOfOperation;
            this.transferAmount = transferAmount;
        }

        MoneyTransfer(double transferAmount, Account interactiveAccount) {
            this(transferAmount, interactiveAccount, true);
        }

        abstract public void run() throws TransferException;
    }

    class Receipt extends MoneyTransfer {

        public Receipt(double transferAmount, Account interactiveAccount) {
            super(transferAmount, interactiveAccount);
        }

        private Receipt(double transferAmount, Account interactiveAccount, boolean initiatorOfOperation) {
            super(transferAmount, interactiveAccount, initiatorOfOperation);
        }

        public void run() throws TransferException {
            Account.this.incBalance(this.transferAmount);
            if (this.initiatorOfOperation) {
                this.interactiveAccount.new Payment(this.transferAmount, Account.this, false).run();
            }
        }
    }

    public class Payment extends MoneyTransfer {
        public Payment(double transferAmount, Account interactiveAccount) {
            super(transferAmount, interactiveAccount, true);
        }

        private Payment(double transferAmount, Account interactiveAccount, boolean initiatorOfOperation) {
            super(transferAmount, interactiveAccount, initiatorOfOperation);
        }

        public void run() throws TransferException {
            Account.this.lowBalance(this.transferAmount);
            if (this.initiatorOfOperation) {
                this.interactiveAccount.new Receipt(this.transferAmount, Account.this, false).run();
            }
        }
    }

    class Withdrawal implements Operation {
        final double transferAmount;

        Withdrawal(double transferAmount) {
            this.transferAmount = transferAmount;
        }

        public void run() throws TransferException{
            Account.this.lowBalance(this.transferAmount);
            // code to send money
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", balance=" + balance +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public double getBalance() {
        return balance;
    }
}
