public class SemanticAnalyzer extends MiniLangBaseVisitor<Void> {

    private final SymbolTable symbolTable = new SymbolTable();
    
    private String getExpressionType(MiniLangParser.ExpressionContext ctx) {

        if (ctx.INT() != null)
            return "int";

        if (ctx.FLOAT() != null)
            return "float";

        if (ctx.STRING() != null)
            return "string";

        if (ctx.BOOL() != null)
            return "bool";

        if (ctx.ID() != null) {

            VariableInfo variable = symbolTable.get(ctx.ID().getText());

            if (variable == null) {
                throw new RuntimeException(
                        "Error semántico: la variable '" +
                        ctx.ID().getText() +
                        "' no fue declarada.");
            }

            return variable.getType();
        }
        
        if (ctx.getChildCount() == 3) {

            String leftType = getExpressionType(ctx.expression(0));
            String rightType = getExpressionType(ctx.expression(1));

            if (leftType.equals(rightType)) {
                return leftType;
            }
        }

        return "unknown";
    }

    @Override
    public Void visitVariableDeclaration(MiniLangParser.VariableDeclarationContext ctx) {

        String type = ctx.type().getText();
        String name = ctx.ID().getText();

        if (symbolTable.exists(name)) {
            throw new RuntimeException(
                    "Error semántico: la variable '" + name + "' ya fue declarada.");
        }

        String expressionType = getExpressionType(ctx.expression());

        if (!type.equals(expressionType)) {
            throw new RuntimeException(
                    "Error semántico: no se puede asignar un "
                            + expressionType
                            + " a una variable "
                            + type + ".");
        }

        symbolTable.declare(name, type, null);

        return null;
    }
    
    @Override
    public Void visitAssignment(MiniLangParser.AssignmentContext ctx) {

        String name = ctx.ID().getText();

        if (!symbolTable.exists(name)) {
            throw new RuntimeException(
                    "Error semántico: la variable '" + name + "' no fue declarada.");
        }

        String variableType = symbolTable.get(name).getType();
        String expressionType = getExpressionType(ctx.expression());

        if (!variableType.equals(expressionType)) {
            throw new RuntimeException(
                    "Error semántico: no se puede asignar un "
                            + expressionType
                            + " a una variable "
                            + variableType + ".");
        }

        return null;
    }
    
    @Override
    public Void visitExpression(MiniLangParser.ExpressionContext ctx) {

        if (ctx.ID() != null) {

            String name = ctx.ID().getText();

            if (!symbolTable.exists(name)) {
                throw new RuntimeException(
                        "Error semántico: la variable '" + name + "' no fue declarada.");
            }
        }

        if (ctx.getChildCount() == 3) {

            String operator = ctx.getChild(1).getText();

            if (operator.equals("/")) {

                String right = ctx.expression(1).getText();

                if (right.equals("0") || right.equals("0.0")) {
                    throw new RuntimeException(
                            "Error semántico: división por cero.");
                }
            }
        }

        return visitChildren(ctx);
    }
    
    @Override
    public Void visitForStatement(MiniLangParser.ForStatementContext ctx) {

        String type = ctx.variableDeclarationFor().type().getText();
        String name = ctx.variableDeclarationFor().ID().getText();

        if (symbolTable.exists(name)) {
            throw new RuntimeException(
                    "Error semántico: la variable '" + name + "' ya fue declarada.");
        }

        symbolTable.declare(name, type, null);

        visit(ctx.expression());
        visit(ctx.block());

        return null;
    }
}