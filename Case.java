import javax.swing.*;

public class Case extends JButton{
    Character etat = null;
    Integer idCase;
    static Integer compteur = 0;

    Case(){
        this.idCase = compteur;
        compteur++;
    }

    public boolean identiques(Case c2){
        if(this.etat == null || c2.etat == null)return false;
        if(this.etat.equals(c2.etat))return true;
        return false;
    }
    
}
