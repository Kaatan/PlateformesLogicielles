package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class JPAFilmDAO implements FilmDAO {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Film> findAll() throws SQLException {
        TypedQuery<Film> query =
                entityManager.createQuery("SELECT film FROM Film film",
                        Film.class);
        List<Film> films = query.getResultList();
        return films;
    }

    @Override
    public Film save(Film film) {
        entityManager.persist(film);
        return film;
    }

    @Override
    public Optional<Film> findById(long id) throws SQLException {
        try {
            Film film = entityManager.find(Film.class, id);
            return Optional.of(film);
        }
        catch(Exception e){
            throw new SQLException("In DAo.findById : " + e);
        }

    }

    @Override
    public void delete(Film film) {
        entityManager.remove(film);
    }

    @Override
    public List<Film> findByRealisateurId(long realisateurId) throws SQLException {

        try{
            List<Film> list = findAll();
            List<Film> films = null;

            for (Film aFilm:list
                 ) {
                if (aFilm.getRealisateur().getId() == realisateurId) {
                    films.add(aFilm);
                }
            }
            return films;

        }
        catch(Exception e){
            throw new SQLException("In DAO.findByRealisateurID : " + e);
        }

    }

    @Override
    public Film update(long id, FilmForm filmForm) {
        Film film = entityManager.find(Film.class, id);
        film.setTitre(filmForm.getTitre());
        Realisateur real = entityManager.find(Realisateur.class, filmForm.getRealisateurId());
        film.setRealisateur(real);
        film.setDuree(filmForm.getDuree());
        return film;
    }
}
