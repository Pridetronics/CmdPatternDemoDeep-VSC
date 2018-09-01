package tim.cmddemo;
/**
 * 
 */

/**
 * @author Tim
 *
 */
public class CommandTwo extends CommandAbstract {

  /* (non-Javadoc)
   * @see CommandAbstract#execute()
   */
  private int count;
  
  public CommandTwo( String name ) {
    super("CommandTwo " + name);
    count = 0;
  }

  @Override
  public void execute() {
    ++count;
    System.out.println("  CommandTwo " + getName() + " count " + count);
  }

  /* (non-Javadoc)
   * @see CommandAbstract#isFinished()
   */
  @Override
  public boolean isFinished() {
    if (count > 1) {
      return true;
    }
    return false;
  }

}  // end class CommandTwo
