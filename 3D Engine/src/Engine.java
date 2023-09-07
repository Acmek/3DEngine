import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.*;

/*URL url = new URL("http://www.puzzlers.org/pub/wordlists/pocket.txt");
Scanner s = new Scanner(url.openStream());*/

//check if plane is being blocked by using bounding blocks from screen space //that portal youtube showed how to check for collision //new shadows //make shadows a setting
//physics engine, gravity is number value
//shadow does not work if point is above light

public class Engine extends Canvas implements KeyListener, Runnable {

    private double maxFps, time, milliseconds, fps;

    private BufferedImage back;

    private Camera camera;

    private boolean keys[];

    private List<Object> objects;
    private Object[] sorted;
    private List<Object> shadows;
    private Light light;

    private boolean highlight;
    
    public Engine() {
        maxFps = 125;

        camera = new Camera(Math.PI / 2, 0);
        keys = new boolean[13];

        keys[11] = true; //debug
        keys[12] = true; //edit

        objects = new ArrayList<Object>();
        load("C:/Users/Kevin/Desktop/Projects/3D Engine/src/Objects/House.txt");

        light = new Light(75, 0, 50);
        objects.add(new Sphere(light.getX(), light.getY(), light.getZ(), 50, Color.YELLOW));
        

        setBackground(Color.GRAY);
        setVisible(true);

        new Thread(this).start();
        addKeyListener(this);
    }

    public void update(Graphics window) {
        paint(window);
    }

