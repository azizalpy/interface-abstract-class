package com.turkcell.business.concretes;

import com.turkcell.business.abstracts.BaseCustomerManager;
import com.turkcell.business.abstracts.ICustomerCheckService;
import com.turkcell.business.abstracts.ICustomerService;
import com.turkcell.entities.concretes.Customer;

public class StarbucksCustomerManager extends BaseCustomerManager {

    private ICustomerCheckService _customerCheckService;

    public StarbucksCustomerManager(ICustomerCheckService _customerCheckService) {
        this._customerCheckService = _customerCheckService;
    }

    @Override
    public void save(Customer customer) {
        try {
            if(_customerCheckService.checkIfRealPerson(customer)) {
                super.save(customer);
            }
            else throw new Exception("Not a valid person");
        } catch (Exception e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
    }
}
