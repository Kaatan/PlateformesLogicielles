package com.ensta.myfilmlist.service;

import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;

import java.lang.reflect.Array;

import java.util.List;

public interface MyFilmsService {

    /**
     *
     * @param real
     * @return le réalisateur modifié en celebrité selon son nombre de films
     * @throws ServiceException
     */
    public Realisateur updateRealisateurCelebre(Realisateur real) throws ServiceException;


    /**
     *
     * @param filmList
     * @return la somme des durées des films
     */
    public int calculerDureeTotale(List<Film> filmList);

    /**
     *
     * @param table
     * @return la note moyenne des films de la table
     */
    public double calculerNoteMoyenne(double[] table);

    /**
     *
     * @return la liste de tous les films
     */
    public List<FilmDTO> findAllFilms() throws ServiceException;

    /**
     *
     * @param form
     * @return
     * @throws ServiceException
     */
    public FilmDTO createFilm(FilmForm form) throws ServiceException;

    /**
     *
     * @return la liste de tous les realisateurs, peut être vide
     * @throws ServiceException
     */
    public List<RealisateurDTO> findAllRealisateurs() throws ServiceException;

    /**
     *
     * @param nom
     * @param prenom
     * @return un réalisateur avec les noms et prenoms demandés, ou null sinon.
     * @throws ServiceException
     */
    public RealisateurDTO findRealisateurByNomAndPrenom(String nom, String prenom) throws ServiceException;

    /**
     *
     * @param Id
     * @return
     * @throws ServiceException
     */
    public FilmDTO findFilmById(long Id) throws ServiceException;

    /**
     *
     * @param id
     * @throws ServiceException
     */
    public void deleteFilm(long id) throws ServiceException;

    /**
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    public List<FilmDTO> findFilmByRealisateurId(long id) throws ServiceException;

    /**
     *
     * @param filmForm
     * @return
     * @throws ServiceException
     */
    public FilmDTO updateFilm(FilmForm filmForm, long id) throws ServiceException;

}
