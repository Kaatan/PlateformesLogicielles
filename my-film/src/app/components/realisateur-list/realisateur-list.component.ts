import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { empty, firstValueFrom, Subscription } from 'rxjs';
import { Film } from 'src/app/models/film';
import { Realisateur } from 'src/app/models/realisateur';
import { FilmService } from 'src/app/services/film.service';
// import { NgbModalConfig, NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-realisateur-list',
  templateUrl: './realisateur-list.component.html',
  styleUrls: ['./realisateur-list.component.css']
  // providers: [NgbModalConfig, NgbModal]
})
export class RealisateurListComponent implements OnInit {

  realForm!: FormGroup;
  filmsSubscription:Subscription;
  realisateurSubscription:Subscription;
  editMode:boolean;
  actualReal?:Realisateur;

  selectedReal:string;

  // public realisateurs: Realisateur[] = [];
  public list: Realisateur[] = [];

  constructor(
    private service:FilmService,
    private formBuilder: FormBuilder
    
    ) { }

  initRealForm():void{
    this.realForm = this.formBuilder.group({
      nom : ['', Validators.required],
      prenom : ['', Validators.required]
    })
  }

  

  ngOnInit(): void {
    // this.filmsSubscription = this.service.filmsSubject.subscribe({
    //   next: (films: Film[]) => this.list = films
    //  });
    this.realisateurSubscription = this.service.realisateursSubject.subscribe({
       next: (reals: Realisateur[]) => this.list = reals
     })
    // this.service.findAllFilms();
    this.service.findAllRealisateurs();
    // console.log("realisateurs : " + this.realisateurs);
    // console.log("films : " + this.list);
    this.initRealForm();
    
  }

  setEditMode(bool:boolean){
    this.editMode = bool;
    // console.log(this.editMode);
    

  }

  setReal(id:number){
    // console.log(this.list);
    // console.log(id);
    this.actualReal = this.list.find( (value:Realisateur) => value.id == id);
    // console.log(this.actualFilm);
    this.realForm.get('nom')?.setValue(this.actualReal!.nom);
    this.realForm.get('prenom')?.setValue(this.actualReal!.prenom);
  }

//   deleteReal(){
//     this.service.deleteReal(this.actualReal!.id!);
//   }

//   onSubmit(){

//     if(this.editMode){
//       console.log("Edited Real : " + this.realForm.value.titre + " " + this.realForm.value.idReal);
//       console.log("Id du film actuel : " + this.actualReal!.id);
//       this.service.editReal(this.realForm.value.titre, this.realForm.value.duree, this.realForm.value.idReal, this.actualReal!.id);
      
//     }
//     else{
//       console.log(this.realForm.value);
//       this.service.createReal(this.realForm.value.titre, this.realForm.value.duree, this.realForm.value.idReal);
//       this.realForm.reset();
//     }
    
//   }

}
