/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.fit.rentalserviceclient.EntityClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bekbolot
 */
public class ClientsList {
    private List<Clients> clients = new ArrayList();

    public List<Clients> getClients() {
        return clients;
    }

    public void setClients(List<Clients> clients) {
        this.clients = clients;
    }
}
