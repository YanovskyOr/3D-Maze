package controller;

/**
 * This is the interface for Command.
 * <BR>
 * Each command executes via the doCommand method, which receives the arguments for the command.
 * <BR>
 * Commands are implemented inside the CommandsManager Class.
 * @author Or Yanovsky & Lilia Misotchenko
 *
 */
public interface Command {
	void doCommand(String[] args);
}
