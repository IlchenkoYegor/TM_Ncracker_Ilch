package ua.edu.sumdu.j2se.ilchenkoYegor.tasks;

import ua.edu.sumdu.j2se.ilchenkoYegor.tasks.controller.ControllerTask;
import ua.edu.sumdu.j2se.ilchenkoYegor.tasks.model.ModelTask;
import ua.edu.sumdu.j2se.ilchenkoYegor.tasks.view.TaskView;

public class Main {

	public static void main(String[] args) {
		ModelTask modelTask = new ModelTask();
		TaskView taskView = new TaskView();
		ControllerTask controllerTask = new ControllerTask(modelTask, taskView);
	}
}


