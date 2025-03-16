package com.phasmidsoftware.dsaipg.adt.pq;

import java.util.*;

public class FibonacciHeap {
    private Node minNode;
    private int num;

    public int size() {
        return num;
    }

    public boolean isEmpty() {
        return num == 0;
    }

    private static class Node {
        int key;
        int degree;
        Node next;
        Node prev;
        Node child;
        Node parent;

        Node(int key) {
            this.key = key;
            this.degree = 0;
            this.next = this;
            this.prev = this;
            this.child = null;
            this.parent = null;
        }
    }

    public void insert(int key) {
        Node x = new Node(key);
        if (minNode == null) {
            minNode = x;
        } else {
            x.next = minNode.next;
            x.prev = minNode;
            minNode.next.prev = x;
            minNode.next = x;

            if (x.key < minNode.key) {
                minNode = x;
            }
        }
        num++;
    }

    public Node extractMin() {
        if (minNode != null) {
            if (minNode.child != null) {
                Node child = minNode.child;
//                System.out.println("child: " + minNode.child.key);
                do {
                    Node next = child.next;
//                    System.out.println("Moving child: " + child.key);
                    child.prev.next = child.next;
                    child.next.prev = child.prev;

                    child.next = minNode;
                    child.prev = minNode.prev;
                    minNode.prev.next = child;
                    minNode.prev = child;
//                    System.out.println("Root List: " + minNode.key + " " + minNode.next.key + " " + minNode.prev.key);

                    child.parent = null;
                    if(child == next) {
                        break;
                    }
                    child = next;
                } while (child != minNode.child);
            }
//            System.out.println("Root List: " + minNode.key + " " + minNode.next.key + " " + minNode.prev.key);

            minNode.prev.next = minNode.next;
            minNode.next.prev = minNode.prev;

//            System.out.println("Extracted min: " + minNode.next.key);
//            System.out.println("Extracted min: " + minNode.key);
            if (minNode == minNode.next) {
//                System.out.println("null");
                minNode = null;
            } else {
                minNode = minNode.next;
                consolidate();
//                System.out.println(minNode.key);
//                System.out.println(minNode.next.key);
//                System.out.println(minNode.prev.key);
            }

            num--;
        }
        return minNode;
    }

    private void consolidate() {
//        System.out.println("Consolidating...");
        int maxDegree = (int) Math.floor(Math.log(num) / Math.log(2)) + 1;
        Node[] A = new Node[maxDegree];

        List<Node> rootNodes = new ArrayList<>();
        Node x = minNode;
        if (x != null) {
            do {
                rootNodes.add(x);
                x = x.next;
            } while (x != minNode);
        }

        for (Node n : rootNodes) {
            int d = n.degree;
            while (A[d] != null) {
                Node o = A[d];
                if (n.key > o.key) {
                    Node temp = n;
                    n = o;
                    o = temp;
                }
                link(o, n);
                A[d] = null;
                d++;
            }
            A[d] = n;
        }

        minNode = null;
        for (Node n : A) {
            if (n != null) {
                if (minNode == null || n.key < minNode.key) {
                    minNode = n;
                }
            }
        }
    }

    private void link(Node y, Node x) {
//        System.out.println("Linking " + y.key + " to " + x.key);
        y.prev.next = y.next;
        y.next.prev = y.prev;

        y.parent = x;
        if (x.child == null) {
            x.child = y;
            y.next = y;
            y.prev = y;
        } else {
            y.next = x.child;
            y.prev = x.child.prev;
            x.child.prev.next = y;
            x.child.prev = y;
        }
        x.degree++;
    }
}
