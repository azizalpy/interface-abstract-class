package com.turkcell.business.concretes;

import com.turkcell.business.abstracts.ICustomerCheckService;
import com.turkcell.entities.concretes.Customer;

public class CustomerCheckManager implements ICustomerCheckService {

    @Override
    public boolean checkIfRealPerson(Customer customer) {
         return true;
    }
}
