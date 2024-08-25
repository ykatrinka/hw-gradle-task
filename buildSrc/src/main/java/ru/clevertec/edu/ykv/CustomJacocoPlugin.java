package ru.clevertec.edu.ykv;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.testing.jacoco.plugins.JacocoPlugin;
import org.gradle.testing.jacoco.tasks.JacocoReport;

public class CustomJacocoPlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        target.getPlugins().apply(JavaPlugin.class);
        target.getPlugins().apply(JacocoPlugin.class);

        target.getTasks().register("generateCustomJacocoReport", JacocoReport.class, task -> {
            task.setDescription("Generates custom JaCoCo coverage report for all subprojects");
            task.setGroup("reporting");

            task.getReports().getXml().getRequired().set(true);
            task.getReports().getHtml().getRequired().set(true);

            // Configure subprojects
            target.getSubprojects().forEach(subproject -> {
                subproject.getPlugins().apply(JacocoPlugin.class);
                task.dependsOn(subproject.getPath() + ":test");
                task.getExecutionData().from(
                        subproject.fileTree(
                                subproject.getProjectDir(), conf -> {
                                    conf.include("**/build/jacoco/test.exec");
                                }
                        )
                );

                task.sourceSets(
                        subproject.getExtensions()
                                .getByType(SourceSetContainer.class)
                                .getByName("main")
                );

            });
        });
    }
}
