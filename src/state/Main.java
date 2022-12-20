package state;

import state.states.Start;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Start start = new Start();
        Parser parser = new Parser(start);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Type a word to check if grammar accepts it or 'exit': ");
            String word = scanner.nextLine();
            if (word.equals("exit")) break;

            System.out.println("\033[0;1m" + "Does grammar accepts '" + word + "'? " + (parser.parse(word) ? "\u001B[32m" + "YES" + "\u001B[0m\n" : "\u001B[31m" + "NO" + "\u001B[0m\n"));
        }
    }
}
