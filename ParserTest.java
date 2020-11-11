package redundant_csv_parser;

import org.junit.Assert;
import org.junit.Test;


public class ParserTest {

    @Test
    public void test() {
        Assert.assertEquals(new double[0][], new Parser("").parse());
        Assert.assertEquals(new double[][]{{0.2d, 1d},{1.7d, 0d}}, new Parser("0.2 1, 1.7 ö0").parse());
    }

    @Test(expected = IllegalStateException.class)
    public void testInvalidInput() {
        Assert.assertEquals(new double[][]{{0.2d, 1d},{1.7d, 0d}}, new Parser("0.2 1, 1.7 ä 0").parse());
    }
}