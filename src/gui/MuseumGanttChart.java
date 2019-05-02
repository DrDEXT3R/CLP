package gui;

import core.Museum;
import javafx.embed.swing.SwingNode;

import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.jfree.chart.*;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;

import java.util.Calendar;
import java.util.Date;

/**
 * The class for drawing simple Gantt Chart of scheduling problem.
 *
 * @author Tomasz Strzoda
 */
public class MuseumGanttChart {

    /**
     * The class constructor which is responsible for the implementation of the entire chart.
     * @param stage     stage on which to display the chart.
     * @param solution  solution of the CLP problem.
     */
    public MuseumGanttChart(Stage stage, int[][] solution) {
        final SwingNode chartSwingNode = new SwingNode();
        chartSwingNode.setContent( new ChartPanel( generateGanttChart(solution) ) );
        stage.setScene( new Scene( new StackPane(chartSwingNode) ) );

        stage.setWidth(600);
        stage.setHeight(400);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primaryScreenBounds.getWidth() - 600) / 2);
        stage.setY((primaryScreenBounds.getHeight() - 400) / 2);
        stage.show();
    }

    /**
     * The method for generating Gantt Chart.
     * @param solution  solution of the CLP problem.
     * @return          chart.
     */
    private JFreeChart generateGanttChart(int[][] solution) {
        final IntervalCategoryDataset dataset = createDataset(solution);

        return ChartFactory.createGanttChart("Museum Schedule", "Exhibitions", "Time", dataset);
    }

    /**
     * The method for creating dataset.
     * @param sol   solution of the CLP problem.
     * @return      dataset.
     */
    public static IntervalCategoryDataset createDataset(int[][] sol) {
        MainWindow mainWindow = new MainWindow();
        Museum activeModule = (Museum) mainWindow.getModule(1);

        int[][] dur = activeModule.getDurations();
        String[] exhNames = activeModule.getExhibitionsNames();

        Date[][] dateStart = new Date[dur.length][dur[0].length];
        Date[][] dateEnd = new Date[dur.length][dur[0].length];

        for (int i = 0; i < dur.length; i++)
            for (int j = 0; j < dur[0].length; j++) {
                dateStart[i][j] = date(sol[j][i]);
                dateEnd[i][j] = date(sol[j][i] + dur[j][i]);
            }

        TaskSeries[] series = new TaskSeries[exhNames.length];

        for (int i = 0; i < exhNames.length; i++)
            series[i] = new TaskSeries(activeModule.getSolutionAsArray()[i][0]);

        for (int i = 0; i < exhNames.length; i++)
            for (int j = 0; j < series.length; j++)
                series[j].add(new Task(exhNames[i], new SimpleTimePeriod(dateStart[i][j], dateEnd[i][j])));

        final TaskSeriesCollection collection = new TaskSeriesCollection();

        for (TaskSeries serie : series)
            collection.add(serie);

        return collection;
    }

    /**
     * The method for calculating hours and minutes.
     * @param min   number of minutes.
     * @return      date.
     */
    private static Date date(final int min) {
        final int day = 20, month = 3, year = 2019;
        final int hour = min%60, minute = min/60;
        final Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, minute, hour);
        final Date result = calendar.getTime();
        return result;
    }

}
