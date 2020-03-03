package chapter14.hamilton;


import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class HamiltonCycle {

    public static void main(String[] args) {
        new HamiltonCycle().findCycles();

        String[] array = { "hawk", "robin" };     // [hawk, robin]
        List<String> list = Arrays.asList(array); // returns fixed size list

        new Date();
    }

    public void findCycles() {
        Vertex a = new Vertex('A', new LinkedHashSet<Edge>());
        Vertex b = new Vertex('B', new LinkedHashSet<Edge>());
        Vertex c = new Vertex('C', new LinkedHashSet<Edge>());
        Vertex d = new Vertex('D', new LinkedHashSet<Edge>());
        Vertex e = new Vertex('E', new LinkedHashSet<Edge>());
        Vertex g = new Vertex('G', new LinkedHashSet<Edge>());
        Vertex h = new Vertex('H', new LinkedHashSet<Edge>());
        Vertex k = new Vertex('K', new LinkedHashSet<Edge>());

        a.getEdges().add(new Edge(c, 1));
        a.getEdges().add(new Edge(b, 1));

        b.getEdges().add(new Edge(c, 1));
        b.getEdges().add(new Edge(g, 1));

        c.getEdges().add(new Edge(b, 1));
        c.getEdges().add(new Edge(e, 1));

        d.getEdges().add(new Edge(e, 1));
        d.getEdges().add(new Edge(h, 1));

        e.getEdges().add(new Edge(a, 1));
        e.getEdges().add(new Edge(b, 1));

        g.getEdges().add(new Edge(h, 1));

        h.getEdges().add(new Edge(d, 1));
        h.getEdges().add(new Edge(k, 1));

        k.getEdges().add(new Edge(d, 1));
//        k.getEdges().add(new Edge(h, 1));

        Vertex[] vertices = new Vertex[]{a, b, c, d, e, g, h, k};

        for (int i = 0; i < vertices.length; i++) {
//        for (int i = 2; i < 3; i++) {

            for (Vertex vertex : vertices) {
                for (Edge edge : vertex.getEdges()) {
                    edge.setVisited(false);
                }
            }

            LinkedList<Vertex> route = new LinkedList<Vertex>();
            route.add(vertices[i]);
            LinkedList<Edge> stack = new LinkedList<Edge>();
            for (Edge edge : vertices[i].getEdges()) {
                stack.addFirst(edge);
            }

            while (!stack.isEmpty()) {
                Edge current = stack.poll();
                route.add(current.getDestination());
                current.setVisited(true);

                if (route.size() == vertices.length) {
                    for (Edge edge : current.getDestination().getEdges()) {
                        if (edge.getDestination().getSymbol() == route.getFirst().getSymbol()) {
                            System.out.println(Arrays.toString(route.toArray()) + " " + route.getFirst().getSymbol());
                        }
                    }
                    boolean toContinue = true;
                    while (toContinue && route.size() > 1) {
                        Vertex last = route.pollLast();
                        Vertex preLast = route.pollLast();
                        route.addLast(preLast);
                        Set<Edge> preLastsEdges = new HashSet<Edge>(preLast.getEdges());

                        for (Iterator<Edge> iterator = preLastsEdges.iterator(); iterator.hasNext(); ) {
                            Edge edge = iterator.next();
                            // perhaps, it surplus - last if branch will remove it as it should be visited
//                            if (edge.getDestination().getSymbol() == last.getSymbol()) {
//                                iterator.remove();

//                            } else
                            if (route.contains(edge.getDestination())) {
                                iterator.remove();
                                for (Edge toResetVisited : edge.getDestination().getEdges()) {
                                    toResetVisited.setVisited(false);
                                }

                            } else if (edge.isVisited()) {
                                iterator.remove();
                                for (Edge toResetVisited : edge.getDestination().getEdges()) {
                                    toResetVisited.setVisited(false);
                                }
                            }
                        }

                        if (!preLastsEdges.isEmpty()) {
                            toContinue = false;

                        }
                    }
                    continue;
                }
                boolean wereAdditions = false;
                for (Edge edge : current.getDestination().getEdges()) {
                    if (!route.contains(edge.getDestination())) {
                        stack.addFirst(edge);
                        wereAdditions = true;
                    }
                }
                if (!wereAdditions) {
                    Vertex last = route.pollLast();
                    for (Edge toResetVisited : last.getEdges()) {
                        toResetVisited.setVisited(false);
                    }

                    boolean toBeContinued = true;
                    while (toBeContinued) {
                        Vertex preLast = route.pollLast();
                        for (Edge edge : preLast.getEdges()) {
                            if (! edge.isVisited() && ! route.contains(edge.getDestination())) {
                                toBeContinued = false;
                                route.addLast(preLast);
                                break;
                            }
                        }
                    }

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
