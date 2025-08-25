package controller;



import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.User;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserCenterFrame extends JFrame {

	private final User loggedInUser;

    public UserCenterFrame(User user) {
        this.loggedInUser = user;

        setTitle("使用者中心");
        setSize(400, 288);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        getContentPane().setLayout(null);

        JLabel welcomeLabel = new JLabel("歡迎使用，" + loggedInUser.getName() + "！");
        welcomeLabel.setBounds(30, 20, 300, 30);
        getContentPane().add(welcomeLabel);

        JButton addMusicButton = new JButton("新增音樂");
        addMusicButton.setBounds(120, 70, 150, 30);
        getContentPane().add(addMusicButton);

        addMusicButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose(); 
                AddMusicFrame addMusicFrame = new AddMusicFrame(loggedInUser);
                addMusicFrame.setVisible(true);
            }
        });
        JButton viewMusicButton = new JButton("查看音樂清單");
        viewMusicButton.setBounds(120, 110, 150, 30);
        getContentPane().add(viewMusicButton);

        viewMusicButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                MusicListFrame musicListFrame = new MusicListFrame(loggedInUser);
                musicListFrame.setVisible(true);
            }
        });

        JButton editProfileButton = new JButton("查看個人資料");
        editProfileButton.setBounds(120, 150, 150, 30);
        getContentPane().add(editProfileButton);
        editProfileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                EditProfileFrame edit = new EditProfileFrame(loggedInUser);
                edit.setVisible(true);
            }
        });
        
        JButton logoutButton = new JButton("登出");
        logoutButton.setBounds(120, 187, 150, 30);
        getContentPane().add(logoutButton);

        logoutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose(); 
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            }
        });

        setVisible(true);
    }
}