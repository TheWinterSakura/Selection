import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class MainGUI extends JFrame {
    private Classes classA;
    private Classes classB;
    private ArrayList<Student> allStudentsFromBothClasses;
    private JTextArea outputArea;
    private ArrayList<Student> students;

    public MainGUI() {
        this(StudentDataManager.loadStudents());
    }

    public MainGUI(ArrayList<Student> existingStudents) {
        this.students = existingStudents;
        
        // 设置窗口基本属性
        setTitle("班级管理系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 创建按钮面板，使用GridLayout实现平铺布局
        JPanel buttonPanel = new JPanel(new GridLayout(1, 7, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // 创建六个功能按钮
        JButton showStudentsBtn = new JButton("显示学生信息");
        JButton searchStudentBtn = new JButton("查找学生");
        JButton deleteStudentBtn = new JButton("删除学生");
        JButton randomStudentBtn = new JButton("随机抽取学生");
        JButton randomGroupBtn = new JButton("随机抽取小组");
        JButton randomStudentFromGroupBtn = new JButton("随机抽取小组学生");
        JButton addStudentBtn = new JButton("添加学生");
        
        // 添加按钮到面板
        buttonPanel.add(showStudentsBtn);
        buttonPanel.add(searchStudentBtn);
        buttonPanel.add(deleteStudentBtn);
        buttonPanel.add(randomStudentBtn);
        buttonPanel.add(randomGroupBtn);
        buttonPanel.add(randomStudentFromGroupBtn);
        buttonPanel.add(addStudentBtn);
        
        // 创建输出区域
        outputArea = new JTextArea(20, 60);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        
        // 添加组件到窗口
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // 添加按钮事件监听器
        randomGroupBtn.addActionListener(e -> randomGroupAction());
        randomStudentFromGroupBtn.addActionListener(e -> randomStudentFromGroupAction());
        randomStudentBtn.addActionListener(e -> randomStudentAction());
        searchStudentBtn.addActionListener(e -> searchStudentAction());
        addStudentBtn.addActionListener(e -> addStudentAction());
        deleteStudentBtn.addActionListener(e -> deleteStudentAction());
        showStudentsBtn.addActionListener(e -> showStudentsAction());
        
        // 设置窗口大小和位置
        pack();
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        
        // 在所有UI组件初始化完成后，再初始化班级数据
        initializeData();
    }
    
    private void initializeData() {
        if (students == null) {
            students = new ArrayList<>();
        }
        initializeClasses();
    }
    
    // 当需要保存数据时调用
    private void saveData() {
        StudentDataManager.saveStudents(students);
    }
    
    // 在程序关闭时调用
    public void onClose() {
        saveData();
    }
    
    private void initializeClasses() {
        classA = new Classes("Class A");
        classB = new Classes("Class B");
        
        if (students.isEmpty()) {
            return;
        }
        
        for (Student student : students) {
            if (student != null && student.getClassName() != null && student.getGroupName() != null) {
                if ("Class A".equals(student.getClassName())) {
                    classA.addStudent(student);
                } else if ("Class B".equals(student.getClassName())) {
                    classB.addStudent(student);
                }
            }
        }
    }
    
    // 这里添加各个按钮的动作处理方法
    private void randomGroupAction() {
        // 重新加载学生数据
        initializeClasses();
        
        String[] options = {"Class A", "Class B"};
        int choice = JOptionPane.showOptionDialog(this,
            "请选择班级",
            "选择班级",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
            
        if (choice >= 0) {
            Classes selectedClass = choice == 0 ? classA : classB;
            Group randomGroup = selectedClass.pickRandomGroup();
            outputArea.setText("随机抽取的小组: " + randomGroup.getGroupName());
        }
    }
    
    private void randomStudentFromGroupAction() {
        // 重新加载学生数据
        initializeClasses();
        
        String[] classOptions = {"Class A", "Class B"};
        int classChoice = JOptionPane.showOptionDialog(this,
            "请选择班级",
            "选择班级",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            classOptions,
            classOptions[0]);
        
        if (classChoice >= 0) {
            Classes selectedClass = classChoice == 0 ? classA : classB;
            
            // 获取选中班级的所有小组
            String[] groupOptions = selectedClass.getGroups().stream()
                .map(Group::getGroupName)
                .toArray(String[]::new);
                
            // 让用户选择小组
            int groupChoice = JOptionPane.showOptionDialog(this,
                "请选择小组",
                "选择小组",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                groupOptions,
                groupOptions[0]);
                
            if (groupChoice >= 0) {
                // 获取选中的小组
                Group selectedGroup = selectedClass.getGroups().get(groupChoice);
                if (selectedGroup != null) {
                    Student randomStudent = selectedGroup.pickRandomStudent();
                    if (randomStudent != null) {
                        outputArea.setText("从 " + selectedClass.getClassName() + 
                                        " 的 " + selectedGroup.getGroupName() + 
                                        " 中随机抽取的学生: " + randomStudent.getName());
                    } else {
                        outputArea.setText("所选小组中没有学生可供抽取");
                    }
                }
            }
        }
    }

    private void randomStudentAction() {
        // 重新加载学生数据
        initializeClasses();
        
        String[] options = {"Class A", "Class B", "所有学生"};
        int choice = JOptionPane.showOptionDialog(this,
            "请选择抽取范围",
            "选择范围",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        
        if (choice >= 0) {
            Student randomStudent = null;
            String className = "";
            
            if (choice == 0) {
                ArrayList<Student> classAStudents = classA.getAllStudents();
                if (!classAStudents.isEmpty()) {
                    Random random = new Random();
                    randomStudent = classAStudents.get(random.nextInt(classAStudents.size()));
                    className = "Class A";
                }
            } else if (choice == 1) {
                ArrayList<Student> classBStudents = classB.getAllStudents();
                if (!classBStudents.isEmpty()) {
                    Random random = new Random();
                    randomStudent = classBStudents.get(random.nextInt(classBStudents.size()));
                    className = "Class B";
                }
            } else {
                // 从所有学生中随机抽取
                if (!students.isEmpty()) {
                    Random random = new Random();
                    randomStudent = students.get(random.nextInt(students.size()));
                    className = "所有学生";
                }
            }
            
            if (randomStudent != null) {
                outputArea.setText("从" + className + "中随机抽取的学生: " + randomStudent.getName());
            } else {
                outputArea.setText("所选范围内没有学生可供抽取");
            }
        }
    }

    private void searchStudentAction() {
        // 重新加载学生数据
        initializeClasses();
        
        String[] options = {"按姓名查找", "按学号查找"};
        int searchType = JOptionPane.showOptionDialog(this,
            "请选择查找方式",
            "查找学生",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        
        if (searchType >= 0) {
            String searchText = JOptionPane.showInputDialog(this, 
                searchType == 0 ? "请输入要查找的学生姓名:" : "请输入要查找的学生学号:");
            
            if (searchText != null && !searchText.trim().isEmpty()) {
                StringBuilder result = new StringBuilder();
                
                // 在 Class A 中查找
                Student studentInA = searchType == 0 ? 
                    classA.findStudent(searchText) : 
                    classA.findStudentById(searchText);
                if (studentInA != null) {
                    result.append("在 Class A 中找到学生:\n");
                    result.append(studentInA.toString()).append("\n");
                }
                
                // 在 Class B 中查找
                Student studentInB = searchType == 0 ? 
                    classB.findStudent(searchText) : 
                    classB.findStudentById(searchText);
                if (studentInB != null) {
                    result.append("在 Class B 中找到学生:\n");
                    result.append(studentInB.toString()).append("\n");
                }
                
                if (result.length() == 0) {
                    result.append("未找到").append(searchType == 0 ? "姓名为 " : "学号为 ")
                          .append(searchText).append(" 的学生");
                }
                
                outputArea.setText(result.toString());
            }
        }
    }

    private void addStudentAction() {
        String studentName = JOptionPane.showInputDialog(this, "请输入新学生姓名:");
        if (studentName != null && !studentName.trim().isEmpty()) {
            String studentId = JOptionPane.showInputDialog(this, "请输入学生学号:");
            if (studentId != null && !studentId.trim().isEmpty()) {
                String[] options = {"Class A", "Class B"};
                int classChoice = JOptionPane.showOptionDialog(this,
                    "请选择要添加到哪个班级",
                    "选择班级",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
                    
                if (classChoice >= 0) {
                    Classes selectedClass = classChoice == 0 ? classA : classB;
                    String className = classChoice == 0 ? "Class A" : "Class B";
                    
                    // 选择小组
                    String[] groupOptions = classChoice == 0 ? 
                        new String[]{"A1组", "A2组"} : 
                        new String[]{"B1组", "B2组"};
                        
                    int groupChoice = JOptionPane.showOptionDialog(this,
                        "请选择小组",
                        "选择小组",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        groupOptions,
                        groupOptions[0]);
                    
                    if (groupChoice >= 0) {
                        String groupName = groupOptions[groupChoice];
                        Student newStudent = new Student(studentName, studentId, className, groupName);
                        
                        // 添加到班级和学生列表
                        selectedClass.addStudent(newStudent);
                        students.add(newStudent);
                        
                        // 保存到文件
                        saveData();
                        
                        outputArea.setText("已将学生 " + studentName + " 添加到 " + className + " 的 " + groupName);
                    }
                }
            }
        }
    }

    private void deleteStudentAction() {
        String[] options = {"按姓名删除", "按学号删除"};
        int deleteType = JOptionPane.showOptionDialog(this,
            "请选择删除方式",
            "删除学生",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        
        if (deleteType >= 0) {
            String searchText = JOptionPane.showInputDialog(this, 
                deleteType == 0 ? "请输入要删除的学生姓名:" : "请输入要删除的学生学号:");
            
            if (searchText != null && !searchText.trim().isEmpty()) {
                StringBuilder result = new StringBuilder();
                boolean deleted = false;
                
                // 从 Class A 中删除
                Student studentInA = deleteType == 0 ? 
                    classA.findStudent(searchText) : 
                    classA.findStudentById(searchText);
                if (studentInA != null) {
                    classA.removeStudent(studentInA);
                    students.remove(studentInA);
                    result.append("已从 Class A 中删除学生: ").append(studentInA.getName()).append("\n");
                    deleted = true;
                }
                
                // 从 Class B 中删除
                Student studentInB = deleteType == 0 ? 
                    classB.findStudent(searchText) : 
                    classB.findStudentById(searchText);
                if (studentInB != null) {
                    classB.removeStudent(studentInB);
                    students.remove(studentInB);
                    result.append("已从 Class B 中删除学生: ").append(studentInB.getName()).append("\n");
                    deleted = true;
                }
                
                if (deleted) {
                    // 保存更改到文件
                    saveData();
                } else {
                    result.append("未找到").append(deleteType == 0 ? "姓名为 " : "学号为 ")
                          .append(searchText).append(" 的学生");
                }
                
                outputArea.setText(result.toString());
            }
        }
    }

    private void showStudentsAction() {
        // 重新加载学生数据
        initializeClasses();
        
        StringBuilder result = new StringBuilder();
        result.append("当前所有学生信息：\n\n");
        
        // 显示 Class A 的学生
        result.append("=== Class A ===\n");
        for (Group group : classA.getGroups()) {
            result.append("\n").append(group.getGroupName()).append(":\n");
            for (Student student : group.getStudents()) {
                result.append(student.toString()).append("\n");
            }
        }
        
        // 显示 Class B 的学生
        result.append("\n=== Class B ===\n");
        for (Group group : classB.getGroups()) {
            result.append("\n").append(group.getGroupName()).append(":\n");
            for (Student student : group.getStudents()) {
                result.append(student.toString()).append("\n");
            }
        }
        
        outputArea.setText(result.toString());
    }
} 