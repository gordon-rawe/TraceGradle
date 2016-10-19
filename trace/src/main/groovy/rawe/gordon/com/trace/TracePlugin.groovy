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

        /**
         * notice that extension params can be only acquired after evaluation...
         * */
        project.afterEvaluate {
            def extension = project.extensions.findByName("traceExt") as TraceExtension
            project.gradle.addListener(new TraceListener(
                    extension.eachEnabled,
                    extension.resultEnabled,
                    extension.traceInput,
                    extension.traceOutput,
                    extension.thresholdTime
            ))

            project.task("showConfiguration") << {
                println "show time for every  task: " + extension.eachEnabled.toString()
                println "show tasks over threshold: " + extension.resultEnabled.toString()
                println "threshold     time    set: " + extension.thresholdTime.toString()
            }
        }

    }
}
