import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class Interface extends JFrame implements ActionListener{
    public JButton[][] cases;


    public Interface(){
        super("Test");
        this.setLayout(new BorderLayout());
        cases = new JButton[3][3];
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,3));
        for(int i=0;i<3;i++){
            for(int y=0;y<3;y++){
                cases[i][y] = new JButton(i+"x"+y);
                cases[i][y].addActionListener(this);
                panel.add(cases[i][y]);
            }
        }
        panel.setSize(new Dimension(300,300));
        this.add(new Label("Le jeu"), BorderLayout.NORTH);
        this.add(new Label(), BorderLayout.SOUTH);
        this.add(new Label(), BorderLayout.EAST);
        this.add(new Label(), BorderLayout.WEST);
        this.add(panel,BorderLayout.CENTER);
        this.setSize(new Dimension(500,500));
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        System.out.println("yay");
    }

    public static void main(String[] args){
        new Interface();
    }
}