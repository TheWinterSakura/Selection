import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StudentManagementGUI extends JFrame {
    private Classes classA;
    private Classes classB;
    private ArrayList<Student> allStudentsFromBothClasses;

    public StudentManagementGUI(Classes classA, Classes classB) {
        this.classA = classA;
        this.classB = classB;
        this.allStudentsFromBothClasses = new ArrayList<>();
        allStudentsFromBothClasses.addAll(classA.getAllStudents());
        allStudentsFromBothClasses.addAll(classB.getAllStudents());

        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("学生管理系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.CENTER);
        
        setLocationRelativeTo(null);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton[] buttons = {
            new JButton("随机抽取小组"),
            new JButton("从小组随机抽取学生"),
            new JButton("随机抽取学生"),
            new JButton("添加新学生"),
            new JButton("查询学生信息"),
            new JButton("修改学生信息")
        };

        for (JButton button : buttons) {
            buttonPanel.add(button);
        }

        buttons[0].addActionListener(e -> randomGroup());
        buttons[1].addActionListener(e -> randomStudentFromGroup());
        buttons[2].addActionListener(e -> randomStudent());
        buttons[3].addActionListener(e -> addStudent());
        buttons[4].addActionListener(e -> searchStudent());
        buttons[5].addActionListener(e -> modifyStudent());

        return buttonPanel;
    }

    private void randomGroup() {
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
            RandomGroupSelectionDialog dialog = new RandomGroupSelectionDialog(
                this,
                selectedClass.getGroups(),
                "随机抽取小组"
            );
            dialog.startSelection();
        }
    }

    private void randomStudentFromGroup() {
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
            String[] groupNames = selectedClass.getGroups().stream()
                .map(Group::getGroupName)
                .toArray(String[]::new);

            int groupChoice = JOptionPane.showOptionDialog(this,
                "请选择小组",
                "选择小组",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                groupNames,
                groupNames[0]);

            if (groupChoice >= 0) {
                Group selectedGroup = selectedClass.getGroups().get(groupChoice);
                RandomSelectionDialog dialog = new RandomSelectionDialog(
                    this,
                    selectedGroup.getStudents(),
                    "从" + selectedGroup.getGroupName() + "随机抽取学生"
                );
                dialog.startSelection();
            }
        }
    }

    private void randomStudent() {
        if (!allStudentsFromBothClasses.isEmpty()) {
            RandomSelectionDialog dialog = new RandomSelectionDialog(
                this,
                allStudentsFromBothClasses,
                "随机抽取学生"
            );
            dialog.startSelection();
        }
    }

    private void addStudent() {
        String[] classOptions = {"Class A", "Class B"};
        int classChoice = JOptionPane.showOptionDialog(this,
            "请选择班级",
            "添加学生",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            classOptions,
            classOptions[0]);

        if (classChoice >= 0) {
            Classes selectedClass = classChoice == 0 ? classA : classB;
            String[] groupNames = selectedClass.getGroups().stream()
                .map(Group::getGroupName)
                .toArray(String[]::new);

            int groupChoice = JOptionPane.showOptionDialog(this,
                "请选择小组",
                "选择小组",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                groupNames,
                groupNames[0]);

            if (groupChoice >= 0) {
                String name = JOptionPane.showInputDialog(this, "请输入学生姓名：");
                String idStr = JOptionPane.showInputDialog(this, "请输入学生ID：");

                if (name != null && idStr != null && !name.trim().isEmpty()) {
                    try {
                        int studentId = Integer.parseInt(idStr);
                        if (isStudentIdExists(studentId)) {
                            ResultDialog dialog = new ResultDialog(this, "添加结果", "该学生ID已存在！");
                            dialog.setVisible(true);
                            return;
                        }

                        Student newStudent = new Student(name, studentId);
                        selectedClass.getGroups().get(groupChoice).addStudent(newStudent);
                        allStudentsFromBothClasses.add(newStudent);
                        ResultDialog dialog = new ResultDialog(this, "添加结果", "学生添加成功！");
                        dialog.setVisible(true);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this,
                            "请输入有效的学生ID！",
                            "错误",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    private boolean isStudentIdExists(int studentId) {
        return allStudentsFromBothClasses.stream()
            .anyMatch(student -> student.getStudentId() == studentId);
    }

    private void searchStudent() {
        String[] options = {"按学号查询", "按姓查询"};
        int choice = JOptionPane.showOptionDialog(this,
            "请选择查询方式",
            "查询学生",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);

        if (choice == 0) {
            String idStr = JOptionPane.showInputDialog(this, "请输入学生ID：");
            if (idStr != null) {
                try {
                    int searchId = Integer.parseInt(idStr);
                    searchStudentById(searchId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this,
                        "请输入有效的学生ID！",
                        "错误",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (choice == 1) {
            String searchName = JOptionPane.showInputDialog(this, "请输入学生姓名：");
            if (searchName != null) {
                searchStudentByName(searchName);
            }
        }
    }

    private void searchStudentById(int searchId) {
        StringBuilder result = new StringBuilder();
        boolean found = false;
        
        for (Student student : allStudentsFromBothClasses) {
            if (student.getStudentId() == searchId) {
                result.append("找到学生：\n");
                result.append("姓名：").append(student.getName()).append("\n");
                result.append("学号：").append(student.getStudentId());
                found = true;
                break;
            }
        }
        
        if (!found) {
            result.append("未找到该学生！");
        }
        
        ResultDialog dialog = new ResultDialog(this, "查询结果", result.toString());
        dialog.setVisible(true);
    }

    private void searchStudentByName(String searchName) {
        StringBuilder result = new StringBuilder();
        boolean found = false;
        
        for (Student student : allStudentsFromBothClasses) {
            if (student.getName().equals(searchName)) {
                if (found) result.append("\n\n");
                result.append("找到学生：\n");
                result.append("姓名：").append(student.getName()).append("\n");
                result.append("学号：").append(student.getStudentId());
                found = true;
            }
        }
        
        if (!found) {
            result.append("未找到该学生！");
        }
        
        ResultDialog dialog = new ResultDialog(this, "查询结果", result.toString());
        dialog.setVisible(true);
    }

    private void modifyStudent() {
        String idStr = JOptionPane.showInputDialog(this, "请输入要修改的学生ID：");
        if (idStr != null) {
            try {
                int modifyId = Integer.parseInt(idStr);
                for (Student student : allStudentsFromBothClasses) {
                    if (student.getStudentId() == modifyId) {
                        String newName = JOptionPane.showInputDialog(this, 
                            "请输入新的学生姓名：", 
                            student.getName());
                        if (newName != null && !newName.trim().isEmpty()) {
                            student.setName(newName);
                            ResultDialog dialog = new ResultDialog(this, "修改结果", "学生信息修改成功！");
                            dialog.setVisible(true);
                        }
                        return;
                    }
                }
                ResultDialog dialog = new ResultDialog(this, "修改结果", "未找到该学生！");
                dialog.setVisible(true);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "请输入有效的学生ID！",
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
} 