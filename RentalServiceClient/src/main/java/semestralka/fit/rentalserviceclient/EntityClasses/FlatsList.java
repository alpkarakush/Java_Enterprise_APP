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
public class FlatsList {
    List<Flats> flats = new ArrayList();

    public List<Flats> getFlats() {
        return flats;
    }

    public void setFlats(List<Flats> flats) {
        this.flats = flats;
    }
}
