package controller;

import model.User;
import service.UserService;
import service.impl.UserServiceImpl;
import util.AdminUsersUtil;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class AdminUsersFrame extends JFrame {
    private final User admin;
    private final UserService userService = new UserServiceImpl();

    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> userList;

    public AdminUsersFrame(User admin) {
        this.admin = admin;

        setTitle("使用者管理");
        setSize(640, 460);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        userList = new JList<>(listModel);
        JScrollPane sp = new JScrollPane(userList);
        sp.setBounds(20, 20, 460, 380);
        getContentPane().add(sp);

        JButton backBtn = new JButton("返回");
        backBtn.setBounds(500, 370, 120, 30);
        getContentPane().add(backBtn);

        backBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new AdminDashboardFrame(admin).setVisible(true);
            }
        });

        AdminUsersUtil.refreshUserList(listModel, userService);
    }

}
