package tim.cmddemo;

import java.util.ArrayDeque;
import java.util.Stack;

public class DaLoop {

  private ArrayDeque<CommandAbstract> commands;
  private boolean teleop;

  private double fpgaTime;
  private static final int TIMEPRINT = 1;
  private static final double LOOPTIMESTEP = 0.020;
  private static final double ENDTIME = 1.0;

  public DaLoop() { // ctor
    commands = new ArrayDeque<CommandAbstract>();
    commands.addFirst(new CommandOne("default"));
    teleop = false;
    fpgaTime = 0.0;
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
      if (fpgaTime > ENDTIME) {
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
    Stack<CommandAbstract> deleteList = new Stack<>();

    if ((fpgaTime * 1000.0) % TIMEPRINT == 0) {
      System.out.println();
      System.out.println("Time " + fpgaTime);
    }
    for (CommandAbstract c : commands) {
      if (c.getName() == "default")
        continue; // skip the default ommand
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
    DaLoop daRobot = new DaLoop();
    daRobot.addCommand(new CommandOne("A"));
    daRobot.addCommand(new CommandTwo("B"));
    daRobot.addCommand(new CommandThree("C"));
    daRobot.addCommand(new CommandFour("D"));
    daRobot.toggleTeleop();
    daRobot.runRobot();
    System.out.println();
    System.out.println("End Time " + daRobot.getTime());
  } // end main
} // end class DaLoop
