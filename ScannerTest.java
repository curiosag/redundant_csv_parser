package redundant_csv_parser;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static redundant_csv_parser.Scanner.EOF;

public class ScannerTest {

    @Test
    public void test() {

        Assert.assertEquals(list(EOF), scan(""));
        Assert.assertEquals(list(num("0.1")), scan("0.1"));
        Assert.assertEquals(numList("0", "1"), scan("0 1"));

        List<Symbol> expected = numList("0", "1");
        expected.add(Scanner.ROW_SEPARATOR);
        expected.addAll(numList("1", "0"));
        Assert.assertEquals(expected, scan("0 1, 1 0"));
        Assert.assertEquals(expected, scan("0 1,1 0"));

        assertError(scan("a"));
        assertError(scan("0 1,"));
        assertError(scan("0 1,,"));
    }

    private void assertError(List<Symbol> a) {
        a.stream().filter(i -> i.type == SymType.ERROR).findFirst().orElseThrow(IllegalStateException::new);
    }

    private List<Symbol> scan(String value) {
        Scanner s = new Scanner(value.toCharArray());
        List<Symbol> symbols = new ArrayList<>();
        while (!(s.getState() == Scanner.STATE.EOF || s.getState() == Scanner.STATE.ERROR)) {
            symbols.add(s.nextSymbol());
        }
        return symbols;
    }

    private List<Symbol> list(Symbol... symbols) {
        return new ArrayList<>(Arrays.asList(symbols));
    }

    private Symbol num(String value) {
        return new Symbol(SymType.NUMBER, value.toCharArray());
    }

    private List<Symbol> numList(String... values) {
        ArrayList<Symbol> result = new ArrayList<>();
        for (String value : values) {
            result.add(num(value));
        }
        return result;
    }

}