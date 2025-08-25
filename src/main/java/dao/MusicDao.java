package dao;

import java.util.List;

import model.Music;

public interface MusicDao {
	boolean addMusic(Music music);
    List<Music> findByUserId(int userId);
    List<Music> searchByUserIdAndKeyword(int userId, String keyword);
    List<Music> findAll();
    List<Music> searchAllByKeyword(String keyword);
    boolean deleteById(int id);
}
