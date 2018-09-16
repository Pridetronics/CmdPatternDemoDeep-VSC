package tim.cmddemo;

import java.util.ArrayDeque;
import java.util.Stack;

public class DaLoop {

  private ArrayDeque<CommandAbstract> commands;
  private boolean teleop;

  private double fpgaTime;
  private static final int TIMEPRINT = 20;
  private static final double LOOPTIMESTEP = 0.020;
  private double endTime;

  public DaLoop(double timeToStop) { // ctor
    commands = new ArrayDeque<CommandAbstract>();
    commands.addFirst(new CommandOne("default"));
    teleop = false;
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
      if (fpgaTime > endTime) {
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

    // use this list to remember which commands have finished
    Stack<CommandAbstract> deleteList = new Stack<>();

    if (Math.round(fpgaTime * 1000.0) % TIMEPRINT == 0) {
      // print the time out every now and then
      System.out.println();
      System.out.println("Time " + fpgaTime);
    }

    // execute each active command
    for (CommandAbstract c : commands) {
      if (c.getName() == "default")
        continue; // skip the default command
      c.execute();
      if (c.isFinished()) {
        // if the command is done, remember for deletion later
        deleteList.push(c);
      }
    }

    while (!deleteList.empty()) {
      // remove commands that are done from the execution list
      commands.remove(deleteList.pop());
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

    // set teleoperation active
    daRobot.toggleTeleop();

    // start the robot
    daRobot.runRobot();

    // all done
    System.out.println();
    System.out.println("End Time " + daRobot.getTime());
  } // end main
} // end class DaLoop
