import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


/**
 * Interface InterfaceChat
 * Définit les méthodes utilisées par le client pour communiquer avec le serveur
 * @author BOULLIER Arthur
 * @author GONIN-SAGET Allan
 */
public interface InterfaceChat extends Remote{
    public boolean envoyerMessage(String message, Character j) throws RemoteException;
    public ArrayList<String> recevoirMessage() throws RemoteException;
}
