package util;

import java.util.List;

import javax.swing.DefaultListModel;

import model.Music;
import service.MusicService;

public class MusicListUtil {
	public static List<Music> refreshMusicList(
            DefaultListModel<String> listModel,
            MusicService musicService,
            int userId,
            String keyword) {

        List<Music> musicList;
        if (keyword == null || keyword.isEmpty()) {
            musicList = musicService.findByUserId(userId);
        } else {
            musicList = musicService.searchByUserIdAndKeyword(userId, keyword);
        }

        listModel.clear();
        for (Music music : musicList) {
            listModel.addElement(music.getTitle());
        }

        return musicList; 
    }
}
