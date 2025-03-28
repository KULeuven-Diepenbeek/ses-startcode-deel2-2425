package ses.candycrush;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import ses.candycrush.board.Board;
import ses.candycrush.board.BoardSize;
import ses.candycrush.board.Position;

import java.lang.reflect.Field;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class Assignment03_Collections_Tests {
    /*************************************************************************
     * DO NOT MODIFY THE CODE OF THESE TESTS!
     * If these tests do not compile/run, your assignment will not be graded.
     ************************************************************************/

    @Test
    public void board_has_map_field() {
        Condition<Field> isMapField = new Condition<>(f -> f.getGenericType().getTypeName().matches("java\\.util\\.Map<ses\\.candycrush\\.board\\.Position, [A-Z][A-Za-z]*>"), "is a map from position to cell");
        assertThat(Board.class.getDeclaredFields()).haveAtLeastOne(isMapField);
    }

    @Test
    public void board_has_reverse_map_field() {
        Condition<Field> isReverseMapField = new Condition<>(f -> f.getGenericType().getTypeName().matches("java\\.util\\.Map<[A-Z][A-Za-z]*, .*ses\\.candycrush\\.board\\.Position.*>"), "is a map from cell to position");
        assertThat(Board.class.getDeclaredFields()).haveAtLeastOne(isReverseMapField);
    }

    interface Cell {
        record A() implements Cell {}
        record B() implements Cell {}
    }

    @Test
    public void get_positions_of_test() {
        var size = new BoardSize(5, 10);
        var board = new Board<>(size);
        var posB1 = new Position(1, 3, size);
        var posB2 = new Position(1, 8, size);
        board.replaceCellAt(posB1, new Cell.B());
        board.replaceCellAt(posB2, new Cell.B());
        board.replaceCellAt(new Position(2, 2, size), new Cell.A());
        board.replaceCellAt(new Position(2, 3, size), new Cell.A());

        var result = board.getPositionsOfElement(new Cell.B());
        // is it a set?
        assertThat(result).isInstanceOf(Set.class);
        // size
        assertThat(result).hasSize(2);
        // does it contain the right elements?
        assertThat(result).containsExactlyInAnyOrder(posB1, posB2);
        // is it unmodifiable?
        assertThatException().isThrownBy(() -> result.add(new Position(2, 2, size)));
    }
}
