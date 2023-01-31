import javax.swing.*;
import java.awt.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.rmi.*;



public class AffichageJeu extends JPanel{
    
    public Grille grille;
    private Integer hauteur;
    public JProgressBar progressBar = null;

    public AffichageJeu(Joueur jeSuisJoueur){
        this.hauteur = 50;
        this.vraiCreation(jeSuisJoueur);
    }

    public AffichageJeu(Joueur jeSuisJoueur, Integer taille){
        this.hauteur = taille;
        this.vraiCreation(jeSuisJoueur);
    }

    public void vraiCreation(Joueur jeSuisJoueur){
        this.grille = new Grille();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        //Création d'un thread pour plus tard
        ExecutorService executor = Executors.newSingleThreadExecutor();
        AttentePourJouer attente = new AttentePourJouer(this.grille,jeSuisJoueur,this);
        for(Case c:this.grille.listeDeCases){
            c.addActionListener(e-> {
                if(c.etat == null){   
                    c.changeCarac(jeSuisJoueur.getIcone());                 
                    try{
                        InterfacePartie ServeurPartie = (InterfacePartie) Naming.lookup("rmi://localhost:1099/Partie");
                        Integer gagner = ServeurPartie.jouer(c.idCase,jeSuisJoueur.getIcone());

                        if(gagner == 1){
                            JOptionPane.showMessageDialog(this, "Vous avez gagné !");
                            System.exit(0);
                        }
                        
                    }catch(Exception e2){
                        System.out.println("ne peut pas jouer");
                        e2.printStackTrace();
                    }
                    //lancement du job d'attente
                    attente.stop();
                    executor.execute(attente);              
                }
            });
            panel.add(c);
        }
        this.add(panel);
        this.progressBar = new JProgressBar();
        this.progressBar.setPreferredSize(new Dimension(hauteur - 150, 25));
        this.resetProgressBar();
        this.add(this.progressBar);
        this.setPreferredSize(new Dimension(hauteur - 50, hauteur - 50));


        //Partie reseau
        InterfacePartie.retourConnexion retour;
        try{
            InterfacePartie ServeurPartie = (InterfacePartie) Naming.lookup("rmi://localhost:1099/Partie");

            retour = ServeurPartie.connexion(jeSuisJoueur.getIcone());
            
            switch(retour){
                case JOUEUR1:
                    System.out.println("Vous etes le joueur 1");
                    executor.execute(attente);
                    break;
                case JOUEUR2:
                    System.out.println("Vous etes le joueur 2");
                    break;

                case PARTIE_DEJA_COMMENCEE:
                case CARACTERE_IDENTIQUE:
                    //délégation de responsabilité
                    afficherMessageEtSortir(retour == InterfacePartie.retourConnexion.PARTIE_DEJA_COMMENCEE ? "Partie deja commencee" : "Caractere identique");
                    break;
            }

        }catch(Exception e){
            System.out.println("Impossible de joindre le serveur pour se connecter");
            JOptionPane.showMessageDialog(this, "Impossible de joindre le serveur pour se connecter");
            System.exit(0);
        }


    }

    public void resetProgressBar(){
        this.progressBar.setValue(0);
    }
    
    public void avanceProgressBar(){
        int currentValue = progressBar.getValue();
        int newValue = currentValue + (int)(progressBar.getMaximum() * 0.1);
        this.progressBar.setValue(newValue);
    }

    private void afficherMessageEtSortir(String message) {
        System.out.println(message);
        JOptionPane.showMessageDialog(this, message);
        System.exit(0);
    }


}
