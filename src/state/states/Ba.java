package state.states;


import state.Parser;

public class Ba implements State {
    private static final boolean acceptingState = true;

    @Override
    public void follow(Parser parser, char letter) {
        parser.reject();
    }

    @Override
    public boolean isAcceptingState() {
        return acceptingState;
    }
}
