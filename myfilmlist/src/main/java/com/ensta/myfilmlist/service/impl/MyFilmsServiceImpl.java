package com.ensta.myfilmlist.service.impl;
import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.dao.impl.JdbcFilmDAO;

import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.mapper.RealisateurMapper;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.service.MyFilmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ensta.myfilmlist.dao.impl.JdbcRealisateurDAO;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Stream;

import static com.ensta.myfilmlist.mapper.FilmMapper.convertFilmToFilmDTOs;

@Service
public class MyFilmsServiceImpl implements MyFilmsService {

    @Autowired
    private JdbcFilmDAO dao;

    @Autowired
    private JdbcRealisateurDAO realDao;
    static final int NB_FILM_MIN_REALISATEUR_CELEBRE = 3;


    @Override
    public Realisateur updateRealisateurCelebre(Realisateur real) throws ServiceException {

        try{

            if (real == null){
                throw new ServiceException("Real is null");
            }

            List<Film> listFilm = dao.findByRealisateurId(real.getId());

            if (listFilm == null){
                real.setCelebre(false);
                realDao.update(real);
                return real;
            }

            int nb = listFilm.size();
            if (nb >= NB_FILM_MIN_REALISATEUR_CELEBRE){
                real.setCelebre(true);

            }
            else {
                real.setCelebre(false);
            }
            realDao.update(real);
            return real;

        }
        catch (Exception e){
            throw new ServiceException("Exception error in updateRealisateurCelebre : " + e + " Cause : " + e.getCause());
        }

    }

    @Override
    public int calculerDureeTotale(List<Film> filmList){

        int sum = filmList.stream()
                .map(film -> film.getDuree())
                .reduce((a, b) -> a + b)
                .get();

        return sum;



    }

    public double calculerNoteMoyenne(double [] table){
        double sum = 0;
        sum = Arrays.stream(table)
                .average()
                .getAsDouble();
        sum = Math.round(sum * 100) / (double)100;
        return sum;

    }

    public List<FilmDTO> findAllFilms() throws  ServiceException{

        try{
            List<Film> filmList = dao.findAll();

            List<FilmDTO> newList = convertFilmToFilmDTOs(filmList);
            return newList;
        }
        catch (Exception e){
            throw new ServiceException("Exception error in findAllFilms : " + e);
        }
    }


    @Transactional
    public FilmDTO createFilm(FilmForm form) throws ServiceException{

        try{
            if( ! realDao.findById( form.getRealisateurId()).isPresent() ) {
                throw new ServiceException("No realisateur found");
            }

            Film film = dao.save(FilmMapper.convertFilmFormToFilm(form));

//          On a créé un film, on va update la célébrité de son réal.
            Realisateur real = realDao.findById(film.getRealisateur().getId()).get();
            updateRealisateurCelebre(real);

            FilmDTO newFilmDTO = FilmMapper.convertFilmToFilmDTO(film);
            return newFilmDTO;
        }
        catch (Exception e){
            throw new ServiceException("Exception error in create Film : " + e);
        }


    }

    public List<RealisateurDTO> findAllRealisateurs() throws ServiceException{
        try{
            List<RealisateurDTO> list = null;
            list = RealisateurMapper.convertRealisateurToRealisateurDTOs(realDao.findAll());
            return list;
        }
        catch(Exception e){
            return (Collections.emptyList());
//            throw new ServiceException(e);
        }

    }

    @Override
    public RealisateurDTO findRealisateurByNomAndPrenom(String nom, String prenom) throws ServiceException {
        try{

            Realisateur real = realDao.findByNomAndPrenom(nom, prenom);
//            updateRealisateurCelebre(real);
            RealisateurDTO realdto = RealisateurMapper.convertRealisateurToRealisateurDTO(real);
            return realdto;
        }
        catch(Exception e){
            throw new ServiceException("Exception in findRealisateurByNomAndPrenom : " + e);
        }
    };

    @Override
    public FilmDTO findFilmById(long id) throws ServiceException {
        try{
            Film film = dao.findById(id).orElse(null);
            if (film != null){
                FilmDTO dto = FilmMapper.convertFilmToFilmDTO(film);
                return dto;
            }
            return null;

        }

        catch(Exception e){
            throw new ServiceException("ServiceException in findFilmById " + e);
        }

    }

    @Transactional
    @Override
    public void deleteFilm(long id) throws ServiceException {
        try{
            Film film = dao.findById(id).orElse(null);
            dao.delete(film);

            updateRealisateurCelebre(film.getRealisateur());
            return;
        }
        catch(NoSuchElementException e){

        }
        catch(Exception e){
            throw new ServiceException("ServiceException in DeleteFilm " + e);
        }
    }

    @Override
    public List<FilmDTO> findFilmByRealisateurId(long id) throws ServiceException {
        try{

            List<Film> filmList = dao.findByRealisateurId(id);


            return FilmMapper.convertFilmToFilmDTOs(filmList);

        }

        catch(Exception e){
            throw new ServiceException("ServiceException in findFilmByRealisateurId " + e);
        }
    }

    @Override
    public FilmDTO updateFilm(FilmForm filmForm, long id) throws ServiceException{
        try{
            long realId = filmForm.getRealisateurId();
            int duree = filmForm.getDuree();
            String titre = filmForm.getTitre();

            if (realDao.findById(realId).isEmpty()){
                throw new ServiceException("No realisator found");
            }

            if (dao.findById(id).isEmpty()){
                throw new ServiceException("No film found");
            }
//            if (filmForm.getRealisateurId()==null){
//
//            }
            Film oldFilm = FilmMapper.convertFilmDTOToFilm(findFilmById(id));

            Realisateur oldReal = realDao.findById(oldFilm.getRealisateur().getId()).get();

            Realisateur newReal = realDao.findById(realId).get();

            Film newFilm = dao.update(id, filmForm);

            if (oldReal.getId() != newReal.getId()){
                updateRealisateurCelebre(oldReal);
                updateRealisateurCelebre(newReal);
            }
            FilmDTO returnDTO = FilmMapper.convertFilmToFilmDTO(newFilm);
            returnDTO.setId(id);
            return returnDTO;
        }
        catch(Exception e){
            throw new ServiceException("ServiceException in updateFilm : " + e );
        }
    }
}
