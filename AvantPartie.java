import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class AvantPartie extends JFrame implements ActionListener{
    
    AvantPartie(){
        super("XxXx__TicTacToe__xXxX");
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(700,500));
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("il se passe un truc");
        
    }

    public static void main(String[] args){
        new AvantPartie();
    }
}
