import java.util.ArrayList;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ArrayList<Student> students = StudentDataManager.loadStudents();
            MainGUI gui = new MainGUI(students);
            gui.setVisible(true);
        });
    }
}
