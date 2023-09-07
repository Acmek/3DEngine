# 3DEngine
IMPORTANT
=========
Please change directories in files to match your own

How to Use
==========
Compile GraphicsRunner.java in order to run the engine

WASD to move
Arrow keys to control pitch and yaw
'R' is used to load objects listed in its function

Add new objects by creating a .txt file following the format of the other .txt files
Use the load() function in the Engine.java to add your object into the world either during instantiation or with 'R'

Point.java
==========
Uses variables to store points in 3D space
update() uses the position, pitch, and yaw of Camera.java to convert 3D coordinates to 2D coordinates

Sphere.java
===========
Uses a single Point.java object
Fills or draws sphere calculating size with distance from Camera.java

Plane.java, Line.java, etc.
===========================
Uses a collection of Point.java
Fills or draws in space with listed points

Light.java
==========
Uses Point.java to establish a point light
Uses 3D line formula with z as 0 to create multiple Plane.java objects
Fills planes with transparent black

Engine.java
===========
Instantiates Camera.java and objects
Calculate shadows
Quicksort every object by distance from the camera
Draw objects from farthest to closest
Skip object when a single point is off-camera

Notes
=====
Each file has extra functions such as setting position or roatation
