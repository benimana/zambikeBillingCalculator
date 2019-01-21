package persistence;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistence.Booking;
import persistence.Receipt;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T22:03:25")
@StaticMetamodel(Clients.class)
public class Clients_ { 

    public static volatile SingularAttribute<Clients, String> clientName;
    public static volatile SingularAttribute<Clients, Integer> clientArea;
    public static volatile SingularAttribute<Clients, Integer> clientNo;
    public static volatile CollectionAttribute<Clients, Receipt> receiptCollection;
    public static volatile CollectionAttribute<Clients, Booking> bookingCollection;

}