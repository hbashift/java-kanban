package manager.history;

import task.Task;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private static class Node {
        Task value;
        Node prev;
        Node next;

        private Node(Task task, Node next, Node prev) {
            this.value = task;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public String toString() {
            return "Node:{" + value.toString() + "}";
        }
    }

    private Node head;
    private Node last;
    private int size;
    private final Map<Integer, Node> nodeMap = new HashMap<>();

    private List<Integer> getTasks() {
        List<Integer> taskList = new ArrayList<>();
        Node newHead = head;
        if (newHead != null){
            while (newHead.next != null) {
                taskList.add(newHead.value.getId());
                newHead = newHead.next;
            }
            taskList.add(newHead.value.getId());
        }

        return taskList;
    }

    public void linkLast(Task task) {
        final Node l = last;
        final Node newNode = new Node(task, null, l);
        last = newNode;
        if (l == null)
            head = newNode;
        else
            l.next = newNode;

        size += 1;
    }

    public Task removeFirst() {
        Task task;

        if (getSize() == 0) {
            throw new NoSuchElementException("History is empty");
        }

        if (head == last) {
            task = head.value;
            last = null;
        } else {
            task = head.next.prev.value;
            head.next.prev = null;
        }

        head = head.next;
        size -= 1;

        return task;
    }

    public Task removeLast() {
        Task task;

        if (getSize() == 0) {
            throw new NoSuchElementException("History is empty");
        }

        if (head == last) {
            task = head.value;
            head = null;
        } else {
            task = last.prev.value;
            last.prev.next = null;
        }

        last = last.prev;
        size -= 1;

        return task;
    }

    @Override
    public Task remove(int id) {
        Task task = null;

        if (nodeMap.containsKey(id)) {
            final Node node = nodeMap.remove(id);
            if (node.prev == null) {
                task = removeFirst();
            } else if (node.next == null) {
                task = removeLast();
            } else {
                node.next.prev = node.prev;
                node.prev.next = node.next;
                size -= 1;
            }
        }

        return task;
    }

    @Override
    public List<Integer> getHistory() {
        return getTasks();
    }

    @Override
    public void add(Task task) {
        if (nodeMap.containsKey(task.getId())) {
            remove(task.getId());
        }

        if (checkSize()) {
            removeFirst();
        }

        linkLast(task);
        nodeMap.put(task.getId(), last);
    }

    @Override
    public void removeAll() {
        nodeMap.clear();
        Node newNode = head;
        while (newNode.next != null) {
            head = null;
            newNode = newNode.next;
        }
    }

    @Override
    public void printAll() {
        for (Map.Entry<Integer, Node> entry : nodeMap.entrySet()) {
            System.out.println(entry.getValue().value);
        }
    }

    private boolean checkSize() {
        return size == 10;
    }

    public int getSize() {
        return size;
    }
}
