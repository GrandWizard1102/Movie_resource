import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
class RateActionListener implements ActionListener{
    JButton Rate;
    String t;
    String login;
    DataInputStream in;
    DataOutputStream out;
    RateActionListener(JButton b,String Title,String l,DataInputStream i,DataOutputStream o){
        Rate=b;
        t=Title;
        login=l;
        in=i;
        out=o;
    }
    public void actionPerformed(ActionEvent e){
        try{
            if(login.equals("Login")){
                JOptionPane.showMessageDialog(null,"Login To Rate!","Error",JOptionPane.ERROR_MESSAGE);
            }
            else{
                new RateEntry(t,login,in,out);
            }
    }
    catch (Exception a) {
        System.out.println(a.getMessage());
    }
    }
}

class RateEntry{
    double x;
    String rt=null;
    JFrame f5;
    RateEntry(String Title,String login,DataInputStream in,DataOutputStream out) throws Exception{
        f5=new JFrame("Rate");
        f5.setBounds(650,300,400,250);
        f5.setVisible(true);
        f5.setLayout(null);

        JButton submit=new JButton("Submit");
        submit.setBounds(150,160,100,30);

        JLabel rate=new JLabel("Give your rating:");
        rate.setBounds(10,20,500,50);
        rate.setFont(new Font("Arial", Font.BOLD, 16));

        JSlider slider = new JSlider(0, 100, 50); // 0.0 to 10.0, initial 5.0
        slider.setMajorTickSpacing(10); 
         slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBounds(60,60,250,50);

        // Label to display the slider value in decimal
        JLabel valueLabel = new JLabel("Value: 5.0");
        valueLabel.setFont(new Font("Arial", Font.BOLD, 16));

       
        submit.addActionListener(new SubmitButton(slider,login,Title,f5,in,out));
        f5.add(slider);
        f5.add(submit);
        f5.add(rate);
    }
    JFrame getFrame(){
        return f5;
    }
}
class SubmitButton implements ActionListener {
    private static DataOutputStream out;
    private static DataInputStream in;
    private final JSlider newRateVal;
    private final String login;
    private final String title;
    private final JFrame f5;
    
    SubmitButton(JSlider newRateVal, String login, String title, JFrame f5,DataInputStream i,DataOutputStream o) {
        this.newRateVal = newRateVal;
        this.login = login;
        this.title = title;
        this.f5 = f5;
        in=i;
        out=o;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        try {
            out.writeUTF("n");
            out.writeInt(newRateVal.getValue());
            out.flush();

            out.writeUTF(title);
            out.flush();

            out.writeUTF(login);
            out.flush();

            System.out.println(newRateVal.getValue());
            int rowsAffected=in.readInt();
            if (rowsAffected > 0) {
                // Create and display the updated rating
                JLabel Updatedr = new JLabel("Updated Rating:");
                Updatedr.setBounds(25, 110, 150, 50);
                Updatedr.setFont(new Font("Arial",Font.BOLD,16));

                JLabel Updatedr_val = new JLabel();
                Updatedr_val.setBounds(200, 110, 50, 50);
                Updatedr_val.setFont(new Font("Arial",Font.BOLD,16));
                String r=in.readUTF();
                if (r.equals("true")) {
                    String x = in.readUTF();
                    Updatedr_val.setText(x);
                }

                // Add labels to the frame
                SwingUtilities.invokeLater(() -> {
                    f5.add(Updatedr);
                    f5.add(Updatedr_val);
                    f5.revalidate();  // Revalidate the frame to reflect changes
                    f5.repaint();     // Repaint the frame to show new components
                });
    }
}
catch (Exception a) {
    System.out.println(a.getMessage());
    }
}
}