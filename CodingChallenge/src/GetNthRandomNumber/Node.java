package GetNthRandomNumber;

public class Node {

    private int number;
    private Node next;

    public Node(int number, Node next){
        this.number = number;
        this.next = next;
    }

    public int getNumber(){
        return number;
    }

    public Node getNext(){
        return next;
    }
}
