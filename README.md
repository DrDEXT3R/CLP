# CLP Calculator
It is a simple calculator for solving problems using Constraint Logic Programming. <br/>
Thanks to it, we can quickly see solution of:
 - Combinatory problem (Einstein's Riddle)
 - Scheduling problem (Museum of Art)
 - Optimization problems (Map Coloring)

![UI](CLP_Calculator.PNG)

## Installing
#### Windows
[CLP-Calculator_win_x86_portable.zip](https://sourceforge.net/projects/clp-calculator/files/CLP-Calculator_win_x86_portable.zip/download) <br/>
[CLP-Calculator_win_x64_portable.zip](https://sourceforge.net/projects/clp-calculator/files/CLP-Calculator_win_x64_portable.zip/download)

#### Linux
[CLP-Calculator_linux.zip](https://sourceforge.net/projects/clp-calculator/files/CLP-Calculator_linux.zip/download) <br/>
In the downloaded folder, open the terminal and enter:
```
$ apt-get install openjfx
$ java --module-path "javafx-sdk-11.0.2/lib" --add-modules=javafx.controls,javafx.swing,javafx.fxml -jar CLP-Calculator.jar
```
#### JAR File
[Download JAR File](https://sourceforge.net/projects/clp-calculator/files/CLP-Calculator.jar/download)  

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