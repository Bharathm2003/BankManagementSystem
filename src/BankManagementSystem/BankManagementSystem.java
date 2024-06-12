package BankManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class BankAccount {
    private String accountNumber;
    private String accountHolder;
    private double balance;

    public BankAccount(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            JOptionPane.showMessageDialog(null, "Insufficient balance!");
        }
    }

    @Override
    public String toString() {
        return accountNumber + " - " + accountHolder + " - Balance: $" + balance;
    }
}

public class BankManagementSystem extends JFrame implements ActionListener {
    private ArrayList<BankAccount> accounts;
    private JTextField tfAccountNumber, tfAccountHolder, tfAmount;
    private JTextArea taAccountsList;

    public BankManagementSystem() {
        accounts = new ArrayList<>();
        setTitle("Bank Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        JLabel lbAccountNumber = new JLabel("Account Number:");
        tfAccountNumber = new JTextField(10);
        JLabel lbAccountHolder = new JLabel("Account Holder:");
        tfAccountHolder = new JTextField(10);
        JLabel lbAmount = new JLabel("Amount:");
        tfAmount = new JTextField(10);

        JButton btnCreateAccount = new JButton("Create Account");
        btnCreateAccount.addActionListener(this);
        JButton btnDeposit = new JButton("Deposit");
        btnDeposit.addActionListener(this);
        JButton btnWithdraw = new JButton("Withdraw");
        btnWithdraw.addActionListener(this);

        panel.add(lbAccountNumber);
        panel.add(tfAccountNumber);
        panel.add(lbAccountHolder);
        panel.add(tfAccountHolder);
        panel.add(lbAmount);
        panel.add(tfAmount);
        panel.add(btnCreateAccount);
        panel.add(btnDeposit);
        panel.add(btnWithdraw);

        taAccountsList = new JTextArea();
        taAccountsList.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(taAccountsList);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new BankManagementSystem();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Create Account")) {
            String accountNumber = tfAccountNumber.getText();
            String accountHolder = tfAccountHolder.getText();
            double initialBalance = 0;
            try {
                initialBalance = Double.parseDouble(tfAmount.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid initial balance!");
                return;
            }

	    if (findAccount(accountNumber) != null) {
            JOptionPane.showMessageDialog(null, "An account with this account number already exists!");
            return;
            }

            BankAccount account = new BankAccount(accountNumber, accountHolder, initialBalance);
            accounts.add(account);
            tfAccountNumber.setText("");
            tfAccountHolder.setText("");
            tfAmount.setText("");
            displayAccounts();
        } else if (e.getActionCommand().equals("Deposit")) {
            String accountNumber = tfAccountNumber.getText();
            double amount = 0;
            try {
                amount = Double.parseDouble(tfAmount.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid amount!");
                return;
            }

            BankAccount account = findAccount(accountNumber);
            if (account != null) {
                account.deposit(amount);
                displayAccounts();
            } else {
                JOptionPane.showMessageDialog(null, "Account not found!");
            }
        } else if (e.getActionCommand().equals("Withdraw")) {
            String accountNumber = tfAccountNumber.getText();
            double amount = 0;
            try {
                amount = Double.parseDouble(tfAmount.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid amount!");
                return;
            }

            BankAccount account = findAccount(accountNumber);
            if (account != null) {
                account.withdraw(amount);
                displayAccounts();
            } else {
                JOptionPane.showMessageDialog(null, "Account not found!");
            }
        }
    }

    private BankAccount findAccount(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    private void displayAccounts() {
        taAccountsList.setText("");
        for (BankAccount account : accounts) {
            taAccountsList.append(account.toString() + "\n");
        }
    }
}