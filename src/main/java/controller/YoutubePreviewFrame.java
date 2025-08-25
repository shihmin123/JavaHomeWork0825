package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.Beans;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.web.WebEngine;
import javafx.concurrent.Worker;

import org.json.JSONObject;

import util.YouTubeUtil;

public class YoutubePreviewFrame extends JFrame {

    private final JLabel titleLabel = new JLabel("", SwingConstants.CENTER);
    private final JPanel center = new JPanel(new BorderLayout());
    private JComponent videoComponent;
    private JFXPanel fxPanel;
    private WebEngine webEngine;

    private String lastVideoId;
    private boolean retriedWithNoCookie = false;

    public YoutubePreviewFrame() {
        super("YouTube 影片");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        center.setBorder(BorderFactory.createEmptyBorder(8, 8, 0, 8));
        center.setPreferredSize(new Dimension(854, 480));

        if (Beans.isDesignTime()) {
            JLabel placeholder = new JLabel("（設計模式）此處為影片區", SwingConstants.CENTER);
            placeholder.setOpaque(true);
            placeholder.setBackground(new Color(245, 245, 245));
            placeholder.setBorder(BorderFactory.createDashedBorder(Color.GRAY));
            videoComponent = placeholder;
        } else {
            fxPanel = new JFXPanel();
            videoComponent = fxPanel;
        }
        center.add(videoComponent, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.WHITE);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.PLAIN, 16f));
        bottom.add(titleLabel, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        if (!Beans.isDesignTime()) {
            Platform.setImplicitExit(false);
            Platform.runLater(() -> {
                webEngine = YouTubeUtil.initWebView(
                    fxPanel,
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36",
                    (obs, old, st) -> {
                        if (st == Worker.State.SUCCEEDED) {
                            boolean blocked = YouTubeUtil.isEmbedBlocked(webEngine);
                            if (blocked) {
                                if (!retriedWithNoCookie && lastVideoId != null) {
                                    retriedWithNoCookie = true;
                                    YouTubeUtil.loadHtml(webEngine, YouTubeUtil.buildEmbedHtml(lastVideoId, true));
                                } else if (lastVideoId != null) {
                                    SwingUtilities.invokeLater(() ->
                                        YouTubeUtil.askOpenInBrowser(YoutubePreviewFrame.this, lastVideoId));
                                }
                            }
                        } else if (st == Worker.State.FAILED) {
                            if (!retriedWithNoCookie && lastVideoId != null) {
                                retriedWithNoCookie = true;
                                YouTubeUtil.loadHtml(webEngine, YouTubeUtil.buildEmbedHtml(lastVideoId, true));
                            } else if (lastVideoId != null) {
                                SwingUtilities.invokeLater(() ->
                                    YouTubeUtil.askOpenInBrowser(YoutubePreviewFrame.this, lastVideoId));
                            }
                        }
                    }
                );
            });
        }

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                Platform.runLater(() -> YouTubeUtil.clearEngine(webEngine));
            }
        });
    }

    public YoutubePreviewFrame(String initialUrl) {
        this();
        if (initialUrl == null || initialUrl.trim().isEmpty() || Beans.isDesignTime()) return;

        titleLabel.setText("載入中…");
        SwingWorker<String[], Void> worker = new SwingWorker<>() {
            @Override
            protected String[] doInBackground() throws Exception {

                String title = "(無標題)";
                try {
                    JSONObject obj = YouTubeUtil.fetchOEmbed(initialUrl.trim());
                    title = obj.optString("title", "(無標題)");
                } catch (Exception ignore) {}

                String videoId = YouTubeUtil.extractVideoId(initialUrl.trim());
                if (videoId == null || videoId.isEmpty()) {
                    throw new RuntimeException("無法解析影片 ID，請確認 URL。");
                }
                String html = YouTubeUtil.buildEmbedHtml(videoId, false);
                return new String[]{title, html, videoId};
            }

            @Override
            protected void done() {
                try {
                    String[] res = get();
                    final String title = res[0];
                    final String html = res[1];
                    lastVideoId = res[2];
                    retriedWithNoCookie = false;

                    titleLabel.setText(title);
                    Platform.runLater(() -> YouTubeUtil.loadHtml(webEngine, html));
                } catch (ExecutionException ex) {
                    titleLabel.setText("讀取失敗：" + (ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage()));
                } catch (Exception ex) {
                    titleLabel.setText("讀取失敗：" + ex.getMessage());
                }
            }
        };
        worker.execute();
    }
}
