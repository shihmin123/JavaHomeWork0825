package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import model.Music;
import model.User;
import service.MusicService;
import service.UserService;
import service.impl.MusicServiceImpl;
import service.impl.UserServiceImpl;

public class StatisticsUtil {
	  public static class UserMusicCount {
	        private final String userName;
	        private final int count;

	        public UserMusicCount(String userName, int count) {
	            this.userName = userName;
	            this.count = count;
	        }
	        public String getUserName() { return userName; }
	        public int getCount() { return count; }
	    }

	  public static List<UserMusicCount> fetchUserMusicCounts() {
		    UserService userService = new UserServiceImpl();
		    MusicService musicService = new MusicServiceImpl();

		    List<User> users = userService.findAll();
		    if (users == null || users.isEmpty()) {
		        return Collections.emptyList();
		    }

		    List<UserMusicCount> out = new ArrayList<>();
		    for (User u : users) {
		        String name = u.getName();
		        if (name == null) continue;
		        name = name.trim();

		        if ("管理員".equals(name)) continue;

		        List<Music> musics = musicService.findByUserId(u.getId());
		        int cnt = (musics == null) ? 0 : musics.size();
		        out.add(new UserMusicCount(name, cnt));
		    }

		    out.sort(Comparator.comparing(UserMusicCount::getUserName, String.CASE_INSENSITIVE_ORDER));
		    return out;
		}
	
}
