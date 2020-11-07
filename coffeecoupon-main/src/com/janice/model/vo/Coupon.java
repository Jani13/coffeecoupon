package com.janice.model.vo;

import java.io.Serializable;

public class Coupon implements Serializable {
	private static final long serialVersionUID = 3481612569682470087L;
	boolean valid = true;
	
	public Coupon() {
		super();
	}
	
	public Coupon(boolean valid) { // 사용전: true, 사용후: false
		super();
		this.valid = valid;
	}
	
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
