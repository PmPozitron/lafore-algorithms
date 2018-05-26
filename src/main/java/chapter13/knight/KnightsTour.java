package chapter13.knight;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public class KnightsTour {

    private static final int[][] KNIGHT_MOVES = new int[][]{new int[]{-1, -2}, new int[]{1, -2}, new int[]{-2, -1}, new int[]{2, -1}, new int[]{-2, 1}, new int[]{2, 1}, new int[]{-1, 2}, new int[]{1, 2}};
    private final static Vertex MARKER = new Vertex(-1);
    private int boardSize;
    private int[][] board;
    private int[] vector;
    private Graph graph;

    public KnightsTour() {
        this.boardSize = 5;
        this.board = new int[boardSize][boardSize];
        this.vector = new int[boardSize * boardSize];
        this.graph = new Graph();
    }


    public static void main(String[] args) {
        new KnightsTour().takeTour();
    }

    public void takeTour() {
        for (int i = 0; i < vector.length; i++) {
            Vertex start = new Vertex(i);
            start.setEdges(createValidDestinations(start));
            graph.addVertex(start);
            LinkedList<Vertex> stack = new LinkedList<Vertex>(start.getEdges());

            while (!stack.isEmpty()) {
                Vertex current = stack.peek();
                if (graph.contains(current)) {
                    stack.poll();
                    graph.removeVertex(current);
                    continue;
                }
                graph.addVertex(current);
                if (graphIsFull()) {
                    System.out.println("SUCCESS  " + graph);
                    break;
                }
                Set<Vertex> moves = createValidDestinations(current);
                current.setEdges(moves);
                if (moves.isEmpty()) {
                    stack.poll();
                    graph.removeVertex(current);
                } else {
                    for (Vertex move : moves){
                        stack.addFirst(move);
                    }
                }
            }
            graph = new Graph();
        }

    }


    public Set<Vertex> createValidDestinations(Vertex vertex) {
        HashSet<Vertex> result = new HashSet<Vertex>();

        int abscissa = vertex.getNumber() % boardSize;
        int ordinate = vertex.getNumber() / boardSize;

        for (int[] move : KNIGHT_MOVES) {
            if (abscissa + move[0] < 0) {
                continue;
            } else if (abscissa + move[0] >= boardSize) {
                continue;
            } else if (ordinate + move[1] < 0) {
                continue;
            } else if (ordinate + move[1] >= boardSize) {
                continue;
            } else {
                Vertex newVertex = new Vertex(abscissa + move[0] + (ordinate + move[1]) * boardSize);
                if (graph.contains(newVertex) || vector[newVertex.getNumber()] == 1) {
                    continue;
                } else {
//                System.out.printf("Possible move FROM %d, x:%d, y:%d is TO %d, x:%d, y:%d\n",
//                        vertex.getNumber(), abscissa, ordinate, abscissa + move[0] + (ordinate + move[1]) * boardSize, abscissa + move[0], ordinate + move[1]);
                    result.add(newVertex);
                }
            }
        }

//        System.out.println(vertex + " " + result + " " + graph);
        return result;
    }

    public boolean graphIsFull() {
        return graph.getVertices().size() == vector.length;
    }

    static class Graph {
        private LinkedList<Vertex> vertices;

        public Graph() {
            this.vertices = new LinkedList<Vertex>();
        }

        public LinkedList<Vertex> getVertices() {
            return vertices;
        }

        public void setVertices(LinkedList<Vertex> vertices) {
            this.vertices = vertices;
        }

        public Vertex peek() {
            return vertices.peek();
        }

        public Vertex poll() {
            return vertices.poll();
        }

        public void addVertex(Vertex toAdd) {
            this.vertices.addLast(toAdd);
        }

        public boolean contains(Vertex toCheck) {
            return this.vertices.contains(toCheck);
        }

        public boolean removeVertex(Vertex toRemove) {
            return this.vertices.remove(toRemove);
        }

        public boolean isEmpty() {
            return this.vertices.isEmpty();
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();
            for (Vertex vertex : vertices) {
                result.append(vertex.toString()).append(",");
            }
            result.append(" size is ").append(this.vertices.size());
            return result.toString();
        }
    }

    static class Vertex {
        private Integer number;
        private Set<Vertex> edges;

        public Vertex() {
            this.edges = new LinkedHashSet<Vertex>();
        }

        public Vertex(Integer number) {
            this.number = number;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        public Set<Vertex> getEdges() {
            return edges;
        }

        public void setEdges(Set<Vertex> edges) {
            this.edges = edges;
        }

        public void addEdge(Vertex to) {
            this.edges.add(to);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Vertex)) return false;

            Vertex vertex = (Vertex) o;

            return number != null ? number.equals(vertex.number) : vertex.number == null;
        }

        @Override
        public int hashCode() {
            return number != null ? number.hashCode() : 0;
        }

        @Override
        public String toString() {
            return number.toString();
        }
    }
}

