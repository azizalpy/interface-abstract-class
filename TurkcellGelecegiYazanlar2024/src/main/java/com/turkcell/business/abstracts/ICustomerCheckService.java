package com.turkcell.business.abstracts;

import com.turkcell.entities.concretes.Customer;

public interface ICustomerCheckService {

    boolean checkIfRealPerson(Customer customer);
}
