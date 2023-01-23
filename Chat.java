import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
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


        Box b1 = new Box(BoxLayout.Y_AXIS);

        //gestion de la zone de texte des messages deja envoyés
        JTextArea zoneChat = new JTextArea();
        zoneChat.setEditable(false);
        zoneChat.setPreferredSize(new Dimension(300,400));

        //changer la couleur de fond en gris(x)
        zoneChat.setBackground(Color.LIGHT_GRAY);
        b1.add(zoneChat);
        Box b2 = new Box(BoxLayout.X_AXIS);

        //gestion de la zone de texte pour envoyer un message et du bouton d'envoi du message à droite de la zone de texte
        JTextField messageAEnvoyer = new JTextField("Message à envoyer");
        messageAEnvoyer.setPreferredSize(new Dimension(200,100));
        b2.add(messageAEnvoyer);

        
        //Petit trick pour avoir une image sur un bouton de la bonne taille
        ImageIcon iconeDeBonneTaille = new ImageIcon();
        try{
            Image img = ImageIO.read(new File("./assets/icon_send.png"));
            iconeDeBonneTaille.setImage(img.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH));
        }catch(Exception e){
            System.out.println("Erreur");
        }
        
        JButton boutonEnvoyer = new JButton(iconeDeBonneTaille);
        
        boutonEnvoyer.setPreferredSize(new Dimension(100, 100));
        b2.add(boutonEnvoyer);

        b1.add(b2);
        //gestion du panel qui contiendra le chat et son bouton
        JPanel panel = new JPanel();
        panel.add(b1);
        fenetre.add(b1);
        fenetre.pack();
















        /* 
        

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
        */
        

        
        //Fenetre visible
        fenetre.setVisible(true);
    }
}
