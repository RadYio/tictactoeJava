import javax.swing.*;

public class Case extends JButton{
    Character etat = null;
    Case(){     
        this.addActionListener(e-> {
            if(this.etat == null){
                this.etat = Partie.getIcone();
                this.setText(this.etat.toString());   
                FenetreJoueur.victoire();         
            }
        });
    }
    public boolean identiques(Case c2){
        if(this.etat == null || c2.etat == null)return false;
        if(this.etat.equals(c2.etat))return true;
        return false;
    }
    
}
