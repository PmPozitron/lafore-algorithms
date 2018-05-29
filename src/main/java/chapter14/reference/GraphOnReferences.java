package chapter14.reference;

import java.util.LinkedHashSet;
import java.util.Set;

public class GraphOnReferences {

    public static void main(String[] args) {
        new GraphOnReferences().testDrive();
    }

    private void testDrive() {
        Graph graph = new Graph();
        graph.addVertex(new Vertex('A'));
        graph.addVertex(new Vertex('B'));
        graph.addVertex(new Vertex('C'));
        graph.addVertex(new Vertex('D'));
        graph.addVertex(new Vertex('E'));

        graph.addEdge('A', new Vertex('B'), 5);
        graph.addEdge('A', new Vertex('c'), 15);
        graph.addEdge('B', new Vertex('E'), 50);

        System.out.println(graph);
    }

    static class Vertex {
        private char symbol;
        private Set<Edge> edges;

        public Vertex() {
            this.edges = new LinkedHashSet<Edge>();
        }

        public Vertex(char symbol) {
            this();
            this.symbol = symbol;
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

        public void addEdge(Edge newEdge) {
            this.edges.add(newEdge);
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
            return String.valueOf(getSymbol());
        }
    }

    static class Edge {
        private Vertex end;
        private int weight;

        public Edge() {
        }

        public Edge(Vertex end, int weight) {
            this.end = end;
            this.weight = weight;
        }

        public Vertex getEnd() {
            return end;
        }

        public void setEnd(Vertex end) {
            this.end = end;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Edge)) return false;

            Edge edge = (Edge) o;

            return end != null ? end.equals(edge.end) : edge.end == null;
        }

        @Override
        public int hashCode() {
            return end != null ? end.hashCode() : 0;
        }

        @Override
        public String toString() {
            return " to " + end + " with weight " + weight;
        }
    }

    static class Graph {
        private Set<Vertex> vertices;

        public Graph() {
            this.vertices = new LinkedHashSet<Vertex>();
        }

        public Set<Vertex> getVertices() {
            return vertices;
        }

        public void setVertices(Set<Vertex> vertices) {
            this.vertices = vertices;
        }

        public boolean addVertex(Vertex newVertex) {
            return this.vertices.add(newVertex);
        }

        public void addEdge(Vertex from, Vertex to, int weight) {
            Edge newEdge = new Edge(to, weight);
            for (Vertex vertex : this.vertices) {
                if (vertex.equals(from)) {
                    vertex.addEdge(newEdge);
                }
            }
        }

        public void addEdge(char from, Vertex to, int weight) {
            Edge newEdge = new Edge(to, weight);
            for (Vertex vertex : this.vertices) {
                if (vertex.getSymbol() == from) {
                    vertex.addEdge(newEdge);
                }
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("Graph with vertices:\n");
            for (Vertex vertex : vertices) {
                sb.append("vertex ").append(vertex.getSymbol()).append(" with edges:\n");
                for (Edge edge : vertex.getEdges()) {
                    sb.append("\tto ").append(edge.getEnd()).append(" with weight ").append(edge.getWeight()).append("\n");
                }
            }
            return sb.toString();
        }
    }
}
