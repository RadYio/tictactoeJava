import javax.swing.*;
import java.awt.*;

import java.util.Random;

import java.rmi.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* Classe FenetreJoueur qui est une JFrame
 * @author BOULLIER Arthur
 * @author GONIN-SAGET Allan
 */
public class FenetreJoueur extends JFrame {
    
    //hautuer de la fenetre
    static int hauteur = 600;

    //largueur de la fenetre
    static int largeur = 950;

    //grille de jeu
    public Grille grille;

    /*
     * Constructeur de la classe FenetreJoueur
     */
    public FenetreJoueur(Character choix){
        super("XxXx__TicTacToe__xXxX");
        Joueur jeSuisJoueur = new Joueur(choix);
        this.grille = new Grille();
        this.setLayout(new GridLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setSize(new Dimension(largeur,hauteur));
        //Création de la zone de chat
        AffichageChat zoneChat = new AffichageChat(jeSuisJoueur.getIcone());
        //Création des cases
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,3));
        for(Case c:this.grille.listeDeCases){
            c.addActionListener(e-> {
                if(c.etat == null){   
                    c.changeCarac(jeSuisJoueur.getIcone());                 
                    try{
                        InterfacePartie ServeurPartie = (InterfacePartie) Naming.lookup("rmi://localhost:1099/Partie");
                        ServeurPartie.jouer(c.idCase,jeSuisJoueur.getIcone());  
                    }catch(Exception e2){
                        System.out.println("ne peut pas jouer");
                        e2.printStackTrace();
                    }
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.execute(new Job(this.grille,jeSuisJoueur));              
                }
            });
            panel.add(c);
        }
        panel.setPreferredSize(new Dimension(hauteur - 50,hauteur - 50));

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1,2));
        panel2.add(panel);
        panel2.add(zoneChat);
        panel2.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));


        this.add(panel2);

        UIManager.put("Label.font", new Font("Liberation Serif",Font.PLAIN,18));
        try {
            UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager
                        .getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                e.printStackTrace();
                System.out.println("Erreur dans le thème");
            }
        }
        SwingUtilities.updateComponentTreeUI(this);
        this.repaint();
        this.setVisible(true);
        this.setResizable(false);


        //Partie reseau
        Integer nbJoueur;
        try{
            InterfacePartie ServeurPartie = (InterfacePartie) Naming.lookup("rmi://localhost:1099/Partie");

            nbJoueur = ServeurPartie.connexion(jeSuisJoueur.getIcone());
            System.out.println("Vous etes le joueur " + nbJoueur);

            if(nbJoueur == 1){
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(new Job(this.grille,jeSuisJoueur));
            }
            

        }catch(Exception e){
            System.out.println("Impossible de joindre le serveur pour se connecter");
        }
        
    }

    public static void main(String[] args){
        Random r = new Random();
        char choix = (char)(r.nextInt(26) + 'A');
        new FenetreJoueur(choix);
    }
}

