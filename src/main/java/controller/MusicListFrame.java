package controller;

import model.Music;
import model.User;
import service.MusicService;
import service.impl.MusicServiceImpl;
import util.AdminMusicUtil;
import util.MusicListUtil;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MusicListFrame extends JFrame {

	private final User loggedInUser;
	private final MusicService musicService = new MusicServiceImpl();
	private DefaultListModel<String> listModel;
	private JList<String> musicJList;
	private List<Music> currentMusicList;

	public MusicListFrame(User user) {
		this.loggedInUser = user;

		setTitle("我的音樂清單");
		setSize(600, 450);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());

		JLabel title = new JLabel("使用者 " + loggedInUser.getName() + " 的音樂清單", JLabel.CENTER);
		title.setFont(new Font("微软雅黑", Font.BOLD, 16));
		getContentPane().add(title, BorderLayout.NORTH);

		listModel = new DefaultListModel<>();
		musicJList = new JList<>(listModel);
		currentMusicList = MusicListUtil.refreshMusicList(listModel, musicService, loggedInUser.getId(), null);

		JScrollPane scrollPane = new JScrollPane(musicJList);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

		JButton addButton = new JButton("新增");
		JButton searchButton = new JButton("查詢");
		JButton deleteButton = new JButton("刪除");
		buttonPanel.add(addButton);
		buttonPanel.add(searchButton);
		JButton playButton = new JButton("播放");
		playButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedIndex = musicJList.getSelectedIndex();
				if (selectedIndex == -1) {
					JOptionPane.showMessageDialog(MusicListFrame.this, "請先選擇要播放的音樂！");
					return;
				}

				Music selectedMusic = currentMusicList.get(selectedIndex);
				String url = selectedMusic.getYoutubeUrl();
				if (url == null || url.trim().isEmpty()) {
					JOptionPane.showMessageDialog(MusicListFrame.this, "這首歌沒有有效的 YouTube 連結！");
					return;
				}

				YoutubePreviewFrame player = new YoutubePreviewFrame(url);
				player.setVisible(true);
			}
		});

		JButton resetButton = new JButton("重整");
		resetButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				currentMusicList = MusicListUtil.refreshMusicList(listModel, musicService, loggedInUser.getId(), null);
				musicJList.clearSelection();
			}
		});
		buttonPanel.add(resetButton);

		buttonPanel.add(playButton);
		buttonPanel.add(deleteButton);

		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		JButton backButton = new JButton("返回");
		buttonPanel.add(backButton);

		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				UserCenterFrame userCenterFrame = new UserCenterFrame(loggedInUser);
				userCenterFrame.setVisible(true);
			}
		});

		addButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				AddMusicFrame addMusicFrame = new AddMusicFrame(loggedInUser);
				addMusicFrame.setVisible(true);
			}
		});

		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String keyword = JOptionPane.showInputDialog(MusicListFrame.this, "請輸入歌名或歌手關鍵字進行搜尋：");
				if (keyword != null) {
					currentMusicList = MusicListUtil.refreshMusicList(listModel, musicService, loggedInUser.getId(),
							keyword.trim());
				}
			}
		});

		deleteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedIndex = musicJList.getSelectedIndex();
				if (selectedIndex == -1) {
					JOptionPane.showMessageDialog(MusicListFrame.this, "請先選擇要刪除的音樂！");
					return;
				}

				Music selectedMusic = currentMusicList.get(selectedIndex);
				int confirm = JOptionPane.showConfirmDialog(MusicListFrame.this,
						"確定要刪除「" + selectedMusic.getTitle() + "」嗎？", "刪除確認", JOptionPane.YES_NO_OPTION);

				if (confirm == JOptionPane.YES_OPTION) {
					boolean deleted = musicService.deleteById(selectedMusic.getId());
					if (deleted) {
						JOptionPane.showMessageDialog(MusicListFrame.this, "刪除成功！");
						currentMusicList = MusicListUtil.refreshMusicList(listModel, musicService, loggedInUser.getId(),
								null);
					} else {
						JOptionPane.showMessageDialog(MusicListFrame.this, "刪除失敗！");
					}
				}
			}
		});

		setVisible(true);
	}

}
