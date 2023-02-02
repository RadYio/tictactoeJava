import java.rmi.*;
import java.rmi.registry.*;

/*
 * Classe JeSuisServeur qui s'occupe de lancer le serveur de chat ainsi que de jeu.
 * @author BOULLIER Arthur
 * @author GONIN-SAGET Allan
 */
public class JeSuisServeur {

    public JeSuisServeur(){
        Integer port = InterfaceAdresse.port;
        try{
            //Remplace le lancement de rmiregistry
            LocateRegistry.createRegistry(port);
            System.out.println("RMIregistry lance sur le port: (" + port + ")");
        }catch(RemoteException e){
            System.out.println("Erreur de creation du RMIregistry sur le port: " + port);
        }

        try {
            System.out.println( "[Serveur] : Construction des objets distants");
            ChatRemote leChatDuJeu = new ChatRemote();
            PartieRemote MoteurDuJeu = new PartieRemote(leChatDuJeu);

            //On rend l'objet PartieRemote disponible à distance
            Naming.rebind("rmi://localhost:"+ port +"/Partie", MoteurDuJeu);
            System.out.println("[Serveur] : Objet MoteurDuJeu lie dans le RMIregistry");


            //On rend l'objet ChatRemote disponible à distance
            Naming.rebind("rmi://localhost:" + port + "/Chat", leChatDuJeu);
            System.out.println("[Serveur] : Objet leChatDuJeu lie dans le RMIregistry");
            
            System.out.println("[Serveur] : Attente d'invocation des clients...");

        }catch (Exception e) {
            System.out.println("[Serveur] : Erreur au moment de la liaison");
            System.out.println(e.toString());
        }

    }

    public static void main(String[] args){
        new JeSuisServeur();
    }
    

   

}
