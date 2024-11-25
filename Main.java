import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        Classes classA = new Classes("Class A");
        Classes classB = new Classes("Class B");

        Group group1 = new Group("Group 1");
        group1.addStudent(new Student("Aa", 1001));
        group1.addStudent(new Student("Bb", 1002));

        Group group2 = new Group("Group 2");
        group2.addStudent(new Student("Cc", 1003));
        group2.addStudent(new Student("Dd", 1004));

        Group group3 = new Group("Group 3");
        group3.addStudent(new Student("Ee", 1005));
        group3.addStudent(new Student("Ff", 1006));

        Group group4 = new Group("Group 4");
        group4.addStudent(new Student("Gg", 1007));
        group4.addStudent(new Student("Hh", 1008));

        classA.addGroup(group1);
        classA.addGroup(group2);
        classB.addGroup(group3);
        classB.addGroup(group4);

        ArrayList<Student> allStudentsFromBothClasses = new ArrayList<>();
        allStudentsFromBothClasses.addAll(classA.getAllStudents());
        allStudentsFromBothClasses.addAll(classB.getAllStudents());

        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
            System.out.println("\n请选择操作：");
            System.out.println("1. 随机抽取班级中的一个小组");
            System.out.println("2. 从班级中的某个小组随机抽取一个学生");
            System.out.println("3. 从整个班级随机抽取一个学生");
            System.out.println("4. 添加新学生");
            System.out.println("5. 查询学生信息");
            System.out.println("6. 修改学生信息");
            System.out.println("0. 退出程序");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // 清除换行符
            
            switch (choice) {
                case 1:
                    System.out.println("请选择班级：1. Class A  2. Class B");
                    int classChoice = scanner.nextInt();
                    Classes selectedClass = classChoice == 1 ? classA : classB;
                    Group randomGroup = selectedClass.pickRandomGroup();
                    System.out.println("随机抽取的小组: " + randomGroup.getGroupName());
                    break;

                case 2:
                    System.out.println("请选择班级：1. Class A  2. Class B");
                    classChoice = scanner.nextInt();
                    selectedClass = classChoice == 1 ? classA : classB;
                    System.out.println("请选择小组：1. " + selectedClass.getGroups().get(0).getGroupName() + "  2. " + selectedClass.getGroups().get(1).getGroupName());
                    int groupChoice = scanner.nextInt();
                    Group selectedGroup = selectedClass.getGroups().get(groupChoice - 1);
                    Student randomStudent = selectedClass.pickRandomStudentFromGroup(selectedGroup);
                    System.out.println("随机抽取的小组中的学生: " + randomStudent.getName());
                    break;

                case 3:
                    Random random = new Random();
                    int index = random.nextInt(allStudentsFromBothClasses.size());
                    Student randomStudentFromBothClasses = allStudentsFromBothClasses.get(index);
                    System.out.println("从A和B班中随机抽取的学生: " + randomStudentFromBothClasses.getName());
                    break;

                case 4:
                    System.out.println("请选择班级：1. Class A  2. Class B");
                    classChoice = scanner.nextInt();
                    scanner.nextLine();
                    selectedClass = classChoice == 1 ? classA : classB;
                    
                    System.out.println("请选择小组：1. " + selectedClass.getGroups().get(0).getGroupName() + 
                                     "  2. " + selectedClass.getGroups().get(1).getGroupName());
                    groupChoice = scanner.nextInt();
                    scanner.nextLine();
                    
                    System.out.println("请输入学生姓名：");
                    String name = scanner.nextLine();
                    System.out.println("请输入学生ID：");
                    int studentId = scanner.nextInt();
                    
                    // 检查是否存在相同ID的学生
                    boolean studentExists = false;
                    for (Student existingStudent : allStudentsFromBothClasses) {
                        if (existingStudent.getStudentId() == studentId) {
                            studentExists = true;
                            System.out.println("错误：该学生ID已存在，不能添加重复的学生！");
                            break;
                        }
                    }
                    
                    // 只有在学生不存在时才添加
                    if (!studentExists) {
                        Student newStudent = new Student(name, studentId);
                        selectedClass.getGroups().get(groupChoice - 1).addStudent(newStudent);
                        allStudentsFromBothClasses.add(newStudent);
                        System.out.println("学生添加成功！");
                    }
                    break;
                    
                case 5:
                    System.out.println("请选择查询方式：1. 按学号查询  2. 按姓名查询");
                    int searchChoice = scanner.nextInt();
                    scanner.nextLine(); // 清除换行符
                    
                    boolean found = false;
                    
                    if (searchChoice == 1) {
                        System.out.println("请输入要查询的学生ID：");
                        int searchId = scanner.nextInt();
                        
                        for (Student student : allStudentsFromBothClasses) {
                            if (student.getStudentId() == searchId) {
                                System.out.println("找到学生：");
                                System.out.println("姓名：" + student.getName());
                                System.out.println("学号：" + student.getStudentId());
                                found = true;
                                break;
                            }
                        }
                    } else if (searchChoice == 2) {
                        System.out.println("请输入要查询的学生姓名：");
                        String searchName = scanner.nextLine();
                        
                        for (Student student : allStudentsFromBothClasses) {
                            if (student.getName().equals(searchName)) {
                                System.out.println("找到学生：");
                                System.out.println("姓名：" + student.getName());
                                System.out.println("学号：" + student.getStudentId());
                                found = true;
                                // 不break，因为可能有同名学生
                            }
                        }
                    } else {
                        System.out.println("无效的选择！");
                        break;
                    }
                    
                    if (!found) {
                        System.out.println("未找到该学生！");
                    }
                    break;
                    
                case 6:
                    System.out.println("请输入要修改的学生ID：");
                    int modifyId = scanner.nextInt();
                    scanner.nextLine();
                    
                    for (Student student : allStudentsFromBothClasses) {
                        if (student.getStudentId() == modifyId) {
                            System.out.println("请输入新的学生姓名：");
                            String newName = scanner.nextLine();
                            student.setName(newName);
                            System.out.println("学生信息修改成功！");
                            break;
                        }
                    }
                    break;
                    
                case 0:
                    running = false;
                    break;
                    
                default:
                    System.out.println("无效的选择");
                    break;
            }
        }
        scanner.close();

        // 启动GUI
        SwingUtilities.invokeLater(() -> {
            StudentManagementGUI gui = new StudentManagementGUI(classA, classB);
            gui.setVisible(true);
        });
    }
}

