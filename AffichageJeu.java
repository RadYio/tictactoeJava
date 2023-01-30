import javax.swing.JPanel;

import javax.swing.*;
import java.awt.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.rmi.*;

public class AffichageJeu extends JPanel{
    
    public Grille grille;
    private Integer hauteur;

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
        this.setLayout(new GridLayout(3,3));

        //Création d'un thread pour plus tard
        ExecutorService executor = Executors.newSingleThreadExecutor();

        for(Case c:this.grille.listeDeCases){
            c.addActionListener(e-> {
                if(c.etat == null){   
                    c.changeCarac(jeSuisJoueur.getIcone());                 
                    try{
                        InterfacePartie ServeurPartie = (InterfacePartie) Naming.lookup("rmi://localhost:1099/Partie");
                        Integer gagner = ServeurPartie.jouer(c.idCase,jeSuisJoueur.getIcone());

                        if(gagner == 1){
                            JOptionPane.showMessageDialog(null, "Vous avez gagné !");
                            System.exit(0);
                        }
                        
                    }catch(Exception e2){
                        System.out.println("ne peut pas jouer");
                        e2.printStackTrace();
                    }
                    //lancement du job d'attente
                    executor.execute(new AttentePourJouer(this.grille,jeSuisJoueur));              
                }
            });
            this.add(c);
        }

        this.setPreferredSize(new Dimension(hauteur - 50, hauteur - 50));

        //Partie reseau
        Integer nbJoueur;
        try{
            InterfacePartie ServeurPartie = (InterfacePartie) Naming.lookup("rmi://localhost:1099/Partie");

            nbJoueur = ServeurPartie.connexion(jeSuisJoueur.getIcone());
            if(nbJoueur <= 0){
                System.out.println("Trop de joueurs connectés");
                JOptionPane.showMessageDialog(null, "Trop de joueurs connectés");
                System.exit(0);
            }

            //Si le joueur n'est pas le deuxieme joueur, il doit attendre
            if(nbJoueur == 1){
                executor.execute(new AttentePourJouer(this.grille,jeSuisJoueur));
            }

            //On annonce le numéro du joueur
            System.out.println("Vous etes le joueur " + nbJoueur);
            

        }catch(Exception e){
            System.out.println("Impossible de joindre le serveur pour se connecter");
            JOptionPane.showMessageDialog(null, "Impossible de joindre le serveur pour se connecter");
            System.exit(0);
        }
    }


}
