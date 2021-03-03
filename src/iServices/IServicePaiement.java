package iServices;

import entites.Film;
import entites.Paiement;

import java.util.List;

public interface IServicePaiement{
    void addPaiement (Paiement p);
    void editPaiement (Paiement p);
    List<Paiement> paiementList();
    void deletePaiement(int id);
}
