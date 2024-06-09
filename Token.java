import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Token {
    enum Type {
        NUMBER, PLUS, MINUS, MULTIPLY, DIVIDE, LPAREN, RPAREN, EOF
    }

    Type type;
    String text;

    Token(Type type, String text) {
        this.type = type;
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format("Token(%s, %s)", type, text);
    }
}

class Lexer {
    private static final Pattern TOKEN_PATTERN = Pattern.compile(
            "\\s*(?<NUMBER>\\d+(\\.\\d+)?)|(?<PLUS>\\+)|(?<MINUS>-)|(?<MULTIPLY>\\*)|(?<DIVIDE>/)|(?<LPAREN>\\()|(?<RPAREN>\\))");

    private Matcher matcher;
    private List<Token> tokens = new ArrayList<>();
    private int position = 0;

    Lexer(String input) {
        matcher = TOKEN_PATTERN.matcher(input);
        while (matcher.find()) {
            if (matcher.group("NUMBER") != null) {
                tokens.add(new Token(Token.Type.NUMBER, matcher.group("NUMBER")));
            } else if (matcher.group("PLUS") != null) {
                tokens.add(new Token(Token.Type.PLUS, matcher.group("PLUS")));
            } else if (matcher.group("MINUS") != null) {
                tokens.add(new Token(Token.Type.MINUS, matcher.group("MINUS")));
            } else if (matcher.group("MULTIPLY") != null) {
                tokens.add(new Token(Token.Type.MULTIPLY, matcher.group("MULTIPLY")));
            } else if (matcher.group("DIVIDE") != null) {
                tokens.add(new Token(Token.Type.DIVIDE, matcher.group("DIVIDE")));
            } else if (matcher.group("LPAREN") != null) {
                tokens.add(new Token(Token.Type.LPAREN, matcher.group("LPAREN")));
            } else if (matcher.group("RPAREN") != null) {
                tokens.add(new Token(Token.Type.RPAREN, matcher.group("RPAREN")));
            }
        }
        tokens.add(new Token(Token.Type.EOF, ""));
    }

    Token nextToken() {
        return tokens.get(position++);
    }
}