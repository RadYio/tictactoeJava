import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import java.util.Random;

import java.rmi.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class FenetreJoueur extends JFrame {
    static int hauteur = 600;
    static int largeur = 950;
    public Grille grille;

    public FenetreJoueur(){
        super("XxXx__TicTacToe__xXxX");
        Random r = new Random();
        char choix = (char)(r.nextInt(26) + 'a');
        Joueur jeSuisJoueur = new Joueur(choix);
        this.grille = new Grille();
        this.setLayout(new GridLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setSize(new Dimension(largeur,hauteur));
        //Création de la zone de chat
        AffichageChat zoneChat = new AffichageChat();
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
            System.out.println("Premiere erreur de connexion");
            e.printStackTrace();
        }
        


    }

    public static void main(String[] args){
        new FenetreJoueur();
    }
}

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
                 System.out.println("retour "+temp);
            }

            //Si victoire il y a
            if(temp.equals(10)) System.out.println("Il a gagné");
            //Si c'est mon tour de jouer
            else{
                grille.getCase(temp).changeCarac(ServeurPartie.getAdvIcone(jeSuisJoueur.getIcone()));
                for(Case c:grille.listeDeCases){
                    if(c.etat == null){
                        c.setEnabled(true);
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("toujours pas ");
        }  
    }

}