package util;

import model.Music;
import service.MusicService;
import service.impl.MusicServiceImpl;

import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Image;
import java.awt.Desktop;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.concurrent.Worker;
import javafx.beans.value.ChangeListener;

public class YouTubeUtil {

    private static final MusicService musicService = new MusicServiceImpl();


    public static class VideoInfo {
        private final String title;
        private final Image thumbnail;

        public VideoInfo(String title, Image thumbnail) {
            this.title = title;
            this.thumbnail = thumbnail;
        }
        public String getTitle() { return title; }
        public Image getThumbnail() { return thumbnail; }
    }


    public static VideoInfo fetchVideoInfo(String videoId) throws Exception {
        if (videoId == null || videoId.isEmpty()) {
            throw new IllegalArgumentException("videoId 不可為空");
        }

        String title = "(無標題)";
        Image thumb = null;


        try {
            String oembedUrl = "https://www.youtube.com/oembed?url=https://www.youtube.com/watch?v="
                    + videoId + "&format=json";
            HttpURLConnection conn = (HttpURLConnection) new URL(oembedUrl).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(8000);
            conn.setReadTimeout(8000);
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
                            + "(KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36");
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Accept-Language", "zh-TW,zh;q=0.9,en;q=0.8");

            int code = conn.getResponseCode();
            if (code == 200) {
                try (InputStream is = conn.getInputStream();
                     BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                    String json = br.lines().reduce("", (a, b) -> a + b);
                    JSONObject obj = new JSONObject(json);
                    title = obj.optString("title", title);
                }
            } else {

            }
        } catch (Exception ignore) {

        }


        try {
            String thumbnailUrl = "https://img.youtube.com/vi/" + videoId + "/0.jpg";
            thumb = ImageIO.read(new URL(thumbnailUrl));
        } catch (Exception ignore) {

            try {
                String thumbnailUrl = "https://img.youtube.com/vi/" + videoId + "/mqdefault.jpg";
                thumb = ImageIO.read(new URL(thumbnailUrl));
            } catch (Exception ignore2) {}
        }


        if (thumb == null) {
            throw new RuntimeException("無法取得影片資訊（oEmbed 與縮圖皆失敗）");
        }

