import javax.swing.*;
import java.awt.*;

import java.util.Random;

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

        AffichageJeu zoneJeu = new AffichageJeu(jeSuisJoueur);

    
        JPanel panelTotal = new JPanel();
        panelTotal.setLayout(new GridLayout(1,2));
        panelTotal.add(zoneJeu);
        panelTotal.add(zoneChat);
        panelTotal.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));

        //On ajoute nos deux zones a la fenetre
        this.add(panelTotal);

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

