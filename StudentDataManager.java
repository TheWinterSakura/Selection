import java.io.*;
import java.util.ArrayList;

public class StudentDataManager {
    private static final String FILE_PATH = "StudentData.txt";

    // 保存学生数据到文件
    public static void saveStudents(ArrayList<Student> students) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(FILE_PATH), "UTF-8"))) {
            // 如果 students 为空，创建空列表避免 NPE
            if (students == null) {
                students = new ArrayList<>();
            }
            
            for (Student student : students) {
                writer.write(String.format("%s,%s,%s,%s", 
                    student.getName(), 
                    student.getId(), 
                    student.getClassName(), 
                    student.getGroupName()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 从文件中读取学生数据
    public static ArrayList<Student> loadStudents() {
        ArrayList<Student> students = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        if (!file.exists()) {
            return students;
        }
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    students.add(new Student(
                        data[0].trim(), 
                        data[1].trim(), 
                        data[2].trim(), 
                        data[3].trim()
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return students;
    }
} 