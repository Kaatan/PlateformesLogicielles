package com.ensta.myfilmlist.persistence.controller.impl;

import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.persistence.controller.FilmResource;
import com.ensta.myfilmlist.service.MyFilmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@CrossOrigin(origins = "http://localhost:4200")
public class FilmResourceImpl implements FilmResource {

    @Autowired
    private MyFilmsService myFilmsService;

    @Override
    public ResponseEntity<List<FilmDTO>> getAllFilms() throws ControllerException {
        try{

            return ResponseEntity.status(HttpStatus.OK).body(myFilmsService.findAllFilms());
        }
        catch(Exception e){
            throw new ControllerException("Exception error in Resource.getAllFilms : " + e);
        }


    }

    @Override
    public ResponseEntity<FilmDTO> getFilmById(@PathVariable long id) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(myFilmsService.findFilmById(id));
        }
        catch (Exception e){
            throw new ControllerException("Exception error in filmresource.getByFilmId : " + e);
        }
    }


    @Override
    public ResponseEntity<FilmDTO> createFilm(FilmForm filmForm) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(myFilmsService.createFilm(filmForm));
        }
        catch (Exception e){
            throw new ControllerException("Exception error in filmresource.createFilm : " + e);
        }
    }

    @Override
    public ResponseEntity<?> deleteFilm(long id) throws ControllerException {
        try {
            myFilmsService.deleteFilm(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e){
            throw new ControllerException("Exception error in filmresource.createFilm : " + e);
        }
    }

    @Override
    public ResponseEntity<List<FilmDTO>> getFilmsByRealisateurId(long id) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(myFilmsService.findFilmByRealisateurId(id));
        }
        catch (Exception e){
            throw new ControllerException("Exception error in filmresource.getFilmsByRealisateurId : " + e);
        }
    }

    @Override
    public ResponseEntity<FilmDTO> updateFilm(@RequestBody @Valid FilmForm filmform, @PathVariable long id) throws ControllerException{
        try {

            return ResponseEntity.status(HttpStatus.OK).body(myFilmsService.updateFilm(filmform, id));
        }
        catch (Exception e){
            throw new ControllerException("Exception error in filmresource.updateFilm : " + e);
        }
    };


    public ResponseEntity<List<RealisateurDTO>> findAllRealisateurs() throws ControllerException{
        try{

            return ResponseEntity.status(HttpStatus.OK).body(myFilmsService.findAllRealisateurs());
        }
        catch (Exception e){
            throw new ControllerException("Exception error in filmresource.FindAllRealisateurs : " + e);
        }
    }
}
