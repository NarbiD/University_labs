package B7;

public class PartOfSentence {
    private String lexem;
    final static PartOfSentence SPACE = new PartOfSentence(" ");

    PartOfSentence(String lexem) {
        this.lexem = lexem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PartOfSentence that = (PartOfSentence) o;

        return lexem != null ? lexem.equalsIgnoreCase(that.lexem) : that.lexem == null;
    }

    @Override
    public int hashCode() {
        return lexem != null ? lexem.hashCode() : 0;
    }

    @Override
    public String toString() {
        return lexem;
    }
}