    public void paint(Graphics window) {
        time = System.nanoTime();

        Graphics2D twoDGraph = (Graphics2D)window;

        if(back == null)
            back = (BufferedImage)(createImage(getWidth(), getHeight()));

        Graphics graphToBack = back.createGraphics();

        
        move();
        //camera.lookAt(0, 30, 30);

        sorted = objects.toArray();

        if(light != null) {
            shadows = new ArrayList<Object>();
            for(int i = 0; i < sorted.length; i++) {
                if(sorted[i].getClass() == Sphere.class) {
                    
                }
                else if(sorted[i].getClass() == Plane.class) {
                    Point[] arr = new Point[((Plane) sorted[i]).getPoints().length];
                    for(int j = 0; j < ((Plane) sorted[i]).getPoints().length; j++) {
                        arr[j] = new Point(((((Plane) sorted[i]).getPoint(j).getX() - light.getX()) * (-light.getZ() / (((Plane) sorted[i]).getPoint(j).getZ() - light.getZ()))) + light.getX(), ((((Plane) sorted[i]).getPoint(j).getY() - light.getY()) * (-light.getZ() / (((Plane) sorted[i]).getPoint(j).getZ() - light.getZ()))) + light.getY(), 0);
                    }
                    shadows.add(new Plane(arr, new Color(64, 64, 64, 128)));
                }
            }
        }

        graphToBack.setColor(Color.GRAY);
        graphToBack.fillRect(0, 0, 640, 480);

        //graphToBack.setColor(Color.GREEN);
        //graphToBack.fillRect(0, 240, 640, 240);

        //shadow
        if(light != null && shadows.size() > 0)
            for(int i = 0; i < shadows.size(); i++)
                ((Plane) shadows.get(i)).draw(graphToBack, camera.getX(), camera.getY(), camera.getZ(), camera.getYaw(), camera.getPitch());

        if(sorted.length > 0) {
            quickSort(sorted, 0, sorted.length - 1);

            for(int i = 0; i < sorted.length; i++) {
                if(sorted[i].getClass() == Point.class)
                    ((Point) sorted[i]).draw(graphToBack, camera.getX(), camera.getY(), camera.getZ(), camera.getYaw(), camera.getPitch());
                else if(sorted[i].getClass() == Sphere.class)
                    ((Sphere) sorted[i]).draw(graphToBack, camera.getX(), camera.getY(), camera.getZ(), camera.getYaw(), camera.getPitch());
                else if(sorted[i].getClass() == Plane.class)
                    ((Plane) sorted[i]).draw(graphToBack, camera.getX(), camera.getY(), camera.getZ(), camera.getYaw(), camera.getPitch());
                else if(sorted[i].getClass() == Line.class)
                    ((Line) sorted[i]).draw(graphToBack, camera.getX(), camera.getY(), camera.getZ(), camera.getYaw(), camera.getPitch());
            }

            //OUTLINER
            if(highlight) {
                if(sorted[sorted.length - 1].getClass() == Point.class) {
                    Point outline = new Point((Point) sorted[sorted.length - 1], Color.BLACK);
                    outline.setThickness(1.5);
                    outline.setStatus(true);
                    outline.setFilled(false);
                    outline.draw(graphToBack, camera.getX(), camera.getY(), camera.getZ(), camera.getYaw(), camera.getPitch());
                }
                else if(sorted[sorted.length - 1].getClass() == Sphere.class) {
                    Sphere outline = new Sphere(((Sphere) sorted[sorted.length - 1]).getCenter(), ((Sphere) sorted[sorted.length - 1]).getSize(), Color.BLACK);
                    outline.setThickness(1.5);
                    outline.setStatus(true);
                    outline.setFilled(false);
                    outline.draw(graphToBack, camera.getX(), camera.getY(), camera.getZ(), camera.getYaw(), camera.getPitch());
                }
                else if(sorted[sorted.length - 1].getClass() == Plane.class) {
                    Plane outline = new Plane(((Plane) sorted[sorted.length - 1]).getPoints(), Color.BLACK);
                    outline.setThickness(1.5);
                    outline.setStatus(true);
                    outline.setFilled(false);
                    outline.draw(graphToBack, camera.getX(), camera.getY(), camera.getZ(), camera.getYaw(), camera.getPitch());
                }
                else if(sorted[sorted.length - 1].getClass() == Line.class) {
                    Line outline = new Line(((Line) sorted[sorted.length - 1]).getPoints(), ((Line) sorted[sorted.length - 1]).getThickness(), Color.BLACK);
                    outline.setStatus(true);
                    outline.setThickness(1.5);
                    outline.draw(graphToBack, camera.getX(), camera.getY(), camera.getZ(), camera.getYaw(), camera.getPitch());
                }
            }
        }

        graphToBack.setColor(Color.BLACK);
        graphToBack.drawLine(320, 235, 320, 245);
        graphToBack.drawLine(315, 240, 325, 240);

        if(keys[11]) {
            graphToBack.setColor(Color.RED);
            graphToBack.drawString("FPS: " + Math.round(fps), 20, 20);
            graphToBack.drawString("Extra Milliseconds Per Frame: " + Math.round(milliseconds), 20, 40);
            graphToBack.drawString("Number of Objects: " + objects.size(), 20, 60);

            graphToBack.drawString("x : " + camera.getX(), 20, 100);
            graphToBack.drawString("y : " + camera.getY(), 20, 120);
            graphToBack.drawString("z : " + camera.getZ(), 20, 140);
            graphToBack.drawString("yaw : " + Math.toDegrees(camera.getYaw()),20, 160);
            graphToBack.drawString("pitch : " + Math.toDegrees(camera.getPitch()), 20, 180);
        }
        

        twoDGraph.drawImage(back, null, 0, 0);

        milliseconds = (System.nanoTime() - time) / 1000000;
        fps = 1000 / (milliseconds + (1000 / maxFps));
    }

