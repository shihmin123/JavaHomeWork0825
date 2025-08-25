package controller;

import model.User;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminDashboardFrame extends JFrame {
    private final User admin;

    public AdminDashboardFrame(User admin) {
        this.admin = admin;
        setTitle("管理員面板");
        setSize(420, 303);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JButton usersBtn = new JButton("查看所有使用者");
        usersBtn.setBounds(120, 50, 180, 36);
        getContentPane().add(usersBtn);

        JButton musicsBtn = new JButton("查看所有音樂");
        musicsBtn.setBounds(120, 100, 180, 36);
        getContentPane().add(musicsBtn);

        JButton backBtn = new JButton("返回");
        backBtn.setBounds(120, 191, 180, 36);
        getContentPane().add(backBtn);
        
        JButton cart = new JButton("查看圖表");
        cart.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		UserMusicChartFrame userMusicChartFrame = new UserMusicChartFrame();
        		userMusicChartFrame.setVisible(true);
        	}
        });
        cart.setBounds(120, 145, 180, 36);
        getContentPane().add(cart);

        usersBtn.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                dispose();
                new AdminUsersFrame(admin).setVisible(true);
            }
        });
        musicsBtn.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                dispose();
                new AdminMusicFrame(admin).setVisible(true);
            }
        });
        backBtn.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });
    }
}