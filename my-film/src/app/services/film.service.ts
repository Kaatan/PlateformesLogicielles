import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

import { Film } from '../models/film';
import { Realisateur } from '../models/realisateur';

@Injectable({
  providedIn: 'root'
})
export class FilmService {

  url:string = "http://localhost:8080/film";

  films:Film[];
  filmsSubject!:Subject<Film[]>;

  realisateurs:Realisateur[];
  realisateursSubject!:Subject<Realisateur[]>;

  constructor(
    private http:HttpClient
    ) { 
      this.filmsSubject = new Subject<Film[]>();
      this.realisateursSubject = new Subject<Realisateur[]>();
    }


  emitFilms(){
    this.filmsSubject.next(this.films);
  }

  emitRealisateurs(){
    this.realisateursSubject.next(this.realisateurs);
  }


  findAllFilms():void{
    this.http.get<Film[]>(this.url).subscribe(
      {
        next: (value:Film[]) => this.films = value,
        error: (err: any) => console.error(err),
        complete : () => {
          console.log("Successfully got all films.");
          console.log(this.films);
          this.emitFilms();
        }
      }
    );
    
  }

  findFilmById(id:String) : Observable<Film> {
    return this.http.get<Film>(this.url+"/"+id);
  }

  findFilmByRealisateurId(id:String) : Observable<Film[]> {
    return this.http.get<Film[]>(this.url+"/real/"+id);
  }

  createFilm(titre:string, duree:number, idReal: number){
    this.http.post<any>(this.url, {titre : titre, duree : duree, realisateurId : idReal}).subscribe(
      {
        next: (value:Film) => this.films.push(value),
        error: (err: any) => console.error(err),
        complete : () => console.log("Successfully pushed film.")
      }
    );
    this.emitFilms();
  }

  deleteFilm(id:number){
    this.http.delete<any>(this.url+"/"+id).subscribe(
      {
        error: (err: any) => console.error(err),
        complete : () => {
          console.log("Successfully deleted film.");
          const index:number = this.films.findIndex(
            (film:Film) => film.id == id
          );
          this.films.splice(index, 1);
        }
      }
    );

    this.emitFilms();
  }

  editFilm(titre:string, duree:number, idReal: number, id:number){
    this.http.put<any>(this.url+"/"+id, {titre : titre, duree : duree, realisateurId : idReal}).subscribe(
      {
        next: (editedFilm:Film) => {
          const index:number = this.films.findIndex(
            (film:Film) => film.id == id
          );
          this.films.splice(index, 1, editedFilm);
          
        },
        error: (err: any) => {console.error(err);console.log("Error. Edited film data : (titre, duree, idReal, idFilm) " + titre + " " +  duree + " " + idReal + " " + id)},
        complete : () => {console.log("Successfully edited film.");console.log(titre + " " +  duree + " " + idReal + " " + id)}
      }
    );
    this.emitFilms();
  }


  findAllRealisateurs(){
    this.http.get<any>(this.url+"/real").subscribe(
      {
        next: (value:Realisateur[]) => {this.realisateurs = value},
        error: (err: any) => console.error(err),
        complete : () => {
          console.log("Successfully got all realisateurs.");
          console.log(this.realisateurs);
          this.emitRealisateurs();
        }
      }
    )
  }

  


}
