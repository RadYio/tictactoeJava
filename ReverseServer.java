import java.rmi.*;
import java.rmi.registry.*;
public class ReverseServer {
    static int port = 1099;
    public static void main(String[] args){
        
        //On lance le serveur RMI
        try{
            LocateRegistry.createRegistry(port);
            System.out.println("RMIregistry lance sur le port: (" + port + ")");}
        catch(
            RemoteException e){System.out.println("Erreur de creation du RMIregistry");
        }

        try {
            System.out.println( "Serveur : Construction de l'implementation");
            Reverse rev = new Reverse();
            System.out.println("Objet Reverse lie dans le RMIregistry");
            Naming.rebind("rmi://localhost:1099/Reverse", rev);
            System.out.println("Attente des invocations des clients ...");

        }catch (Exception e) {
            System.out.println("Erreur de liaison de l'objet Reverse");
            System.out.println(e.toString());
        }
    } // fin du main
} // fin de la classe