/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.fit.rentalserviceclient.EntityClasses;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bekbolot
 */
public class ServicesList {
    List<Services> services = new ArrayList();

    public List<Services> getServices() {
        return services;
    }

    public void setServices(List<Services> services) {
        this.services = services;
    }
}
