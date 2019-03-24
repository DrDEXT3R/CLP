package gui;

import core.Museum;
import javafx.embed.swing.SwingNode;
import javafx.scene.*;
import javafx.scene.layout.StackPane;
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
 * Class for drawing simple Gantt Chart of scheduling problem.
 *
 * @author Tomasz Strzoda
 */
public class MuseumGanttChart {

    public MuseumGanttChart(Stage stage, int[][] solution) {
        final SwingNode chartSwingNode = new SwingNode();
        chartSwingNode.setContent( new ChartPanel( generateGanttChart(solution) ) );
        stage.setScene( new Scene( new StackPane(chartSwingNode) ) );
        stage.show();
    }

    /**
     * Method for generating Gantt Chart.
     * @return chart.
     */
    private JFreeChart generateGanttChart(int[][] solution) {
        final IntervalCategoryDataset dataset = createDataset(solution);

        return ChartFactory.createGanttChart("Museum Schedule", "Exhibitions", "Time", dataset);
    }

    /**
     * Method for creating dataset.
     * @return dataset.
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
     * Method for calculating hours and minutes.
     * @return date.
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