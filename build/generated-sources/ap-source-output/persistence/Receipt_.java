package persistence;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistence.Clients;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T22:03:25")
@StaticMetamodel(Receipt.class)
public class Receipt_ { 

    public static volatile SingularAttribute<Receipt, Integer> receiptNo;
    public static volatile SingularAttribute<Receipt, Integer> bankCharges;
    public static volatile SingularAttribute<Receipt, Integer> vat;
    public static volatile SingularAttribute<Receipt, Clients> clientNo;
    public static volatile SingularAttribute<Receipt, Integer> totalcost;

}