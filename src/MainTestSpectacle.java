import Entités.SpectacleE;
import connectionBD.SingletonConnection;
import service.serviceSpectacle;
import service.servicespectacleIMP;

import javax.swing.text.DateFormatter;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class MainTestSpectacle {
    public static void main(String[] args) throws SQLException {

        SingletonConnection ds1 = SingletonConnection.getInstance();

        // TESTADD

        SpectacleE s = new SpectacleE("blalblabla", new Date(), "dance", 2, 2);

        serviceSpectacle sp = new servicespectacleIMP();

        sp.AddSpectacle(s);

                                    // TESTUPDATE

        SpectacleE s1 = new SpectacleE(67,"sana", new Date() , "dance" , 2 ,2);

        sp.updateSpectacle(s1);

                                       //TESTDELET



      sp.deleteSpectacle(s1.getId());


                                 //TESTLISTE

      List<SpectacleE> listSpectacle = sp.spectacleEList();

        System.out.println("liste des spectacle est:"+ sp.spectacleEList().size());

        for (SpectacleE  ls:sp.spectacleEList()) {
            System.out.println(ls.toString());
        }

    }
}




