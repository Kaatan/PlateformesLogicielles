import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Component } from '@angular/core';
import { FilmListComponent } from './components/film-list/film-list.component';
import { FilmDetailsComponent } from './components/film-details/film-details.component';
import { IndexComponent } from './components/index/index.component';
import { RealisateurListComponent } from './components/realisateur-list/realisateur-list.component';
import { RealisateurDetailsComponent } from './components/realisateur-details/realisateur-details.component';

const routes: Routes = [
  { path:"my-films", component:FilmListComponent},
  {path:"index", component:IndexComponent},
  {path:"", redirectTo : "/index", pathMatch: "full"},
  {path:"my-films/:id", component:FilmDetailsComponent},
  {path:"my-realisateurs", component:RealisateurListComponent},
  {path:"my-realisateurs/:id", component:RealisateurDetailsComponent},
  {path:"**", redirectTo : "/index", pathMatch: "full"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
