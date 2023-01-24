import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceChat extends Remote{
    public boolean envoyerMessage(String message) throws RemoteException;

}
