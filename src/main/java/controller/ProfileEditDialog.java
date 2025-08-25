package controller;

import model.User;
import service.UserService;
import service.impl.UserServiceImpl;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ProfileEditDialog extends JDialog {

    private final UserService userService = new UserServiceImpl();
    private final User user;
    private final Runnable onUpdated;

    public ProfileEditDialog(JFrame owner, User user, Runnable onUpdated) {
        super(owner, "修改個人資料", true);
        this.user = user;
        this.onUpdated = onUpdated;

        setSize(460, 320);
        setLocationRelativeTo(owner);
        getContentPane().setLayout(null);

        JLabel nameLabel = new JLabel("新姓名：");
        nameLabel.setBounds(60, 40, 100, 25);
        getContentPane().add(nameLabel);

        JTextField nameField = new JTextField(user.getName());
        nameField.setBounds(180, 40, 200, 25);
        getContentPane().add(nameField);

        JLabel pass1Label = new JLabel("新密碼：");
        pass1Label.setBounds(60, 90, 100, 25);
        getContentPane().add(pass1Label);

        JPasswordField pass1Field = new JPasswordField();
        pass1Field.setBounds(180, 90, 200, 25);
        getContentPane().add(pass1Field);

        JLabel pass2Label = new JLabel("確認新密碼：");
        pass2Label.setBounds(60, 130, 100, 25);
        getContentPane().add(pass2Label);

        JPasswordField pass2Field = new JPasswordField();
        pass2Field.setBounds(180, 130, 200, 25);
        getContentPane().add(pass2Field);

        JButton saveBtn = new JButton("儲存");
        saveBtn.setBounds(140, 200, 80, 32);
        getContentPane().add(saveBtn);

        JButton cancelBtn = new JButton("取消");
        cancelBtn.setBounds(240, 200, 80, 32);
        getContentPane().add(cancelBtn);

        saveBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String newName = nameField.getText().trim();
                String p1 = new String(pass1Field.getPassword());
                String p2 = new String(pass2Field.getPassword());

                if (newName.isEmpty()) {
                    JOptionPane.showMessageDialog(ProfileEditDialog.this, "姓名不可為空白");
                    return;
                }
                if (p1.isEmpty() || p2.isEmpty()) {
                    JOptionPane.showMessageDialog(ProfileEditDialog.this, "請輸入新密碼並再次確認");
                    return;
                }
                if (!p1.equals(p2)) {
                    JOptionPane.showMessageDialog(ProfileEditDialog.this, "兩次輸入的新密碼不一致");
                    return;
                }
                if (p1.length() < 4) {
                    JOptionPane.showMessageDialog(ProfileEditDialog.this, "新密碼至少需 4 碼");
                    return;
                }

                user.setName(newName);
                user.setPassword(p1);

                boolean ok = userService.update(user);
                if (ok) {
                    JOptionPane.showMessageDialog(ProfileEditDialog.this, "修改成功");
                    if (onUpdated != null) onUpdated.run();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(ProfileEditDialog.this, "修改失敗，請稍後再試");
                }
            }
        });

        cancelBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	dispose();
                owner.setVisible(true);

            }
        });
    }
}
