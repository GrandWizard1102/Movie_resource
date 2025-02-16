import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
class Check{
    Check(JButton login,JPanel bg,Highrated[] M,DataInputStream in,DataOutputStream out){
        if(!(login.getText().equals("Login"))){
            JButton[] Rate=new JButton[5];
            int[] x_pos={270,640,990,1340};
            for(int i=0;i<4;i++){
                Rate[i] = new JButton();
                Rate[i].setText("Rate");
                Rate[i].setBounds(x_pos[i],515,60,20);
                Rate[i].addActionListener(new RateActionListener(Rate[i],M[i].getTitle(),login.getText(),in,out));
                Rate[i].setBackground(Color.red);
                bg.add(Rate[i]);
            }
            bg.revalidate();
            bg.repaint();
        }
    }
}