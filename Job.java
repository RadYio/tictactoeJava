import java.rmi.*;

class Job implements Runnable{

    private Grille g;
    private Joueur j;

    public Job(Grille grille,Joueur jeSuisJoueur){
        this.g = grille;
        this.j = jeSuisJoueur;
    }

    public void run(){
        go(this.g, this.j);
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
                ServeurPartie.resetPartie();
            //Si c'est mon tour de jouer
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