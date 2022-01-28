package com.ensta.myfilmlist.dao;

import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.model.Film;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface FilmDAO {

    /**
     *
     * @return the list of all films in the DB
     * @throws SQLException
     */
    public List<Film> findAll() throws SQLException ;

    /**
     * prend un film en parametre et le sauvegarde dans la DB
     * @return the created film with its ID
     */
    public Film save(Film film);

    /**
     *
     * @param id
     * @return un film selon son id
     * @throws SQLException
     */
    public Optional<Film> findById(long id) throws SQLException;

    /**
     * Supprimme un film
     * @param film
     */
    public void delete (Film film) ;

    /**
     *
     * @param realisateurId
     * @return la liste de toues les films faits par le réalisateur donné par l'ID
     * @throws SQLException
     */
    public List<Film> findByRealisateurId(long realisateurId) throws SQLException;

    /**
     * Met à jour le film correspondant à l'ID du film donné avec les données de ce film
     * @param id
     * @param filmForm
     * @return le film mis à jour
     */
    public Film update(long id, FilmForm filmForm);

}
