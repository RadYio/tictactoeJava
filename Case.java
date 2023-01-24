import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Case extends JButton{
    Joueur etat = new Joueur('F');
    //M'en fou
    static int id = 0;
    int id_case;
    Case(){     
        id_case = id++;
        /* 
        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println(id_case);
                actuel.setText(etat.getIcone().toString());
           }
        });      
        */
        this.addActionListener(e-> {
            System.out.println(id_case);
            this.setText(etat.getIcone().toString());
        });
    }
}
