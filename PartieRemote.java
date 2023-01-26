import java.rmi.RemoteException;
import java.rmi.*;
import java.rmi.server.*;

public class PartieRemote extends UnicastRemoteObject implements InterfacePartie{
    public Character joueur1;
    public Character joueur2;
    public Integer tour;
    public Grille laGrille;
    public Integer dernierCoup;


    PartieRemote() throws RemoteException{
        this.laGrille = new Grille();
        this.tour = 0;
    }

    public static void main(String[] args){
        //new PartieRemote();
    }

    /* Connexion d'un joueur
     * Si le joueur est le premier à se connecter, il est joueur 1, sinon il est joueur 2
     * @return le numero du joueur
     * @return -1 si la partie est déjà en cours
     * @param j le joueur qui se connecte
     */
    @Override
    public Integer connexion(Character j) throws RemoteException {
        System.out.println("Un joueur se connecte");
        if(joueur1 == null){
            joueur1 = j;
            return 1;
        }
        else if(joueur2 == null){
            joueur2 = j;
            return 2;
        }
        else
            return -1;
    }

    /* Jouer un coup
     * @return 1 si le joueur a gagné
     * @return -1 si la partie n'existe plus
     * @param i le numero de la case jouée
     */
    @Override
    public Integer jouer(Integer i, Character j) throws RemoteException {
        System.out.println(j +" joue");
        this.laGrille.listeDeCases.get(i).etat = j;
        this.dernierCoup = i;
        this.tour++;
        if(this.laGrille.verificationVictoire())
            return 1;
        return 0;
    }

    @Override
    public Integer monTour(Character j) throws RemoteException {
        System.out.println(j + " demande");
        if(this.laGrille.verificationVictoire())return 10;
        else if((tour % 2)==1 && this.joueur1.equals(j))return this.dernierCoup;
        return -1;
    }

    

    
}