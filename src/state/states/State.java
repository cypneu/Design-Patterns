package state.states;


import state.Parser;

public interface State {
    void follow(Parser parser, char letter);

    boolean isAcceptingState();
}
