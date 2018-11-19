package tim.cmddemo;

import java.util.ArrayDeque;
import java.util.Stack;

public class DaLoop {

  // program ID with git SHA1 of build
  public static final String ID = "CommandDemo $Id:$";

  /** commands is a list of commands that are currently active */
  private ArrayDeque<CommandAbstract> commands;

  private boolean teleop; // in teleoperated mode?

  /** keep track of time */
  private double fpgaTime;
  private static final double LOOPTIMESTEP = 0.020;
  private static final double ENDTIME = 1.0;

  public DaLoop() { // ctor
    commands = new ArrayDeque<CommandAbstract>();
    commands.addFirst(new CommandOne("default"));
    teleop = false;
    fpgaTime = 0.0;
  }

  public void addCommand(CommandAbstract c) { // add a command to commands
    commands.add(c);
    c.initialize();
  }

  public void removeCommand(CommandAbstract c) { // remove command from the command list
    c.end();
    commands.remove(c);
  }

  public boolean toggleTeleop() { // toggle teleop on/off
    return teleop = !teleop;
  }

  public boolean runRobot() {
    fpgaTime = 0;
    while (teleop) {
      fpgaTime += LOOPTIMESTEP;
      if (fpgaTime >= ENDTIME) {
        toggleTeleop();
      }
      teleopPeriodic();
    }
    return true;
  }

  public boolean teleopPeriodic() {
    run();
    return true;
  }

  public boolean run() {
    boolean status = true;

    // this is a list of commands to be removed from the
    // list of active commands
    Stack<CommandAbstract> deleteList = new Stack<>();

    // if ( (fpgaTime*1000.0) % 1 == 0 ) {
    System.out.println();
    System.out.println("Time " + fpgaTime);
    // }

    /**
     * every time run is called, visit every command on the active command list and
     * fire off it's execute method. When that returns, call that command's
     * isFinished method. If that returns true, add the command to the list of
     * commands to be removed from the active list.
     */
    for (CommandAbstract c : commands) {
      if (c.getName() == "default")
        continue; // skip the default command
      c.execute();
      if (c.isFinished()) {
        deleteList.push(c);
      }
    }
    while (!deleteList.empty()) {
      commands.remove(deleteList.pop());
    }

    return status;

  } // end run

  public static void main(String[] args) {
    DaLoop dl = new DaLoop();
    dl.addCommand(new CommandOne("A"));
    dl.addCommand(new CommandTwo("B"));
    dl.addCommand(new CommandThree("C"));
    dl.addCommand(new CommandFour("D"));
    dl.toggleTeleop();
    dl.runRobot();
  } // end main
} // end class DaLoop
