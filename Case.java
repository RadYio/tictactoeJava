import javax.swing.*;

public class Case extends JButton{
    Character etat = null;
    Case(){     
        this.addActionListener(e-> {
            if(this.etat == null){
                this.etat = Partie.getIcone();
                this.setText(this.etat.toString());            
            }
        });
    }
}
