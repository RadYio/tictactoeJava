import java.util.ArrayList;

public class Partie implements interfacePartie{
    ArrayList<Case> listeDeCases;
    Joueur joueur1;
    Joueur joueur2;
    Boolean partieEnCours = false;


    Partie(){
        listeDeCases = new ArrayList<Case>();
    }

    //Attention deux joueurs peuvent avoir le meme symbole, il faudra corriger
    public int nouveauJoueur(char choix){
        if(!partieEnCours){
            if(joueur1 == null){
                joueur1 = new Joueur(choix);
                return 1;
            }else if(joueur2 == null){
                joueur2 = new Joueur(choix);
                return 2;
            }else{
                return -1;
            }
        }
        return 0;
    }
}