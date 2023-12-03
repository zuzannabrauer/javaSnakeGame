package org.example;
import javax.swing.JFrame;

public class GameFrame extends JFrame {

    GameFrame(){
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack(); //fit it nicely with all others components
        this.setVisible(true);
        this.setLocationRelativeTo(null); //window in the center of the screen

    }
}
