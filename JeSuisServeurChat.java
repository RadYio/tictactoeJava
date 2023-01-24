import java.rmi.*;
import java.rmi.registry.*;

public class JeSuisServeurChat {
    static int port = 1099;
    
    public static void main(String[] args){
        try{
            LocateRegistry.createRegistry(port);
            System.out.println("RMIregistry lance sur le port: (" + port + ")");}
        catch(
            RemoteException e){System.out.println("Erreur de creation du RMIregistry");
        }

        try {
            System.out.println( "Serveur : Construction de l'implementation");
            ChatRemote leChatDuJeu = new ChatRemote();
            System.out.println("Objet leChatDuJeu lie dans le RMIregistry");
            Naming.rebind("rmi://localhost:1099/Chat", leChatDuJeu);
            System.out.println("Attente...");

        }catch (Exception e) {
            System.out.println("Erreur de liaison de l'objet leChatDuJeu");
            System.out.println(e.toString());
        }

    }
    

}
