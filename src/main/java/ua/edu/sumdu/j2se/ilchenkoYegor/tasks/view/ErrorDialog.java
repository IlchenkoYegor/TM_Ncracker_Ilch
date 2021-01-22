package ua.edu.sumdu.j2se.ilchenkoYegor.tasks.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ErrorDialog extends JDialog {
    private void onOKEr(){
        dispose();
    }
    public ErrorDialog(Exception ex) {
        JPanel errorPane;
        errorPane = new JPanel();
        errorPane.setLayout(new BorderLayout());
        JButton buttonInnerOk = new JButton("OK");
        String error = "the Error " + ex + " were occured, try to change the input data";
        JLabel labelOfError = new JLabel(error);
        errorPane.add(labelOfError, BorderLayout.NORTH);
        errorPane.add(buttonInnerOk, BorderLayout.SOUTH);
        setModal(true);

        setContentPane(errorPane);
        setLocation(700, 350);
        setPreferredSize(new Dimension(900, 100));

        buttonInnerOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOKEr();
            }
        });

    }
}
