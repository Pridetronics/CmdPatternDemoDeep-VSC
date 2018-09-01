package tim.cmddemo;

public abstract class CommandAbstract {

  protected String commandName;

  public CommandAbstract(String n) {
    commandName = n;
  }

  protected String getName() { return commandName; }
  
  public void initialize() {
    // set up to do something
    System.out.println("Initialize command " + commandName);
  }

  public abstract void execute();

  public abstract boolean isFinished();

  public void end() {
    // stop doing what you're doing
    System.out.println("  end command " + commandName);
  }

  public void interrupted() {
    // usually
    end();
    // but sometimes something else, then end()
  }
}
