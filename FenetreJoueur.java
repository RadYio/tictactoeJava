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
    JProgressBar progressBar = null;
    public Grille grille;

    /*
     * Constructeur de la classe FenetreJoueur
     */
    public FenetreJoueur(Joueur jeSuisJoueur){

        //choix du titre de la fenetre
        super("XxXx__TicTacToe__xXxX");

        //choix du layout de la fenetre
        this.setLayout(new GridLayout());

        //On decide de tout quitter si on ferme la fenetre (classique)
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //On definit la taille de la fenetre
        this.setSize(new Dimension(largeur,hauteur));

        //Création de la zone de chat
        AffichageChat zoneChat = new AffichageChat(jeSuisJoueur.getIcone());
        /////////
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
        ////////////
        AffichageJeu zoneJeu = new AffichageJeu(jeSuisJoueur);

    
        JPanel panelTotal = new JPanel();
        panelTotal.setLayout(new GridLayout(1,2));
        panelTotal.add(zoneJeu);
        panelTotal.add(zoneChat);
        panelTotal.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));

        //On ajoute nos deux zones a la fenetre
        this.add(panelTotal);
        ////////////////
        this.add(panel3);
        ////////////

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
        
        Random r = new Random();
        char choix = (char)(r.nextInt(26) + 'A');

        new FenetreJoueur(new Joueur(choix));
    }
}

