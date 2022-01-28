import { Film } from "./film";

export interface Realisateur {
    id : number, 
    nom : String, 
    prenom : String, 
    dateNaissance : String, 
    filmRealises : Film[],
    celebre : boolean

}
