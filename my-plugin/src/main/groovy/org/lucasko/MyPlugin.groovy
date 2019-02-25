package org.lucasko

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.task("hello") {
            println("Hello World from MyPlugin")
        }

        project.getTasks().create("greeting", Greeting.class)
                {
                    message = "Hi"
                    recipient = "lucas";
                };



        project.getTasks().create("runCommand", ExecTask.class)
                {
                    description= "Run Commands"
                    group= "Lucas"
                };

    }
}