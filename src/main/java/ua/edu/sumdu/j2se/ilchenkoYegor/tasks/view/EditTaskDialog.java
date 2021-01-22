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

public class EditTaskDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textNameT;
    private JTextField textStartT;
    private JTextField textInterval;
    private JTextField textEndT;
    private JCheckBox yesCheckBox;
    private ModelTask model;
    private static final Logger editTaskDialogLog = LogManager.getLogger(EditTaskDialog.class.getName());
    private void checkTheInput(){
        if(!textNameT.getText().equals("") && !textStartT.getText().equals("")){
            buttonOK.setEnabled(true);
        }
        else{
            buttonOK.setEnabled(false);
        }
    }
    public EditTaskDialog(ModelTask m_model, Task editingTask) {
        model = m_model;
        setContentPane(contentPane);
        setLocation(700, 350);
        setPreferredSize(new Dimension(600, 300));
        buttonOK.setEnabled(false);
        setModal(true);
        textStartT.setText(editingTask.getStartTime().toString());
        textNameT.setText(editingTask.getTitle());
        textEndT.setText(editingTask.getEndTime().toString());
        textInterval.setText(Integer.toString(editingTask.getRepeatInterval()));
        if(editingTask.isActive()) {
            yesCheckBox.setSelected(true);
        }
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    try{
                        onOK(editingTask);
                    }catch (DateTimeParseException ex){
                        editTaskDialogLog.error("handled Error with time parse occured in editTimeDialog " + ex.getMessage());
                        ErrorDialog err = new ErrorDialog(ex);
                        err.pack();
                        err.setVisible(true);
                    }
                    catch (IllegalArgumentException ex){
                        //log4j..
                        editTaskDialogLog.error("handled Error with arguments occured in editTimeDialog " + ex.getMessage());
                        ErrorDialog err = new ErrorDialog(ex);
                        err.pack();
                        err.setVisible(true);

                    }
                }

            }
        );
        textNameT.addCaretListener(e -> {checkTheInput();});
        textStartT.addCaretListener(e -> {checkTheInput();});
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

    private void onOK(Task editTask) throws IllegalArgumentException, DateTimeParseException {
        // AddTask your code here
        String name = textNameT.getText();
        String interval = textInterval.getText();
        if(interval.equals("") || interval.equals("0")) {
            String time = textStartT.getText();
            if (time.equals("")) {
                throw new IllegalArgumentException();
            }
            Task lastTask = new Task(name, LocalDateTime.parse(time));
            if (yesCheckBox.isSelected()) {
                lastTask.setActive(true);
            }
            model.deleterOfTask(editTask);
            model.adderOfTask(lastTask);
        }
        else {
            String starttime = textStartT.getText();
            String endtime = textEndT.getText();
            if (starttime.equals("") || endtime.equals("")) {
                throw new IllegalArgumentException();
            }
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss").withResolverStyle(ResolverStyle.STRICT);
            Task lastTask = new Task(name, LocalDateTime.parse(starttime, fmt), LocalDateTime.parse(endtime, fmt), Integer.parseInt(interval));
            if(yesCheckBox.isSelected()) {
                lastTask.setActive(true);
            }
            if(!lastTask.equals(editTask)){
                model.deleterOfTask(editTask);
                model.adderOfTask(lastTask);
            }
        }
        dispose();
    }
    private void onCancel() {
        // AddTask your code here if necessary
        dispose();
    }
}
