import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Chat extends JPanel{
    
    Chat(){

    }

    public static void main(String[] args){

        //gestion de la fenetre
        JFrame fenetre = new JFrame("Chat");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(300, 500);
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(false);

        //gestion du panel qui contiendra le chat et son bouton
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,3));
        fenetre.add(panel);

        //gestion de la zone de texte des messages deja envoyés
        JTextArea zoneChat = new JTextArea();
        zoneChat.setEditable(false);
        //changer la couleur de fond en gris(x)
        zoneChat.setBackground(Color.LIGHT_GRAY);
        panel.add(zoneChat);

        //gestion de la zone de texte pour envoyer un message et du bouton d'envoi du message à droite de la zone de texte
        JTextField messageAEnvoyer = new JTextField("Message à envoyer");
        panel.add(messageAEnvoyer);
        JButton boutonEnvoyer = new JButton("Envoyer");
        panel.add(boutonEnvoyer);

        

        
        //Fenetre visible
        fenetre.setVisible(true);
    }
}

