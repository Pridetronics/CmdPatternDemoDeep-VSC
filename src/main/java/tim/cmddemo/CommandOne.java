package tim.cmddemo;


public class CommandOne extends CommandAbstract {

  private int counter;

  public CommandOne( String name ) {
    super("CommandOne " + name);
  }

  public void initialize() {
    counter = 0;
    super.initialize();
  }
  
  public void execute() {
    ++counter;
    System.out.println("  CommandOne " + getName() + " counter " + counter);
  }
  
  public boolean isFinished() {
    if ( counter > 0 ) { return true; }
    return false;
  }
  
  public void end() {
    System.out.println("CommandOne is done");
    super.end();
  }
  
  public void interrupted() {
    end();
  }
}
