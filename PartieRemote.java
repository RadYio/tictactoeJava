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
        this.resetPartie();
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
     * @return -1 si la partie n'existe plus
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

    @Override
    public Integer monTour(Character j) throws RemoteException {
        
        if(this.laGrille.verificationVictoire()){
            //this.resetPartie();
            return (10+this.dernierCoup);
        } 
        if((tour % 2)==1 && this.joueur1.equals(j)) return this.dernierCoup;
        if((tour % 2)==0 && this.joueur2 != null && this.joueur2.equals(j)) return this.dernierCoup;
        return -1;
    }

    @Override
    public Character getAdvIcone(Character j) throws RemoteException{
        
        if(j.equals(this.joueur1))return this.joueur2;
        else return this.joueur1; 
    }

    public void resetPartie(){
        this.laGrille = new Grille();
        this.tour = 0;
        System.out.println("[SERVEUR] - Remise à 0 de la grille");
    }

    private void supprimerJoueurs(){
        this.joueur1 = null;
        this.joueur2 = null;
        System.out.println("[SERVEUR] - suppresion du lien des joueurs");
    }

    @Override
    public Character iconeGagnant() throws RemoteException {
        return this.dernierGagnant;
    }

    

    
}