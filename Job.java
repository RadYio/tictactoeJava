import java.rmi.*;

import javax.swing.*;

/*
 * Classe Job qui implémente Runnable et qui permet de jouer à distance via un serveur RMI
 * 
 * @author BOULLIER Arthur
 * @author GONIN-SAGET Allan
 * 
 */

public class Job implements Runnable{

    private Grille g;
    private Joueur j;

    /*
     * Constructeur de la classe Job
     * @param grille: la grille de jeu
     * @param jeSuisJoueur: le joueur qui est en attente
     * @return void
     */
    public Job(Grille grille,Joueur jeSuisJoueur){
        this.g = grille;
        this.j = jeSuisJoueur;
    }

    /*
     * run() est la méthode qui doit etre implémentée pour que la classe puisse être utilisée comme un Thread
     * @return void
     */
    public void run(){
        go(this.g, this.j);
    }
    
    /*
     * go() est la méthode qui sera éxécutée à chaque fois que le joueur sera en attente de l'autre joueur à distance
     * @param grille: la grille de jeu
     * @param jeSuisJoueur: le joueur qui est en attente
     * @return void
     */
    public void go(Grille grille, Joueur jeSuisJoueur){
        try{
            InterfacePartie ServeurPartie = (InterfacePartie) Naming.lookup("rmi://localhost:1099/Partie");
            
            //Desactivation de toutes les cases
            for(Case c:grille.listeDeCases) 
                c.setEnabled(false);

            //On attend que l'autre joueur joue via un serveur
            Integer temp;
            while((temp = ServeurPartie.monTour(jeSuisJoueur.getIcone())).equals(-1)){
                 Thread.sleep(1000);
                 System.out.println("Pas encore de retour -- "+ temp);
            }
            System.out.println("Retour:  "+ temp);

            //Si victoire
            if(temp >= 10) {
                grille.getCase(temp-10).changeCarac(ServeurPartie.iconeGagnant());
                JOptionPane.showMessageDialog(null, "Vous avez perdu !");
                System.exit(0);
            //Sinon un nouveau tour de jeu, donc on active les cases
            }else{
                grille.getCase(temp).changeCarac(ServeurPartie.getAdvIcone(jeSuisJoueur.getIcone()));
                for(Case c:grille.listeDeCases){
                    if(c.etat == null){
                        c.setEnabled(true);
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("Impossible de joindre le serveur pour jouer");
        }  
    }

}