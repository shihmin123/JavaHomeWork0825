package controller;

import util.ChartUtil;
import util.StatisticsUtil;
import util.FxPlatformUtil;   
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javafx.embed.swing.JFXPanel;

public class UserMusicChartFrame extends JFrame {

    private final JFXPanel fxLinePanel = new JFXPanel();
    private final JFXPanel fxBarPanel  = new JFXPanel();

    public UserMusicChartFrame() {
    	
        super("各使用者音樂數量（折線圖 + 長條圖）");
        FxPlatformUtil.keepAlive();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 900);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        JPanel container = new JPanel(new GridLayout(2, 1, 0, 8));
        container.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel lineWrap = new JPanel(new BorderLayout());
        lineWrap.setBorder(BorderFactory.createTitledBorder("折線圖"));
        lineWrap.add(fxLinePanel, BorderLayout.CENTER);

        JPanel barWrap = new JPanel(new BorderLayout());
        barWrap.setBorder(BorderFactory.createTitledBorder("長條圖"));
        barWrap.add(fxBarPanel, BorderLayout.CENTER);

        container.add(lineWrap);
        container.add(barWrap);
        getContentPane().add(container, BorderLayout.CENTER);


        List<StatisticsUtil.UserMusicCount> data = StatisticsUtil.fetchUserMusicCounts();
        ChartUtil.renderUserMusicCountLineChart(
                fxLinePanel, data, "各使用者音樂數量（折線圖）");
        ChartUtil.renderUserMusicCountBarChart(
                fxBarPanel,  data, "各使用者音樂數量（長條圖）");
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserMusicChartFrame().setVisible(true));
    }
}
