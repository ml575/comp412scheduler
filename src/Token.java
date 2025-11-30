public class Token {
    public int tokenType;
    public int lexeme;
    public int numVal;

    public Token(int tokenType, int lexeme, int numVal) {
        this.tokenType = tokenType;
        this.lexeme = lexeme;
        this.numVal = numVal;
    }
}