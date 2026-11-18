package com.waypoint.model;

import com.waypoint.id.CustomerId;

public class Customer {

    private final CustomerId customerId;
    private final String name;

    public Customer(CustomerId customerId, String name) {

        if (customerId == null || name == null || name.isBlank()) {
            throw new IllegalArgumentException("Invalid customer details");
        }

        this.customerId = customerId;
        this.name = name;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}
