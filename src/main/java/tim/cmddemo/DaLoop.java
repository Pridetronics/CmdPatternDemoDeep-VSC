package tim.cmddemo;

import java.util.ArrayDeque;
import java.util.Stack;

/* 
//To show the inner workings of the command schedule.
// Set break points to line 59
// Go to Debug in the taskbar uptop
// Run without Debugging
// Then Start Debugging
// You should be able to see changes on the left where it says variables.
*/

public class DaLoop {

  private ArrayDeque<CommandAbstract> activeCommands;
  private boolean autonomousActive;

  private double fpgaTime;
  private static final int TIMEPRINT = 20;
  private static final double LOOPTIMESTEP = 0.020;
  private double endTime;

  public DaLoop(double timeToStop) { // ctor

    System.out.println("\nRunning DaLoop\n");

    activeCommands = new ArrayDeque<CommandAbstract>();
    activeCommands.addFirst(new CommandOne("default"));
    autonomousActive = false;
    fpgaTime = 0.0;
    setEndTime(timeToStop);
  }

  public void setEndTime(double timeToStop) {
    endTime = timeToStop;
  }

  public double getTime() {
    return fpgaTime;
  }

  public void addCommand(CommandAbstract c) { // add a command to commands
    activeCommands.add(c);
    c.initialize();
  }

  public void removeCommand(CommandAbstract c) { // remove command from the command list
    c.end();
    activeCommands.remove(c);
  }

  public boolean toggleAutonomousActive() { // toggle autonomousActive on/off
    return autonomousActive = !autonomousActive;
  }

  public boolean runRobot() {
    fpgaTime = 0;
    while (autonomousActive) {
      fpgaTime += LOOPTIMESTEP;
      if (fpgaTime > endTime) {
        toggleAutonomousActive();
      }
      autonomousActivePeriodic();
    }
    return true;
  }

  public boolean autonomousActivePeriodic() {
    run();
    return true;
  }

  public boolean run() {
    // this is the moral equivalent of Scheduler.getInstance().run()

    boolean status = true;

    // use this list to remember which commands have finished
    Stack<CommandAbstract> deleteList = new Stack<>();

    if (Math.round(fpgaTime * 1000.0) % TIMEPRINT == 0) {
      // print the time out every now and then
      System.out.println();
      System.out.println("Time " + fpgaTime);
    }

    // execute each active command
    for (CommandAbstract c : activeCommands) {
      if (c.getName().contains("default"))
        continue; // skip the default command
      c.execute();
      if (c.isFinished()) {
        // if the command is done, remember for deletion later
        deleteList.push(c);
      }
    }

    while (!deleteList.empty()) {
      // remove commands that are done from the execution list
      activeCommands.remove(deleteList.pop());
    }

    return status;
  } // end run

  public static void main(String[] args) {
    // create a robot
    DaLoop daRobot = new DaLoop(1.0);

    // add commands to execute
    daRobot.addCommand(new CommandOne("A"));
    daRobot.addCommand(new CommandTwo("B"));
    daRobot.addCommand(new CommandThree("C"));
    daRobot.addCommand(new CommandFour("D"));

    // set autonomousActiveeration active
    daRobot.toggleAutonomousActive();

    // start the robot
    daRobot.runRobot();

    // all done
    System.out.println();
    System.out.println("End Time " + daRobot.getTime());
  } // end main
} // end class DaLoop
