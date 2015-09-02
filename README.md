# CG - Assignment 1

## Introduction

Drawing of elementary geometric forms using OpenGL.
In special, 2D vectors using the [Midpoint Line Algorithm](http://www.mat.univie.ac.at/~kriegl/Skripten/CG/node25.html)
and circles using [Bresenham's Algorithm](http://web.engr.oregonstate.edu/~sllu/bcircle.pdf).

## Installing and running

If you're having problems installing or executing this project, follow this [steps](https://github.com/lucasdavid/CG-Assignment-1/blob/master/INSTALL.md).

## Usage

### Drawing
Select the drawing mode in the upper-left corner.
Then, click with our left mouse-button and drag the mouse over the program's frame.
Finally, release the button.

### Undoing and redoing
You can undo/redo with your mouse wheel.

## Extending

### Implementing new drawings

Classes inside the package `org.CG.drawings` are automatically scanned and loaded for usage.
To implement new drawings, simply declare a new class in the package,
extend `Drawing` and implement its abstract methods.
