package util;

import java.util.List;
import java.util.Objects;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.Music;
import service.MusicService;

public class AdminMusicUtil {
	public static List<Music> refreshMusicList(DefaultListModel<String> listModel, MusicService musicService,
			String keyword) {
		List<Music> list = (keyword == null || keyword.isEmpty()) ? musicService.findAll()
				: musicService.searchAllByKeyword(keyword);
		fillList(listModel, list);
		return list;
	}
	public static List<Music> onSearch(JFrame parent, DefaultListModel<String> listModel, MusicService musicService) {
		String keyword = JOptionPane.showInputDialog(parent, "請輸入關鍵字（標題，支援模糊搜尋）：", "搜尋", JOptionPane.QUESTION_MESSAGE);
		if (keyword == null)
			return null; 
		keyword = keyword.trim();
		List<Music> result = (keyword.isEmpty()) ? musicService.findAll() : musicService.searchAllByKeyword(keyword);
		fillList(listModel, result);
		if (result.isEmpty()) {
			JOptionPane.showMessageDialog(parent, "找不到符合的結果");
		}
		return result;
	}
	public static void fillList(DefaultListModel<String> listModel, List<Music> data) {
		listModel.clear();
		if (data == null)
			return;
		for (Music m : data) {
			listModel.addElement(Objects.toString(m.getTitle(), "(無標題)"));
		}
	}

}
