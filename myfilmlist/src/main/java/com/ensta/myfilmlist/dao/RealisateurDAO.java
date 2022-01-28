package com.ensta.myfilmlist.dao;

import com.ensta.myfilmlist.model.Realisateur;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RealisateurDAO {

    /**
     *
     * @return tous les réalisateurs de la DB
     * @throws SQLException
     */
    public List<Realisateur> findAll() throws SQLException ;

    /**
     *
     * @param nom
     * @param prenom
     * @return Le réalisateur correspondant au nom et prénom données en arguments
     * @throws Exception
     */
    public Realisateur findByNomAndPrenom(String nom, String prenom) throws Exception;

    /**
     *
     * @param id
     * @return le réalisateur correspondant à l'idée
     * @throws SQLException
     */
    public Optional<Realisateur> findById(long id) throws SQLException ;

    /**
     * Met à jour le réalisateur correspondant à l'ID de celui en argument avec les données de ce même réalisateur en argument
     * @param realisateur
     * @return the updated realisator
     */
    public Realisateur update(Realisateur realisateur) ;


}
