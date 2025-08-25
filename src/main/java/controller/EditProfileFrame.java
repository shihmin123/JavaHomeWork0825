package controller;

import model.User;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class EditProfileFrame extends JFrame {
    private final User loggedInUser;

    private JLabel nameValue;
    private JLabel usernameValue;

    public EditProfileFrame(User user) {
        this.loggedInUser = user;

        setTitle("個人資料");
        setSize(420, 240);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JLabel nameLabel = new JLabel("姓名：");
        nameLabel.setBounds(50, 40, 80, 25);
        getContentPane().add(nameLabel);

        nameValue = new JLabel(loggedInUser.getName());
        nameValue.setBounds(140, 40, 220, 25);
        getContentPane().add(nameValue);

        JLabel usernameLabel = new JLabel("帳號：");
        usernameLabel.setBounds(50, 80, 80, 25);
        getContentPane().add(usernameLabel);

        usernameValue = new JLabel(loggedInUser.getUsername());
        usernameValue.setBounds(140, 80, 220, 25);
        getContentPane().add(usernameValue);

        JButton editButton = new JButton("修改");
        editButton.setBounds(110, 130, 90, 30);
        getContentPane().add(editButton);

        JButton closeButton = new JButton("返回");
        closeButton.setBounds(220, 130, 90, 30);
        getContentPane().add(closeButton);

        editButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ProfileEditDialog dlg = new ProfileEditDialog(EditProfileFrame.this, loggedInUser, () -> {
                    nameValue.setText(loggedInUser.getName());
                });
                dlg.setVisible(true);
            }
        });

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                UserCenterFrame userCenterFrame = new UserCenterFrame(loggedInUser);
                userCenterFrame.setVisible(true);
            }
        });
    }
}
