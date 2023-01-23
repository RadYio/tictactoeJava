import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class FenetreJoueur extends JFrame {
    public Case[] cases;


    public FenetreJoueur(){
        super("XxXx__TicTacToe__xXxX");
        this.setLayout(new BorderLayout());
        cases = new Case[9];
        
        //chat
        JTextArea zoneChat = new JTextArea();
        zoneChat.setPreferredSize(new Dimension(200, 400));
        zoneChat.setEditable(false);
        //changer la couleur de fond en gris
        zoneChat.setBackground(Color.LIGHT_GRAY);


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,3));
        for(int i=0;i<9;i++){
            cases[i] = new Case();          
            panel.add(cases[i]);
        }
        panel.setPreferredSize(new Dimension(400,400));
        this.add(new JLabel("Le jeu"), BorderLayout.NORTH);
        this.add(new JLabel("   "), BorderLayout.SOUTH);
        this.add(new JLabel("   "), BorderLayout.EAST);
        this.add(zoneChat, BorderLayout.EAST);
        this.add(new JLabel("   "), BorderLayout.WEST);
        this.add(panel,BorderLayout.CENTER);
        this.setSize(new Dimension(700,500));
        this.setVisible(true);
        this.setResizable(false);
    }

    public static void main(String[] args){
        new FenetreJoueur();
    }
}