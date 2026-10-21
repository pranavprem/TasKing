package gui;

import beans.UserBean;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;
import services.NewUserService;
import services.TaskService;
import util.Utilities;

/**
 * Writes the README screenshots. Not a unit test.
 *
 * <p>Sample ids and the password {@code welcome} exist only for these pictures.
 */
public final class ScreenshotHarness {
  private ScreenshotHarness() {}

  public static void main(String[] args) throws Exception {
    Path output = args.length > 0 ? Path.of(args[0]) : Path.of("docs/screenshots");
    Files.createDirectories(output);
    Path data = Files.createTempDirectory("tasking-screenshots");
    System.setProperty(Utilities.DATA_DIR_PROPERTY, data.toString());
    seed();

    SwingUtilities.invokeAndWait(
        () -> {
          try {
            JLogin login = new JLogin();
            capture(login.getAppFrame(), output.resolve("login.png"));
            login.getAppFrame().dispose();

            JTasking board = new JTasking("10000001", "10000000");
            capture(board.getAppFrame(), output.resolve("board.png"));
            board.getAppFrame().dispose();
          } catch (Exception ex) {
            throw new RuntimeException(ex);
          }
        });
    System.exit(0);
  }

  private static void seed() {
    NewUserService.createUser(new UserBean("10000000", "welcome", "Division Lead", "00000000"));
    NewUserService.createUser(new UserBean("10000001", "welcome", "Alex Kim", "10000000"));
    NewUserService.createUser(new UserBean("10000002", "welcome", "Jordan Lee", "10000001"));
    NewUserService.createUser(new UserBean("10000003", "welcome", "Riley Chen", "10000001"));

    TaskService tasks = new TaskService();
    tasks.addTask(
        "10000001", "Close the quarter", "Tie out the shared workbook", "5", "15/10/2026");
    tasks.addTask(
        "10000001", "Review the vendor file", "Flag anything over threshold", "3", "16/10/2026");
    tasks.addTask(
        "10000000-10000001", "Send the pack upstairs", "Due before standup", "2", "15/10/2026");
    tasks.addTask("10000002", "Reconcile invoices", "Match the open items", "5", "15/10/2026");
    tasks.addTask("10000002", "Call the client", "Confirm the delivery date", "3", "17/10/2026");
    tasks.addTask("10000003", "File the receipts", "Scan the travel folder", "2", "18/10/2026");
    tasks.addTask(
        "10000001-10000002", "Prepare the slide notes", "Assigned by Alex", "1", "15/10/2026");
    tasks.addTask(
        "10000001-pool",
        "Cover the front desk",
        "Anyone on the team can pull this",
        "1",
        "15/10/2026");
    tasks.addTask(
        "10000000-pool",
        "Pick up the archive boxes",
        "From the division pool",
        "1.5",
        "20/10/2026");

    tasks.addTask("10000001", "Draft the status note", "Done this morning", "1", "15/10/2026");
    String done = dao.TaskDAO.getAllTasks("10000001").get(2).getID().toString();
    tasks.completeTask(done, "10000001", 0.5f);
  }

  private static void capture(JFrame frame, Path file) throws Exception {
    frame.setLocation(60, 40);
    frame.setVisible(true);
    frame.validate();
    RepaintManager.currentManager(frame).setDoubleBufferingEnabled(false);
    BufferedImage image =
        new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics = image.createGraphics();
    graphics.setColor(java.awt.Color.WHITE);
    graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
    frame.getRootPane().printAll(graphics);
    graphics.dispose();
    ImageIO.write(image, "png", file.toFile());
  }
}
