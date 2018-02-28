package semestralka.fit.rentalservice;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import semestralka.fit.rentalservice.Flats;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-21T15:32:50")
@StaticMetamodel(Clients.class)
public class Clients_ { 

    public static volatile SingularAttribute<Clients, String> telNumber;
    public static volatile SingularAttribute<Clients, Flats> rentedFlat;
    public static volatile SingularAttribute<Clients, String> name;
    public static volatile SingularAttribute<Clients, Long> id;
    public static volatile SingularAttribute<Clients, String> surename;

}