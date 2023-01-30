import javax.swing.*;
import java.awt.*;

import java.util.Random;

import java.rmi.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class FenetreJoueur extends JFrame {
    static int hauteur = 600;
    static int largeur = 950;
    JProgressBar progressBar = null;
    public Grille grille;

    public FenetreJoueur(){
        super("XxXx__TicTacToe__xXxX");
        Random r = new Random();
        char choix = (char)(r.nextInt(26) + 'A');
        Joueur jeSuisJoueur = new Joueur(choix);
        this.grille = new Grille();
        this.setLayout(new GridLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setSize(new Dimension(largeur,hauteur));
        //Création de la zone de chat
        AffichageChat zoneChat = new AffichageChat(jeSuisJoueur.getIcone());

        Job job = new Job(this.grille,jeSuisJoueur,this);
        ExecutorService executor = Executors.newSingleThreadExecutor();

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
                    //ExecutorService executor = Executors.newSingleThreadExecutor();
                    job.stop();
                    executor.execute(job);              
                }
            });
            panel.add(c);
        }
        panel.setPreferredSize(new Dimension(hauteur - 50,hauteur - 50));


        

        this.progressBar = new JProgressBar();
        this.progressBar.setPreferredSize(new Dimension(hauteur - 150, 25));
        this.resetProgressBar();

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.add(panel);
        panel2.add(this.progressBar);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayout(1,2));
        panel3.add(panel2);
        panel3.add(zoneChat);
        
        panel3.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));


        this.add(panel3);

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
                //ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(job);
            }
            

        }catch(Exception e){
            System.out.println("Impossible de joindre le serveur pour se connecter");
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

    public static void main(String[] args){
        new FenetreJoueur();
    }
}

