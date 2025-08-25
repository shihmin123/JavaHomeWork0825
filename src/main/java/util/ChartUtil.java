// ChartUtil.java
package util;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Font;

import java.util.List;

public final class ChartUtil {
	private ChartUtil() {
	}

	public static void renderUserMusicCountLineChart(JFXPanel fxPanel, List<StatisticsUtil.UserMusicCount> data,
			String title) {
		Platform.runLater(() -> {
			CategoryAxis xAxis = new CategoryAxis();
			xAxis.setLabel("使用者");

			NumberAxis yAxis = new NumberAxis();
			yAxis.setLabel("數量");

			Font f20 = Font.font(20);
			xAxis.setTickLabelFont(f20);
			yAxis.setTickLabelFont(f20);

			xAxis.setStyle("-fx-font-size: 20px; -fx-tick-label-font-size: 20px;");
			yAxis.setStyle("-fx-font-size: 20px; -fx-tick-label-font-size: 20px;");

			int max = 0;
			if (data != null) {
				for (StatisticsUtil.UserMusicCount row : data) {
					if (row.getCount() > max)
						max = row.getCount();
				}
			}
			int upper = Math.max(5, max);
			yAxis.setAutoRanging(false);
			yAxis.setLowerBound(0);
			yAxis.setUpperBound(upper);
			yAxis.setTickUnit(1);
			yAxis.setMinorTickCount(0);
			yAxis.setForceZeroInRange(true);

			LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
			chart.setTitle(title != null ? title : "各使用者音樂數量");
			chart.setLegendVisible(false);
			chart.setCreateSymbols(true);

			XYChart.Series<String, Number> series = new XYChart.Series<>();
			if (data != null) {
				for (StatisticsUtil.UserMusicCount row : data) {
					series.getData().add(new XYChart.Data<>(row.getUserName(), row.getCount()));
				}
			}
			chart.getData().add(series);

			fxPanel.setScene(new Scene(chart));
		});
	}

	public static void renderUserMusicCountBarChart(JFXPanel fxPanel, List<StatisticsUtil.UserMusicCount> data,
			String title) {
		Platform.runLater(() -> {
			CategoryAxis xAxis = new CategoryAxis();
			xAxis.setLabel("使用者");

			NumberAxis yAxis = new NumberAxis();
			yAxis.setLabel("數量");

// 字體 20
			Font f20 = Font.font(20);
			xAxis.setTickLabelFont(f20);
			yAxis.setTickLabelFont(f20);
			xAxis.setStyle("-fx-font-size: 20px; -fx-tick-label-font-size: 20px;");
			yAxis.setStyle("-fx-font-size: 20px; -fx-tick-label-font-size: 20px;");

// Y 軸整數刻度
			int max = 0;
			if (data != null) {
				for (StatisticsUtil.UserMusicCount row : data) {
					if (row.getCount() > max)
						max = row.getCount();
				}
			}
			int upper = Math.max(5, max);
			yAxis.setAutoRanging(false);
			yAxis.setLowerBound(0);
			yAxis.setUpperBound(upper);
			yAxis.setTickUnit(1);
			yAxis.setMinorTickCount(0);
			yAxis.setForceZeroInRange(true);

			BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
			chart.setTitle(title != null ? title : "各使用者音樂數量");
			chart.setLegendVisible(false);


			chart.setCategoryGap(50); // 由原本的 10 增加到 30，類別間距大 → 單一系列時條狀更細
			chart.setBarGap(12); // 多系列時條與條的距離更大，也會看起來更細
			chart.setAnimated(false);

			XYChart.Series<String, Number> series = new XYChart.Series<>();
			if (data != null) {
				for (StatisticsUtil.UserMusicCount row : data) {
					series.getData().add(new XYChart.Data<>(row.getUserName(), row.getCount()));
				}
			}
			chart.getData().add(series);

			fxPanel.setScene(new Scene(chart));
		});
	}
}
