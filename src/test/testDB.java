package test;

import entitie.PlanningFilm;
import entitie.Reclamation;
import entitie.SalleDeCinema;
import service.RecalamationService;
import service.SaleDeCinemaService;
import utils.SingletonConnection;

import java.util.List;

public class testDB {
    public static void main(String[] args) {
//                  tester reclamation crud

//         test DB conn
//        SingletonConnection ds1 = SingletonConnection.getInstance();


//        // test AddReclamation

//        Reclamation r = new Reclamation("rec2", "decription rec2", "resolu", 1);
////        System.out.println(r.toString());
//        RecalamationService rs = new RecalamationService();
//        rs.AddReclamation(r);


        //        test delete reclamation
//        RecalamationService rs = new RecalamationService();
//            rs.deleteReclamation(3);

//        test reclamationListe
//        RecalamationService rs = new RecalamationService();
//        List<Reclamation> listeReclamation = rs.reclamationListe();
//        System.out.println("liste size : " + listeReclamation.size());
//        for (Reclamation r:listeReclamation) {
//            System.out.println(r.toString());
//        }

        //        test reclamationListe
//        RecalamationService rs = new RecalamationService();
//        List<Reclamation> listeReclamation = rs.reclamationListe(1);
//        System.out.println("liste size : " + listeReclamation.size());
//        for (Reclamation r:listeReclamation) {
//            System.out.println(r.toString());
//        }


//        tester salle de cinema crud


//  test AddSalle

//        SalleDeCinema s = new SalleDeCinema("salle cc", 40, "ccccccc B", "adresse", 1);
//        SaleDeCinemaService sc = new SaleDeCinemaService();
//        sc.AddSalle(s);

//      tester updateSalleDeCinema
//        SalleDeCinema s = new SalleDeCinema(4, "salle 33", 2, "Description", "adresse", 1);
//        SaleDeCinemaService sc = new SaleDeCinemaService();
//        sc.updateSalleDeCinema(s);


        //        test salleDeCinemaListe
//        SaleDeCinemaService sc = new SaleDeCinemaService();
//        List<SalleDeCinema> listSalle = sc.salleDeCinemaListe();
//        System.out.println("liste size : " + listSalle.size());
//        for (SalleDeCinema sl:listSalle) {
//            System.out.println(sl.toString());
//        }

        //        test deleteSalleDeCinema
//        SaleDeCinemaService sc = new SaleDeCinemaService();
//        sc.deleteSalleDeCinema(1);

        //        tester afficherPlanningFilm
//        SaleDeCinemaService sc = new SaleDeCinemaService();
//        List<PlanningFilm> lp=  sc.afficherPlanningFilm(1);
//        for(PlanningFilm p:lp) {
//            System.out.println(p.toString());
//        }

//                tester afficherPlanningSpectacle
//        SaleDeCinemaService sc = new SaleDeCinemaService();
//        List<PlanningSpectacle> ps=  sc.afficherPlanningSpectacle(1);
//        for(PlanningSpectacle p:ps) {
//            System.out.println(p.toString());
//        }

//        rechercherSalleByName
//        SaleDeCinemaService sc = new SaleDeCinemaService();
//        List<SalleDeCinema> ss = sc.rechercherSalleByName("al");
//        for(SalleDeCinema s:ss) {
//            System.out.println(s.toString());
//        }

    }
}
