import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class FenetreJoueur extends JFrame {
    static Case[] cases;
    static int hauteur = 600;
    static int largeur = 950;

    public FenetreJoueur(){
        super("XxXx__TicTacToe__xXxX");
        this.setLayout(new GridLayout());
        cases = new Case[9];
        this.setSize(new Dimension(largeur,hauteur));
        //Création de la zone de chat
        AffichageChat zoneChat = new AffichageChat();
        //Création des cases
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,3));
        for(int i=0;i<9;i++){
            cases[i] = new Case();          
            panel.add(cases[i]);
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
    }
    //A replacer 
    static boolean victoire(){
        // Vérifie les lignes horizontales
        for (int i = 0; i < 3; i += 3) {
            if (cases[i].identiques(cases[i + 1]) && cases[i + 1].identiques(cases[i + 2])) {
                return true;
            }
        }
        // Vérifie les colonnes verticales
        for (int i = 0; i < 3; i++) {
            if (cases[i].identiques(cases[i + 3]) && cases[i + 3].identiques(cases[i + 6])) {     
                System.out.println("Victoire");

                return true;
            }
        }
        // Vérifie les diagonales
        if (cases[0].identiques(cases[4]) && cases[4].identiques(cases[8])) {
            System.out.println("Victoire");

            return true;
        }
        if (cases[2].identiques(cases[4]) && cases[4].identiques(cases[6])) {
            System.out.println("Victoire");

            return true;
        }
        return false;
    }

    public static void main(String[] args){
        new FenetreJoueur();
    }
}