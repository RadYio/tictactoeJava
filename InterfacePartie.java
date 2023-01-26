import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfacePartie extends Remote{
    public Integer connexion(Character j) throws RemoteException;
    public Integer jouer(Integer i, Character j) throws RemoteException;
    public Integer monTour(Character j) throws RemoteException;
}

