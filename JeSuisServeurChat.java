import java.rmi.*;
import java.rmi.registry.*;

public class JeSuisServeurChat {
    
    public static void main(String[] args){
        try{
            LocateRegistry.createRegistry(1099);
            System.out.println("RMIregistry lance sur le port: (" + 1099 + ")");
        }catch(
            RemoteException e){System.out.println("Erreur de creation du RMIregistry");
        }

        try {
            System.out.println( "Serveur : Construction de l'implementation");
            ChatRemote leChatDuJeu = new ChatRemote();
            PartieRemote MoteurDuJeu = new PartieRemote();

            System.out.println("Objet MoteurDuJeu lie dans le RMIregistry");
            Naming.rebind("rmi://localhost:1099/Partie", MoteurDuJeu);

            System.out.println("Objet leChatDuJeu lie dans le RMIregistry");
            Naming.rebind("rmi://localhost:1099/Chat", leChatDuJeu);
            
            System.out.println("Attente...");

        }catch (Exception e) {
            System.out.println("Erreur de liaison de l'objet peut etre pas");
            System.out.println(e.toString());
        }

    }
    

}
