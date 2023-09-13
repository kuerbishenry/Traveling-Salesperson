public class Tour {
    private int size;
    private Node current_node;
    private double length;


    private class Node {
        private Point p;
        private Node next;

        public Node(Point p) {
            this.p = p;
            next = null;
        }
    }


    // Creates an empty tour.
    public Tour() {
        size = 0;
        current_node = null;
        length = 0.0;

    }


    // Creates the 4-point tour a->b->c->d->a (for debugging).
    public Tour(Point a, Point b, Point c, Point d) {

        Node a1 = new Node(a);
        Node b1 = new Node(b);
        Node c1 = new Node(c);
        Node d1 = new Node(d);

        a1.p = a;
        b1.p = b;
        c1.p = c;
        d1.p = d;

        a1.next = b1;
        b1.next = c1;
        c1.next = d1;
        d1.next = a1;

        size = 4;

        current_node = a1;
    }


    // Returns the number of points in this tour.
    public int size() {
        Node temp = current_node;
        int count = 0;
        if (temp == null) {
            count = 0;
        }
        else {
            do {
                count++;
                temp = temp.next;
            } while (temp != current_node);
        }
        size = count;
        return size;
    }


    // Returns the length of this tour.
    public double length() {
        double len = 0.0;
        Node temp = current_node;
        for (int i = 0; i < size; i++) {
            len += temp.p.distanceTo(temp.next.p);
            temp = temp.next;
        }
        length = len;
        return len;
    }

    // Returns a string representation of this tour.
    public String toString() {

        StringBuilder output = new StringBuilder();

        if (current_node == null) {
            return output.toString();
        }

        Node current = current_node;
        while (current.next != current_node) {
            output.append(current.p).append("\n");
            current = current.next;
        }
        output.append(current.p);

        return output.toString();
    }
    // Draws this tour to standard drawing.
    public void draw() {
        Node temp = current_node;
        for (int i = 0; i < size; i++) {
            temp.p.drawTo(temp.next.p);
            temp = temp.next;
        }

    }

    // Inserts a point using the nearest neighbor heuristic.
    public void insertNearest(Point p) {

        if (size == 0) {
            current_node = new Node(p);
            current_node.next = current_node;
            size += 1;
        }
        else {
            double nearest_distance = current_node.p.distanceTo(current_node.next.p);
            Node nearest_node = current_node;


            Node node = current_node;
            for (int i = 0; i < size; i++) {
                double distance = node.p.distanceTo(p);
                if (distance < nearest_distance) {
                    nearest_distance = distance;
                    nearest_node = node;
                }
                node = node.next;
            }


            Node new_node = new Node(p);
            new_node.next = nearest_node.next;
            nearest_node.next = new_node;
            size++;
        }
    }


    // Inserts a point using the smallest increase heuristic.
    public void insertSmallest(Point p) {

        if (size == 0) {
            current_node = new Node(p);
            current_node.next = current_node;
            size++;
        }
        else {
            double smallest_increase = Double.MAX_VALUE;
            Node insertion_node = null;


            Node node = current_node;
            for (int i = 0; i < size; i++) {
                double increase = node.p.distanceTo(p) + p.distanceTo(node.next.p)
                        - node.p.distanceTo(node.next.p);
                if (increase < smallest_increase) {
                    smallest_increase = increase;
                    insertion_node = node;
                }
                node = node.next;
            }

            Node new_node = new Node(p);
            new_node.next = insertion_node.next;
            insertion_node.next = new_node;
            size++;
        }
    }


    // Tests this class by calling all constructors and instance methods.
    public static void main(String[] args) {
        // define 4 points as corners of a square
        Point a = new Point(1.0, 1.0);
        Point b = new Point(1.0, 4.0);
        Point c = new Point(4.0, 4.0);
        Point d = new Point(4.0, 1.0);

        // create the tour a -> b -> c -> d -> a
        Tour squareTour = new Tour(a, b, c, d);
    }

}
