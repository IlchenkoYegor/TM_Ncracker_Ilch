package ua.edu.sumdu.j2se.ilchenkoYegor.tasks.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.sumdu.j2se.ilchenkoYegor.tasks.model.ModelTask;
import ua.edu.sumdu.j2se.ilchenkoYegor.tasks.model.Task;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Iterator;

public class TaskCalendar extends JFrame{
    private DefaultListModel modelWithtasks;
    private JList<String> list1;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton backButton;
    private JPanel mainpanel;
    private JTextField startText;
    private JTextField endText;
    private JButton searchButton;
    private JScrollPane calendarScroll;
    private JButton showAllButton;
    private JLabel Labelforcurrent;
    private JButton applyChangesButton;
    private ModelTask m_model;
    private int indexOfLastEl = 0;
    private TrayIcon trayIcon;
    private final String PATH_TO_IMAGE = "./src/main/java/ua/edu/sumdu/j2se/ilchenkoYegor/tasks/bin/trayIcon.jpg";
    private SystemTray systemTray = SystemTray.getSystemTray();
    private boolean firstinc;
    private static final Logger calendarLogger = LogManager.getLogger(TaskCalendar.class.getName());
    public TaskCalendar(ModelTask model) throws IOException {
        trayIcon = new TrayIcon(ImageIO.read(new File(PATH_TO_IMAGE)), "Your Tasks are here!");
        modelWithtasks = (DefaultListModel)list1.getModel();
        setLocation(500, 350);
        setPreferredSize(new Dimension(1200, 600));
        setContentPane(mainpanel);
        calendarScroll.setWheelScrollingEnabled(true);
        this.m_model = model;
        showAllTasks();
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        list1.revalidate();
    }
    public void removeTr(){
        systemTray.remove(trayIcon);
    }
    public void addTrayListener(ActionListener act){
        trayIcon.addActionListener(act);
    }
    public void addWindowListenerCal(WindowListener act){
        addWindowListener(act);
    }
    public void addTr(){
         {
            try{
                systemTray.add(trayIcon);
                if (!firstinc) {
                    trayIcon.displayMessage("Calendar", "Your calendar is Here!", TrayIcon.MessageType.INFO);
                }
                firstinc = true;
            }
            catch(AWTException ex)
            {
                calendarLogger.error("AWT exception in addTr method (it work with adding to tray) ");
                calendarLogger.error(ex);
            }
        }
    }
    public void addTaskListener(ActionListener e){
        addButton.addActionListener(e);
    }
    public void deleteTaskListener(ActionListener e){
        deleteButton.addActionListener(e);
    }
    public void setTextForCInc(String incomingInfoStart, String incomingInfoEnd){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("uuuu.MM.dd' 'HH:mm").withResolverStyle(ResolverStyle.STRICT);
        Labelforcurrent.setText("Current Interval: " + incomingInfoStart + "  --------------  " + incomingInfoEnd);
    }
    public void editTaskListener(ActionListener e){
        editButton.addActionListener(e);
    }
    public String getStartC(){
        return startText.getText();
    }
    public String getEndC(){
        return endText.getText();
    }
    public void showAllTasks(){
        modelWithtasks.removeAllElements();
        indexOfLastEl = 0;
        Iterator<Task> it =  m_model.getAllTasksArr().iterator();
        while(it.hasNext()){
            indexOfLastEl++;
            modelWithtasks.addElement(Integer.toString(indexOfLastEl) + "." + it.next().getTitle());
        }
        validate();
    }
    public void showIncomingTasks(){
        modelWithtasks.removeAllElements();
        Iterator<Task> it =  m_model.getIncomingTasks().iterator();
        indexOfLastEl = 0;
        while(it.hasNext()){
            indexOfLastEl++;
            modelWithtasks.addElement(Integer.toString(indexOfLastEl) + "." + it.next().getTitle());
        }
        validate();
    }
    public int getSelectedElement(){
        return list1.getSelectedIndex();
    }
    public void backFromCalendarListener(ActionListener e){
        backButton.addActionListener(e);
    }
    public void searchTimeListener(ActionListener e){
        searchButton.addActionListener(e);
    }

    public void setApplyChangesButton(ActionListener e){
        applyChangesButton.addActionListener(e);
    }
    public void setShowAllButton(ActionListener e){
        showAllButton.addActionListener(e);
    }
    public void enableButtonsWithSelect(boolean caseof ){
        editButton.setEnabled(caseof);
        deleteButton.setEnabled(caseof);
    }
    public void setApplyChangesButtonActive(boolean state){
        applyChangesButton.setEnabled(state);
    }
    public boolean getIfSelected(){
        return list1.isSelectionEmpty();
    }
    public void addSelectionL(ListSelectionListener listener){
        list1.addListSelectionListener(listener);
    }
    public void addMouseL(MouseListener listener){
        list1.addMouseListener(listener);
    }
    public int getLocationToIndex(Point p){
        return list1.locationToIndex(p);
    }
    public void setElementOfListSelected(int index){
        list1.setSelectedIndex(index);
    }
    public void showDetailInfo(int index, Task toShow){
        modelWithtasks.removeElementAt(index);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("uuuu.MM.dd' 'HH:mm").withResolverStyle(ResolverStyle.STRICT);
        modelWithtasks.add(index, Integer.toString(index+1) + ".Name: \""+toShow.getTitle()
                +"\". Time of start: "+ toShow.getStartTime().format(fmt) + (toShow.isRepeated()? ". Time of end: "
                + toShow.getEndTime().format(fmt)+ ". Repeating interval(min) : "
                +Integer.toString(toShow.getRepeatInterval()):("")) + (toShow.isActive()?". Is active.": ". Is not active.")+"");
        validate();
    }
}
