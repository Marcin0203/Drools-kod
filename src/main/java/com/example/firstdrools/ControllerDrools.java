package com.example.firstdrools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerDrools {

    private final ServiceDrools serviceDrools;

    @Autowired
    public ControllerDrools (ServiceDrools serviceDrools){
        this.serviceDrools = serviceDrools;
    }

    @RequestMapping(value = "/getDiscount", method = RequestMethod.GET, produces = "application/json")
    public Product getQuestions(@RequestParam(required = true) String type) {

        Product product = new Product();
        product.setType(type);
        serviceDrools.getProductDiscout(product);
        return product;
    }
}
