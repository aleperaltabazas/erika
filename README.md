# Erika

Erika is a small tool that generates diagrams from your source code, without needing to do any manual input or modification. This tool works with [PlantUML](https://github.com/plantuml/plantuml/), thus, requires its dependency, [Graphviz](https://www.graphviz.org/).

## Featured languages

Currently, Java is the only language supported, but the plan is to extend this tool to other languages, starting first with Scala.

## Featured diagrams

Only Class Diagrams are supported right now, and the only other diagram planned for the future is Entity-Relationship Diagram.

## How do I use it?

Right now, the only option available is to run it as a typical JVM program from command line. It takes an argument, which is the sources root folder, and the place where the image will be generated, e.g.: ```java -jar erika.jar /path/to/source```. Since using SBT, the jar is available in the target/ folder generated by running sbt clean package.
