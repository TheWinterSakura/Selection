import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class RandomGroupSelectionDialog extends JDialog {
    private JLabel displayLabel;
    private Timer animationTimer;
    private List<Group> groupList;
    private Group selectedGroup;
    private int animationCount = 0;
    private final int ANIMATION_DURATION = 20;
    
    public RandomGroupSelectionDialog(JFrame parent, List<Group> groups, String title) {
        super(parent, title, true);
        this.groupList = groups;
        
        // 设置窗口属性
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        
        // 创建显示标签
        displayLabel = new JLabel("", SwingConstants.CENTER);
        displayLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        
        // 创建带边框的面板
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.add(displayLabel, BorderLayout.CENTER);
        
        // 添加确认按钮
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
                int index = random.nextInt(groupList.size());
                Group tempGroup = groupList.get(index);
                displayLabel.setText(tempGroup.getGroupName());
                animationCount++;
            } else {
                // 动画结束，显示最终选中的小组
                animationTimer.stop();
                displayLabel.setText(selectedGroup.getGroupName());
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
        // 随机选择一个小组
        Random random = new Random();
        selectedGroup = groupList.get(random.nextInt(groupList.size()));
        
        // 重置动画计数器并启动动画
        animationCount = 0;
        animationTimer.start();
        
        // 显示对话框
        setVisible(true);
    }
} 