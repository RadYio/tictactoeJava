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
        go(this.g,this.j);
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
                grille.getCase(temp).changeCarac(ServeurPartie.getAdvIcone(jeSuisJoueur.getIcone()));
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
            System.out.println("Trop tard");

        }
        catch(Exception e){
            System.out.println("Impossible de joindre le serveur pour jouer");
        }  
    }

}