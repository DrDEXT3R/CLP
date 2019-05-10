# CLP Calculator
It is a simple calculator for solving problems using Constraint Logic Programming.
Thanks to it, we can quickly see solution of:
 - Combinatory problem (Einstein's Riddle)
 - Scheduling problem (Museum of Art)
 - Optimization problems (Map Coloring)

![UI](CLP_Calculator.PNG)

## Installing
#### Windows
1. Download the [file](https://drive.google.com/open?id=15Ofjf2lGzx6isuxqjx1CSVrG1FNaRnKr) and extract it.
2. Open cmd.
2. Go to the jre/bin directory using the terminal e.g.:
```
cd Desktop/jre/bin
```
3. Run the CLP Calculator:
```
java --add-modules=javafx.controls,javafx.swing,javafx.fxml -jar CLP-Calculator.jar
```
#### Linux
1. Download the [file](https://drive.google.com/open?id=1YGvjgwLLjX4Ay-ENx-pL3KHExEUJ0Qpw) and extract it.
2. Install the font by clicking on the file "berlin-sans-fb-demi-bold".
3. Open the terminal in the folder named CLP_linux.
4. Enter the following commands:
```
sudo apt-get install openjfx
```
```
sudo apt-get install ttf-mscorefonts-installer
sudo fc-cache
```
5. Run the CLP Calculator:
```
java --module-path "javafx-sdk-11.0.2/lib" --add-modules=javafx.controls,javafx.swing,javafx.fxml -jar CLP-Calculator.jar
```
#### JAR File
[Download](https://drive.google.com/open?id=1vB7aISYgWkwvvoPGeJO6-QEBL708UNCq)

## Deployment
VM options:
```
--module-path ${PATH_TO_FX} --add-modules=javafx.controls,javafx.swing,javafx.fxml
```

## Built With
* [JDK 11](https://docs.oracle.com/en/java/javase/11/) - Java Development Kit
* [OpenJFX](https://openjfx.io/openjfx-docs/) - Used to create GUI
* [JFoenix](http://www.jfoenix.com/documentation.html) - Expands the list of GUI components
* [JaCoP](http://jacopguide.osolpro.com/guideJaCoP.html) - Java Constraint Programming solver
* [JFreeChart](http://www.jfree.org/jfreechart/api/javadoc/index.html) - Used to generate chart

## Authors
* **Tomasz Strzoda** - [DrDEXT3R](https://github.com/DrDEXT3R)

## License
This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](https://github.com/DrDEXT3R/CLP-Calculator/blob/master/LICENSE) file for details