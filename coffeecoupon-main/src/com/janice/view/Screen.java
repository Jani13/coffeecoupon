package com.janice.view;

import java.util.Scanner;

import com.janice.controller.Cafe;
import com.janice.model.vo.Customer;

public class Screen {
	private int drinksOrdered;
	private int option;
	private boolean quit;
		
	private Scanner sc = new Scanner(System.in);
	private Cafe cafe = new Cafe("Coffee Factory"); // 생성자에서 파일 읽어오기
	
	public void getCoffee() {		
		quit = false;
				
		while(!quit) {	
		    System.out.println(); // 콘솔 입력 시작
			System.out.println("How many drinks are you ordering?");
			drinksOrdered = sc.nextInt();
			sc.nextLine();
			
			if(drinksOrdered < 1) {
				System.out.println("Thanks for visiting");
				break;
			}
			
			System.out.println("Would you like to add or use your coupons?\n"
					+ "1 - I don't have an account. Can I make one?\n"
					+ "2 - Yes!\n"
					+ "3 - No, I'm good");
			option = sc.nextInt();
			sc.nextLine();
			
			switch (option) {
				case 1:
					newCustomer();
					break;
				case 2: 
					originalCustomer();
					break;
				case 3:
					System.out.println("Thanks for visiting");
					break;
				default:
					System.out.println("Error...please check again");
					quit = true;
					break;
			}
		}
		
		cafe.exitProgram(); // 프로그램 종료 전 파일 저장하기
	}
	
	public void newCustomer() {
		String name;
		String phoneNo;
		
		System.out.println("Sure! Let's get started");
		System.out.print("name : ");
		name = sc.nextLine();		
		System.out.print("phoneNo : ");
		phoneNo = sc.nextLine();
		
		if(cafe.addCustomer(name, phoneNo, drinksOrdered)) {
			System.out.println("Welcome " + name + "!");
		} else {
			System.out.println("There's an existing account with this number\n"
					+ "Please check again");
		}
	}
	
	public void originalCustomer() {
		String phoneNo;
		String yesOrNo;
		
		System.out.println("OK, let us find you");
		System.out.print("phoneNo : ");
		phoneNo = sc.nextLine();
		
		Customer customer = cafe.findCustomer(phoneNo);
		
		if(customer == null) {
			System.out.println("We couldn't find you... Please check again");
			return;
		}
		
		System.out.println("Are you " + customer.getName() + "? (Y/N)");
		yesOrNo = sc.nextLine().toUpperCase();
		
		cafe.showCoupons(customer);			
		
		if(yesOrNo.equals("Y")) {
			useCoupons(customer);
		} else {
			System.out.println("We couldn't find you... Please check again");
		}
	}
	
	public void useCoupons(Customer customer) {
		String yesOrNo;
		int freeOrders;
		
		System.out.println("Would you like to use your coupons now? (Y/N)");
		yesOrNo = sc.nextLine().toUpperCase();
		
		if(yesOrNo.equals("Y")) {
			System.out.println("How many free drinks would you like?");
			freeOrders = sc.nextInt();
			
			if(cafe.useCoupon(customer, freeOrders)) {
				// freeOrders == drinksOrdered -> 주문한 잔수만큼 무료음료 잔수를 주문하는 경우 패스
				// 갖고 있는 무료음료 잔수보다 적은 무료음료를 주문했을 때 나머지는 쿠폰 적립
				if(freeOrders < drinksOrdered) {
					int remaining = drinksOrdered - freeOrders;
					cafe.addCoupon(customer, remaining);
				}
				
				System.out.println("Your order will be ready soon!");
			} else {
				System.out.println("You don't have enough coupons yet...");
			}
		} else {			
			cafe.addCoupon(customer, drinksOrdered);
			System.out.println("OK, your coupons are added instead");
		}
	}
}
