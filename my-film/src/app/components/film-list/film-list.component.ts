import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { empty, firstValueFrom, Subscription } from 'rxjs';
import { getAllFilms } from 'src/app/external/functions';
import { Film } from 'src/app/models/film';
import { Realisateur } from 'src/app/models/realisateur';
import { FilmService } from 'src/app/services/film.service';
// import { NgbModalConfig, NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-film-list',
  templateUrl: './film-list.component.html',
  styleUrls: ['./film-list.component.css']
  // providers: [NgbModalConfig, NgbModal]
})
export class FilmListComponent implements OnInit {

  filmForm!: FormGroup;
  filmsSubscription:Subscription;
  realisateurSubscription:Subscription;
  editMode:boolean;
  actualFilm?:Film;

  selectedReal:string;

  public realisateurs: Realisateur[] = [];
  public list: Film[] = [];

  constructor(
    private service:FilmService,
    private formBuilder: FormBuilder
    
    ) { }

  initFilmForm():void{
    this.filmForm = this.formBuilder.group({
      titre : ['', Validators.required],
      duree : ['', Validators.required],
      idReal : ['', Validators.required]
    })
  }

  

  ngOnInit(): void {
    this.filmsSubscription = this.service.filmsSubject.subscribe({
      next: (films: Film[]) => this.list = films
     });
     this.realisateurSubscription = this.service.realisateursSubject.subscribe({
       next: (reals: Realisateur[]) => this.realisateurs = reals
     })
    this.service.findAllFilms();
    this.service.findAllRealisateurs();
    // console.log("realisateurs : " + this.realisateurs);
    // console.log("films : " + this.list);
    this.initFilmForm();
    
  }

  setEditMode(bool:boolean){
    this.editMode = bool;
    // console.log(this.editMode);
    

  }

  setFilm(id:number){
    // console.log(this.list);
    // console.log(id);
    this.actualFilm = this.list.find( (value:Film) => value.id == id);
    // console.log(this.actualFilm);
    this.filmForm.get('titre')?.setValue(this.actualFilm!.titre);
    this.filmForm.get('duree')?.setValue(this.actualFilm!.duree);
    this.filmForm.get('idReal')?.setValue(this.actualFilm!.realisateur.id);
  }

  deleteFilm(){
    this.service.deleteFilm(this.actualFilm!.id!);
  }

  onSubmit(){

    if(this.editMode){
      console.log("Edited Film : " + this.filmForm.value.titre + " " + this.filmForm.value.idReal);
      console.log("Id du film actuel : " + this.actualFilm!.id);
      this.service.editFilm(this.filmForm.value.titre, this.filmForm.value.duree, this.filmForm.value.idReal, this.actualFilm!.id);
      
    }
    else{
      console.log(this.filmForm.value);
      this.service.createFilm(this.filmForm.value.titre, this.filmForm.value.duree, this.filmForm.value.idReal);
      this.filmForm.reset();
    }
    
  }

}
