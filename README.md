### Building Plugin

[Document](https://guides.gradle.org/writing-gradle-plugins/)


src/main/groovy/org/lucasko/ExecTask.groovy


```groovy
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
```



In MyPlugin, I have two tasks, 'hello' and 'greeting'

my-plugin/src/main/groovy/org/lucasko/MyPlugin.groovy

```groovy
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

    }
```




Building jar file.

```sh
cd gradle-standalone-plugin/my-plugin

./gradlew clean build publishToMavenLocal

# It should create my-plugin-1.3.jar file.
ls -l  ~/.m2/repository/org/lucasko/my-plugin/1.3/my-plugin-1.3.jar 
```



### Using plugin in My Another Gradle Project

my-project/build.gradle 

```
buildscript {
    repositories {
        mavenLocal()
    }

    dependencies {
        classpath 'org.lucasko:my-plugin:1.3'
    }
}

plugins {
    id 'groovy'
    id 'java'
    id 'application'
}

repositories {
    jcenter()
    mavenLocal()
}

dependencies {
    compile 'org.codehaus.groovy:groovy-all:2.4.15'
    testCompile 'org.spockframework:spock-core:1.1-groovy-2.4'
}


apply plugin: org.lucasko.MyPlugin

```


Build & Publish

```sh
cd gradle-standalone-plugin/my-plugin

./gradlew build publishToMavenLocal
```


Run Tasks

```sh

cd gradle-standalone-plugin/my-project

$ ./gradlew hello
Starting a Gradle Daemon, 1 stopped Daemon could not be reused, use --status for details

> Configure project :
Hello World from MyPlugin

BUILD SUCCESSFUL in 4s
$ ./gradlew greeting

> Configure project :
Hello World from MyPlugin

> Task :greeting
Hi, lucas!

BUILD SUCCESSFUL in 0s
1 actionable task: 1 executed






$ ./gradlew runCommand

> Configure project :
Hello World from MyPlugin

> Task :runCommand
Mon 25 Feb 2019 13:36:55 GMT
PING 8.8.8.8 (8.8.8.8): 56 data bytes
64 bytes from 8.8.8.8: icmp_seq=0 ttl=119 time=6.848 ms
64 bytes from 8.8.8.8: icmp_seq=1 ttl=119 time=7.017 ms
64 bytes from 8.8.8.8: icmp_seq=2 ttl=119 time=6.244 ms
64 bytes from 8.8.8.8: icmp_seq=3 ttl=119 time=7.092 ms
64 bytes from 8.8.8.8: icmp_seq=4 ttl=119 time=7.275 ms

--- 8.8.8.8 ping statistics ---
5 packets transmitted, 5 packets received, 0.0% packet loss
round-trip min/avg/max/stddev = 6.244/6.895/7.275/0.353 ms

```
