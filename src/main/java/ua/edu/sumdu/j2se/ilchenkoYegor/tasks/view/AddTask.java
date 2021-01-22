package ua.edu.sumdu.j2se.ilchenkoYegor.tasks.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.sumdu.j2se.ilchenkoYegor.tasks.model.ModelTask;
import ua.edu.sumdu.j2se.ilchenkoYegor.tasks.model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class AddTask extends JDialog {
    private static final Logger addTaskDialogLog = LogManager.getLogger(AddTask.class.getName());
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textName;
    private JTextField textInterval2;
    private JTextField textBegin;
    private JTextField textEnd4;
    private ModelTask model;
    private Task lastTask;


    private void checkTheInput(){
        if(!textName.getText().equals("") && !textBegin.getText().equals("")){
            buttonOK.setEnabled(true);
        }
        else{
            buttonOK.setEnabled(false);
        }
    }

    public AddTask(ModelTask mModel, JFrame parent) {
        model = mModel;
        setContentPane(contentPane);
        setLocation(700, 350);
        setPreferredSize(new Dimension(600, 300));
        buttonOK.setEnabled(false);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        textName.addCaretListener(e -> {checkTheInput();});
        textBegin.addCaretListener(e -> {checkTheInput();});

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    onOK(parent);
                }catch (DateTimeParseException ex){
                    ErrorDialog err = new ErrorDialog(ex);
                    err.pack();
                    err.setVisible(true);
                    addTaskDialogLog.error("while adding task in calendar occured" + ex.getMessage());
                }
                catch (IllegalArgumentException ex){
                    //log4j..
                    addTaskDialogLog.error("while adding task in calendar occured" + ex.getMessage());
                    ErrorDialog err = new ErrorDialog(ex);
                    err.pack();
                    err.setVisible(true);

                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK(JFrame parent) throws IllegalArgumentException, DateTimeParseException {
        // AddTask your code here
        String name = textName.getText();
        String interval = textInterval2.getText();
        if(interval.equals("") || interval.equals("0")){
            String time = textBegin.getText();
            if(time.equals("")){
                throw new IllegalArgumentException();
            }
            lastTask = new Task(name, LocalDateTime.parse(time));
            lastTask.setActive(true);
            model.adderOfTask(lastTask);
        }
        else {
            String starttime = textBegin.getText();
            String endtime = textEnd4.getText();
            if (starttime.equals("") || endtime.equals("")) {
                throw new IllegalArgumentException();
            }
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss").withResolverStyle(ResolverStyle.STRICT);
            lastTask = new Task(name, LocalDateTime.parse(starttime, fmt), LocalDateTime.parse(endtime, fmt), Integer.parseInt(interval));
            lastTask.setActive(true);
            model.adderOfTask(lastTask);
        }
        dispose();
    }

    public Task getLastTask() {
        return lastTask;
    }

    private void onCancel() {
        dispose();
    }
}
