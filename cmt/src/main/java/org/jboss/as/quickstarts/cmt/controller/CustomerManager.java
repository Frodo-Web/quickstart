/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.cmt.controller;

import java.util.List;
import java.util.logging.Logger;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import javax.naming.NamingException;
import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;

import org.jboss.as.quickstarts.cmt.ejb.CustomerManagerEJB;
import org.jboss.as.quickstarts.cmt.model.Customer;

@Named("customerManager")
@RequestScoped
public class CustomerManager {
    private Logger logger = Logger.getLogger(CustomerManager.class.getName());

    @Inject
    private CustomerManagerEJB customerManager;

    public List<Customer> getCustomers() throws SecurityException, IllegalStateException, NamingException,
        NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
        return customerManager.listCustomers();
    }

    public String addCustomer(String name) {
        try {
            customerManager.createCustomer(name);
            return "customerAdded";
        } catch (Exception e) {
            if (e.getMessage().contains("Invalid name")) {
                logger.warning("Invalid name: " + e.getMessage());
                return "customerInvalidName";
            }
            logger.warning("Caught a duplicate: " + e.getMessage());
            // Transaction will be marked rollback only anyway utx.rollback();
            return "customerDuplicate";
        }
    }
}
