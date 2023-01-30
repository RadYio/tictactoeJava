import java.rmi.*;

class Job implements Runnable{

    private Grille g;
    private Joueur j;
    private FenetreJoueur f;
    private boolean stop = false;

    public Job(Grille grille,Joueur jeSuisJoueur,FenetreJoueur fenetre){
        this.g = grille;
        this.j = jeSuisJoueur;
        this.f = fenetre;
    }

    
    

    public void stop() {
        stop = true;
    }

    public void run() {
        play(this.g,this.j);
    }


    public void go(Grille grille, Joueur jeSuisJoueur){
        try{
            InterfacePartie ServeurPartie = (InterfacePartie) Naming.lookup("rmi://localhost:1099/Partie");
            
            for(Case c:grille.listeDeCases) c.setEnabled(false);


            Integer temp;
            while((temp = ServeurPartie.monTour(jeSuisJoueur.getIcone())).equals(-1)){
                 Thread.sleep(1000);
                 System.out.println("Pas encore de retour -- "+ temp);
            }
            System.out.println("Retour:  "+ temp);

            //Si victoire il y a
            if(temp >= 10) {
                grille.getCase(temp-10).changeCarac(ServeurPartie.iconeGagnant());
                //ServeurPartie.resetPartie();
            //Si c'est mon tour de jouer
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
            //Compteur de temps de jeu d'un joueur
            Integer cpt = 0;
            while(!cpt.equals(10)){
                //Le joueur à joué
                if(stop){
                    stop = false;
                    System.out.println("je joue");
                    this.f.resetProgressBar();
                    return;
                }
                Thread.sleep(1000);
                this.f.avanceProgressBar();
                cpt++;
            }
            cpt = 0;
            //ServeurPartie.jouer(9,jeSuisJoueur.getIcone());
            this.f.resetProgressBar();
            //System.out.println("Trop tard");
            //go(grille,jeSuisJoueur);

        }
        catch(Exception e){
            System.out.println("Impossible de joindre le serveur pour jouer");
        }  
    }






    public void play(Grille grille, Joueur jeSuisJoueur){
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