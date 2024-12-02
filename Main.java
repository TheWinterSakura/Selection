import java.util.ArrayList;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 从文件加载学生数据
            ArrayList<Student> students = StudentDataManager.loadStudents();
            MainGUI gui = new MainGUI(students);
            gui.setVisible(true);
        });
    }
}
