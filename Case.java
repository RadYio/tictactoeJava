import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Case extends JButton{
    Joueur etat = new Joueur('F');;
    //M'en fou
    static int id = 0;
    int id_case;
    Case(){     
        Case actuel = this;
        id_case = id++;
        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println(id_case);
                //actuel.setBackground(Color.darkGray);
                actuel.setText(etat.getIcone().toString());
           }
        });      
    }
}
