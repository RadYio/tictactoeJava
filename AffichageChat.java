import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;



import java.io.File;
import java.awt.*;

import java.rmi.*;
import java.util.ArrayList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AffichageChat extends JPanel{
    static int largeur = 400;
    static int hauteur = 500;
    AffichageChat(){
        super();
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
                ServeurMessage.envoyerMessage(messageAEnvoyer.getText());
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


        //Partie reseau
        try{ 
            InterfaceChat ServeurMessage = (InterfaceChat) Naming.lookup("rmi://localhost:1099/Chat");
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.submit(() -> {
                while(true){
                    try{
                        zoneChat.setText("");
                        ArrayList<String> messages = ServeurMessage.recevoirMessage();
                        for(String message : messages){
                            zoneChat.append(message + "\n");
                        }
                    }catch(Exception e){
                        System.out.println("Erreur");
                        e.printStackTrace();

                    }
                    Thread.sleep(1000);
                }
            });
        }catch (Exception e){ 
                System.out.println ("Erreur d'accès à l'objet distant.");
                System.out.println (e.toString());
        }
    

    }

    public static void main(String[] args){

        //gestion de la fenetre
        JFrame fenetre = new JFrame("Chat");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(largeur, hauteur);
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(false);

        AffichageChat notreChat = new AffichageChat();
        
        fenetre.add(notreChat);
        //fenetre.pack();
        
    
        //Fenetre visible
        fenetre.setVisible(true);
    }
}
