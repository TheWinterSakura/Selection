import java.util.ArrayList;

class Group {
    private String groupName;
    private ArrayList<Student> students;

    public Group(String groupName) {
        this.groupName = groupName;
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public String getGroupName() {
        return groupName;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }
}