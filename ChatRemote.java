import java.rmi.*;
import java.rmi.server.*;
import java.text.SimpleDateFormat;
import java.util.*;

/* Classe ChatRemote
 * Implémente l'interface InterfaceChat
 * Gère le chat
 * @author BOULLIER Arthur
 * @author GONIN-SAGET Allan
 */

public class ChatRemote extends UnicastRemoteObject implements InterfaceChat{
    
    // Nombre de messages à afficher dans le chat
    private static Integer nbMessageDansLeChat = 15;
    // Liste des messages
    private ArrayList<String> messages;

    /*
     * Constructeur de la classe ChatRemote
     * Initialise les variables de la classe
     */
    public ChatRemote() throws RemoteException{
        super();
        messages = new ArrayList<String>();
        System.out.println("Objet ChatRemote cree");
        ajouterBDD("Bienvenue sur le chat du jeu", '*');
        ajouterBDD("Premier message tres important et tres long histoire de voir si ca rentre dans la fenetre ou pas", '*');
    }

    /*
     * Envoi un message au chat
     * @return true si le message a bien été envoyé
     * @param message le message à envoyer
     * @param j le joueur qui envoie le message
     */
    public boolean envoyerMessage(String message, Character j) throws RemoteException{
        System.out.println("Quelqu'un envoye un message au serveur");
        return ajouterBDD(message, j);
    }

    /*
     * Récupère les derniers messages du chat
     * @return la liste des messages
     */
    public ArrayList<String> recevoirMessage() throws RemoteException{
        //System.out.println("Quelqu'un demande la liste des messages");
        ArrayList<String> listeARenvoyer = new ArrayList<String>();

        for(String e: messages.subList(Math.max(messages.size()-nbMessageDansLeChat, 0), messages.size())){
            listeARenvoyer.add(e);
        }
        
        return listeARenvoyer;
    }

    /*
     * Ajoute un message à la liste des messages
     * @return true si le message a bien été ajouté
     * @param message le message à ajouter
     * @param iconeJoueur l'icone du joueur qui envoie le message
     */
    private boolean ajouterBDD(String message, Character iconeJoueur){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String heureFormat = sdf.format(new Date());
        messages.add("(" + heureFormat + ") [" + iconeJoueur + "] -- " + message);
        System.out.println("Ajout de:__ " + heureFormat + "->" + message);
        return true;
    }
}
