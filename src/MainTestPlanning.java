import Entités.Planning;
import connectionBD.SingletonConnection;
import service.servicePlanning;
import service.serviceplanningIMP;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class MainTestPlanning {


    public static void main(String[] args) throws SQLException {

        SingletonConnection ds1 = SingletonConnection.getInstance();

        // TESTADD

        Planning p = new Planning(4 , new Date(), new Date(), new Date(), 2, 2);

        servicePlanning sp = new serviceplanningIMP();

        sp.AddPlanning(p);


        //UPDATEPLANNING


        Planning p1 = new Planning(3, new Date(), new Date(), new Date(), 10, 10);

        sp.updatePlanning(p1);


        //DELETE

      // sp.deletePlanning(p1.getId());


        //TESTAFFICHAGECONSOLE

      List<Planning> planningList = sp.planningList();
        System.out.println("les planning sont:"+planningList.size());
        for (Planning ls :sp.planningList()){
            System.out.println(ls.toString());
        }

    }
}



























