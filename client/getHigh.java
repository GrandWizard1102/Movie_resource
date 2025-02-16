
import java.io.DataInputStream;
import java.io.DataOutputStream;

import javax.swing.JOptionPane;
public class getHigh {
    String[] links=new String[5];
    String[] Movie_title=new String[5];
    getHigh(DataInputStream in,DataOutputStream out) {
    try{
            out.writeUTF("h");
            for(int i=0;i<5;i++){
              Movie_title[i]=in.readUTF();
              links[i]=in.readUTF();
          }

}
catch(Exception e){
    JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
}
 }
    public String[] get_Link(){
        return links;
    }
    public String[] get_title(){
        return Movie_title;
    }
}
