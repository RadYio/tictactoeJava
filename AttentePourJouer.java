import java.rmi.*;


/*
 * Classe Job qui implémente Runnable et qui permet de jouer à distance via un serveur RMI
 * 
 * @author BOULLIER Arthur
 * @author GONIN-SAGET Allan
 * 
 */

public class AttentePourJouer implements Runnable{

    private Grille g;
    private Joueur j;
    private AffichageJeu f;
    private boolean stop = false;

    /*
     * Constructeur de la classe Job
     * @param grille: la grille de jeu
     * @param jeSuisJoueur: le joueur qui est en attente
     * @return void
     */
    public AttentePourJouer(Grille grille,Joueur jeSuisJoueur,AffichageJeu affichage){
        this.g = grille;
        this.j = jeSuisJoueur;
        this.f = affichage;
    }

    /*
     * run() est la méthode qui doit etre implémentée pour que la classe puisse être utilisée comme un Thread
     * @return void
     */
    public void run(){
        go(this.g, this.j);
    }
    
   
    public void stop() {
        stop = true;
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
    
            
    
            while(true){
                for(Case c:grille.listeDeCases) c.setEnabled(false);
                Integer temp;
                while((temp = ServeurPartie.monTour(jeSuisJoueur.getIcone())).equals(-1)){
                     Thread.sleep(1000);
                     System.out.println("No response yet -- "+ temp);
                }
                System.out.println("Response:  "+ temp);
    
                //If victory
                if(temp >= 10) {
                    grille.getCase(temp-10).changeCarac(ServeurPartie.iconeGagnant());
                    //ServeurPartie.resetPartie();
                //If it's my turn to play
                }else{
                    if(!temp.equals(9)){
                        grille.getCase(temp).changeCarac(ServeurPartie.getAdvIcone(jeSuisJoueur.getIcone()));
                    }
                    for(Case c:grille.listeDeCases){
                        if(c.etat == null){
                            c.setEnabled(true);
                        }
                    }
                }
                //Player's play time counter
                Integer cpt = 0;
                while(!cpt.equals(10)){
                    //The player has played
                    if(stop){                 
                        System.out.println("I play");
                        this.f.resetProgressBar();
                        break;
                    }
                    Thread.sleep(1000);
                    this.f.avanceProgressBar();
                    cpt++;
                }
                if(!stop){
                    ServeurPartie.jouer(9,jeSuisJoueur.getIcone());                   
                    System.out.println("Too late");
                }
                stop = false;
                cpt = 0;
                this.f.resetProgressBar();
                
            }
        }
        catch(Exception e){
            System.out.println("Unable to join server to play");
        }  
    }
}