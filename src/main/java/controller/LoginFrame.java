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

public class LoginFrame extends JFrame {

	private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;
    
    public static void main(String[] args) {
        new LoginFrame(); 
    }
    public LoginFrame() {
        setTitle("登入畫面");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        JPanel panel = new JPanel();
        getContentPane().add(panel);
        placeComponents(panel);
        
        JButton register = new JButton("註冊");
        register.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		dispose();
        		RegisterFrame registerFrame = new RegisterFrame();
        		registerFrame.setVisible(true);
        	}
        });
        register.setBounds(185, 91, 80, 25);
        panel.add(register);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("使用者名稱:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(100, 20, 165, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("密碼:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 165, 25);
        panel.add(passwordField);

        loginButton = new JButton("登入");
        loginButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                UserService userService = new UserServiceImpl();
                User user = userService.login(username, password);

                if (user != null) {
                    JOptionPane.showMessageDialog(LoginFrame.this, 
                        "登入成功，歡迎 " + user.getName(), 
                        "登入成功", 
                        JOptionPane.INFORMATION_MESSAGE);
                    	dispose();
                    	if("admin".equalsIgnoreCase(user.getRole()))
                    	{
                    		new AdminDashboardFrame(user).setVisible(true);
                    	}else {
                    		UserCenterFrame userCenterFrame = new UserCenterFrame(user);
                            userCenterFrame.setVisible(true);
                    	}
                    	
                } else {
                    int option = JOptionPane.showConfirmDialog(LoginFrame.this, 
                        "登入失敗，帳號或密碼錯誤\n是否要前往註冊？", 
                        "登入失敗", 
                        JOptionPane.YES_NO_OPTION);

                
                    if (option == JOptionPane.YES_OPTION) {
                        dispose();
                        RegisterFrame registerFrame = new RegisterFrame();
                        registerFrame.setVisible(true);
                    }
                }
        	}
        });
        loginButton.setBounds(100, 90, 80, 25);
        panel.add(loginButton);

    }
}
