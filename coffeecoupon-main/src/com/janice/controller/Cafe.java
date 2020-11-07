package com.janice.controller;

import java.util.List;
import java.util.ListIterator;

import com.janice.model.dao.ObjectStream;
import com.janice.model.dao.Stream;
import com.janice.model.vo.Coupon;
import com.janice.model.vo.Customer;

public class Cafe {
	private String name;
	private int perFreeDrink = 10;
	private List<Customer> customers;
	
	private ObjectStream objectStream = new ObjectStream();
	
	public Cafe() {
	}

	public Cafe(String name) {
		this.name = name;
		this.customers = objectStream.readFile();
	}
	
	public void exitProgram() {
		objectStream.saveFile(customers);
	}
	
	public boolean addCustomer(String name, String phoneNo, int drinksPaid) {	
		if(findCustomer(phoneNo) != null) { // 이름은 중복 가능
			return false;
		}			
		
		Customer newCustomer = new Customer(name, phoneNo);
		
		customers.add(newCustomer);
		addCoupon(newCustomer, drinksPaid);
		return true;
	}
	
	public void addCoupon(Customer customer, int drinksPaid) {			
		for(int i = 0; i < drinksPaid; i++) {
			customer.getCoupons().add(new Coupon()); // valid 기본값: true	
		}
	}
	
	public boolean useCoupon(Customer customer, int freeOrders) {
		int freeInStore = countValid(customer) / perFreeDrink; // countValid() 호출 
						
		int couponsDeleted = freeOrders * perFreeDrink;
		int count = 0;
						
		List<Coupon> list = customer.getCoupons();	
		ListIterator<Coupon> iterator = list.listIterator();
	
		if(freeOrders > freeInStore) { // 쿠폰 개수가 모자른 경우
			return false;
		}
		
		// Customer 객체의 List<Coupon>에서 false인 것을 건너뛰고 true인 것부터 false로 만들기
		while(iterator.hasNext()) { 
			Coupon coupon = iterator.next();
			
			if(!coupon.isValid()) { // 패스
				continue;
			} else {
				coupon.setValid(false); // 쿠폰 사용
				count++;
			}
			
			if(couponsDeleted == count) {
				return true;
			}
		}
		return false;
	}

	public Customer findCustomer(String name, String phoneNo) {		
		Customer thisCustomer = findCustomer(phoneNo);
		
		if(thisCustomer != null) {
			if(thisCustomer.getName().equals(name)) {
				return thisCustomer;
			}
		}
		return null;
	}
	
	public Customer findCustomer(String phoneNo) {
		for(Customer customer : customers) {
			if(customer.getPhoneNo().equals(phoneNo)) {
				return customer;
			}
		}
		return null;
	}
	
	public void showCoupons(Customer customer) {
		String name = customer.getName();
		String phoneNo = customer.getPhoneNo();
		int coupons = countValid(customer); // countValid() 호출
				
		if(findCustomer(name, phoneNo) == null) {			
			return;
		}
		
		System.out.println("Coupons collected: " + coupons + "\n" + 
							"Free drinks available: " + (coupons / perFreeDrink));
		
	}
	
	public static int countValid(Customer customer) { // static 메소드로 Customer 클래스 toString() 메소드에서도 사용
		int cValid = 0;
		
		List<Coupon> list = customer.getCoupons(); 	
		ListIterator<Coupon> iterator = list.listIterator();
		
		while(iterator.hasNext()) {
			if(iterator.next().isValid() == true) {
				cValid++;
			}
		}
		return cValid; // 사용가능 쿠폰 개수
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPerFreeDrink() {
		return perFreeDrink;
	}

	public void setPerFreeDrink(int perFreeDrink) {
		this.perFreeDrink = perFreeDrink;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
}
