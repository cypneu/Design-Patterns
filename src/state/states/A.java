package state.states;


import state.Parser;

public class A implements State {
    private static final boolean acceptingState = true;

    @Override
    public void follow(Parser parser, char letter) {
        switch (letter) {
            case 'a' -> parser.changeState(this);
            case 'b' -> parser.changeState(new B());
            default -> parser.reject();
        }
    }

    @Override
    public boolean isAcceptingState() {
        return acceptingState;
    }
}
