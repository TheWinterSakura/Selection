import javax.swing.*;
import java.awt.*;

public class ResultDialog extends JDialog {
    public ResultDialog(JFrame parent, String title, String message) {
        super(parent, title, true);
        
        // 设置窗口属性
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        
        // 创建文本区域
        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        textArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // 添加滚动面板
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        // 添加确定按钮
        JButton confirmButton = new JButton("确定");
        confirmButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmButton);
        
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
} 