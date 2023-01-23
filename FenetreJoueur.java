import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class FenetreJoueur extends JFrame {
    public Case[] cases;
    static int hauteur = 600;
    static int largeur = 950;

    public FenetreJoueur(){
        super("XxXx__TicTacToe__xXxX");
        this.setLayout(new BorderLayout());
        cases = new Case[9];
        this.setSize(new Dimension(largeur,hauteur));
        //chat
        /* 
        JTextArea zoneChat = new JTextArea();
        zoneChat.setPreferredSize(new Dimension(200, 400));
        zoneChat.setEditable(false);
        //changer la couleur de fond en gris
        zoneChat.setBackground(Color.LIGHT_GRAY);
        */

        Chat zoneChat = new Chat();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,3));
        for(int i=0;i<9;i++){
            cases[i] = new Case();          
            panel.add(cases[i]);
        }
        panel.setPreferredSize(new Dimension(hauteur - 50,hauteur - 50));

        
        JLabel north = new JLabel("");
        north.setPreferredSize(new Dimension(largeur,25));
        this.add(north, BorderLayout.NORTH);
        JLabel south = new JLabel("");
        south.setPreferredSize(new Dimension(largeur,25));
        this.add(south, BorderLayout.SOUTH);
        JLabel west = new JLabel("");
        west.setPreferredSize(new Dimension(25,hauteur));
        this.add(west, BorderLayout.WEST);
        JLabel east = new JLabel("");
        east.setPreferredSize(new Dimension(25,hauteur));
        this.add(east, BorderLayout.EAST);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1,2));
        panel2.add(panel);
        panel2.add(zoneChat);
        //panel.add(zoneChat, BorderLayout.EAST);
        this.add(panel2,BorderLayout.CENTER);
        //this.pack();
        this.setVisible(true);
        this.setResizable(false);
    }
    //A replacer 
    public boolean Victoire(){
        // Vérifie les lignes horizontales
        for (int i = 0; i < 3; i += 3) {
            if (cases[i].equals(cases[i + 1]) && cases[i + 1].equals(cases[i + 2])) {
                return true;
            }
        }
        // Vérifie les colonnes verticales
        for (int i = 0; i < 3; i++) {
            if (cases[i].equals(cases[i + 3]) && cases[i + 3].equals(cases[i + 6])) {
                return true;
            }
        }
        // Vérifie les diagonales
        if (cases[0].equals(cases[4]) && cases[4].equals(cases[8])) {
            return true;
        }
        if (cases[2].equals(cases[4]) && cases[4].equals(cases[6])) {
            return true;
        }
        return false;
    }

    public static void main(String[] args){
        new FenetreJoueur();

    }
}