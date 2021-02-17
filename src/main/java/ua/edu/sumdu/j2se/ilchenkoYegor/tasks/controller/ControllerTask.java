package ua.edu.sumdu.j2se.ilchenkoYegor.tasks.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.sumdu.j2se.ilchenkoYegor.tasks.model.*;
import ua.edu.sumdu.j2se.ilchenkoYegor.tasks.view.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Iterator;

public class ControllerTask {
    private static final Logger controllerLog = LogManager.getLogger(ControllerTask.class.getName());
    private ModelTask modelTask;
    private TaskView taskView;
    private TaskCalendar calendar;
    private boolean isSaved = true;
    private void setSaved(boolean act){
        isSaved = act;
    }
    private void updateCalendar(){
        calendar.setApplyChangesButtonActive(!isSaved);
        if(modelTask.isIncomingValid()){
            calendar.showIncomingTasks();
        }
        else{
            calendar.showAllTasks();
        }
    }
    private void confirmTheChanges(){
        Object[] options = {"Yes", "NO!"};
        int rc = JOptionPane.showOptionDialog(
                taskView, "Do you really want to Save the changes?",
                "Confirmation", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        if (rc == 0) {
            modelTask.end();
            setSaved(true);
            updateCalendar();
        }
    }
    private Task getSelectedTask(){
        int numberOfEl = calendar.getSelectedElement();
        AbstractTaskList incomingTasks = TaskListFactory.createTaskList(ListTypes.types.LINKED);
        Iterator<Task> it = modelTask.getSuitIterator();
        while (it.hasNext()) {
            incomingTasks.add(it.next());
        }
        return incomingTasks.getTask(numberOfEl);
    }
    private void deleteSelectedTasks(){
        modelTask.deleterOfTask(getSelectedTask());
        setSaved(false);
    }

    public ControllerTask(ModelTask model, TaskView m_view){
        controllerLog.info("start of working ControllerTask Constructor");
        controllerLog.error("start of working ControllerTask Constructor");
        modelTask = model;
        taskView = m_view;
        try {
            calendar = new TaskCalendar(model);
        }catch (IOException e){
            controllerLog.error(e + "occured when calendar have been tried to be created ", e);
            //log4j..
        }
        m_view.callendarOpenListener(new CalendarOpenListener());
        calendar.addTaskListener(new AddTaskCalendarListener());
        calendar.backFromCalendarListener(new BackCalendarListener());
        calendar.searchTimeListener(new SearchTasks());
        calendar.deleteTaskListener(new DeleteTasksFCListener());
        m_view.exitListener(new ExitProgram());
        calendar.setApplyChangesButton(new ApplyChanger());
        calendar.editTaskListener(new EditTasksFCListener());
        calendar.setShowAllButton(new AllTaskShowListener());
        calendar.addSelectionL(new DetailsOfTask());
        calendar.addMouseL(new GetMouseClicked());
        calendar.addWindowListener(new WindowCalendarController());
        calendar.addTrayListener(new TrayController());
        //calendar.setShowAllButton(ne);
        controllerLog.error("end of working ControllerTask Constructor");
    }
    private void exitConfirmation(JFrame taskView){
        if(isSaved){
            taskView.setVisible(false);
            System.exit(0);
        }
        Object[] options = { "Yes",  "NO!" };
        int rc = JOptionPane.showOptionDialog(
                taskView, "Save the changes?",
                "Confirmation", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        if (rc == 0) {
            taskView.setVisible(false);
            modelTask.end();
            System.exit(0);
        }
        if(rc == 1){
            taskView.setVisible(false);
            System.exit(0);
        }
    }
    class CalendarOpenListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            calendar.setApplyChangesButtonActive(!isSaved);
            calendar.pack();
            taskView.setVisible(false);
            calendar.setVisible(true);
        }
    }
    class AddTaskCalendarListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            AddTask addTask;
            addTask = new AddTask(modelTask, calendar);
            addTask.pack();
            addTask.setVisible(true);
            setSaved(false);
            if(addTask.getLastTask()!=null) {
                updateCalendar();
                //System.out.println(modelTask.getAllTasksArr());
            }

        }
    }
    class BackCalendarListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            calendar.setVisible(false);
            taskView.pack();
            taskView.setVisible(true);
        }
    }

    class DeleteTasksFCListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            Object[] options = { "Yes",  "NO!" };
            int rc = JOptionPane.showOptionDialog(
                    taskView, "Delete the task?",
                    "Confirmation", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);
            if (rc == 0) {
                deleteSelectedTasks();
                updateCalendar();
            }
        }
    }
    class EditTasksFCListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            Task tasktoedit = getSelectedTask();
            EditTaskDialog editTaskDialog = new EditTaskDialog(modelTask ,tasktoedit);
            editTaskDialog.pack();
            editTaskDialog.setVisible(true);
            setSaved(false);
            updateCalendar();
        }
    }
    class DetailsOfTask implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            calendar.enableButtonsWithSelect(!calendar.getIfSelected());
        }
    }
    class GetMouseClicked implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent evt) {

        }

        @Override
        public void mousePressed(MouseEvent evt) {
            if (SwingUtilities.isLeftMouseButton(evt)) {
                if (calendar.getSelectedElement() != -1) {
                    int sIndex = calendar.getLocationToIndex(evt.getPoint());
                    //System.out.println(sIndex);
                    Task detailedTask = getSelectedTask();
                    //System.out.println(detailedTask);
                    calendar.showDetailInfo(sIndex, detailedTask);
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent evt) {
            updateCalendar();
            int sIndex = calendar.getLocationToIndex(evt.getPoint());
            calendar.setElementOfListSelected(sIndex);

        }

        @Override
        public void mouseEntered(MouseEvent evt) {

        }

        @Override
        public void mouseExited(MouseEvent evt) {

        }
    }
    class AllTaskShowListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            modelTask.setEndTime(null);
            modelTask.setStartTime(null);
            updateCalendar();
        }
    }

    class SearchTasks implements ActionListener{
        public void actionPerformed(ActionEvent e){
                try {
                    String start = calendar.getStartC();
                    String end = calendar.getEndC();

                    if ((!end.equals("") && !start.equals(""))) {
                        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("uuuu.MM.dd' 'HH:mm").withResolverStyle(ResolverStyle.STRICT);
                        try {
                            modelTask.setStartTime(LocalDateTime.parse(start, fmt));
                            modelTask.setEndTime(LocalDateTime.parse(end, fmt));
                            calendar.setTextForCInc(start, end);
                        } catch (DateTimeParseException ex) {
                            controllerLog.error("Consumer`s input was invalid");
                        }
                        calendar.showIncomingTasks();
                    } else {
                        throw new IllegalArgumentException();
                    }
                }catch(IllegalArgumentException ex){
                    calendar.showAllTasks();
                    controllerLog.error(ex + " was occured when listener for searching have been tried to be created ", ex);
                    ErrorDialog errorDialog = new ErrorDialog(ex);
                    errorDialog.pack();
                    errorDialog.setVisible(true);
                }
        }
    }
    class ApplyChanger implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            confirmTheChanges();
            setSaved(true);
        }
    }
    class ExitProgram implements ActionListener{
        public void actionPerformed(ActionEvent e){
            exitConfirmation(taskView);
        }
    }
    class WindowCalendarController implements WindowListener{

            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                exitConfirmation(calendar);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {
                calendar.setVisible(false);
                calendar.addTr();
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                calendar.pack();
                calendar.setVisible(true);
                calendar.removeTr();
            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
    }
    class TrayController implements ActionListener{
        public void actionPerformed(ActionEvent ev)
        {
            calendar.setVisible(true);
            calendar.setState(JFrame.NORMAL);
            calendar.removeTr();
        }
    }

}
