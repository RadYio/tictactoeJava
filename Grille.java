import java.util.ArrayList;

/**
 * Classe Grille
 * @author BOULLIER Arthur
 * @author GONIN-SAGET Allan
 */

public class Grille{
    ArrayList<Case> listeDeCases;

    Grille(){
        listeDeCases = new ArrayList<Case>();
        for(int i=0;i<9;i++){
            listeDeCases.add(new Case());          
        }
    }

    /*
     * Méthode qui permet de récupérer une case de la grille
     * @param id: l'identifiant de la case
     * @return Case: la case correspondante
     */
    public Case getCase(Integer id){
        if(id.equals(9)){
            System.out.println("LA 9e case");
            return null;
        }
        return this.listeDeCases.get(id);
    }

    /*
     * Méthode qui permet de savoir si un joueur a gagné
     * @return boolean: vrai si un joueur a gagné, faux sinon
     */
    public boolean verificationVictoire(){
        // Vérifie les lignes horizontales
        for (int i = 0; i < 9; i += 3) {
            if (this.listeDeCases.get(i).identiques(this.listeDeCases.get(i + 1)) && this.listeDeCases.get(i + 1).identiques(this.listeDeCases.get(i + 2))) {
                return true;
            }
        }
        // Vérifie les colonnes verticales
        for (int i = 0; i < 3; i++) {
            if (this.listeDeCases.get(i).identiques(this.listeDeCases.get(i + 3)) && this.listeDeCases.get(i + 3).identiques(this.listeDeCases.get(i + 6))) {     
                return true;
            }
        }
        // Vérifie les diagonales
        if (this.listeDeCases.get(0).identiques(this.listeDeCases.get(4)) && this.listeDeCases.get(4).identiques(this.listeDeCases.get(8))) {
         return true;
        }
        if (this.listeDeCases.get(2).identiques(this.listeDeCases.get(4)) && this.listeDeCases.get(4).identiques(this.listeDeCases.get(6))) {
          return true;
        }
        return false;
    }

    /*
     * main de test
     */
    public static void main(String[] args){
        new Grille();
    }
}