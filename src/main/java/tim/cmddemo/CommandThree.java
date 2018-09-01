package tim.cmddemo;
/**
 * 
 */

/**
 * @author Tim
 *
 */
public class CommandThree extends CommandAbstract {

  /**
   * @param n
   */
  private int cntr;
  
  public CommandThree(String n) {
    super( "CommandThree " + n);
    cntr = 0;
  }

  /* (non-Javadoc)
   * @see CommandAbstract#execute()
   */
  @Override
  public void execute() {
    ++cntr;
    System.out.println("  CommandThree " + getName() + " cntr " + cntr);
  }

  /* (non-Javadoc)
   * @see CommandAbstract#isFinished()
   */
  @Override
  public boolean isFinished() {
    if ( cntr > 2 ) { return true; }
    return false;
  }

}  //  end CommandThree
