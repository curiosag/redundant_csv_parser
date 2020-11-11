package redundant_csv_parser;

public class Scanner {
    public enum STATE {
        BOF, NUMBER, ROW_SEPARATOR, EOF, ERROR;
    }

    private final char NUL = (char) 0;
    private static final char[] empty = new char[0];
    public static final Symbol EOF = new Symbol(SymType.EOF, empty);
    public static final Symbol ERROR = new Symbol(SymType.ERROR, empty);
    public static final Symbol ROW_SEPARATOR = new Symbol(SymType.ROW_SEPARATOR, ", ".toCharArray());

    private final char[] input;

    private STATE state = STATE.BOF;

    short idx;
    private char[] numeralsRead = empty;

    public Scanner(char[] input) {
        this.input = input;
    }

    public STATE getState() {
        return state;
    }

    private Symbol error = ERROR;

    public Symbol nextSymbol() {
        if (state == STATE.EOF) {
            return EOF;
        }
        if (state == STATE.ERROR) {
            return error;
        }

        while (true) {
            char c = nextChar();

            if (state == STATE.BOF) {
                if (c == NUL) {
                    state = STATE.EOF;
                    return EOF;
                }
                if (isNumeral(c)) {
                    state = STATE.NUMBER;
                    appendNumeral(c);
                    continue;
                }
                return unexpectedCharError();
            }

            if (state == STATE.NUMBER) {
                if (isNumeral(c)) {
                    appendNumeral(c);
                    continue;
                }
                if (c == ' ') {
                    return getNumberSymbol();
                }
                if (c == ',') {
                    state = STATE.ROW_SEPARATOR;
                    return getNumberSymbol();
                }
                if (c == NUL) {
                    state = STATE.EOF;
                    return getNumberSymbol();
                }
                return unexpectedCharError();
            }

            if (state == STATE.ROW_SEPARATOR) {
                if (c == NUL) {
                    return eofError();
                }
                if (c == ' ') {
                    continue;
                }
                if (isNumeral(c)) {
                    state = STATE.NUMBER;
                    appendNumeral(c);
                    return ROW_SEPARATOR;
                }
                return unexpectedCharError();
            }
        }
    }

    private boolean isNumeral(char c) {
        return (c >= '0' && c <= '9') || c == '.';
    }

    private Symbol eofError() {
        state = STATE.ERROR;
        error =  new Symbol(SymType.ERROR, String.format("end of file reached after character %s at position %d ", input[idx - 1], idx - 1).toCharArray());
        return error;
    }

    private Symbol unexpectedCharError() {
        state = STATE.ERROR;
        error = new Symbol(SymType.ERROR, String.format("unexpected character %s at position %d ", input[idx - 1], idx - 1).toCharArray());
        return error;
    }

    private Symbol getNumberSymbol() {
        char[] result = numeralsRead;
        numeralsRead = empty;
        return new Symbol(SymType.NUMBER, result);
    }

    private char nextChar() {
        return idx < input.length ? input[idx++] : NUL;
    }

    private void appendNumeral(char c) {
        char[] augmented = new char[numeralsRead.length + 1];
        augmented[augmented.length - 1] = c;
        if (numeralsRead.length > 0) {
            System.arraycopy(numeralsRead, 0, augmented, 0, numeralsRead.length);
        }
        numeralsRead = augmented;
    }
}
