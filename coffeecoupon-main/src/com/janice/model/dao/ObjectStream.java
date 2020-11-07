package com.janice.model.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.janice.model.vo.Customer;

public class ObjectStream {
	
	public List<Customer> readFile() {
		ObjectInputStream ois = null;
		List<Customer> customers = new ArrayList<>();
		
		try {
			File file = new File("customers.dat");
			
			if(file.exists() && !file.isDirectory()) {
			    ois = new ObjectInputStream(new FileInputStream(file));			    			  
			    Customer obj = null;
			    
			    System.out.println("Customer list");
			    
			    while ((obj = (Customer) ois.readObject()) != null) {
			    	customers.add(obj);
			    	System.out.println(obj.toString());			    	
			    }			    
			}else{
				System.out.print("File customers.csv does not exist\n");
			}			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("(End)");
//			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ois != null) {
					ois.close();
				}				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return customers;
	}
	
	public void saveFile(List<Customer> customers) {
		ObjectOutputStream oos = null;
		File file = new File("customers.dat");
		
		try {
			oos = new ObjectOutputStream(new FileOutputStream(file));
		    
			ListIterator<Customer> listIterator = customers.listIterator();
			
			while(listIterator.hasNext()) {				
				oos.writeObject(listIterator.next()); 
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}	
}
