package semestralka.fit.rentalservice;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import semestralka.fit.rentalservice.Flats;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-21T15:32:50")
@StaticMetamodel(Services.class)
public class Services_ { 

    public static volatile SingularAttribute<Services, Long> pricePerMonth;
    public static volatile ListAttribute<Services, Flats> flats;
    public static volatile SingularAttribute<Services, String> name;
    public static volatile SingularAttribute<Services, String> description;
    public static volatile SingularAttribute<Services, Long> id;

}