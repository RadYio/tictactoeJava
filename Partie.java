import java.util.ArrayList;

public class Partie implements interfacePartie{
    ArrayList<Case> listeDeCases;
    static Joueur joueur1;
    static Joueur joueur2;
    static int tour = 1;
    Boolean partieEnCours = false;


    Partie(){
        listeDeCases = new ArrayList<Case>();
        joueur1 = new Joueur('A');
        joueur2 = new Joueur('B');
        new FenetreJoueur();
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

    static Character getIcone(){
        if(tour == 1){
            tour = 2;
            return joueur1.getIcone();
        }
        tour = 1;
        return joueur2.getIcone();
    }

    public static void main(String[] args){
        new Partie();
    }
}