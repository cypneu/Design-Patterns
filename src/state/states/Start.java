package state.states;


import state.Parser;

public class Start implements State {
    private static final boolean acceptingState = false;

    @Override
    public void follow(Parser parser, char letter) {
        switch (letter) {
            case 'a' -> parser.changeState(new A());
            case 'b' -> parser.changeState(new C());
            default -> parser.reject();
        }
    }

    @Override
    public boolean isAcceptingState() {
        return acceptingState;
    }
}
