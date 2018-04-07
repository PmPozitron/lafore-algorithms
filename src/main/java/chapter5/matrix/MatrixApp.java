package chapter5.matrix;

public class MatrixApp {
    public static void main(String[] args) {
        new MatrixApp().testDrive();
    }

    public void testDrive() {
        Matrix theMatrix = new Matrix(5, 5);
        theMatrix.display();

        theMatrix.insertAt(2, 2, 2.);
        theMatrix.insertAt(3, 3, 3.);
        theMatrix.insertAt(2, 3, 2.3);
        theMatrix.insertAt(3, 2, 3.2);

        theMatrix.display();
        System.out.println(theMatrix.getValueFrom(2, 3));
        System.out.println(theMatrix.getValueFrom(3, 2));

    }
}

class Link {
    private double value;
    private Link right;
    private Link down;

    Link() {
        this(Double.NaN, null, null);
    }

    Link(double value, Link right, Link down) {
        this.value = value;
        this.right = right;
        this.down = down;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setRight(Link right) {
        this.right = right;
    }

    public void setDown(Link down) {
        this.down = down;
    }

    public double getValue() {
        return value;
    }

    public Link getRight() {
        return right;
    }

    public Link getDown() {
        return down;
    }
}

class Matrix {
    private Link head;
    private Link current;

    public Matrix() {
        head = new Link(Double.NaN, null, null);
    }

    public Matrix(int abscissa, int ordinate) {
        Link current = new Link();
        Link previous = null;
        Link nextToRight = null;
        Link nextToDown = null;
        Link columnTop = null;

        this.head = current;
        for (int i = 1; i < abscissa; i++) {
            columnTop = current;
            nextToRight = new Link();
            current.setRight(nextToRight);

            for (int j = 1; j < ordinate; j++) {
                nextToDown = new Link();
                current.setDown(nextToDown);
                current = nextToDown;
                if (previous != null) {
                    previous.getDown().setRight(nextToDown);
                    previous = previous.getDown();
                }
            }

            previous = columnTop;
            current = nextToRight;
        }
//      arguably, dirty trick to process last column
        for (int j = 1; j < ordinate; j++) {
            nextToDown = new Link();
            current.setDown(nextToDown);
            current = nextToDown;
            if (previous != null) {
                previous.getDown().setRight(nextToDown);
                previous = previous.getDown();
            }
        }
    }

    public void display() {
        Link currentInRow = this.head;
        Link currentInColumn = this.head;

        while (currentInColumn != null) {
            while (currentInRow != null) {
                System.out.print(currentInRow.getValue() + " ");
                currentInRow = currentInRow.getRight();
            }
            System.out.println();
            currentInColumn = currentInColumn.getDown();
            currentInRow = currentInColumn;
        }
    }

    public void insertAt(int abscissa, int ordinate, double value) {
        navigateTo(abscissa, ordinate).setValue(value);
    }

    public double getValueFrom(int abcsissa, int ordinate) {
        return navigateTo(abcsissa, ordinate).getValue();
    }

    private Link navigateTo(int abscissa, int ordinate) {
        if (this.head == null) {
            return null;
        }
        Link current = this.head;

        for (int i = 0; i < abscissa; i++) {
            current = current.getRight();
        }

        for (int i = 0; i < ordinate; i++) {
            current = current.getDown();
        }

        return current;
    }
}
