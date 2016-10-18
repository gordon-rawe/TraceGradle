package rawe.gordon.com.trace.listeners

import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskState
import org.gradle.util.Clock


/**
 * Created by gordon on 16/10/18.
 */
public class TraceListener implements TaskExecutionListener, BuildListener {
    private Clock clock
    private times = []
    private boolean eachEnabled
    private boolean resultEnabled
    private long thresholdTime

    public TraceListener(boolean eachEnabled, boolean resultEnabled, long thresholdTime) {
        this.eachEnabled = eachEnabled
        this.resultEnabled = resultEnabled
        this.thresholdTime = thresholdTime
    }


    @Override
    void buildStarted(Gradle gradle) {

    }

    @Override
    void settingsEvaluated(Settings settings) {

    }

    @Override
    void projectsLoaded(Gradle gradle) {

    }

    @Override
    void projectsEvaluated(Gradle gradle) {

    }

    @Override
    void buildFinished(BuildResult result) {

        println "Task spend time:"
        if (!resultEnabled) {
            println "details are ignored..."
            return
        }
        for (time in times) {
            if (time[0] >= thresholdTime) {
                printf "%7sms  %s\n", time
            }
        }
    }

    @Override
    void beforeExecute(Task task) {
        clock = new Clock();
    }

    @Override
    void afterExecute(Task task, TaskState state) {
        def ms = clock.timeInMs
        times.add([ms, task.path])
        if (eachEnabled) task.project.logger.warn "${task.path} spend ${ms}ms"
    }
}
