import javax.swing.*;
import java.awt.*;
import java.net.URL;
class Highrated extends JButton{
    JButton  b;
    JLabel n;
    Highrated(){
        
    }
    Highrated(JPanel bg,int x,int y,int w,int h,String imageUrl,String z){
    try {
            // Create a JLabel for the movie title
            n = new JLabel(z);

            // Load image from GitHub URL
            URL url = new URL(imageUrl);  // Convert the image URL to a URL object
            ImageIcon icon1 = new ImageIcon(url);

            // Resize the image to fit the button dimensions
            Image img1 = icon1.getImage();  
            Image newimg1 = img1.getScaledInstance(w, h + 10, Image.SCALE_SMOOTH);  
            icon1 = new ImageIcon(newimg1);  // Re-create the ImageIcon with the resized image

            // Create the JButton with the resized image
            b = new JButton(icon1);

            // Set bounds for button and label
            b.setBounds(x, y, w, h);
            n.setBounds(x + 100, y + h - 10, 150, 50);
            n.setFont(new Font("Arial", Font.BOLD, 20));
            n.setForeground(Color.RED);

            // Add components to the background panel
            bg.add(n);
            bg.add(b);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to load image: " + imageUrl);
        }
    }
    public JButton getButton(){
        return b;
    }
    public String getTitle(){
        return n.getText();
    }
}
