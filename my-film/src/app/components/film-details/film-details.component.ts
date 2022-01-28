import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { getFilmById, getRealisateurByNomPrenom } from 'src/app/external/functions';
import { Film } from 'src/app/models/film';
import { Realisateur } from 'src/app/models/realisateur';
import { FilmService } from 'src/app/services/film.service';

@Component({
  selector: 'app-film-details',
  templateUrl: './film-details.component.html',
  styleUrls: ['./film-details.component.css']
})
export class FilmDetailsComponent implements OnInit {

  public id:number = 0;
  public realisateur:Realisateur|undefined = {id:0, nom:'', prenom:'', dateNaissance:"", filmRealises:[], celebre:false };
  public film:Film|undefined;
  public exists:boolean = false;


  constructor(
    private route: ActivatedRoute, 
    private router: Router,
    private service:FilmService
    ) { 
      
   }

  ngOnInit(): void {
    this.route.paramMap.subscribe({
      next: (params: ParamMap) => {
      this.id = +params.get('id')!;
      
      this.service.findFilmById(this.id.toString()).subscribe(
        {
          
          next: (value:Film) => this.film = value,
          error: (err: any) => console.error(err),
          complete : () => {console.log("Successfully got film by id.");
            console.log(this.film);
              if (this.film!=undefined){
                this.exists=true;
                // Deux méthodes pour récupérer les films du réalisateurs : on peut récupérer le film, le réalisateur de ce film puis la liste
                // Ou on peut récupérer le film, le réalisateur, puis le nom du réalisateur et chercher à partir de là.
        
                
                // Méthode 1
                // this.realisateur=this.film?.realisateur;

                
                // Méthode 2
                this.realisateur = getRealisateurByNomPrenom(this.film?.realisateur?.nom!, this.film?.realisateur?.prenom!);
                
                // this.realisateur?.filmRealises.filter( (value:Film) =>  )
                this.realisateur!.filmRealises = this.realisateur!.filmRealises.filter( (value:Film) => {if (value.id!=this.film?.id) {return true} else {return false} })
                
              
              }
            },
          
          
        });

      }
     });
 
    
  }

}
