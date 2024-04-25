package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Reservation;
import tn.esprit.models.User;
import tn.esprit.models.hotel;

import java.time.LocalDate;
import java.util.List;

public class UserService implements IService <User> {
    @Override
    public void add(User o) {

    }

    @Override
    public void update(User o) {

    }

    @Override
    public void generatePDF(List<Reservation> reservations, String filePath) {

    }

    @Override
    public List<hotel> rechercherParNom(String nom) {
        return null;
    }

    @Override
    public void delete(User o) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public User getOne(int id) {
        return null;
    }

    @Override
    public List<Reservation> getReservationByDate(LocalDate dateSelectionnee) {
        return null;
    }
}
