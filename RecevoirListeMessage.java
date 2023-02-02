import java.rmi.*;

import java.util.ArrayList;

import java.io.File;
import javax.imageio.ImageIO;

import java.awt.*;
import javax.swing.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecevoirListeMessage implements Runnable{
    private static Integer delai = 1000;
    private static Integer nbDelaiTimeOut = 5;


    private Integer nbTry;

    private AffichageChat unChat;
    private ImageIcon iconeReconnect;

    /*
     * Constructeur de la classe Job
     * @param zoneChat: la zone de chat ou sont affichés les messages
     * @param boutonEnvoyer: le bouton qui permet d'envoyer un message
     * @param zoneSaisie: la zone de saisie des messages
     * @return void
     */
    public RecevoirListeMessage(AffichageChat leChat){
        this.unChat = leChat;
        this.nbTry = 0;

        this.iconeReconnect = new ImageIcon();
        try{
            Image img = ImageIO.read(new File("./assets/icon_network_fail.png"));
            this.iconeReconnect.setImage(img.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH));
        }catch(Exception e){
            System.out.println("Erreur");
        }
    }
    
    /*
     * run() est la méthode qui doit etre implémentée pour que la classe puisse être utilisée comme un Thread
     * @return void
     */
    public void run(){
        go(this.unChat);
    }

  
    private void go(AffichageChat leChat){
        //Partie reseau
        while(this.nbTry <= nbDelaiTimeOut){
            try{
                InterfaceChat ServeurMessage = (InterfaceChat) Naming.lookup(InterfaceAdresse.adresseConnexionChat);
                this.nbTry = 0;
                unChat.zoneChat.setText("");

                //On verifie si le bouton est passé dans l'état de reconnexion
                if(unChat.boutonEnvoyer.getIcon() != leChat.iconeEnvoyer){
                    unChat.boutonEnvoyer.setIcon(leChat.iconeEnvoyer);
                    unChat.boutonEnvoyer.addActionListener(leChat.actionEnvoyer);
                }

                ArrayList<String> messages = ServeurMessage.recevoirMessage();
                for(String message : messages){
                    unChat.zoneChat.append(message + "\n");
                }
                changerEtatInterface(true);
            }catch(Exception e){
                System.out.println ("Erreur d'accès à l'objet distant (" + this.nbTry + ")");
                changerEtatInterface(false);
                this.nbTry++;
            }
            try{ Thread.sleep(delai); } catch(Exception e){ System.out.println("La c'est la merde sur le thread"); }
            
        }
        JOptionPane.showMessageDialog(null, "Impossible de joindre le serveur pour le chat");
        unChat.boutonEnvoyer.removeActionListener(unChat.boutonEnvoyer.getActionListeners()[0]);
        //On change le bouton pour qu'il propose une reconnexion au lieu d'envoyer un message
        unChat.boutonEnvoyer.setIcon(this.iconeReconnect);
        System.out.println("je suis la");
        unChat.boutonEnvoyer.addActionListener(e -> {
            this.nbTry = 0;
            ExecutorService nouveau = Executors.newSingleThreadExecutor();
            nouveau.execute(new RecevoirListeMessage(unChat));
            unChat.boutonEnvoyer.removeActionListener(unChat.boutonEnvoyer.getActionListeners()[0]);
        });
        unChat.boutonEnvoyer.setEnabled(true);
    }

    /*
     * changerEtatInterface() permet de changer l'état de l'interface
     * @param etat: l'état de l'interface
     * @return void
     */
    private void changerEtatInterface(Boolean etat){
        System.out.println("Etat de l'interface: " + etat);
        unChat.zoneChat.setEnabled(etat);
        unChat.boutonEnvoyer.setEnabled(etat);
        unChat.messageAEnvoyer.setEnabled(etat);
    }

    

}
