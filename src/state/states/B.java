package state.states;


import state.Parser;

public class B implements State {
    private static final boolean acceptingState = true;

    @Override
    public void follow(Parser parser, char letter) {
        switch (letter) {
            case 'a' -> parser.changeState(new Ba());
            case 'b' -> parser.changeState(this);
            default -> parser.reject();
        }
    }

    @Override
    public boolean isAcceptingState() {
        return acceptingState;
    }
}
