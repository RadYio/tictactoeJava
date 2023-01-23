import java.rmi.*;

public class ReverseClient  {
    public static void main (String [] args){
        try{ 
            ReverseInterface rev = (ReverseInterface) Naming.lookup("rmi://77.132.100.204:1099/Reverse");
            String result = rev.reverseStringCustom(args [0]);
            System.out.println ("L'inverse de "+args[0]+" est "+result); 

        }catch (Exception e){ 
                System.out.println ("Erreur d'accès à l'objet distant.");
                System.out.println (e.toString());
        }
    }
}