package state;

import state.states.State;

public class Parser {
    private final State initialState;
    private State state;
    private boolean rejected;

    public Parser(State initialState) {
        this.initialState = initialState;
        this.state = initialState;
    }

    public void changeState(State newState) {
        this.state = newState;
    }

    public void reject() {
        this.rejected = true;
    }

    public boolean parse(String word) {
        this.rejected = false;
        this.state = initialState;

        for (char letter : word.toCharArray()) {
            this.follow(letter);
            if (this.rejected) return false;
        }

        return this.state.isAcceptingState();
    }

    public void follow(char letter) {
        this.state.follow(this, letter);
    }
}
