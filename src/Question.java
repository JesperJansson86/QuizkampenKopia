import java.util.*;

/**
 * Created by NAME HERE
 * Date: 12/11/2020
 * Time: 13:20
 * Project: QuizkampenKopia
 * Copyright: MIT
 **/
public class Question {

    //a string array that represent a couple of  row in a TXT file that's on the server or something
    //Question, answer, option1, option2, option3, option4;
    String[][] question = {
            {"Who's branch is this?", "Lukas", "Lukas", "Jesper", "Pavel", "David"},//hodei was offline on discord
            {"What Los Angeles community is noted for celebrities and mansions?", "Beverly Hills","Nob Hill","Beverly Hills","Chestnut Hill","Bunker Hill"}, //stole form www.q4quiz.com
            {"Substances that have fast-moving particles that are far apart, and have no definite volume nor shape are:","Gases","Gases","Liquids","Solids","Other"}, //stole form www.q4quiz.com ||onLy had 3 options so i added other
    };


    //creates an instance of the class Random
    Random random = new Random();


    public Question(){

        Scanner scan = new Scanner(System.in);

        int questionRand = questionRand(); //holds the question
        Integer[] optionRand = optionRand(); //holds the order of the options

        //prints out the question then the options in the order of the optionRand
        System.out.println(question[questionRand][0] //prints out the question
                        + "\n1: " + question[questionRand][optionRand[0]] //prints out option 1
                        + "\n2: " + question[questionRand][optionRand[1]] //prints out option 2
                        + "\n3: " + question[questionRand][optionRand[2]] //prints out option 3
                        + "\n4: " + question[questionRand][optionRand[3]] //prints out option 4
        );

        int answer = scan.nextInt(); //takes in user input that is hopefully an int

        scanCheck(answer); //Checks user input for correct or wrong answer
    }


    /**
     * Decides witch question in the array that will be chosen.
     * @return returns a number that will later be used to decide witch question will be asked.
     */
    public int questionRand(){

        //random between the Question.length() to decide witch question
        return random.nextInt(question.length);
    }

    /**
     * Decides what order the options will be in.
     * @return returns an array of Integers in an order witch will decide what order the options will be.
     */
    public Integer[] optionRand(){

        Integer[] optionOrder = new Integer[]{2,3,4,5}; // an array with the indexes of the answer options for the question

        List<Integer> shuffle = Arrays.asList(optionOrder); //makes a list called shuffle

        Collections.shuffle(shuffle); //shuffles the list

        Integer[] shuffled = new Integer[shuffle.size()]; //the array that will be sent back but with out any values

        shuffled = shuffle.toArray(shuffled); //gives shuffled the values of shuffle

        return shuffled;

    }



    public void scanCheck(int answer){

        System.out.println(answer + " was the user inputted answer ");

    }














}
