package chapter14.salesman;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public class TravellingSalesman {

    public static void main(String[] args) {
        new TravellingSalesman().travel();
    }

    public void travel() {
        Vertex a = new Vertex('A', new LinkedHashSet<Edge>());
        Vertex b = new Vertex('B', new LinkedHashSet<Edge>());
        Vertex c = new Vertex('C', new LinkedHashSet<Edge>());
        Vertex d = new Vertex('D', new LinkedHashSet<Edge>());
        Vertex e = new Vertex('E', new LinkedHashSet<Edge>());

        a.getEdges().add(new Edge(b, 5));

        b.getEdges().add(new Edge(a, 5));
        b.getEdges().add(new Edge(c, 7));
        b.getEdges().add(new Edge(d, 8));

        c.getEdges().add(new Edge(b, 7));
        c.getEdges().add(new Edge(d, 3));
        c.getEdges().add(new Edge(e, 2));

        d.getEdges().add(new Edge(b, 8));
        d.getEdges().add(new Edge(c, 3));
        d.getEdges().add(new Edge(e, 10));

        e.getEdges().add(new Edge(d, 10));
        e.getEdges().add(new Edge(c, 2));

        LinkedList<Vertex> route = new LinkedList<Vertex>();
        route.add(a);
        int totalLength = 0;
        LinkedList<Edge> stack = new LinkedList<Edge>();
        for (Edge edge : a.getEdges()) {
            stack.addFirst(edge);
        }

        while (!stack.isEmpty()) {
            Edge current = stack.poll();
            route.add(current.getDestination());
            totalLength += current.getLength();
            current.setVisited(true);

            if (route.size() == 5) {
                System.out.println(Arrays.toString(route.toArray()) + " " + totalLength);
                boolean toContinue = true;
                while (toContinue && route.size() > 2) {
                    Vertex last = route.pollLast();
                    Vertex preLast = route.pollLast();
                    route.addLast(preLast);
                    Set<Edge> preLastsEdges = new HashSet<Edge>(preLast.getEdges());

                    for (Iterator<Edge> iterator = preLastsEdges.iterator(); iterator.hasNext(); ) {
                        Edge edge = iterator.next();

                        if (edge.getDestination().getSymbol() == last.getSymbol()) {
                            totalLength -= edge.getLength();
                            iterator.remove();

                        } else if (route.contains(edge.getDestination())) {
                            iterator.remove();
                        } else if (edge.isVisited()) {
                            iterator.remove();
                        }
                    }

                    if (!preLastsEdges.isEmpty()) {
                        toContinue = false;
                    }
                }
                continue;
            }

            for (Edge edge : current.getDestination().getEdges()) {
                if (!route.contains(edge.getDestination())) {
                    stack.addFirst(edge);
                }
            }
        }
    }


    static class Graph {
        public final static int INFINITY = Integer.MAX_VALUE;

        private Set<Vertex> vertices;

        public Graph() {
            this.vertices = new LinkedHashSet<Vertex>();
        }

        public Graph(Set<Vertex> vertices) {
            this.vertices = vertices;
        }

        public Set<Vertex> getVertices() {
            return vertices;
        }

        public void setVertices(Set<Vertex> vertices) {
            this.vertices = vertices;
        }
    }

    static class Vertex {
        private char symbol;
        private Set<Edge> edges;

        public Vertex() {
            this.edges = new LinkedHashSet<Edge>();
        }

        public Vertex(char symbol, Set<Edge> edges) {
            this.symbol = symbol;
            this.edges = edges;
        }

        public char getSymbol() {
            return symbol;
        }

        public void setSymbol(char symbol) {
            this.symbol = symbol;
        }

        public Set<Edge> getEdges() {
            return edges;
        }

        public void setEdges(Set<Edge> edges) {
            this.edges = edges;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Vertex)) return false;

            Vertex vertex = (Vertex) o;

            return symbol == vertex.symbol;
        }

        @Override
        public int hashCode() {
            return (int) symbol;
        }


        @Override
        public String toString() {
            return "Vertex{" +
                    "symbol=" + symbol +
                    '}';
        }
    }

    static class Edge {
        private Vertex destination;
        private int length;
        private boolean visited;

        public Edge() {
        }

        public Edge(Vertex destination, int length) {
            this.destination = destination;
            this.length = length;
        }

        public Vertex getDestination() {
            return destination;
        }

        public void setDestination(Vertex destination) {
            this.destination = destination;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Edge)) return false;

            Edge edge = (Edge) o;

            return destination != null ? destination.equals(edge.destination) : edge.destination == null;
        }

        @Override
        public int hashCode() {
            return destination != null ? destination.hashCode() : 0;
        }
    }
}
