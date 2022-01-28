package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.RealisateurDAO;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class JPARealisateurDAO implements RealisateurDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Realisateur> findAll() throws SQLException {
        TypedQuery<Realisateur> query =
                entityManager.createQuery("SELECT real FROM Realisateur real",
                        Realisateur.class);
        List<Realisateur> reals = query.getResultList();
        return reals;
    }

    @Override
    public Realisateur findByNomAndPrenom(String nom, String prenom) throws Exception {
        try{
            Realisateur blub = null;
            int cpt = 0;
            List<Realisateur> list = findAll();

            for (Realisateur real:list
            ) {
                if (real.getPrenom().equals(prenom) && real.getNom().equals(nom)){
                    blub = real;
                    cpt ++;
                }
            }
            if (cpt == 1){
                return blub;
            }
            if (cpt >1){
                throw new RuntimeException();
            }
            return null;
        }
        catch(EmptyResultDataAccessException e){
            return null;
        }
        catch(RuntimeException e){
            throw e;
        }
    }

    @Override
    public Optional<Realisateur> findById(long id) throws SQLException {

        try{
            Realisateur real = entityManager.find(Realisateur.class, id);
            return Optional.of(real);
        }
        catch(Exception e){
            throw new SQLException("Realisator not found (by id) " + e);
        }

    }

    @Override
    public Realisateur update(Realisateur realisateur) {
        Realisateur real = entityManager.find(Realisateur.class, realisateur.getId());
        real.setId(realisateur.getId());
        real.setCelebre(realisateur.isCelebre());
        real.setDateNaissance(realisateur.getDateNaissance());
        real.setFilmRealises(realisateur.getFilmRealises());
        real.setPrenom(realisateur.getPrenom());
        real.setNom(realisateur.getNom());
        return real;
    }
}
