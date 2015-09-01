# CG - Assignment 1

## Introduction

Drawing of elementary geometric forms using OpenGL.
In special, 2D lines using the [Midpoint Line Algorithm](http://www.mat.univie.ac.at/~kriegl/Skripten/CG/node25.html),
polygons (which are dealt here as, essentially, collections of lines) and circles using [Bresenham's Circle Algorithm](http://web.engr.oregonstate.edu/~sllu/bcircle.pdf).

## Usage

### Drawing
#### Lines
Click and hold the left mouse-button, dragging it to the end of the `line`.
Finally, release the button.

#### Circles and circumferences
Click and hold the left mouse-button. Dragging it away from the origin will increase the radius
of the form. Releasing the button will then commit it.

#### Polygons
`Polygons` can have their sides drawn in the exact same manner as you would draw a `line`.
Once you release the left mouse-button, though, you have two options:

* Click and hold the left mouse-button, creating a line which has as starting coordinate
the end of the previous one.
* Click with the right mouse-button, commiting the form. If the form is disconnected,
a new line will be drawn between the end coordinate of the last side and the first coordinate
of the first one.

#### Rectangles

Click and hold the left mouse-button. Dragging it away from the origin `(0, 0)` will form the rectangle
from the two vectors `(x, 0)` and `(0, y)`.

#### Squares

`Squares` can be drawn similarly to rectangles, except that only one of the vectors `(x, 0)` and `(0, y)`
is considered for its size increase/decrease (the one with greater norm).

### Undoing and redoing
You can undo/redo with your mouse wheel.

## Extending

### Implementing new drawings

Classes inside the package `org.CG.drawings` are automatically scanned and loaded for usage.
To implement new drawings, simply declare a new class in the package,
extend `Drawing` and implement its abstract methods.
