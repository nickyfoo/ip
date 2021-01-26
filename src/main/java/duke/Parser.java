package duke;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import duke.command.ByeCommand;
import duke.command.Command;
import duke.command.DeadlineCommand;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
import duke.command.EventCommand;
import duke.command.ListCommand;
import duke.command.TodoCommand;
import duke.exception.CommandException;


public class Parser {

    // takes care of parsing the input, then calls Command with appropriate arguments;
    public Parser() {

    }

    public Command parse(String line) throws CommandException, IndexOutOfBoundsException, NumberFormatException {
        String command = line.split(" ")[0];
        try {
            switch (command) {
            case "bye":
                return new ByeCommand();
            case "list": {
                return new ListCommand();
            }
            case "done": {
                String[] ar = line.split(" ", 2);
                if (ar.length == 1) {
                    throw new CommandException("Which task are you done with?");
                }
                line = line.split(" ", 2)[1];
                int index = Integer.parseInt(line) - 1;
                return new DoneCommand(index);
            }
            case "delete": {
                String[] ar = line.split(" ", 2);
                if (ar.length == 1) {
                    throw new CommandException("Which task are you deleting?");
                }
                line = line.split(" ", 2)[1];
                int index = Integer.parseInt(line) - 1;
                return new DeleteCommand(index);
            }
            case "todo": {
                String[] ar = line.split(" ", 2);
                if (ar.length == 1) {
                    throw new CommandException("I can't add an empty task to the list!");
                }
                line = line.split(" ", 2)[1];
                return new TodoCommand(line);
            }
            case "deadline": {
                String[] ar = line.split(" ", 2);
                if (ar.length == 1) {
                    throw new CommandException("I can't add an empty task to the list!");
                }
                line = line.split(" ", 2)[1];
                String[] result = line.split("/by ");
                if (result.length == 1) {
                    throw new CommandException("Er... when do you need to finish this /by?");
                }
                try {
                    LocalDate date = LocalDate.parse(result[1]);
                    return new DeadlineCommand(result[0], date);
                } catch (DateTimeParseException e) {
                    throw new CommandException("Please input a valid date as yyyy-mm-dd");
                }
            }
            case "event": {
                String[] ar = line.split(" ", 2);
                if (ar.length == 1) {
                    throw new CommandException("I can't add an empty task to the list!");
                }
                line = line.split(" ", 2)[1];
                String[] result = line.split("/at ");
                if (result.length == 1) {
                    throw new CommandException("Er... /at what time does this event start?");
                }

                try {
                    LocalDate date = LocalDate.parse(result[1]);
                    return new EventCommand(result[0], date);
                } catch (DateTimeParseException e) {
                    throw new CommandException("Please input your date as yyyy-mm-dd");
                }
            }
            default: {
                throw new CommandException("I don't understand");
            }
            }

        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            throw new CommandException("Please enter a valid value");
        }

    }

}
