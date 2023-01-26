import java.util.ArrayList;

public class Grille{
    ArrayList<Case> listeDeCases;

    Grille(){
        listeDeCases = new ArrayList<Case>();
        for(int i=0;i<9;i++){
            listeDeCases.add(new Case());          
        }
    }

    public boolean verificationVictoire(){
        // Vérifie les lignes horizontales
        for (int i = 0; i < 3; i += 3) {
            if (this.listeDeCases.get(i).identiques(this.listeDeCases.get(i + 1)) && this.listeDeCases.get(i + 1).identiques(this.listeDeCases.get(i + 2))) {
                return true;
            }
        }
        // Vérifie les colonnes verticales
        for (int i = 0; i < 3; i++) {
            if (this.listeDeCases.get(i).identiques(this.listeDeCases.get(i + 3)) && this.listeDeCases.get(i + 3).identiques(this.listeDeCases.get(i + 6))) {     
                System.out.println("Victoire");

                return true;
            }
        }
        // Vérifie les diagonales
        if (this.listeDeCases.get(0).identiques(this.listeDeCases.get(4)) && this.listeDeCases.get(4).identiques(this.listeDeCases.get(8))) {
            System.out.println("Victoire");

            return true;
        }
        if (this.listeDeCases.get(2).identiques(this.listeDeCases.get(4)) && this.listeDeCases.get(4).identiques(this.listeDeCases.get(6))) {
            System.out.println("Victoire");

            return true;
        }
        return false;
    }
    
    public static void main(String[] args){
        new Grille();
    }
}