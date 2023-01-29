import java.rmi.*;
import java.rmi.server.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ChatRemote extends UnicastRemoteObject implements InterfaceChat{
    private ArrayList<String> messages;

    public ChatRemote() throws RemoteException{
        super();
        messages = new ArrayList<String>();
        System.out.println("Objet ChatRemote cree");
        ajouterBDD("Bienvenue sur le chat du jeu", '*');
        ajouterBDD("Premier message tres important et tres long histoire de voir si ca rentre dans la fenetre ou pas", '*');
    }

    public boolean envoyerMessage(String message, Character j) throws RemoteException{
        System.out.println("Quelqu'un envoye un message au serveur");
        return ajouterBDD(message, j);
    }

    public ArrayList<String> recevoirMessage() throws RemoteException{
        //System.out.println("Quelqu'un demande la liste des messages");
        ArrayList<String> listeARenvoyer = new ArrayList<String>();

        for(String e: messages.subList(Math.max(messages.size()-10, 0), messages.size())){
            listeARenvoyer.add(e);
        }
        
        return listeARenvoyer;
    }

    private boolean ajouterBDD(String message, Character iconeJoueur){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String heureFormat = sdf.format(new Date());
        messages.add("(" + heureFormat + ") [" + iconeJoueur + "] -- " + message);
        System.out.println("Ajout de:__ " + heureFormat + "->" + message);
        return true;
    }
}
