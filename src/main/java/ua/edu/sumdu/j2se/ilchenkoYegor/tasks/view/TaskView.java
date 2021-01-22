package ua.edu.sumdu.j2se.ilchenkoYegor.tasks.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TaskView extends JFrame {
    private JButton calendarButton;
    private JPanel myPanel;
    private JButton exitButton;

    public void callendarOpenListener(ActionListener a){
        calendarButton.addActionListener(a);
    }
    public void exitListener(ActionListener a){
        exitButton.addActionListener(a);
    }

    public TaskView() {
        setContentPane(myPanel);
        setLocation(800, 350);
        setPreferredSize(new Dimension(200, 200));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

}
