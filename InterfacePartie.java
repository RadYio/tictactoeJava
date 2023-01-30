import java.rmi.Remote;
import java.rmi.RemoteException;

/* Interface InterfacePartie
 * Définit les méthodes utilisées par le client pour communiquer avec le serveur
 * @author BOULLIER Arthur
 * @author GONIN-SAGET Allan
 */
public interface InterfacePartie extends Remote{
    public Integer connexion(Character j) throws RemoteException;
    public Integer jouer(Integer i, Character j) throws RemoteException;
    public Integer monTour(Character j) throws RemoteException;
    public Character getAdvIcone(Character j) throws RemoteException;
    public Character iconeGagnant() throws RemoteException;
    public void resetPartie()throws RemoteException;

}


