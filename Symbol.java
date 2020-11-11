package redundant_csv_parser;

import java.util.Arrays;
import java.util.Objects;

public class Symbol {
    public final SymType type;
    public final char[] value;

    public Symbol(SymType type, char[] value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol = (Symbol) o;
        return type == symbol.type &&
                Arrays.equals(value, symbol.value);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(type);
        result = 31 * result + Arrays.hashCode(value);
        return result;
    }
}