import java.rmi.RemoteException;
import java.rmi.server.*;


public class PartieRemote extends UnicastRemoteObject implements InterfacePartie{
    public Character joueur1;
    public Character joueur2;
    public Integer tour;
    public Grille laGrille;
    public Integer dernierCoup;
    private Character dernierGagnant;
    private ChatRemote petitChat;


    PartieRemote(ChatRemote petitChat) throws RemoteException{
        resetPartie();
        this.dernierGagnant = null;
        this.petitChat = petitChat;
    }

    public static void main(String[] args){
        //new PartieRemote();
    }

    /* Connexion d'un joueur
     * Si le joueur est le premier à se connecter, il est joueur 1, sinon il est joueur 2
     * @return le numero du joueur (1 ou 2)
     * @return -1 si la partie est déjà en cours
     * @param j le joueur qui se connecte
     */
    @Override
    public Integer connexion(Character j) throws RemoteException {
        System.out.println("[SERVEUR] - Un joueur se connecte");
        if(joueur1 == null){
            joueur1 = j;
            petitChat.envoyerMessage("Joueur 1 vient de se connecter", '*');
            return 1;
        }
        else if(joueur2 == null){
            joueur2 = j;
            petitChat.envoyerMessage("Joueur 2 vient de se connecter", '*');
            resetPartie();
            petitChat.envoyerMessage("-------", '*');
            petitChat.envoyerMessage("La partie commence", '*');
            return 2;
        }
        return -1;
    }

    /* Jouer un coup
     * @return 1 si le joueur a gagné
     * @return -1 si la partie n'existe plus
     * @param i le numero de la case jouée
     */
    @Override
    public Integer jouer(Integer i, Character j) throws RemoteException {
        System.out.println("[SERVEUR] - "+ j +" joue");
        this.laGrille.getCase(i).changeCarac(j);
        this.dernierCoup = i;
        this.tour++;
        if(this.laGrille.verificationVictoire()){    
            this.dernierGagnant = j;
            supprimerJoueurs();
            return 1;
        }
            
        return 0;
    }

    @Override
    public Integer monTour(Character j) throws RemoteException {
        //System.out.println(j + " demande pour tour"+this.tour);
        if(this.laGrille.verificationVictoire()) return (10+this.dernierCoup);
        if((tour % 2)==1 && this.joueur1.equals(j)) return this.dernierCoup;
        if((tour % 2)==0 && joueur2 != null && this.joueur2.equals(j)) return this.dernierCoup;
        return -1;
    }

    @Override
    public Character getAdvIcone(Character j) throws RemoteException{
        
        if(j.equals(this.joueur1))return joueur2;
        else return joueur1; 
    }

    public void resetPartie(){
        this.laGrille = new Grille();
        this.tour = 0;
        System.out.println("[SERVEUR] - Remise à 0 de la grille");
    }

    private void supprimerJoueurs(){
        joueur1 = null;
        joueur2 = null;
        System.out.println("[SERVEUR] - suppresion du lien des joueurs");
    }

    @Override
    public Character iconeGagnant() throws RemoteException {
        return this.dernierGagnant;
    }

    

    
}