package rawe.gordon.com.trace

import org.gradle.api.Plugin
import org.gradle.api.Project
import rawe.gordon.com.trace.extensions.TraceExtension
import rawe.gordon.com.trace.listeners.TraceListener;

/**
 * Created by gordon on 16/10/18.
 */
public class TracePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.extensions.create("traceExt", TraceExtension)
        project.afterEvaluate {
            def extension = project.extensions.findByName("traceExt") as TraceExtension
            println "" + extension.eachEnabled + extension.resultEnabled + extension.thresholdTime
            project.gradle.addListener(new TraceListener(
                    extension.eachEnabled,
                    extension.resultEnabled,
                    extension.thresholdTime
            ))
            project.task("showConfiguration") << {
                println extension.eachEnabled + extension.resultEnabled + extension.thresholdTime
            }
        }

    }
}
