import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.net.URL;

class BackgroundImagePanel extends JPanel {
    private BufferedImage image;

    public BackgroundImagePanel(String link) {
        try {
            URL url = new URL(link);
            Image img = new ImageIcon(url).getImage();

            image = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(img, 0, 0, null);
            g2d.dispose();

            // Apply brightness adjustment
            adjustBrightness(0.5f);
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void adjustBrightness(float brightness) {
        if (image == null) return;

        RescaleOp rescaleOp = new RescaleOp(
            new float[] { brightness, brightness, brightness, 1.0f }, 
            new float[] { 0, 0, 0, 0 }, 
            null
        );
        image = rescaleOp.filter(image, null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        } else {
            JOptionPane.showMessageDialog(null, "Image not loaded correctly.Try again!!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
