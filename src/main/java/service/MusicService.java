package service;

import java.util.List;

import model.Music;

public interface MusicService {
	boolean addMusic(Music music);
    List<Music> findByUserId(int userId);
    List<Music> findAll();
    List<Music> searchByUserIdAndKeyword(int userId, String keyword);
    List<Music> searchAllByKeyword(String keyword);
    boolean deleteById(int id);
}