    public void move() {
        if(keys[0]) { camera.movePitch(3.125 / maxFps); } //Up
        if(keys[1]) { camera.moveYaw(3.125 / maxFps); } //Left
        if(keys[2]) { camera.movePitch(-3.125 / maxFps); } //Down
        if(keys[3]) { camera.moveYaw(-3.125 / maxFps); } //Right
        if(keys[4]) { camera.forwardAndBack(125 / maxFps); } //W
        if(keys[5]) { camera.leftAndRight(-125 / maxFps); } //A
        if(keys[6]) { camera.forwardAndBack(-125 / maxFps); } //S
        if(keys[7]) { camera.leftAndRight(125 / maxFps); } //D
        if(keys[8]) { camera.moveZ(125 / maxFps); } //Space
        if(keys[9]) { camera.moveZ(-125 / maxFps); } //Shift
        if(keys[10]) { load("C:/Users/Kevin/Desktop/Projects/3D Engine/src/Objects/Spheres.txt"); sorted = objects.toArray(); } //R
    }

    public int partition(Object[] arr, int low, int high)
    {
        double pivot = 0;
        if(arr[high].getClass() == Point.class)
            pivot = ((Point) arr[high]).getDistance();
        else if(arr[high].getClass() == Sphere.class)
            pivot = ((Sphere) arr[high]).getDistance();
        else if(arr[high].getClass() == Plane.class)
            pivot = ((Plane) arr[high]).getDistance();
        else if(arr[high].getClass() == Line.class)
            pivot = ((Line) arr[high]).getDistance();

        int i = (low - 1); // index of smaller element
        for (int j = low; j < high; j++)
        {
            // If current element is smaller than or
            // equal to pivot
            if(arr[j].getClass() == Point.class) {
                if (((Point) arr[j]).getDistance() >= pivot)
                {
                    i++;
    
                    // swap arr[i] and arr[j]
                    Object temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
            else if(arr[j].getClass() == Sphere.class) {
                if (((Sphere) arr[j]).getDistance() >= pivot)
                {
                    i++;
    
                    // swap arr[i] and arr[j]
                    Object temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
            else if(arr[j].getClass() == Plane.class) {
                if (((Plane) arr[j]).getDistance() >= pivot)
                {
                    i++;
    
                    // swap arr[i] and arr[j]
                    Object temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
            else if(arr[j].getClass() == Line.class) {
                if (((Line) arr[j]).getDistance() >= pivot)
                {
                    i++;
    
                    // swap arr[i] and arr[j]
                    Object temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
 
        // swap arr[i+1] and arr[high] (or pivot)
        Object temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
 
        return i + 1;
    }

    public void quickSort(Object[] arr, int low, int high)
    {
        if (low < high)
        {
            /* pi is partitioning index, arr[p] is now
            at right place */
            int pi = partition(arr, low, high);

            quickSort(arr, low, pi - 1);  // Before pi
            quickSort(arr, pi + 1, high); // After pi
        }
    }

    public void keyPressed(KeyEvent e) {
        //System.out.println(e.getKeyCode());

        switch(e.getKeyCode()) {
            case 38 : keys[0] = true; break; //Up
            case 37 : keys[1] = true; break; //Left
            case 40 : keys[2] = true; break; //Down
            case 39 : keys[3] = true; break; //Right
            case 87 : keys[4] = true; break; //W
            case 65 : keys[5] = true; break; //A
            case 83 : keys[6] = true; break; //S
            case 68 : keys[7] = true; break; //D
            case 32 : keys[8] = true; break; //Space
            case 16 : keys[9] = true; break; //Shift
            case 82 : keys[10] = true; break; //R
            case 114 : if(keys[11]) { keys[11] = false; } else { keys[11] = true; }; break; //F3
            case 115 : if(highlight) { highlight = false; } else { highlight = true; }; break; //F4
        }
    }

    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case 38 : keys[0] = false; break; //Up
            case 37 : keys[1] = false; break; //Left
            case 40 : keys[2] = false; break; //Down
            case 39 : keys[3] = false; break; //Right
            case 87 : keys[4] = false; break; //W
            case 65 : keys[5] = false; break; //A
            case 83 : keys[6] = false; break; //S
            case 68 : keys[7] = false; break; //D
            case 32 : keys[8] = false; break; //Space
            case 16 : keys[9] = false; break; //Shift
            case 82 : keys[10] = false; break; //R
        }
    }

    public void keyTyped(KeyEvent e) { }

    public void run() {
		try {
			while(true) {
				Thread.currentThread();
                Thread.sleep((long)(1000 / maxFps));
				repaint();
			}
		}
        catch(Exception e) {
            e.printStackTrace();
        }
	}

    public void load(String path) {
        try {
            Scanner scan = new Scanner(new File(path));

            while(scan.hasNextLine()) {

                String line = scan.nextLine();
                if(line.equals("Point")) {
                    Point point = null;
                    double size = -1;
                    Color color = null;
                    boolean status = true;
                    boolean filled = true;
                    double thickness = 0;

                    if(scan.hasNextLine())
                        if(!(line = scan.nextLine()).isEmpty()) {
                            String[] arr = line.replaceAll("[(,)]", "").split(" ");
                            if(!arr[0].equals("x"))
                                point = new Point(Double.valueOf(arr[0]), Double.valueOf(arr[1]), Double.valueOf(arr[2]));

                            if(scan.hasNextLine()) {
                                if(!(line = scan.nextLine()).isEmpty())
                                    if(!line.equals("x"))
                                        size = Double.valueOf(line);

                                if(scan.hasNextLine())
                                    if(!(line = scan.nextLine()).isEmpty()) {
                                        arr = line.replaceAll("[(,)]", "").split(" ");
                                        if(!arr[0].equals("x"))
                                            color = new Color(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]), Integer.valueOf(arr[2]));

                                        if(scan.hasNextLine())
                                            if(!(line = scan.nextLine()).isEmpty()) {
                                                if(!line.equals("x"))
                                                    status = Boolean.valueOf(line);

                                                if(scan.hasNextLine())
                                                    if(!(line = scan.nextLine()).isEmpty()) {
                                                        if(!line.equals("x"))
                                                            filled = Boolean.valueOf(line);

                                                        if(scan.hasNextLine())
                                                            if(!(line = scan.nextLine()).isEmpty())
                                                                if(!line.equals("x"))
                                                                    thickness = Double.valueOf(line);
                                                    }
                                            }
                                    }
                            }
                        }

                    objects.add(new Point(point, size, color));
                    ((Point) objects.get(objects.size() - 1)).setStatus(status);
                    ((Point) objects.get(objects.size() - 1)).setFilled(filled);
                    ((Point) objects.get(objects.size() - 1)).setThickness(thickness);
                }
                else if(line.equals("Sphere")) {
                    Point point = null;
                    double size = -1;
                    Color color = null;
                    boolean status = true;
                    boolean filled = true;
                    double thickness = 0;

                    if(scan.hasNextLine())
                        if(!(line = scan.nextLine()).isEmpty()) {
                            String[] arr = line.replaceAll("[(,)]", "").split(" ");
                            if(!arr[0].equals("x"))
                                point = new Point(Double.valueOf(arr[0]), Double.valueOf(arr[1]), Double.valueOf(arr[2]));

                            if(scan.hasNextLine()) {
                                if(!(line = scan.nextLine()).isEmpty())
                                    if(!line.equals("x"))
                                        size = Double.valueOf(line);

                                if(scan.hasNextLine())
                                    if(!(line = scan.nextLine()).isEmpty()) {
                                        arr = line.replaceAll("[(,)]", "").split(" ");
                                        if(!arr[0].equals("x"))
                                            color = new Color(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]), Integer.valueOf(arr[2]));

                                        if(scan.hasNextLine())
                                            if(!(line = scan.nextLine()).isEmpty()) {
                                                if(!line.equals("x"))
                                                    status = Boolean.valueOf(line);

                                                if(scan.hasNextLine())
                                                    if(!(line = scan.nextLine()).isEmpty()) {
                                                        if(!line.equals("x"))
                                                            filled = Boolean.valueOf(line);

                                                        if(scan.hasNextLine())
                                                            if(!(line = scan.nextLine()).isEmpty())
                                                                if(!line.equals("x"))
                                                                    thickness = Double.valueOf(line);
                                                    }
                                            }
                                    }
                            }
                        }

                    objects.add(new Sphere(point, size, color));
                    ((Sphere) objects.get(objects.size() - 1)).setStatus(status);
                    ((Sphere) objects.get(objects.size() - 1)).setFilled(filled);
                    ((Sphere) objects.get(objects.size() - 1)).setThickness(thickness);
                }
                else if(line.equals("Plane")) {
                    Point[] points = null;
                    Color color = null;
                    boolean status = true;
                    boolean filled = true;
                    double thickness = 0;

                    if(scan.hasNextLine())
                        if(!(line = scan.nextLine()).isEmpty()) {
                            String[] arr = line.replaceAll("[(,)]", "").split(" ");
                            if(!arr[0].equals("x")) {
                                points = new Point[arr.length / 3];
                                for(int i = 0; i < points.length; i++)
                                    points[i] = new Point(Double.valueOf(arr[i * 3 + 0]), Double.valueOf(arr[i * 3 + 1]), Double.valueOf(arr[i * 3 + 2]));
                            }

                            if(scan.hasNextLine())
                                if(!(line = scan.nextLine()).isEmpty()) {
                                    arr = line.replaceAll("[(,)]", "").split(" ");
                                    if(!arr[0].equals("x"))
                                        color = new Color(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]), Integer.valueOf(arr[2]));

                                    if(scan.hasNextLine())
                                        if(!(line = scan.nextLine()).isEmpty()) {
                                            if(!line.equals("x"))
                                                status = Boolean.valueOf(line);

                                            if(scan.hasNextLine())
                                                if(!(line = scan.nextLine()).isEmpty()) {
                                                    if(!line.equals("x"))
                                                        filled = Boolean.valueOf(line);

                                                    if(scan.hasNextLine())
                                                        if(!(line = scan.nextLine()).isEmpty())
                                                            if(!line.equals("x"))
                                                                thickness = Double.valueOf(line);
                                                }
                                        }
                                }
                        }

                    objects.add(new Plane(points, color));
                    ((Plane) objects.get(objects.size() - 1)).setStatus(status);
                    ((Plane) objects.get(objects.size() - 1)).setFilled(filled);
                    ((Plane) objects.get(objects.size() - 1)).setThickness(thickness);
                }
                else if(line.equals("Line")) {
                    Point[] points = null;
                    double thickness = -1;
                    Color color = null;
                    boolean status = true;

                    if(scan.hasNextLine())
                        if(!(line = scan.nextLine()).isEmpty()) {
                            String[] arr = line.replaceAll("[(,)]", "").split(" ");
                            if(!arr[0].equals("x")) {
                                points = new Point[arr.length / 3];
                                for(int i = 0; i < points.length; i++)
                                    points[i] = new Point(Double.valueOf(arr[i * 3 + 0]), Double.valueOf(arr[i * 3 + 1]), Double.valueOf(arr[i * 3 + 2]));
                            }

                            if(scan.hasNextLine()) {
                                if(!(line = scan.nextLine()).isEmpty())
                                    if(!line.equals("x"))
                                        thickness = Double.valueOf(line);

                                if(scan.hasNextLine())
                                    if(!(line = scan.nextLine()).isEmpty()) {
                                        arr = line.replaceAll("[(,)]", "").split(" ");
                                        if(!arr[0].equals("x"))
                                            color = new Color(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]), Integer.valueOf(arr[2]));

                                        if(scan.hasNextLine())
                                            if(!(line = scan.nextLine()).isEmpty())
                                                if(!line.equals("x"))
                                                    status = Boolean.valueOf(line);
                                    }
                            }
                        }

                    objects.add(new Line(points, thickness, color));
                    ((Line) objects.get(objects.size() - 1)).setStatus(status);
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}