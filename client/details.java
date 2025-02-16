import javax.swing.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.awt.*;


class details implements ActionListener{
    private static DataOutputStream out;
    private static DataInputStream in;
    public String title;
    public JButton login;
    details(String t,JButton l,DataInputStream i,DataOutputStream o){
        title=t;
        login=l;
        in=i;
        out=o;
    }
    public void actionPerformed(ActionEvent e){
        try{
            out.writeUTF("r");
            out.writeUTF(title);
            out.flush();
            String check;
            do{
                String title=in.readUTF();
                if(title.equals("No such Movie found...Try Another!")){
                    JOptionPane.showMessageDialog(null,"No such Movie found...Try Another!","Error",JOptionPane.ERROR_MESSAGE);
                    check=in.readUTF();
                }
                else{
                JFrame f2 = new JFrame(title);
                f2.setBounds(500,100,600, 600);
                f2.setVisible(true);
                f2.setLayout(null);

                String movie_link=in.readUTF();
    
                BackgroundImagePanel bgP = new BackgroundImagePanel(movie_link);
                bgP.setBounds(0, 0, f2.getWidth(), f2.getHeight());
                bgP.setLayout(null);
    
                JLabel Name=new JLabel("Title :");
                Name.setBounds(150,30,500,100);
                Name.setFont(new Font("Arial",Font.BOLD,18));
                Name.setForeground(Color.white);
    
                JLabel Director=new JLabel("Director:");
                Director.setBounds(150,80,500,100);
                Director.setFont(new Font("Arial",Font.BOLD,18));
                Director.setForeground(Color.white);
    
                JLabel Actors=new JLabel("Actors:");
                Actors.setBounds(150,80+50,500,100);
                Actors.setFont(new Font("Arial",Font.BOLD,18));
                Actors.setForeground(Color.white);
    
                JLabel Role=new JLabel("Role:");
                Role.setBounds(150,80+100,500,100);
                Role.setFont(new Font("Arial",Font.BOLD,18));
                Role.setForeground(Color.white);
                
                JLabel Genre=new JLabel("Genre :");
                Genre.setBounds(150,210+100,500,100);
                Genre.setFont(new Font("Arial",Font.BOLD,18));
                Genre.setForeground(Color.white);
    
                JLabel Duration=new JLabel("Duration :");
                Duration.setBounds(150,170+100,100,100);
                Duration.setFont(new Font("Arial",Font.BOLD,18));
                Duration.setForeground(Color.white);
    
                JLabel year=new JLabel("Year:");
                year.setBounds(150,130+100,100,100);
                year.setFont(new Font("Arial",Font.BOLD,18));
                year.setForeground(Color.white);
    
                JLabel Rating=new JLabel("Rating:");
                Rating.setBounds(150,203+150,100,100);
                Rating.setFont(new Font("Arial",Font.BOLD,18));
                Rating.setForeground(Color.white);
    
                JLabel MovName=new JLabel(title);
                MovName.setBounds(210,30,500,100);
                MovName.setFont(new Font("Arial",Font.BOLD,18));
                MovName.setForeground(Color.white);
                
                JLabel Review=new JLabel("Review:");
                Review.setBounds(150,390,500,100);
                Review.setFont(new Font("Arial",Font.BOLD,14));
                Review.setForeground(Color.white);
                
                String Rating_review=in.readUTF();

                JLabel Review_val=new JLabel(Rating_review);
                Review_val.setBounds(225,390,300,100);
                Review_val.setFont(new Font("Arial",Font.BOLD,14));
                Review_val.setForeground(Color.white);
                
                String Director_name=in.readUTF();

                JLabel MovDirect=new JLabel(Director_name);
                MovDirect.setBounds(250,80,500,100);
                MovDirect.setFont(new Font("Arial",Font.BOLD,18));
                MovDirect.setForeground(Color.white);

                String Duration1=in.readUTF();
    
                JLabel MovDuration=new JLabel(Duration1);
                MovDuration.setBounds(250,170+100,500,100);
                MovDuration.setFont(new Font("Arial",Font.BOLD,18));
                MovDuration.setForeground(Color.white);
    
                String R_year=in.readUTF();
    
                JLabel Movyear=new JLabel(R_year);
                Movyear.setBounds(210,130+100,120,100);
                Movyear.setFont(new Font("Arial",Font.BOLD,18));
                Movyear.setForeground(Color.white);
                
                String genre=in.readUTF();

                JLabel MovGenre=new JLabel(genre);
                MovGenre.setBounds(230,213+100,120,100);
                MovGenre.setFont(new Font("Arial",Font.BOLD,18));
                MovGenre.setForeground(Color.white);

                String Actor=in.readUTF();
    
                JLabel MovActor=new JLabel(Actor);
                MovActor.setBounds(230,80+50,250,100);
                MovActor.setFont(new Font("Arial",Font.BOLD,18));
                MovActor.setForeground(Color.white);

                String role=in.readUTF();
    
                JLabel MovRole=new JLabel(role);
                MovRole.setBounds(210,80+100,250,100);
                MovRole.setFont(new Font("Arial",Font.BOLD,18));
                MovRole.setForeground(Color.white);

                String rat_score=in.readUTF();
    
                JLabel MovRate=new JLabel(rat_score);
                MovRate.setBounds(220,203+150,250,100);
                MovRate.setFont(new Font("Arial",Font.BOLD,18));
                MovRate.setForeground(Color.white);

    
                JButton Rate=new JButton("Rate");
                Rate.setBounds(150,430+50,90,30);
                
                Rate.addActionListener(new RateActionListener(Rate,MovName.getText(),login.getText(),in,out));
                Rate.setBackground(Color.red);
    
    
    
                //bgP.setContentPane(panel);
                bgP.add(Name);
                bgP.add(Duration);
                bgP.add(MovDuration);
                bgP.add(MovName);
                bgP.add(year);
                bgP.add(Movyear);
                bgP.add(Genre);
                bgP.add(MovGenre);
                bgP.add(Actors);
                bgP.add(MovActor);
                bgP.add(Role);
                bgP.add(MovRole);
                bgP.add(Rating);
                bgP.add(MovRate);
                bgP.add(Director);
                bgP.add(MovDirect);
                bgP.add(Rate);
                bgP.add(Review);
                bgP.add(Review_val);
                f2.add(bgP);
                check=in.readUTF();
            }
            
        }while(!check.equals("end"));
        }
        catch(Exception a){
            JOptionPane.showMessageDialog(null,"Server down","Error",JOptionPane.ERROR_MESSAGE);
            System.out.println(a.getMessage());
        }
}
}