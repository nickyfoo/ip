package duke.command;

import java.io.IOException;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.task.Todo;


/**
 * A TodoCommand is when the user wants to add a todo task to the list
 */
public class TodoCommand extends Command {

    private final String description;

    /**
     * Constructor to initialise the task description
     * @param task the description of the task
     */
    public TodoCommand(String task) {
        super("");
        this.description = task;
    }

    /**
     * Executes the command
     * @param ui the ui to respond to the user's input
     * @param s The storage to save the tasklist to
     * @param list The current list of tasks
     * @throws IOException when file fails to be saved
     */
    public void execute(Ui ui, Storage s, TaskList list) throws IOException {
        Todo t = new Todo(this.description);
        list.addTask(t);
        this.reply = "Got it. I've added this task:\n\t" + t.toString()
                + "\n\tNow you have " + list.getSize() + " task" + (list.getSize() != 1 ? "s " : " ") + "in the list.";

        s.storeData(list.getList());
        ui.reply(this.reply);

    }
}






