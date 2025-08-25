package controller;



import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.User;
import service.UserService;
import service.impl.UserServiceImpl;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterFrame extends JFrame {
	  public static void main(String[] args) {
	        new RegisterFrame();
	        }
    public RegisterFrame() {
        setTitle("註冊畫面");
        setSize(350, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        getContentPane().add(panel);
        placeComponents(panel);
        
        JButton back = new JButton("返回");
        back.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		dispose();
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
        		
        	}
        });
        back.setBounds(198, 150, 100, 25);
        panel.add(back);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("姓名:");
        nameLabel.setBounds(10, 20, 80, 25);
        panel.add(nameLabel);

        JTextField nameText = new JTextField(20);
        nameText.setBounds(88, 20, 212, 25);
        panel.add(nameText);

        JLabel userLabel = new JLabel("帳號:");
        userLabel.setBounds(10, 60, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(88, 60, 212, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("密碼:");
        passwordLabel.setBounds(10, 100, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(88, 100, 212, 25);
        panel.add(passwordText);

        JButton registerButton = new JButton("註冊");
        registerButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		String name = nameText.getText().trim();
                String username = userText.getText().trim();
                String password = new String(passwordText.getPassword()).trim();
                if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "所有欄位都必須填寫，不能為空白！", "註冊失敗", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                UserService userService = new UserServiceImpl();
                User newUser = new User(name, username, password, "user");

                boolean result = userService.register(newUser);
                if (result) {
                    JOptionPane.showMessageDialog(null, "註冊成功！");
                    dispose();
                    LoginFrame loginFrame = new LoginFrame();
                    loginFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "帳號已存在！");
                }
        	}
        });
        registerButton.setBounds(88, 150, 100, 25);
        panel.add(registerButton);
    }
}
