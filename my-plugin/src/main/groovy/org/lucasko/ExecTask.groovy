package org.lucasko

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction;


public class ExecTask extends DefaultTask  {

    @TaskAction
    void runCommand() {

        exec 'date'
        exec 'ping -c 5 8.8.8.8'

    }

    private void exec(def command){

        def cmds = command.split( )
        def argList = cmds.size() > 1 ? cmds[1..-1] : []
        project.exec {
            executable cmds[0]
            args argList
        }

    }

}
