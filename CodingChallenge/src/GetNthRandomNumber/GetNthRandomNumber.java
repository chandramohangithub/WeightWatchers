package GetNthRandomNumber;

import java.util.*;

public class GetNthRandomNumber {

    private Random random;
    private int totalNumberOfRandomNumbers;
    private LinkedList<Node> randomNumbersLinkedList;

    // Constructor for the class to generate 500 random numbers and to get Nth random number.
    public GetNthRandomNumber(int totalNumberOfRandomNumbers){
        random = new Random();
        this.totalNumberOfRandomNumbers = totalNumberOfRandomNumbers;
        randomNumbersLinkedList = new LinkedList<Node>();
    }

    // Method to get a random number.
    private int getNextRandomNumber(){
        return(random.nextInt());
    }

    // TIME COMPLEXITY for the linear insertion of random numbers is O(n2)
    // Method to insert a random number in to the linkedlist of randomNumbers.
    private void insertRandomNumber(int randomNumber){

        Node newNode = new Node(randomNumber, null);
        if(randomNumbersLinkedList.size() <= 0 ){
            randomNumbersLinkedList.addFirst(newNode);
        }else if(randomNumber >= randomNumbersLinkedList.getLast().getNumber()){
            randomNumbersLinkedList.addLast(newNode);
        }else {

            for(int i =0; i< randomNumbersLinkedList.size(); i++) {
                if (randomNumbersLinkedList.get(i).getNumber() > randomNumber) {
                    randomNumbersLinkedList.add(i, newNode);
                    break;
                }
            }
        }
    }

    // TIME COMPLEXITY for searching the index to insert random numbers using binary search is O(nlgn)
    // Method to find the index to insert random number in to the linkedList of random numbers using binary search
    private void insertRandomNumberUsingBinarySearch(int randomNumber){

    int begin =0;
    int end = randomNumbersLinkedList.size()-1;
    int mid = (begin+end)/2;
    Node newRandomNumberNode = new Node(randomNumber, null);

        if(randomNumbersLinkedList.size() <= 0 || randomNumber < randomNumbersLinkedList.get(0).getNumber()){
            randomNumbersLinkedList.addFirst(newRandomNumberNode);
        }else if(randomNumber >= randomNumbersLinkedList.getLast().getNumber()){
            randomNumbersLinkedList.addLast(newRandomNumberNode);
        }else {

            while (begin < end) {
                mid = (begin + end )/2;
                //System.out.println("Begin: " + begin + "Mid: " + mid + " End: " + end);
                if (randomNumbersLinkedList.get(mid).getNumber() == randomNumber) {
                    randomNumbersLinkedList.add(mid, newRandomNumberNode);
                    //System.out.println("Inserted randomnumber: " + randomNumber + "at: " + mid);
                    break;
                } else if (randomNumber < randomNumbersLinkedList.get(mid).getNumber()) {
                    if (randomNumber > randomNumbersLinkedList.get(mid - 1 <0?mid:mid-1).getNumber()) {
                        randomNumbersLinkedList.add(mid - 1 <0?mid+1:mid-1+1, newRandomNumberNode);
                        //System.out.println("Inserted randomnumber: " + randomNumber + "at: " + mid);
                        break;
                    } else {
                        end = mid - 1;
                        continue;
                    }
                } else if (randomNumber > randomNumbersLinkedList.get(mid).getNumber()) {
                    if (randomNumber < randomNumbersLinkedList.get(mid + 1>randomNumbersLinkedList.size()-1?mid:mid+1).getNumber()) {
                        randomNumbersLinkedList.add(mid + 1>randomNumbersLinkedList.size()-1?mid:mid+1, newRandomNumberNode);
                        //System.out.println("Inserted randomnumber: " + randomNumber + "at: " + mid);
                        break;
                    } else {
                        begin = mid + 1;
                        continue;
                    }
                }
            }
        }
    }

    // Method to create linked list of random numbers.
    public void createRandomNumberList(){

        for(int i =0; i< totalNumberOfRandomNumbers ;i++){
            int currentRandomNumber = getNextRandomNumber();
            //insertRandomNumber(currentRandomNumber); // Using linear search to find the index to insert new random number is O(n2)
            insertRandomNumberUsingBinarySearch(currentRandomNumber);// Using binary search to find the index to insert new random number is O(nlgn)
            //System.out.println("Inserted Random Number: " + currentRandomNumber);
        }
    }

    // Method to get nth random number from the linkedlist of random numbers.
    public int getNthRandomNumber(int index){

        if(index <=0 && index >=totalNumberOfRandomNumbers){
            return Integer.MIN_VALUE;
        }else{
            return(randomNumbersLinkedList.get(index -1).getNumber());
        }
    }

    // Method to print all random numbers of the linked list of random numbers.
    public void getAllRandomNumbers(){
        for(int i =0; i< randomNumbersLinkedList.size(); i++){
            System.out.println(randomNumbersLinkedList.get(i).getNumber());
        }
    }

    public boolean isAscendingOrder(){
        for(int i =0; i< randomNumbersLinkedList.size()-1; i++){
            if(randomNumbersLinkedList.get(i).getNumber() > randomNumbersLinkedList.get(i+1).getNumber()){
                System.out.println("Random numbers are not in ascending order");
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args){

        int totalNumberOfRandomNumbers = 500;
        GetNthRandomNumber getNthRandomNumber = new GetNthRandomNumber(totalNumberOfRandomNumbers);
        getNthRandomNumber.createRandomNumberList();

        // Get the 100th random number
        int findRandomNumberIndex = 100;
        int foundRandomNumber = getNthRandomNumber.getNthRandomNumber(findRandomNumberIndex);
        System.out.println("RandomNumber for index "+ findRandomNumberIndex + " is " + foundRandomNumber);

        // Get all random numbers in the linkedlist of random numbers
        System.out.println("List of all random numbers are as follows: ");
        getNthRandomNumber.getAllRandomNumbers();

        boolean isAscending = getNthRandomNumber.isAscendingOrder();
        if(isAscending)
            System.out.println("All the numbers in the array are ascending order");

    }
}
