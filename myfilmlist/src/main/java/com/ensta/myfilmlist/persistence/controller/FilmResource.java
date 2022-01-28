package com.ensta.myfilmlist.persistence.controller;

import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import com.ensta.myfilmlist.form.FilmForm;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


import javax.validation.Valid;
import java.util.List;

// L’API s’appelle « Film » et utilise le Tag « Film »
// Le tag « Film » contient la description de l’API
@Api(tags = "Film")
@Tag(name = "Film", description = "Opération sur les films")
@RequestMapping("/film")

public interface FilmResource {

    /**
     *
     * @return liste nulle de tous les films, ainsi que leur Real.
     * @throws ControllerException
     */
    @ApiOperation(value = "Lister les films", notes = "Permet de renvoyer la liste de tous les films.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "La liste des films a été renvoyée correctement")})
    @GetMapping
    ResponseEntity<List<FilmDTO>> getAllFilms() throws ControllerException;

    /**
     *
     * @param id
     * @return Un FilmDTO correspondant à l'ID du film donné
     * @throws ControllerException
     */
    @ApiOperation(value = "Donner un film", notes = "Permet de renvoyer un film par ID.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Le film a été renvoyé correctement")})
    @GetMapping("/{id}")
    ResponseEntity<FilmDTO> getFilmById(@PathVariable long id) throws ControllerException;

    /**
     * Crée un film en fonction d'un filmform donné
     * @param filmForm
     * @return un film DTO correspondant au film créé
     * @throws ControllerException
     */
    @PostMapping
    ResponseEntity<FilmDTO> createFilm( @RequestBody @Valid FilmForm filmForm) throws ControllerException;

    /**
     * Supprimme un film donné par son id
     * @param id
     * @return
     * @throws ControllerException
     */
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteFilm(@PathVariable long id) throws ControllerException;

    /**
     * Rnevoie tous les films réalisés par un réalisateur donné en ID
     * @param id
     * @return
     * @throws ControllerException
     */
    @ApiOperation(value = "Lister les films par id real", notes = "Permet de renvoyer la liste de tous les films faits par un réalisateur.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "La liste des films du réalisateur a été renvoyée correctement")})
    @GetMapping("/real/{id}")
    ResponseEntity<List<FilmDTO>> getFilmsByRealisateurId(@PathVariable long id) throws ControllerException;

    /**
     * Met à jour un film avec un filmform et l'ID du film mis à jour
     * @param filmform
     * @param id
     * @return le filmDTO associé
     * @throws ControllerException
     */
    @ApiOperation(value = "Mettre à jour le film", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Le Film a été mis à jour")})
    @PutMapping("/{id}")
    ResponseEntity<FilmDTO> updateFilm(@RequestBody @Valid FilmForm filmform, @PathVariable long id) throws ControllerException;


    /**
     *
     * @return la liste de tous les réalisateurs
     * @throws ControllerException
     */
    @GetMapping("/real")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Realisateurs Renvoyés")})
    ResponseEntity<List<RealisateurDTO>> findAllRealisateurs() throws ControllerException;

}
