import java.util.ArrayList;
import java.util.Random;

public class Group {
    private String groupName;
    private ArrayList<Student> students;
    
    public Group(String groupName) {
        this.groupName = groupName;
        this.students = new ArrayList<>();
    }
    
    public void addStudent(Student student) {
        students.add(student);
    }
    
    public void removeStudent(Student student) {
        students.remove(student);
    }
    
    public String getGroupName() {
        return groupName;
    }
    
    public Student pickRandomStudent() {
        if (students.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return students.get(random.nextInt(students.size()));
    }
    
    public ArrayList<Student> getStudents() {
        return students;
    }
}