package http.responders.ttt;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class WebDisplayTest {

    private WebDisplay display;

    @Before
    public void setUp() throws Exception {
        display = new WebDisplay();
    }

    @Test
    public void hasDefaultGameChoiceIfNotSet() {
        WebDisplay display = new WebDisplay();
        assertThat(display.getGameChoice(), is("1"));
    }

    @Test
    public void retrievesGameChoice() {
        display.setGameChoice("1");
        assertThat(display.getGameChoice(), is("1"));
    }

    @Test
    public void retrievesOnlyAValidGameChoice() {
        display.setGameChoice("invalid-input");
        assertThat(display.getGameChoice(), is("invalid"));
    }

    @Test
    public void hasDefaultBoardChoice() {
        WebDisplay display = new WebDisplay();
        assertThat(display.getBoardChoice(), is(3));
    }

    @Test
    public void retrievesSelectedBoardChoice() {
        display.setBoardChoice(4);
        assertThat(display.getBoardChoice(), is(4));
    }

    @Test
    public void retrievesOnlyValidBoardSize() {
        display.setBoardChoice(10);
        assertThat(display.getBoardChoice(), is(-1));
    }

    @Test
    public void setsNextMoveToBeMade() {
        display.setMove(1);
        assertThat(display.getMove(), is(1));
    }

    @Test
    public void respondsWhenItHasMove() {
        display.setMove(1);
        assertThat(display.hasMove(), is(true));
    }

    @Test
    public void respondsWhenItHasNoMove() {
        WebDisplay display = new WebDisplay();
        assertThat(display.hasMove(), is(false));
    }

    @Test
    public void respondsWhetherItHasMoveOrNot() {
        display.setMove(1);
        display.getMove();
        assertThat(display.hasMove(), is(false));
    }
}