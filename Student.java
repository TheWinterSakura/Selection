public class Student {
    private String name;
    private String id;
    private String className;
    private String groupName;
    
    public Student(String name, String id, String className, String groupName) {
        this.name = name;
        this.id = id;
        this.className = className;
        this.groupName = groupName;
    }
    
    public Student(String name) {
        this.name = name;
        this.id = "";
        this.className = "";
        this.groupName = "";
    }
    
    public String getName() {
        return name;
    }
    
    public String getId() {
        return id;
    }
    
    public String getClassName() {
        return className;
    }
    
    public String getGroupName() {
        return groupName;
    }
    
    @Override
    public String toString() {
        return "学生: " + name + ", 学号: " + id + ", 班级: " + className + ", 小组: " + groupName;
    }
}