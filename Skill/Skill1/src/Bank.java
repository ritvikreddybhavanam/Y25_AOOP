
import entities.*;

public class Bank {

    public static void main(String[] args) {

        Money overdraft = new Money(500000, "INR");

        BankAccount account1 = new BankAccount("1001", "INR", overdraft);

        BankAccount account2 = new BankAccount("1002", "INR", overdraft);

        account1.deposit(new Money(1000000, "INR"));
        account2.deposit(new Money(500000, "INR"));

        account1.withdraw(new Money(200000, "INR"));

        FundTransfer transfer = new FundTransfer(account1, account2, new Money(300000, "INR"));

        transfer.transfer();

        System.out.println("\n----- ACCOUNT DETAILS -----");

        System.out.println("Account 1: " + account1.getBalance());
        System.out.println("Account 2: " + account2.getBalance());

        account1.freeze();
        account1.unfreeze();

        System.out.println("Account Status: " + account1.getStatus());

        LedgerEntry entry = new LedgerEntry("DEPOSIT", new Money(1000000, "INR"));

        System.out.println("\n----- LEDGER ENTRY -----");

        entry.display();
    }
}
