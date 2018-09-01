package tim.cmddemo;
/**
 * 
 */

/**
 * @author Tim
 *
 */
public class CommandFour extends CommandAbstract {

  /**
   * @param n
   */
  
  private int c;
  
  public CommandFour(String n) {
    super( "CommandFour!! " + n);
    c = 4;
  }

  /* (non-Javadoc)
   * @see CommandAbstract#execute()
   */
  @Override
  public void execute() {
    System.out.println("  CommandFour " + getName() + " c " + c);
    c = c - 1;
  }

  /* (non-Javadoc)
   * @see CommandAbstract#isFinished()
   */
  @Override
  public boolean isFinished() {
    if ( c <= 1 ) return true;
    return false;
  }

}  // end CommandFour
