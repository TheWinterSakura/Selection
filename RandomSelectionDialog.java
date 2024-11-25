import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class RandomSelectionDialog extends JDialog {
    private JLabel displayLabel;
    private Timer animationTimer;
    private List<Student> studentList;
    private Student selectedStudent;
    private int animationCount = 0;
    private final int ANIMATION_DURATION = 20; // 动画循环次数
    
    public RandomSelectionDialog(JFrame parent, List<Student> students, String title) {
        super(parent, title, true);
        this.studentList = students;
        
        // 设置窗口属性
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        
        // 创建显示标签
        displayLabel = new JLabel("", SwingConstants.CENTER);
        displayLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        
        // 创建一个带有边框的面板
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.add(displayLabel, BorderLayout.CENTER);
        
        // 添加确定按钮
        JButton confirmButton = new JButton("确定");
        confirmButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        confirmButton.addActionListener(e -> dispose());
        confirmButton.setEnabled(false);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmButton);
        
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // 创建动画计时器
        animationTimer = new Timer(100, e -> {
            if (animationCount < ANIMATION_DURATION) {
                // 动画过程中随机显示
                Random random = new Random();
                int index = random.nextInt(studentList.size());
                Student tempStudent = studentList.get(index);
                displayLabel.setText(tempStudent.getName());
                animationCount++;
            } else {
                // 动画结束，显示最终选中的学生
                animationTimer.stop();
                displayLabel.setText(selectedStudent.getName());
                confirmButton.setEnabled(true);
                
                // 添加闪烁效果
                Timer blinkTimer = new Timer(500, event -> {
                    displayLabel.setForeground(
                        displayLabel.getForeground() == Color.RED ? 
                        Color.BLACK : Color.RED
                    );
                });
                blinkTimer.start();
            }
        });
    }
    
    public void startSelection() {
        // 随机选择一个学生
        Random random = new Random();
        selectedStudent = studentList.get(random.nextInt(studentList.size()));
        
        // 重置动画计数器并启动动画
        animationCount = 0;
        animationTimer.start();
        
        // 显示对话框
        setVisible(true);
    }
} 