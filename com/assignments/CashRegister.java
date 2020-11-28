package com.assignments;

import java.io.*;
import java.util.*;

public class CashRegister {
	private static int r_cnt = 0;
	private int total;
	public ArrayList<Integer> t_list;

	public CashRegister() {
		total = 0;
		t_list = new ArrayList<>();
		++r_cnt;
	}

	public void AddTransaction(int amount) {
		t_list.add(amount);
		total += amount;
	}
	public ArrayList<Integer> TransactionCount() {return t_list;}  // t_list.size() returns transaction count
	public int Total() {return total;}
	public static int RegisterCount() {return r_cnt;}
	public void ResetTransactions() {
		t_list.clear();
		total = 0;
	}

	@SuppressWarnings("InfiniteLoopStatement")  // suppress warnings for infinite while loops
	public static void main(String[] args) throws IOException {
		CashRegister[] crs = new CashRegister[3];  // instantiate 3 cash registers
		crs[0] = new CashRegister();
		crs[1] = new CashRegister();
		crs[2] = new CashRegister();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1);
		while (true) {
			System.out.println("Number of Cash Registers: " + RegisterCount());
			for (int i = 0; i < crs.length; i++) {
				System.out.println("Cash Register #" + (i + 1));
			}
			System.out.println("Choose Cash Register (Enter Number):");
			int cr_id = Integer.parseInt(br.readLine()) - 1;
			if (cr_id > 2) {
				System.out.println("Choose A Different Cash Register!");
				continue;
			}

			actions:
			while (true){
				System.out.println("Choose Action (Enter Number):\r\n" +
						"1. Add Transaction\r\n2. Show Transactions\r\n" +
						"3. Reset Transactions\r\n4. Switch Cash Register");
				int action = Integer.parseInt(br.readLine());
				switch (action) {
					case 1:  // Add Transaction
					{
						System.out.println("Enter Amount:");
						int amount = Integer.parseInt(br.readLine());
						crs[cr_id].AddTransaction(amount);
						System.out.println("Transaction Successful\n");
						break;
					}
					case 2:  // Show Transactions (transaction count & total)
					{
						ArrayList<Integer> list = crs[cr_id].TransactionCount();
						System.out.println("Cash Register #" + (cr_id + 1) + " Transactions: " + list.size());  // transaction count
						for (int i = 0; i < list.size(); i++) {
							System.out.println("Transaction #" + (i + 1) + ": " + list.get(i));  // transaction amount
						}
						System.out.println("Total Transaction Amount: " + crs[cr_id].Total() + "\n");  // sum of transactions
						break;
					}
					case 3:  // Reset Transactions
					{
						crs[cr_id].ResetTransactions();
						System.out.println("Cash Register #" + (cr_id + 1) + " Successfully Reset\n");
						break;
					}
					case 4:  // Switch Cash Register
						break actions;
				}
			}
		}
	}
}
