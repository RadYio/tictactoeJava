import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class Interface extends JFrame implements ActionListener{
    public Case[] cases;


    public Interface(){
        super("Test");
        this.setLayout(new BorderLayout());
        cases = new Case[9];
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,3));
        for(int i=0;i<9;i++){
            cases[i] = new Case();
            cases[i].addActionListener(this);
            panel.add(cases[i]);
        }
        panel.setPreferredSize(new Dimension(400,400));
        this.add(new JLabel("Le jeu"), BorderLayout.NORTH);
        this.add(new JLabel(), BorderLayout.SOUTH);
        this.add(new JLabel(), BorderLayout.EAST);
        this.add(new JLabel(), BorderLayout.WEST);
        this.add(panel,BorderLayout.CENTER);
        this.setSize(new Dimension(500,500));
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        System.out.println("yay");
    }

    public static void main(String[] args){
        new Interface();
    }
}