import java.util.ArrayList;
import java.util.Random;

public class Classes {
    private String className;
    private ArrayList<Group> groups;
    
    public Classes(String className) {
        this.className = className;
        this.groups = new ArrayList<>();
        
        // 初始化小组
        if (className.equals("Class A")) {
            addGroup(new Group("A1组"));
            addGroup(new Group("A2组"));
        } else if (className.equals("Class B")) {
            addGroup(new Group("B1组"));
            addGroup(new Group("B2组"));
        }
    }
    
    public void addGroup(Group group) {
        groups.add(group);
    }
    
    public Group pickRandomGroup() {
        if (groups.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return groups.get(random.nextInt(groups.size()));
    }
    
    public Student pickRandomStudent() {
        ArrayList<Student> allStudents = getAllStudents();
        if (allStudents.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return allStudents.get(random.nextInt(allStudents.size()));
    }
    
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> allStudents = new ArrayList<>();
        for (Group group : groups) {
            allStudents.addAll(group.getStudents());
        }
        return allStudents;
    }
    
    public String getClassName() {
        return className;
    }
    
    public Student findStudent(String name) {
        for (Group group : groups) {
            for (Student student : group.getStudents()) {
                if (student.getName().equals(name)) {
                    return student;
                }
            }
        }
        return null;
    }
    
    public void removeStudent(Student student) {
        for (Group group : groups) {
            group.removeStudent(student);
        }
    }
    
    public void addStudent(Student student) {
        for (Group group : groups) {
            if (group.getGroupName().equals(student.getGroupName())) {
                group.addStudent(student);
                return;
            }
        }
    }
    
    public ArrayList<Group> getGroups() {
        return groups;
    }
    
    public Student findStudentById(String id) {
        for (Group group : groups) {
            for (Student student : group.getStudents()) {
                if (student.getId().equals(id)) {
                    return student;
                }
            }
        }
        return null;
    }
}
