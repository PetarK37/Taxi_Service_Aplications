package List;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class LinkedList<T> implements Iterable<T>{
    private ListNode<T> head;
    private ListNode<T> tail;
    private int listSize;

    ListNode<T> getHead() {
        return head;
    }

    public void addFirst(T element){

        ListNode<T> newNode = new ListNode<T>(element, head , null);

        if(listSize == 0){
            head = newNode;
            tail = head;
        }

        head.setPrevious(newNode);
        head = newNode;

        listSize++;
    }

    public void add(T element){

        ListNode<T> newNode = new ListNode<T>(element);

        if(listSize == 0){
            head = tail = newNode;
        }
        else{
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        }
        listSize++;
    }

    public void add(int index, T element){
        if(index == 0 && listSize == 0){
            addFirst(element);
            return;
        }

        if(index < 0 || index >= listSize){
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + listSize);
        }

        if(index == listSize - 1){
            add(element);
            return;
        }

        ListNode<T> currentNode = head;

        for (int i = 0; i < index - 1; i++) {
            currentNode = currentNode.getNext();
        }

        ListNode<T> newNode = new ListNode<T>(element, currentNode.getNext(),currentNode);
        currentNode.setNext(newNode);
        listSize++;
    }

    public T getFirst(){
        if(listSize == 0){
            throw new RuntimeException("List is empty!");
        }
        return head.getElement();
    }

    public T getLast(){
        if(listSize == 0){
            throw new RuntimeException("List is empty!");
        }
        return tail.getElement();
    }

    public T get(int index){
        if(index < 0 || index >= listSize){
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + listSize);
        }

        ListNode<T> currentNode = head;

        for (int i = 0; i < index ; i++) {
            currentNode = currentNode.getNext();
        }

        return currentNode.getElement();
    }

    public int indexOf(T element){
        if(listSize == 0){
            throw new RuntimeException("List is empty!");
        } else {

            ListNode<T> currentNode = head;

            int index = 0;

            for (int i = 0; i < listSize; i++) {
                if (currentNode.getElement().equals(element)) {
                    index = i;
                }
                currentNode = currentNode.getNext();
            }
            return index;
        }
    }

    public void set(int index, T element){
        if(listSize == 0){
            throw new RuntimeException("List is empty!");
        }else if(index < 0 || index >= listSize){
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + listSize);
        }

        ListNode <T> currentNode = head;

        for (int i = 0; i < index ; i++) {
            currentNode = currentNode.getNext();
        }
        currentNode.setElement(element);
    }

    public void removeFirst(){
        if(listSize == 0){
            throw new RuntimeException("List is empty!");
        }

        head = head.getNext();
        head.setPrevious(null);
        listSize--;

        if (listSize == 0){
            tail = null;
        }

    }

    public void removeLast(){
        if(listSize == 0){
            throw new RuntimeException("List is empty!");
        }

        ListNode<T> previousNode = null;
        ListNode<T> currentNode = head;

        while (currentNode.getNext() != null){
            previousNode = currentNode;
            currentNode = currentNode.getNext();
        }
        if (previousNode == null){
            head = tail = null;
        }else{
            previousNode.setNext(null);
            tail = previousNode;
        }

        listSize--;
    }

    public void remove(int index){

        if(listSize == 0){
            throw new RuntimeException("List is empty!");
        }else if(index < 0 || index >= listSize){
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + listSize);
        }

        ListNode<T> currentNode = head;
        ListNode<T> previousNode = null;

        for (int i = 0; i < index; i++) {
            previousNode = currentNode;
            currentNode = currentNode.getNext();
        }

        previousNode.setNext(currentNode.getNext());
        currentNode = null;

        listSize--;

    }

    public void remove(T element){
        if(listSize == 0){
            throw new RuntimeException("List is empty!");
        }
        else if (head.getElement() == element){
          removeFirst();

          listSize--;
            return;
        }
        else if (element.equals(tail.getElement())){
            removeLast();

            listSize--;
            return;
        }
        ListNode<T> currentNode = head;

        while(currentNode.getNext() != null){
            if (currentNode.getElement().equals(element)){
                ListNode<T> previous = currentNode.getPrevious();
                ListNode<T> next = currentNode.getNext();

                previous.setNext(next);
                next.setPrevious(previous);

                listSize--;
                return;
            }
            currentNode = currentNode.getNext();
        }
        throw new NoSuchElementException("Element doesnt exist");
    }

    public void clear(){
        if(listSize == 0){
            throw new RuntimeException("List is empty!");
        }
        while (listSize > 0){
            removeFirst();
        }

    }

    public int size(){
        return listSize;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator<T>(this);
    }

}
