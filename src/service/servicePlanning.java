package service;


import Entités.Planning;

import java.util.List;

public interface servicePlanning {
    void AddPlanning(Planning p);
    List<Planning> planningList();
    void deletePlanning(Planning p);
    void updatePlanning(Planning p);




}

