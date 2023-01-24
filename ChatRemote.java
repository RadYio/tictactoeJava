import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;

public class ChatRemote extends UnicastRemoteObject implements InterfaceChat{
    private ArrayList<String> messages;

    public ChatRemote() throws RemoteException{
        super();
        messages = new ArrayList<String>();
        System.out.println("Objet ChatRemote cree");
        messages.add("fesse");
        messages.add("big fesse");
    }

    public boolean envoyerMessage(String message) throws RemoteException{
        System.out.println("Message envoyé");
        return this.messages.add(message);
    }

    public ArrayList<String> recevoirMessage() throws RemoteException{
        System.out.println("Message demandé");
        return messages;
    }
}
