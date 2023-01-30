import javax.imageio.ImageIO;
import java.io.File;


import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;

import java.rmi.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/*
 * Classe AffichageChat qui est un JPanel
 * @author BOULLIER Arthur
 * @author GONIN-SAGET Allan
 */
public class AffichageChat extends JPanel{

    //largueur de la fenetre
    static int largeur = 400;

    //hauteur de la fenetre
    static int hauteur = 500;

    /*
     * Constructeur de la classe AffichageChat
     * @param j le joueur qui utilise l'interface
     */
    AffichageChat(Character j){
        Box boiteTotale = new Box(BoxLayout.Y_AXIS); 

        //gestion de la zone de texte des messages deja envoyés
        JTextArea zoneChat = new JTextArea();
        zoneChat.setEditable(false);
        zoneChat.setPreferredSize(new Dimension(largeur, hauteur - 100));
        zoneChat.setBackground(Color.LIGHT_GRAY);

        //On ajoute cette zoneChat a la boiteTotale
        boiteTotale.add(zoneChat);


        Box boiteMessageEtBouton = new Box(BoxLayout.X_AXIS);

        //gestion de la zone de texte pour envoyer un message et du bouton d'envoi du message à droite de la zone de texte
        JTextField messageAEnvoyer = new JTextField("Message à envoyer");
        messageAEnvoyer.setPreferredSize(new Dimension(largeur - 100, hauteur - 400));
        messageAEnvoyer.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                messageAEnvoyer.setText("");
            }
        });
        
    
        //Petit trick pour avoir une image sur un bouton de la bonne taille
        ImageIcon iconeDeBonneTaille = new ImageIcon();
        try{
            Image img = ImageIO.read(new File("./assets/icon_send.png"));
            iconeDeBonneTaille.setImage(img.getScaledInstance(100, hauteur - 400, java.awt.Image.SCALE_SMOOTH));
        }catch(Exception e){
            System.out.println("Erreur");
        }

        JButton boutonEnvoyer = new JButton(iconeDeBonneTaille);
        boutonEnvoyer.setPreferredSize(new Dimension(largeur - 300, hauteur - 400));
        boutonEnvoyer.addActionListener(e -> {
            try{ 
                InterfaceChat ServeurMessage = (InterfaceChat) Naming.lookup("rmi://localhost:1099/Chat");
                ServeurMessage.envoyerMessage(messageAEnvoyer.getText(), j);
                messageAEnvoyer.setText("");
            }catch (Exception ex){ 
                    System.out.println ("Erreur d'accès à l'objet distant.");
                    System.out.println (ex.toString());
            }
        });



        //On ajoute le messageAEnvoyer et le bouton a la boiteMessageEtBouton
        boiteMessageEtBouton.add(messageAEnvoyer);
        boiteMessageEtBouton.add(boutonEnvoyer);

        //On ajoute la boiteMessageEtBouton a la boiteTotale
        boiteTotale.add(boiteMessageEtBouton);

        this.add(boiteTotale);

        //On lance le thread qui va recevoir les messages
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new RecevoirListeMessage(zoneChat, boutonEnvoyer, messageAEnvoyer));
    

    }

    /*
     * Methode main de test
     */
    public static void main(String[] args){

        //gestion de la fenetre
        JFrame fenetre = new JFrame("Chat");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(largeur, hauteur);
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(false);

        AffichageChat notreChat = new AffichageChat('^');
        
        fenetre.add(notreChat);
        fenetre.pack();
        
    
        //Fenetre visible
        fenetre.setVisible(true);
    }
}
