package com.company;

import java.time.LocalDateTime;
import java.util.*;
import java.io.*;

public class Main{
    //Add the method called findAccount
    private static Account findAccount(List<Account> accountList, String accountNumber){
        for(Account ac: accountList){
            if(ac.getAccountNumber().equals(accountNumber)){
                return ac;
            }
        }
        return null;
    }

    public static void main(String [] args) throws FileNotFoundException
    {
        List<Account> accountList = new ArrayList<>();

        Scanner in = new Scanner(System.in);

        System.out.print("Please input the file pathname: ");
        String filepathname = in.nextLine();
        System.out.print("Enter fullpath for logs file: ");
        String logsPath = in.nextLine();

        Scanner infile = new Scanner(new File(filepathname));

        while (infile.hasNext()){
            String accountNum = infile.next();
            if(accountNum.charAt(0) >= 48 && accountNum.charAt(0)<=53){
                Double balance = infile.nextDouble();
                accountList.add(new SavingsAccount(accountNum, balance));
            }
            else if(accountNum.charAt(0) > 53 && accountNum.charAt(0)<=56){
                Double balance = infile.nextDouble();
                Double creditLimit = infile.nextDouble();
                accountList.add(new CreditCardAccount(accountNum, balance, creditLimit));
            }
            else if(accountNum.charAt(0) == 57){
                Account account1 = findAccount(accountList, infile.next());
                Account account2 = findAccount(accountList, infile.next());
                accountList.add(new PowerAdvantageAccount(accountNum, account1, account2));
            }
            else{

            }
        }
//        Account account1 = new Account() {
//            @Override
//            public Double getTotal() {
//                return (double)100;
//            }
//        };
        infile.close();
        String logs = "";
        LocalDateTime d;
        while(true){
            System.out.print("\nInput an account number to search: ");
            String accountToSearch = in.next();
            if(accountToSearch.length()==1){
                break;
            }
            Account account = findAccount(accountList,accountToSearch);
            if (account != null) {
                d = LocalDateTime.now();
                logs += "\n" + "[Search]  " + d.getHour() + ":" + d.getMinute() + " - " + d.getDayOfMonth() + "/" + d.getMonthValue() + "/" + d.getYear() + "  A/C searched - " + accountToSearch;
                System.out.println("\n[Result]\n" + account.toString());
            }
            else
                System.out.println("\n[Result]\nThe account is not found.");

        }
        try{
            FileWriter fileWriter = new FileWriter(logsPath);

            fileWriter.write(logs);
            fileWriter.close();
        }
        catch (IOException ex){
            System.out.println("Error writing to file!");

        }
        in.close();
    }
}