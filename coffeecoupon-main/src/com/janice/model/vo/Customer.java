package com.janice.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.janice.controller.Cafe;

public class Customer implements Serializable {
	private static final long serialVersionUID = 1325013464458558878L;
	private String name;
	private String phoneNo;
	private List<Coupon> coupons;
	
	public Customer() {
		super();
	}

	public Customer(String name, String phoneNo) {
		this.name = name;
		this.phoneNo = phoneNo;
		this.coupons = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhoneNo() {
		return phoneNo;
	}
	
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}
	
	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return "name=" + name + ", phoneNo=" + phoneNo + ", coupons=" + Cafe.countValid(this);
	}
}
