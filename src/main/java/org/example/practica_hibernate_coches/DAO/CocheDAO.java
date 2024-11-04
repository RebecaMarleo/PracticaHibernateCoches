package org.example.practica_hibernate_coches.DAO;

import org.example.practica_hibernate_coches.model.Coche;
import org.hibernate.Session;

import java.util.List;

public interface CocheDAO {

    void saveCoche(Coche coche, Session session);

    Coche getCocheById(int id, Session session);

    List<Coche> getAllCoches(Session session);

    void updateCoche(Coche coche, Session session);

    void deleteCocheById(int id, Session session);
}
