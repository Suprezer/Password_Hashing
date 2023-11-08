package org.example.view;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    int height = 350;
    int width = 450;

    MyFrame(){
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setTitle("Hashmap Test Application");
        this.setLayout(null);
    }
}
