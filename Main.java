import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int boardWidth = 800;
        int boardHeight = 800;
        JFrame frame = new JFrame("Arcanoid");
        frame.setVisible(true);
        frame.setLocation(500,50);
        frame.setSize(boardWidth, boardHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Arcanoid arcanoid = new Arcanoid(boardWidth, boardHeight);
        frame.add(arcanoid);
        frame.pack();
        arcanoid.requestFocus();
    }
}