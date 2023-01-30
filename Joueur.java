/*
 * Classe Joueur : représente un joueur
 * @author BOULLIER Arthur
 * @author GONIN-SAGET Allan
 */

public class Joueur{
    private Character icone;

    public Joueur(Character choix){
        this.icone = choix;
    }

    public Character getIcone(){
        return this.icone;
    }
}
