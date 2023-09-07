import java.util.*;

public class Editor extends Engine {

    private Scanner scan;
    private String input;

    private boolean run;

    public void run() {
        scan = new Scanner(System.in);

        run = true;

        while(run) {
            edit();
        }
    }

    public void edit() {
        System.out.print("1. Select Object\n2. New Object\n0. Exit\nEnter Command :: "); //maybe list objects
        input = scan.nextLine();

        if(input.equals("0")) {
            System.out.println("\nExiting Editor...");
            run = false;
        }
        else if(input.equals("1")) {
            select();
        }
        else if(input.equals("2")) {
            newObject();
        }
        else {
            System.out.println("\nNot A Command!\n");
            edit();
        }
    }

    public void select() {
        //highlight = true;
        System.out.print("\nGo Near Object and Type 'select' to Select ('0' to Undo) :: ");
        input = scan.nextLine();

        if(input.equals("0")) {
            //highlight = false;
            System.out.println("Undoing...\n");
            edit();
        }
        else if(input.equals("select")) {
            //highlight = false;
            System.out.print("\nSelecting...");
            input = scan.nextLine();
        }
        else {
            System.out.println("\nNot A Command!");
            select();
        }
    }

    public void newObject() {
        System.out.print("\n1. Point\n2. Sphere\n3. Plane\n4. Line\n0. Undo\nEnter Type :: ");
        input = scan.nextLine();

        if(input.equals("0")) {
            System.out.println("Undoing...\n");
            edit();
        }
        else if(input.equals("1")) {
            System.out.print("You Picked Point");
            input = scan.nextLine();
        }
        else if(input.equals("2")) {
            System.out.print("You Picked Sphere");
            input = scan.nextLine();
        }
        else if(input.equals("3")) {
            System.out.print("You Picked Plane");
            input = scan.nextLine();
        }
        else if(input.equals("4")) {
            System.out.print("You Picked Line");
            input = scan.nextLine();
        }
        else {
            System.out.println("\nNot A Type!\n");
            select();
        }
    }
}
