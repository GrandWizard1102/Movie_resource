import javax.swing.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.awt.*;


public class Movie extends JFrame{
    RoundButton login=new RoundButton("Login");
    JButton s = new JButton();
    private static Socket socket;
    private static DataOutputStream out;
    private static DataInputStream in;
    Movie() throws UnknownHostException, IOException{

        socket = new Socket("localhost", 54323); 
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());

        JFrame frame=new JFrame();
        
        frame.setLayout(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        // Get the width and height
        int width = screenSize.width;
        int height = screenSize.height;
        frame.setSize( width,height);

        BackgroundImagePanel backgroundPanel = new BackgroundImagePanel("https://raw.githubusercontent.com/GrandWizard1102/Movie_resource/be8b24d708f57fb1a46c03eb8c8007708a9cdcf1/frame3.jpg");
        backgroundPanel.setLayout(null); 

        //top 10 movies
        JLabel top10=new JLabel("Highest Rated Movies");
        top10.setBounds(50,100,700,100);
        top10.setFont(new Font("Arial",Font.BOLD,50));
        top10.setForeground(Color.RED);

        JTextField search=new JTextField("Search");
        
        
        
        URL url = new URL("https://raw.githubusercontent.com/GrandWizard1102/Movie_resource/main/glass.png");
        ImageIcon icon = new ImageIcon(url);
        Image img1 = icon.getImage();  
        Image newimg1 = img1.getScaledInstance(40, 40,Image.SCALE_SMOOTH); 
        icon = new ImageIcon(newimg1);
        JButton s = new JButton(icon);

        URL url2 = new URL("https://raw.githubusercontent.com/GrandWizard1102/Movie_resource/be417e5f8720aacd2d5c52f4ec998d974ad80ada/WhatsApp%20Image%202024-12-06%20at%2023.07.37_087aaa3a.jpg");
        ImageIcon icon2 = new ImageIcon(url2);
        Image img2 = icon2.getImage();  
        Image newimg2 = img2.getScaledInstance(20, 10 + 10, Image.SCALE_SMOOTH); 
        icon = new ImageIcon(newimg2);
        JButton r = new JButton(icon);
        r.setForeground(getBackground());

        int[] x_pos={50,400,750,1100,1450};
        
        getHigh a=new getHigh(in,out);
        String[] link=a.get_Link();
        Highrated[] M=new Highrated[5];
        String[] Movie_title=a.get_title();
        
        for(int i=0;i<5;i++){
              
              M[i]=new Highrated(backgroundPanel,x_pos[i],200,300,310,link[i],Movie_title[i]);
              M[i].getButton().addActionListener(new details(M[i].getTitle(),login,in,out));
          }

       //Login
       login.setBounds(1350,20,100,40);
       
       BackgroundImagePanel bg = new BackgroundImagePanel("https://raw.githubusercontent.com/GrandWizard1102/Movie_resource/f3015a87b347092dd8209327f4d3f78b22641c84/MDb%20(1).png");
       bg.setBounds(1470,20,40,40);
        
       login.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            try{
            new logpage(login,backgroundPanel,M,in,out);
            }
            catch(Exception a){
                System.out.println(a.getMessage());
            }
        }
    });

    r.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Create a progress bar
            JProgressBar progressBar = new JProgressBar(0, 100);
            progressBar.setValue(0);
            progressBar.setStringPainted(true);
            progressBar.setBounds(600, 400, 500, 20);
    
            JLabel loading = new JLabel("Loading...");
            loading.setBounds(800, 370, 500, 20);
            loading.setFont(new Font("Arial", Font.BOLD, 16));
            loading.setForeground(Color.WHITE);
    
            // Remove all components and add the progress bar
            backgroundPanel.removeAll();
            backgroundPanel.add(progressBar);
            backgroundPanel.add(loading);
            backgroundPanel.revalidate();
            backgroundPanel.repaint();
    
            // Use a SwingWorker for the progress bar animation
            SwingWorker<Void, Integer> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    for (int i = 0; i <= 100; i++) {
                        try {
                            Thread.sleep(20); 
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        publish(i); // Update progress
                    }
                    return null;
                }
    
                @Override
                protected void process(java.util.List<Integer> chunks) {
                    // Update progress bar with the latest value
                    progressBar.setValue(chunks.get(chunks.size() - 1));
                }
    
                @Override
                protected void done() {
                    // Clear the panel and restore components
                    backgroundPanel.removeAll();
                    
                    // Recreate the original components or reload your initial state
                    backgroundPanel.add(top10);
                    backgroundPanel.add(search);
                    backgroundPanel.add(s);
                    backgroundPanel.add(login);
                    backgroundPanel.add(r);
                    backgroundPanel.add(bg);
                    
                    getHigh a;
                    try {
                        a = new getHigh(in,out);
                        String[] link=a.get_Link();
                        String[] Movie_title=a.get_title();
                    
                    for(int i=0;i<5;i++){
                          
                          M[i]=new Highrated(backgroundPanel,x_pos[i],200,300,310,link[i],Movie_title[i]);
                          M[i].getButton().addActionListener(new details(M[i].getTitle(),login,in,out));
                      }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    new Check(login,backgroundPanel,M,in,out);
                    backgroundPanel.revalidate();
                    backgroundPanel.repaint();
                }
            };
    
            worker.execute(); // Start the background task
        }
    });
    
    

        r.setBounds(18,28,20,20);
        search.setBounds(50,20,500,40);
        search.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (search.getText().equals("Search")) {
                    search.setText("");
                    search.setForeground(Color.BLACK);
                }
            }
        
            @Override
            public void focusLost(FocusEvent e) {
                if (search.getText().isEmpty()) {
                    search.setText("Search");
                    search.setForeground(new Color(0, 0, 0, 128)); 
                }
            }
        });
        
        s.setBounds(550,20,40,40);
        search.setForeground(new Color(0, 0, 0, 128));
        s.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    details movieDetails = new details(search.getText(), login,in,out);
                    movieDetails.actionPerformed(e);
                } catch (Exception a) {
                    System.out.println(a.getMessage());
                }
            }
        });


        backgroundPanel.add(search);
        backgroundPanel.add(s);
        backgroundPanel.add(top10);
        backgroundPanel.add(login);
        backgroundPanel.add(r);
        backgroundPanel.add(bg);
        
        frame.setContentPane(backgroundPanel);
        frame.setVisible(true);
    }
    public static void main(String args[]) throws UnknownHostException, IOException{
        new Movie();
    }
}
