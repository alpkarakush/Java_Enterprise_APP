package semestralka.fit.rentalservice;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import semestralka.fit.rentalservice.Clients;
import semestralka.fit.rentalservice.Services;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-21T15:32:50")
@StaticMetamodel(Flats.class)
public class Flats_ { 

    public static volatile SingularAttribute<Flats, String> address;
    public static volatile SingularAttribute<Flats, Long> pricePerMonth;
    public static volatile ListAttribute<Flats, Clients> clients;
    public static volatile SingularAttribute<Flats, Services> service;
    public static volatile SingularAttribute<Flats, Long> id;

}