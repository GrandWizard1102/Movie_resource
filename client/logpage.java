import javax.swing.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.awt.*;
class logpage {
    JFrame f3=new JFrame("Login");
    JButton L,S;
    logpage(JButton login,JPanel backgroundPanel,Highrated[] M,DataInputStream in,DataOutputStream out) throws IOException{



        f3.setBounds(600,100,500,350);
        f3.setLayout(null);
        f3.setVisible(true);
        f3.setForeground(Color.BLACK);

        JLabel u=new JLabel("Username:");
        JLabel p=new JLabel("Password:");
        u.setFont(new Font("Arial",Font.BOLD,16));
        p.setFont(new Font("Arial",Font.BOLD,16));

        JTextField uname=new JTextField();
        JPasswordField upass=new JPasswordField();
        upass.setEchoChar('*');

        L=new JButton("Login");
        S=new JButton("Sign up");

        L.setBounds(125,220,100,30);
        S.setBounds(235,220,100,30);
        uname.setBounds(200,87,150,20);
        upass.setBounds(200,165-30,150,20);
        u.setBounds(100,70,100,50);
        p.setBounds(100,150-30,100,50);
        
        L.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    out.writeUTF("l");
                    char[] message =upass.getPassword();
                    
                    out.writeUTF(new String(message));
                    out.writeUTF(uname.getText());  
                    out.flush();

                    String det=in.readUTF();
                    System.out.println("Recieved:"+det);
                    if(det.equals("The given details are wrong")){
                        JOptionPane.showMessageDialog(null,"The given details are wrong!","Error",JOptionPane.ERROR_MESSAGE);
                        f3.setVisible(false);
                    }
                    else{
                        f3.setVisible(false);
                        login.setText(det);
                    }
                    new Check(login,backgroundPanel,M,in,out);
                } catch (Exception a) {
                    System.out.println(a.getMessage());
                }
            }
        });
        S.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JFrame f4=new JFrame("Sign up");
                f4.setBounds(600,100,500,350);
                f4.setLayout(null);
                f3.setVisible(false);
                f4.setVisible(true);
                f4.setForeground(Color.BLACK);
                
                JLabel Sete=new JLabel("Enter new Email:");
                JLabel Setn=new JLabel("Enter new username:");
                JLabel Setp=new JLabel("Set new Password:");
                S=new JButton("Submit");
                JTextField Semail=new JTextField();
                JTextField Suname=new JTextField();
                JTextField Spass=new JTextField();


                Sete.setBounds(70,50,150,50);
                Setp.setBounds(70,150,150,50);
                Setn.setBounds(50,100,150,50);


                Suname.setBounds(180,115,150,20);
                Semail.setBounds(180,65,150,20);
                Spass.setBounds(180,165,150,20);

                Sete.setFont(new Font("Arial",Font.BOLD,12));
                Setp.setFont(new Font("Arial",Font.BOLD,12));
                Setn.setFont(new Font("Arial",Font.BOLD,12));

                S.setBounds(200,220,100,30);

                S.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            out.writeUTF("s");
                            out.writeUTF(Semail.getText()); 
                            out.flush();
                            out.writeUTF(Suname.getText());  
                            out.flush();
                            out.writeUTF(Spass.getText());  
                            out.flush();

                            String msg=in.readUTF();
                            
                            if(msg.equals("The given user already exist!")){
                                JOptionPane.showMessageDialog(null,"The given user already exist!","Error",JOptionPane.ERROR_MESSAGE);
                                f4.setVisible(false);
                            }
                            else if(msg.equals("Registered successfully!!")){
                                JOptionPane.showMessageDialog(null,"Registered successfully!!","Info",JOptionPane.INFORMATION_MESSAGE);
                                String logText=in.readUTF();
                                login.setText(logText);
                                f4.setVisible(false);
                            }   
                            
                            new Check(login,backgroundPanel,M,in,out);
                        } catch (Exception a) {
                            System.out.println(a.getMessage());
                        }
                    }
                });
               

                f4.add(Suname);
                f4.add(Spass); 
                f4.add(Semail);
                f4.add(Sete);
                f4.add(Setn);
                f4.add(Setp);
                f4.add(S);
            }

        });

        f3.add(u);
        f3.add(p);
        f3.add(uname);
        f3.add(upass);
        f3.add(L);
        f3.add(S);
    }
}