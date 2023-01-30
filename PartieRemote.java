import java.rmi.RemoteException;
import java.rmi.server.*;

/* Classe PartieRemote
 * Implémente l'interface InterfacePartie
 * Gère la partie
 * @author BOULLIER Arthur
 * @author GONIN-SAGET Allan
 */
public class PartieRemote extends UnicastRemoteObject implements InterfacePartie{
    public Character joueur1;
    public Character joueur2;
    public Integer tour;
    public Grille laGrille;
    public Integer dernierCoup;
    private Character dernierGagnant;
    private ChatRemote petitChat;

    /* Constructeur de la classe PartieRemote
     * Initialise les variables de la classe
     */
    PartieRemote(ChatRemote petitChat) throws RemoteException{
        this.resetPartie();
        this.dernierGagnant = null;
        this.petitChat = petitChat;
    }
    
    public static void main(String[] args){
        //new PartieRemote();
    }

    /* Connexion d'un joueur à la partie puis envoi d'un message au chat pour indiquer que le joueur est connecté
     * Si le joueur est le premier à se connecter, il est joueur 1, sinon il est joueur 2
     * @return le numero du joueur (1 ou 2)
     * @return -1 si la partie est déjà en cours
     * @param j le joueur qui se connecte
     */
    @Override
    public Integer connexion(Character j) throws RemoteException {
        System.out.println("[SERVEUR] - Un joueur se connecte");
        if(this.joueur1 == null){
            this.joueur1 = j;
            this.petitChat.envoyerMessage("Joueur 1 vient de se connecter", j);
            return 1;
        }
        else if(this.joueur2 == null){
            this.joueur2 = j;
            this.petitChat.envoyerMessage("Joueur 2 vient de se connecter", j);
            this.resetPartie();
            this.petitChat.envoyerMessage("---", '*');
            this.petitChat.envoyerMessage("La partie commence", '*');
            return 2;
        }
        return -1;
    }

    /* Jouer un coup
     * @return 1 si le joueur a gagné
     * @return 0 sinon
     * @param i le numero de la case jouée
     */
    @Override
    public Integer jouer(Integer i, Character j) throws RemoteException {
        System.out.println("[SERVEUR] - "+ j +" joue "+i);
        this.tour++;
        this.dernierCoup = i;
        if(i.equals(9))return 0;
        this.laGrille.getCase(i).changeCarac(j);

        if(this.laGrille.verificationVictoire()){    
            this.dernierGagnant = j;
            this.supprimerJoueurs();
            petitChat.envoyerMessage("Le joueur " + j + " gagne la partie", '*');
            return 1;
        }
        return 0;
    }

    /* Récupérer la grille de la partie
     * @param j le joueur qui demande la grille
     * @return la grille de la partie
     */
    @Override
    public Integer monTour(Character j) throws RemoteException {
        
        if(this.laGrille.verificationVictoire()){
            System.out.println("[SERVEUR] - La partie est terminée");
            this.resetPartie();
            return (10+this.dernierCoup);
        } 
        if((tour % 2)==1 && this.joueur1.equals(j)) return this.dernierCoup;
        if((tour % 2)==0 && this.joueur2 != null && this.joueur2.equals(j)) return this.dernierCoup;
        return -1;
    }

    /* Récupérer l'icone adverse
     * @param j le joueur qui demande l'icone adverse
     * @return l'icone adverse
     */
    @Override
    public Character getAdvIcone(Character j) throws RemoteException{
        
        if(j.equals(this.joueur1))return this.joueur2;
        else return this.joueur1; 
    }

    /* Remise à 0 de la partie
     * @return void
     */
    public void resetPartie(){
        this.laGrille = new Grille();
        this.tour = 0;
        System.out.println("[SERVEUR] - Remise à 0 de la grille");
    }

    /* Suppression des joueurs de la partie
     * @return void
     */
    private void supprimerJoueurs(){
        this.joueur1 = null;
        this.joueur2 = null;
        System.out.println("[SERVEUR] - suppresion du lien des joueurs");
    }

    /* Récupérer l'icone du dernier gagnant
     * @return l'icone du dernier gagnant
     */
    @Override
    public Character iconeGagnant() throws RemoteException {
        return this.dernierGagnant;
    }

    

    
}