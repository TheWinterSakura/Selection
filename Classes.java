import java.util.ArrayList;
import java.util.Random;
class Classes {
    private String className;
    private ArrayList<Group> groups;
    private ArrayList<Student> allStudents;

    public ArrayList<Student> getAllStudents(){
        return allStudents;
    }

    public ArrayList<Group> getGroups(){
        return groups;
    }

    public Classes(String className) {
        this.className = className;
        groups = new ArrayList<>();
        allStudents = new ArrayList<>();
    }

    public void addGroup(Group group) {
        if (group != null) {
            groups.add(group);
            allStudents.addAll(group.getStudents());
        }
    }

    public Group pickRandomGroup() {
        Random random = new Random();
        int index = random.nextInt(groups.size());
        return groups.get(index);
    }

    public Student pickRandomStudentFromGroup(Group group) {
        Random random = new Random();
        int index = random.nextInt(group.getStudents().size());
        return group.getStudents().get(index);
    }

}
