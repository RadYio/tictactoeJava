import java.rmi.*;
import java.rmi.server.*;
public class Reverse extends UnicastRemoteObject implements ReverseInterface{ 

    public Reverse() throws RemoteException {
        super();
    }

    public String reverseString(String chaineOrigine) throws RemoteException{

        int longueur = chaineOrigine.length();
        StringBuffer temp=new StringBuffer(longueur);

        for (int i=longueur; i>0; i--)
            temp.append(chaineOrigine.substring(i-1, i));

        return temp.toString();
    }

    public String reverseStringCustom(String chaineOrigine) throws RemoteException{

        int longueur = chaineOrigine.length();
        StringBuffer temp=new StringBuffer(longueur);

        for (int i=longueur; i>0; i--)
            temp.append(chaineOrigine.substring(i-1, i));

        System.out.println(temp.toString());
        return "Fesse".toString();
    }
}