import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface InterfaceChat extends Remote{
    public boolean envoyerMessage(String message, Character j) throws RemoteException;
    public ArrayList<String> recevoirMessage() throws RemoteException;
}
