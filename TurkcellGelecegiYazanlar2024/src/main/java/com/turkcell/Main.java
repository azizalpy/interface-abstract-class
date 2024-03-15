package com.turkcell;

import com.turkcell.adapters.MernisServiceAdapter;
import com.turkcell.business.abstracts.BaseCustomerManager;
import com.turkcell.business.concretes.NeroCustomerManager;
import com.turkcell.business.concretes.StarbucksCustomerManager;
import com.turkcell.entities.concretes.Customer;

public class Main {
    public static void main(String[] args) {
        // It is a valid customer
        // If you change any data from the customer's constructor, it will throw an exception for Starbucks due to Mernis Validation
        Customer customer = new Customer ("Aziz Alp", "Yolcu", 2000, "17704455654");

        BaseCustomerManager customerManager1 = new NeroCustomerManager();
        customerManager1.save(customer);

        BaseCustomerManager customerManager2 = new StarbucksCustomerManager(new MernisServiceAdapter());
        customerManager2.save(customer);

    }
}