        return new VideoInfo(title, thumb);
    }

    public static boolean saveMusic(int userId, String videoTitle, String videoId) {
        if (videoTitle == null || videoTitle.isEmpty() || videoId == null || videoId.isEmpty()) {
            return false;
        }
        Music music = new Music();
        music.setUserId(userId);
        music.setTitle(videoTitle);
        music.setYoutubeUrl("https://www.youtube.com/watch?v=" + videoId);
        music.setCreatedAt(LocalDateTime.now());
        return musicService.addMusic(music);
    }

    public static String extractVideoId(String url) {
        if (url == null) return null;
        String s = url.trim();

        try {

            int idx = s.indexOf("/embed/");
            if (idx != -1) {
                String id = s.substring(idx + 7);
                int q = id.indexOf('?');
                return q == -1 ? id : id.substring(0, q);
            }


            int shortIdx = s.indexOf("youtu.be/");
            if (shortIdx != -1) {
                String id = s.substring(shortIdx + 9);
                int q = id.indexOf('?');
                return q == -1 ? id : id.substring(0, q);
            }


            int shortsIdx = s.indexOf("/shorts/");
            if (shortsIdx != -1) {
                String id = s.substring(shortsIdx + 8);
                int q = id.indexOf('?');
                return q == -1 ? id : id.substring(0, q);
            }


            int vIdx = s.indexOf("watch?v=");
            if (vIdx != -1) {
                String id = s.substring(vIdx + 8);
                int amp = id.indexOf('&');
                return amp == -1 ? id : id.substring(0, amp);
            }


            if (s.matches("^[A-Za-z0-9_-]{8,}$")) return s;
        } catch (Exception ignore) {}

        return null;
    }

    public static JSONObject fetchOembedByVideoUrl(String youtubeUrl) throws Exception {
        String api = "https://www.youtube.com/oembed?url=" + urlEncode(youtubeUrl) + "&format=json";
        HttpURLConnection conn = (HttpURLConnection) new URL(api).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        int code = conn.getResponseCode();
        if (code != 200) {
            InputStream es = conn.getErrorStream();
            String err = es != null ? streamToString(es) : ("HTTP " + code);
            throw new RuntimeException("oEmbed 失敗：" + err);
        }
        try (InputStream is = conn.getInputStream()) {
            String json = streamToString(is);
            return new JSONObject(json);
        }
    }


    public static JSONObject fetchOEmbed(String youtubeUrl) throws Exception {
        return fetchOembedByVideoUrl(youtubeUrl);
    }

    public static String buildEmbedHtml(String videoId, boolean useNoCookie) {
        String host = useNoCookie ? "https://www.youtube-nocookie.com" : "https://www.youtube.com";
        String embedUrl = host + "/embed/" + videoId + "?autoplay=1&controls=1&rel=0&modestbranding=1&playsinline=1";
        return "<!DOCTYPE html>\n" +
               "<html>\n" +
               "<head>\n" +
               "  <meta charset=\"UTF-8\">\n" +
               "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
               "  <style>\n" +
               "    html, body { margin:0; padding:0; background:#000; height:100%; }\n" +
               "    .wrap { position:fixed; top:0; right:0; bottom:0; left:0; display:flex; align-items:center; justify-content:center; }\n" +
               "    .wrap iframe { width:100%; height:100%; border:0; }\n" +
               "  </style>\n" +
               "</head>\n" +
               "<body>\n" +
               "  <div class=\"wrap\">\n" +
               "    <iframe src=\"" + embedUrl + "\" title=\"YouTube video player\" frameborder=\"0\"\n" +
               "      allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\"\n" +
               "      allowfullscreen></iframe>\n" +
               "  </div>\n" +
               "</body>\n" +
               "</html>";
    }


    public static WebEngine initWebView(JFXPanel fxPanel,
                                        String userAgent,
                                        ChangeListener<Worker.State> stateListener) {
        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();
        if (userAgent != null && !userAgent.isEmpty()) {
            engine.setUserAgent(userAgent);
        }
        if (stateListener != null) {
            engine.getLoadWorker().stateProperty().addListener(stateListener);
        }
        fxPanel.setScene(new Scene(webView));
        return engine;
    }


    public static void loadHtml(WebEngine engine, String html) {
        if (engine != null && html != null) {
            engine.loadContent(html, "text/html");
        }
    }


    public static void clearEngine(WebEngine engine) {
        if (engine != null) {
            try { engine.load(null); } catch (Exception ignore) {}
        }
    }


    public static boolean isEmbedBlocked(WebEngine engine) {
        try {
            Object o = engine.executeScript(
                "(function(){var t=document.body?document.body.innerText:'';" +
                "if(t.indexOf('無法播放影片')>-1||t.indexOf('在 YouTube 上觀看')>-1||" +
                "t.indexOf('Watch on YouTube')>-1||t.indexOf('Video unavailable')>-1) return 'ERR';" +
                "if(document.querySelector('.ytp-error')||" +
                "document.querySelector('#player-unavailable')||" +
                "document.querySelector('.ytp-error-content-wrap-reason')) return 'ERR';" +
                "return 'OK';})()"
            );
            return !"OK".equals(String.valueOf(o));
        } catch (Exception ignore) {
            return false;
        }
    }


    public static void askOpenInBrowser(JFrame parent, String videoId) {
        int opt = JOptionPane.showConfirmDialog(
                parent,
                "此影片禁止在外部嵌入播放，是否改在瀏覽器開啟？",
                "無法播放",
                JOptionPane.YES_NO_OPTION
        );
        if (opt == JOptionPane.YES_OPTION) {
            openInBrowser("https://www.youtube.com/watch?v=" + videoId);
        }
    }

    public static void openInBrowser(String url) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new java.net.URI(url));
            }
        } catch (Exception ignore) {}
    }



    private static String urlEncode(String s) {
        try { return URLEncoder.encode(s, "UTF-8"); }
        catch (Exception e) { return s; }
    }

    private static String streamToString(InputStream is) {
        try (Scanner sc = new Scanner(is, "UTF-8").useDelimiter("\\A")) {
            return sc.hasNext() ? sc.next() : "";
        }
    }
}