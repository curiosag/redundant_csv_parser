package redundant_csv_parser;

public class Parser {
    private static final double[][] emptyRows = new double[0][];
    private static final double[] emptyRow = new double[0];

    private final Scanner sc;

    public Parser(String input) {
        this.sc = new Scanner(input.toCharArray());
    }

    public double[][] parse() {
        return parseRows();
    }

    private double[][] parseRows() {
        double[] row = parseRow();
        if(row.length == 0)
        {
            return emptyRows;
        }
        return prepend(row, parseRows());
    }

    private double[] parseRow() {
        Symbol s = nextSymbol();
        if (s.type == SymType.EOF || s.type == SymType.ROW_SEPARATOR) {
            return emptyRow;
        }

        return prepend(Double.parseDouble(String.valueOf(s.value)), parseRow());
    }

    private Symbol nextSymbol() {
        Symbol s = sc.nextSymbol();
        if (s.type == SymType.ERROR) {
            throw new IllegalStateException(String.valueOf(s.value));
        }
        return s;
    }

    private double[] prepend(double i, double[] ints) {
        double[] result = new double[ints.length + 1];
        result[0] = i;
        if (ints.length > 0) {
            System.arraycopy(ints, 0, result, 1, ints.length);
        }
        return result;
    }

    private double[][] prepend(double[] arr, double[][] arrays) {
        double[][] result = new double[arrays.length + 1][];
        result[0] = arr;
        if (arrays.length > 0) {
            System.arraycopy(arrays, 0, result, 1, arrays.length);
        }
        return result;
    }
}
