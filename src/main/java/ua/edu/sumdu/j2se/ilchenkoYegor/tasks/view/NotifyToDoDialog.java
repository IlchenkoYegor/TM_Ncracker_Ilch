package ua.edu.sumdu.j2se.ilchenkoYegor.tasks.view;

import ua.edu.sumdu.j2se.ilchenkoYegor.tasks.model.ModelTask;
import ua.edu.sumdu.j2se.ilchenkoYegor.tasks.model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Set;

public class NotifyToDoDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JList<String> listWithTasks;
    private DefaultListModel modeloflist;
    private ModelTask model;
    public NotifyToDoDialog(ModelTask model, Set<Task> current) {
        modeloflist = new DefaultListModel();
        this.model = model;
        setAlwaysOnTop(true);
        setLocation(800, 350);
        setPreferredSize(new Dimension(500, 200));
        createLabelsWithTasks(current);
        listWithTasks.setModel(modeloflist);
        setContentPane(contentPane);
        setModal(false);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
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
    private void createLabelsWithTasks(Set<Task> current){
        //System.out.println(current);
        model.setActiveAfterTime(LocalDateTime.now());
        model.end();
        for(Task a: current){
            String toShow = "Task "+ a.getTitle() + " is needed to be done!";
            modeloflist.addElement(toShow);
            //System.out.println(toShow);
            if(a.getRepeatInterval()>0 && !LocalDateTime.now().isAfter(a.getEndTime())) {
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("uuuu.MM.dd' 'HH:mm").withResolverStyle(ResolverStyle.STRICT);
                String opt = "The next time you`ll see the message with this task at " + LocalDateTime.now().plusSeconds(a.getRepeatInterval()).format(fmt) + ". Hurry up!";
                modeloflist.addElement(opt);
            }

        }

        validate();
    }
    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
