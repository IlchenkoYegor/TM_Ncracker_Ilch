package ua.edu.sumdu.j2se.ilchenkoYegor.tasks.Notificator;

import ua.edu.sumdu.j2se.ilchenkoYegor.tasks.model.Task;
import ua.edu.sumdu.j2se.ilchenkoYegor.tasks.model.ModelTask;
import ua.edu.sumdu.j2se.ilchenkoYegor.tasks.view.NotifyToDoDialog;

import java.awt.*;
import java.util.Set;
import java.util.TimerTask;

public class Notifyer  extends TimerTask {
        Set<Task> e;
        ModelTask modelTask;

        public Notifyer(Set<Task> events, ModelTask modelTask){
            this.modelTask = modelTask;
            e = events;
        }

        @Override
        public void run() {
            NotifyToDoDialog notify = new NotifyToDoDialog(modelTask, e);
            Toolkit.getDefaultToolkit().beep();
            notify.pack();
            notify.setVisible(true);
        }


}
