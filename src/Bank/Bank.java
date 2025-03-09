package Bank;

import java.io.Serializable;
import javax.swing.DefaultListModel;
import Exceptions.AccNotFound;
import Exceptions.InvalidAmount;
import Exceptions.MaxBalance;
import Exceptions.MaxWithdraw;

public class Bank implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int MAX_ACCOUNTS = 100;
    private BankAccount[] accounts = new BankAccount[MAX_ACCOUNTS];
    
    public int addAccount(BankAccount acc) {
        for (int i = 0; i < MAX_ACCOUNTS; i++) {
            if (accounts[i] == null) {
                accounts[i] = acc;
                return i;
            }
        }
        return -1; // Indicating that the bank is full
    }
    
    public int addAccount(String name, double balance, double maxWithLimit) {
        return addAccount(new SavingsAccount(name, balance, maxWithLimit));
    }
    
    public int addAccount(String name, double balance, String tradeLicense) throws Exception {
        return addAccount(new CurrentAccount(name, balance, tradeLicense));
    }
    
    public int addAccount(String name, String institutionName, double balance, double minBalance) {
        return addAccount(new StudentAccount(name, balance, institutionName));
    }
    
    public BankAccount findAccount(String accountNum) {
        for (BankAccount account : accounts) {
            if (account != null && account.acc_num.equals(accountNum)) {
                return account;
            }
        }
        return null;
    }
    
    public void deposit(String accountNum, double amount) throws InvalidAmount, AccNotFound {
        if (amount <= 0) {
            throw new InvalidAmount("Invalid deposit amount");
        }
        BankAccount account = findAccount(accountNum);
        if (account == null) {
            throw new AccNotFound("Account not found");
        }
        account.deposit(amount);
    }
    
    public void withdraw(String accountNum, double amount) throws MaxBalance, AccNotFound, MaxWithdraw, InvalidAmount {
        if (amount <= 0) {
            throw new InvalidAmount("Invalid withdrawal amount");
        }
        BankAccount account = findAccount(accountNum);
        if (account == null) {
            throw new AccNotFound("Account not found");
        }
        if (amount > account.getbalance()) {
            throw new MaxBalance("Insufficient balance");
        }
        account.withdraw(amount);
    }
    
    public DefaultListModel<String> display() {
        DefaultListModel<String> list = new DefaultListModel<>();
        for (BankAccount account : accounts) {
            if (account != null) {
                list.addElement(account.toString());
            }
        }
        return list;
    }
    
    public BankAccount[] getAccounts() {
        return accounts;
    }

    public void setAccounts(BankAccount[] accounts) {
        this.accounts = accounts;
    }
}
