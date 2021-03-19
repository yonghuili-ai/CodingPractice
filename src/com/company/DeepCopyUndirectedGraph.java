package com.company;

import java.util.*;

public class DeepCopyUndirectedGraph {
    public static void main(String[] args) {
        DeepCopyUndirectedGraph s = new DeepCopyUndirectedGraph();
        GraphNode node1 = new GraphNode(1);
        GraphNode node2 = new GraphNode(2);
        GraphNode node3 = new GraphNode(3);
        GraphNode node4 = new GraphNode(4);
        GraphNode node5 = new GraphNode(5);
        node1.neighbors.add(node2);
        node2.neighbors.add(node3);
        node4.neighbors.add(node5);
        node5.neighbors.add(node4);
        node1.neighbors.add(node5);
        List<GraphNode> graph = new ArrayList<>();
        graph.add(node1);
        graph.add(node2);
        graph.add(node3);
        graph.add(node4);
        graph.add(node5);
        printGraph(graph);
        // 1 --> 2  5
        // 2 --> 3
        // 3 -->
        // 4 --> 5
        // 5 --> 4

        System.out.println();
        List<GraphNode> newGraph = s.copy(graph);
        printGraph(newGraph);
    }

    private static void printGraph(List<GraphNode> graph) {
        for (GraphNode node : graph) {
            System.out.print(node.key + " --> ");
            for (GraphNode nei : node.neighbors) {
                System.out.print(nei.key + "  ");
            }
            System.out.println();
        }
    }

    // Method 1: Breadth First Search
    // Time O(V+E)
    // Space O(V)
    public List<GraphNode> copy(List<GraphNode> graph) {
        if (graph == null) {
            return null;
        }
        List<GraphNode> newNodes = new ArrayList<>();
        Deque<GraphNode> queue = new ArrayDeque<>();
        Map<GraphNode, GraphNode> map = new HashMap<>();
        for (GraphNode node : graph) {
            // copy node
            map.put(node, new GraphNode(node.key));
            queue.offer(node);
            newNodes.add(map.get(node));
        }
        while (!queue.isEmpty()) {
            GraphNode old = queue.poll();
            for (GraphNode neighbor : old.neighbors) {
                // copy node
                if (map.get(neighbor) == null) {
                    map.put(neighbor, new GraphNode(neighbor.key));
                    queue.offer(neighbor);
                }
                // copy edge
                map.get(old).neighbors.add(map.get(neighbor));
            }
        }
        return newNodes;
    }

    // Another implementation of BFS:
    // Time O(V+E)
    // Space O(V)
    public List<GraphNode> copyII(List<GraphNode> graph) {
        if (graph == null) {
            return null;
        }
        List<GraphNode> newGraph = new ArrayList<>();
        Map<GraphNode, GraphNode> map = new HashMap<>();
        for (GraphNode node : graph) {
            GraphNode copyNode = getOrCreate(node, map);
            BFS(node, map);
            newGraph.add(copyNode);
        }
        return newGraph;
    }

    private void BFS(GraphNode node, Map<GraphNode, GraphNode> map) {
        Deque<GraphNode> queue = new ArrayDeque<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            GraphNode curr = queue.poll();
            // copy node
            GraphNode copyCurr = getOrCreate(curr, map);
            for (GraphNode nei : curr.neighbors) {
                // copy node
                GraphNode copyNei = getOrCreate(nei, map);
                // copy edge
                copyCurr.neighbors.add(copyNei);
            }
        }
    }

    private GraphNode getOrCreate(GraphNode curr, Map<GraphNode, GraphNode> map) {
        if (!map.containsKey(curr)) {
            map.put(curr, new GraphNode(curr.key));
        }
        return map.get(curr);
    }

    // Assumptions: The given graph is not null
    // Method 2: Recursion(DFS)
    // Recursive manner. Use map to store whether a node has been copied before
    // For every single recursion function call, we make a copy of the input node,
    // and leave all other copies of the successors to the recursion functions.
    // Time O(V+E)
    // Space O(V)
    public List<GraphNode> copyIII(List<GraphNode> graph) {
        if (graph == null) {
            return null;
        }
        Map<GraphNode, GraphNode> map = new HashMap<>();
        for (GraphNode node : graph) {
            if (!map.containsKey(node)) {
                // copy node
                map.put(node, new GraphNode(node.key));
                DFS(node, map);
            }
        }
        return new ArrayList<GraphNode>(map.values());
    }

    private void DFS(GraphNode node, Map<GraphNode, GraphNode> map) {
        GraphNode copyNode = map.get(node);
        for (GraphNode nei : node.neighbors) {
            if (!map.containsKey(nei)) {
                // copy node
                GraphNode copyNei = new GraphNode(nei.key);
                map.put(nei, copyNei);
                DFS(nei, map);
            }
            // copy edge
            copyNode.neighbors.add(map.get(nei));
        }
    }

    // DFS for connected graph:
    // Time O(V+E)
    // Space O(V)
    public GraphNode cloneGraph(GraphNode node, Map<GraphNode, GraphNode> map) {
        if (node == null) {
            return null;
        }
        if (map.containsKey(node)) {
            return map.get(node);
        }
        GraphNode copyNode = new GraphNode(node.key);
        map.put(node, copyNode);
        for (GraphNode neighbor : node.neighbors) {
            // copy node
            GraphNode copyNei = cloneGraph(neighbor, map);
            // copy edge
            copyNode.neighbors.add(copyNei);
        }
        return copyNode;
    }
}