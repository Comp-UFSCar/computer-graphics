# Installing and running this project

In order to run this project, follow the steps bellow:

1. Clone this repository with `git clone https://github.com/lucasdavid/Matrix-Paint`.
2. Make sure you have [OpenGL pack](http://plugins.netbeans.org/plugin/3260/netbeans-opengl-pack) installed.
3. Create a new `JOGL Application` and add Matrix-Paint's `src` folder to the list of `Source Package Folders`,
in your NetBeans project's property.
4. Set [CGAssignment1.java](https://github.com/lucasdavid/Matrix-Paint/blob/master/src/org/CG/CGAssignment1.java)
as the main class. You should now be able to execute it.

Alternatively,

1. Make sure you have [OpenGL pack](http://plugins.netbeans.org/plugin/3260/netbeans-opengl/pack) installed.
2. Clone this repository with `git clone https://github.com/lucasdavid/Matrix-Paint` and choose to
create a new project from the existing sources.
3. Change the folder of the project to be the same of the clone (usually `..` from the default).
4. Add the libraries `JOGL` and `GLUEGEN-RT` to your project.
5. Set [CGAssignment1.class](https://github.com/lucasdavid/Matrix-Paint/blob/master/src/org/CG/CGAssignment1.java)
as the main class.
6. Edit the `PROJECT_HOME/nbproject/project.properties` file to add the following lines at the end, changing the `natives.platform` property to match your architeture. You should now be able to execute the project.
    
    ```
    run-sys-prop.java.library.path=\
        ${libs.JOGL.classpath}-natives-${natives.platform}${path.separator}\
        ${libs.GLUEGEN-RT.classpath}-natives-${natives.platform}
    libs.JOGL.classpath=../opengl/build/cluster/libs/jogl.jar
    libs.GLUEGEN-RT.classpath=../opengl/build/cluster/libs/gluegen-rt.jar
    natives.platform=linux-amd64
    ```

