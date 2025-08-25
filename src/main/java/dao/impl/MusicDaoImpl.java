package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dao.MusicDao;
import model.Music;
import util.DbConnection;

public class MusicDaoImpl implements MusicDao {
	private static Connection conn = DbConnection.getDb();

	@Override
	public boolean addMusic(Music music) {
		String sql = "INSERT INTO music (user_id, title,youtube_url, created_at) VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, music.getUserId());
			ps.setString(2, music.getTitle());
			ps.setString(3, music.getYoutubeUrl());
			ps.setTimestamp(4, Timestamp.valueOf(music.getCreatedAt()));
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Music> findByUserId(int userId) {
	    List<Music> list = new ArrayList<>();
	    String sql = "SELECT * FROM music WHERE user_id = ?";

	    try (
	    	PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, userId);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Music music = new Music();
	                music.setId(rs.getInt("id"));
	                music.setUserId(rs.getInt("user_id"));
	                music.setTitle(rs.getString("title"));
	                music.setYoutubeUrl(rs.getString("youtube_url"));

	                Timestamp ts = rs.getTimestamp("created_at");
	                if (ts != null) {
	                    music.setCreatedAt(ts.toLocalDateTime());
	                } else {
	                    music.setCreatedAt(null);
	                }

	                list.add(music);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return list;
	}

	@Override
	public List<Music> findAll() {
		List<Music> list = new ArrayList<>();
		String sql = "SELECT * FROM music";

		try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Music music = new Music();
				music.setId(rs.getInt("id"));
				music.setUserId(rs.getInt("user_id"));
				music.setTitle(rs.getString("title"));
				music.setYoutubeUrl(rs.getString("youtube_url"));

				Timestamp ts = rs.getTimestamp("created_at");
				music.setCreatedAt(ts != null ? ts.toLocalDateTime() : null);

				list.add(music);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public boolean deleteById(int id) {
		String sql = "DELETE FROM music WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    
	}

	@Override
	public List<Music> searchByUserIdAndKeyword(int userId, String keyword) {
		List<Music> musicList = new ArrayList<>();
	    String sql = "SELECT * FROM music WHERE user_id = ? AND title LIKE ? ORDER BY created_at DESC";

	    try (
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, userId);
	        ps.setString(2, "%" + keyword + "%");

	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            Music music = new Music();
	            music.setId(rs.getInt("id"));
	            music.setUserId(rs.getInt("user_id"));
	            music.setTitle(rs.getString("title"));
	            music.setYoutubeUrl(rs.getString("youtube_url"));
	            music.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

	            musicList.add(music);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return musicList;

	}

	@Override
	public List<Music> searchAllByKeyword(String keyword) {
		 String sql = "SELECT * FROM music WHERE title LIKE ?";
		    try (
		    		
		        PreparedStatement ps = conn.prepareStatement(sql)) {
		        ps.setString(1, "%" + keyword + "%");
		        ResultSet rs = ps.executeQuery();
		        List<Music> list = new ArrayList<>();
		        while (rs.next()) {
		            Music music = new Music();
		            music.setId(rs.getInt("id"));
		            music.setTitle(rs.getString("title"));
		            music.setYoutubeUrl(rs.getString("youtube_url"));
		            music.setUserId(rs.getInt("user_id"));
		            music.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
		            list.add(music);
		        }
		        return list;
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return new ArrayList<>();
		}
	

}
