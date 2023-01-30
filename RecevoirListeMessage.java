import java.rmi.*;

import java.util.ArrayList;

import javax.swing.*;

public class RecevoirListeMessage implements Runnable{
    private static Integer delai = 1000;
    private static Integer nbDelaiTimeOut = 10;


    private Integer nbTry;
    private JTextArea zoneChat;
    private JButton boutonEnvoyer;
    private JTextField zoneSaisie;

    /*
     * Constructeur de la classe Job
     * @param zoneChat: la zone de chat ou sont affichés les messages
     * @param boutonEnvoyer: le bouton qui permet d'envoyer un message
     * @param zoneSaisie: la zone de saisie des messages
     * @return void
     */
    public RecevoirListeMessage(JTextArea zoneChat, JButton boutonEnvoyer, JTextField zoneSaisie){
        this.zoneChat = zoneChat;
        this.boutonEnvoyer = boutonEnvoyer;
        this.zoneSaisie = zoneSaisie;
        this.nbTry = 0;
    }
    
    /*
     * run() est la méthode qui doit etre implémentée pour que la classe puisse être utilisée comme un Thread
     * @return void
     */
    public void run(){
        go(this.zoneChat, this.boutonEnvoyer, this.zoneSaisie);
    }

    /*
     * go() est la méthode qui sera éxécutée à chaque fois que le joueur sera en attente de l'autre joueur à distance
     * @param zoneChat: la zone de chat ou sont affichés les messages
     * @param boutonEnvoyer: le bouton qui permet d'envoyer un message
     * @param zoneSaisie: la zone de saisie du message
     * @return void
     */
    private void go(JTextArea zoneChat, JButton boutonEnvoyer, JTextField zoneSaisie){
        //Partie reseau
        while(this.nbTry <= nbDelaiTimeOut){
            try{
                InterfaceChat ServeurMessage = (InterfaceChat) Naming.lookup("rmi://localhost:1099/Chat");
                this.nbTry = 0;
                zoneChat.setText("");
                ArrayList<String> messages = ServeurMessage.recevoirMessage();
                for(String message : messages){
                    zoneChat.append(message + "\n");
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

    }

    /*
     * changerEtatInterface() permet de changer l'état de l'interface
     * @param etat: l'état de l'interface
     * @return void
     */
    private void changerEtatInterface(Boolean etat){
        System.out.println("Etat de l'interface: " + etat);
        this.zoneChat.setEnabled(etat);
        this.boutonEnvoyer.setEnabled(etat);
        this.zoneSaisie.setEnabled(etat);
    }

    

}
