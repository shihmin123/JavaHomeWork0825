package controller;

import model.User;
import service.MusicService;
import service.impl.MusicServiceImpl;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import util.YouTubeUtil;
import util.YouTubeUtil.VideoInfo;

public class AddMusicFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JTextField urlField;
    private JButton fetchButton;
    private JButton saveButton;
    private JLabel titleLabel;
    private JLabel thumbnailLabel;
    private String videoTitle = "";
    private String videoId = "";
    private int userId;
    private User loggedInUser;
    private final MusicService musicService = new MusicServiceImpl();

    public AddMusicFrame(User user) {
        this.loggedInUser = user;
        this.userId = user.getId();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("新增音樂");
        setBounds(100, 100, 551, 429);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel urlText = new JLabel("YouTube 連結：");
        urlText.setBounds(20, 20, 120, 25);
        getContentPane().add(urlText);

        urlField = new JTextField();
        urlField.setBounds(140, 20, 300, 25);
        getContentPane().add(urlField);

        fetchButton = new JButton("取得資訊");
        fetchButton.setBounds(140, 60, 100, 30);
        getContentPane().add(fetchButton);

        titleLabel = new JLabel("影片標題：");
        titleLabel.setBounds(20, 100, 459, 25);
        getContentPane().add(titleLabel);

        thumbnailLabel = new JLabel();
        thumbnailLabel.setBounds(90, 140, 320, 180);
        getContentPane().add(thumbnailLabel);

        saveButton = new JButton("儲存音樂");
        saveButton.setBounds(140, 350, 120, 30);
        getContentPane().add(saveButton);

        JButton back = new JButton("返回");
        back.setBounds(290, 350, 120, 30);
        contentPane.add(back);




        fetchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String url = urlField.getText().trim();
                String vid = YouTubeUtil.extractVideoId(url);
                if (vid == null) {
                    JOptionPane.showMessageDialog(AddMusicFrame.this, "請輸入有效的 YouTube 影片連結！");
                    return;
                }
                try {
                    VideoInfo info = YouTubeUtil.fetchVideoInfo(vid);
                    videoId = vid;
                    videoTitle = info.getTitle();
                    titleLabel.setText("影片標題：" + videoTitle);

                    Image scaledImage = info.getThumbnail().getScaledInstance(
                            thumbnailLabel.getWidth(),
                            thumbnailLabel.getHeight(),
                            Image.SCALE_SMOOTH
                    );
                    thumbnailLabel.setIcon(new ImageIcon(scaledImage));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AddMusicFrame.this, "無法取得影片資訊！");
                }
            }
        });


        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (videoTitle.isEmpty() || videoId.isEmpty()) {
                    JOptionPane.showMessageDialog(AddMusicFrame.this, "請先取得影片資訊！");
                    return;
                }
                boolean result = YouTubeUtil.saveMusic(userId, videoTitle, videoId);
                if (result) {
                    JOptionPane.showMessageDialog(AddMusicFrame.this, "儲存成功！");
                    urlField.setText("");
                    titleLabel.setText("影片標題：");
                    thumbnailLabel.setIcon(null);
                    videoTitle = "";
                    videoId = "";
                    urlField.requestFocusInWindow();
                } else {
                    JOptionPane.showMessageDialog(AddMusicFrame.this, "儲存失敗！");
                }
            }
        });


        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                UserCenterFrame userCenterFrame = new UserCenterFrame(loggedInUser);
                userCenterFrame.setVisible(true);
            }
        });
    }
}