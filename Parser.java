class Parser {
    private Lexer lexer;
    private Token currentToken;

    Parser(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.nextToken();
    }

    private void eat(Token.Type type) {
        if (currentToken.type == type) {
            currentToken = lexer.nextToken();
        } else {
            throw new RuntimeException("Unexpected token: " + currentToken);
        }
    }

    double expression() {
        double result = term();
        while (currentToken.type == Token.Type.PLUS || currentToken.type == Token.Type.MINUS) {
            if (currentToken.type == Token.Type.PLUS) {
                eat(Token.Type.PLUS);
                result += term();
            } else if (currentToken.type == Token.Type.MINUS) {
                eat(Token.Type.MINUS);
                result -= term();
            }
        }
        return result;
    }

    double term() {
        double result = factor();
        while (currentToken.type == Token.Type.MULTIPLY || currentToken.type == Token.Type.DIVIDE) {
            if (currentToken.type == Token.Type.MULTIPLY) {
                eat(Token.Type.MULTIPLY);
                result *= factor();
            } else if (currentToken.type == Token.Type.DIVIDE) {
                eat(Token.Type.DIVIDE);
                result /= factor();
            }
        }
        return result;
    }

    double factor() {
        double result;
        if (currentToken.type == Token.Type.NUMBER) {
            result = Double.parseDouble(currentToken.text);
            eat(Token.Type.NUMBER);
        } else if (currentToken.type == Token.Type.LPAREN) {
            eat(Token.Type.LPAREN);
            result = expression();
            eat(Token.Type.RPAREN);
        } else {
            throw new RuntimeException("Unexpected token: " + currentToken);
        }
        return result;
    }
}

//<expression> ::= <term> { ("+" | "-") <term> }
//<term> ::= <factor> { ("*" | "/") <factor> }
//<factor> ::= <number> | "(" <expression> ")"
//<number> ::= [0-9]+(\.[0-9]+)?