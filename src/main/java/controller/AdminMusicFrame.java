package controller;

import model.Music;
import model.User;
import service.MusicService;
import service.impl.MusicServiceImpl;
import util.AdminMusicUtil;
import util.MusicListUtil;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class AdminMusicFrame extends JFrame {
    private final User admin;
    private final MusicService musicService = new MusicServiceImpl();

    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> musicJList;
    private List<Music> currentMusicList;

    public AdminMusicFrame(User admin) {
        this.admin = admin;

        setTitle("音樂管理");
        setSize(640, 460);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        musicJList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(musicJList);
        scrollPane.setBounds(20, 20, 460, 380);
        getContentPane().add(scrollPane);

        JButton searchBtn = new JButton("搜尋");
        searchBtn.setBounds(500, 20, 120, 30);
        getContentPane().add(searchBtn);

        JButton refreshBtn = new JButton("重新整理");
        refreshBtn.setBounds(500, 70, 120, 30);
        getContentPane().add(refreshBtn);

        JButton deleteBtn = new JButton("刪除");
        deleteBtn.setBounds(500, 120, 120, 30);
        getContentPane().add(deleteBtn);

        JButton backBtn = new JButton("返回");
        backBtn.setBounds(500, 370, 120, 30);
        getContentPane().add(backBtn);

        searchBtn.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {

                List<Music> result = AdminMusicUtil.onSearch(AdminMusicFrame.this, listModel, musicService);
                if (result != null) {
                    currentMusicList = result;

                    AdminMusicUtil.fillList(listModel, currentMusicList);
                }
            }
        });

        refreshBtn.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {

                currentMusicList = AdminMusicUtil.refreshMusicList(listModel, musicService, null);
            }
        });

        deleteBtn.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                int idx = musicJList.getSelectedIndex();
                if (idx < 0) {
                    JOptionPane.showMessageDialog(AdminMusicFrame.this, "請先選擇音樂");
                    return;
                }
                Music selected = currentMusicList.get(idx);
                int opt = JOptionPane.showConfirmDialog(
                        AdminMusicFrame.this,
                        "確定刪除「" + selected.getTitle() + "」？",
                        "確認刪除",
                        JOptionPane.YES_NO_OPTION
                );
                if (opt == JOptionPane.YES_OPTION) {
                    boolean ok = musicService.deleteById(selected.getId());
                    JOptionPane.showMessageDialog(AdminMusicFrame.this, ok ? "已刪除" : "刪除失敗");
                    currentMusicList = AdminMusicUtil.refreshMusicList(listModel, musicService, null);
                    
                }
            }
        });

        backBtn.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                dispose();
                new AdminDashboardFrame(admin).setVisible(true);
            }
        });


        currentMusicList = AdminMusicUtil.refreshMusicList(listModel, musicService, null);
        AdminMusicUtil.fillList(listModel, currentMusicList);

        setVisible(true);
    }
}
