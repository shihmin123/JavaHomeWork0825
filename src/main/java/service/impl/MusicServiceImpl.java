package service.impl;

import java.time.LocalDateTime;
import java.util.List;

import dao.MusicDao;
import dao.impl.MusicDaoImpl;
import model.Music;
import service.MusicService;

public class MusicServiceImpl   implements MusicService{
	private MusicDao musicDao = new MusicDaoImpl();
	public static void main(String[] args) {
		MusicServiceImpl service = new MusicServiceImpl();
		 Music music = new Music();
	       /* music.setUserId(6); 
	        music.setTitle("測試歌曲");
	        music.setFilePath("C:/music/test.mp3");
	        music.setYoutubeUrl("https://youtube.com/example");
	        music.setCreatedAt(LocalDateTime.now());
	        boolean addResult = service.addMusic(music);
	        System.out.println("新增音樂結果: " + addResult);*/
		 /*List<Music> userMusicList = service.findByUserId(6);
	        System.out.println("使用者ID 1的音樂清單:");
	        for (Music m : userMusicList) {
	            System.out.println(m.getId() + ": " + m.getTitle() + ", " + m.getYoutubeUrl());
	        }*/
	        /*List<Music> allMusic = service.findAll();
	        System.out.println("所有音樂清單:");
	        for (Music m : allMusic) {
	            System.out.println(m.getId() + ": " + m.getTitle() + ", 使用者ID: " + m.getUserId());
	        }*/
	        /*int deleteId = 1;  // 你要刪除的ID
	        boolean deleteResult = service.deleteById(deleteId);
	        System.out.println("刪除ID " + deleteId + " 音樂結果: " + deleteResult);*/

	}

	@Override
	public boolean addMusic(Music music) {

		return musicDao.addMusic(music);
	}

	@Override
	public List<Music> findByUserId(int userId) {

		return musicDao.findByUserId(userId);
	}

	@Override
	public List<Music> findAll() {

		return musicDao.findAll();
	}

	@Override
	public boolean deleteById(int id) {

		return musicDao.deleteById(id);
	}

	@Override
	public List<Music> searchByUserIdAndKeyword(int userId, String keyword) {

		return musicDao.searchByUserIdAndKeyword(userId, keyword);
	}

	@Override
	public List<Music> searchAllByKeyword(String keyword) {
		
		 return musicDao.searchAllByKeyword(keyword);
	}

}
