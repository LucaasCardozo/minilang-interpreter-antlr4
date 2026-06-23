public class Interpreter extends MiniLangBaseVisitor<Object> {

    private final SymbolTable symbolTable = new SymbolTable();

    @Override
    public Object visitVariableDeclaration(MiniLangParser.VariableDeclarationContext ctx) {

        String type = ctx.type().getText();
        String name = ctx.ID().getText();

        Object value = visit(ctx.expression());

        symbolTable.declare(name, type, value);

        return null;
    }

    @Override
    public Object visitAssignment(MiniLangParser.AssignmentContext ctx) {

        String name = ctx.ID().getText();

        Object value = visit(ctx.expression());

        symbolTable.assign(name, value);

        return null;
    }

    @Override
    public Object visitPrintStatement(MiniLangParser.PrintStatementContext ctx) {

        Object value = visit(ctx.expression());

        System.out.println(value);

        return null;
    }

    @Override
    public Object visitExpression(MiniLangParser.ExpressionContext ctx) {

        if (ctx.INT() != null)
            return Integer.parseInt(ctx.INT().getText());

        if (ctx.FLOAT() != null)
            return Double.parseDouble(ctx.FLOAT().getText());

        if (ctx.STRING() != null)
            return ctx.STRING().getText().replace("\"", "");

        if (ctx.BOOL() != null)
            return Boolean.parseBoolean(ctx.BOOL().getText());

        if (ctx.ID() != null)
            return symbolTable.get(ctx.ID().getText()).getValue();
        
        if (ctx.getChildCount() == 3) {

            String operator = ctx.getChild(1).getText();

            if (operator.equals("+")) {

                Object left = visit(ctx.expression(0));
                Object right = visit(ctx.expression(1));

                if (left instanceof Integer && right instanceof Integer) {
                    return (Integer) left + (Integer) right;
                }
            }

            if (operator.equals("-")) {

                Object left = visit(ctx.expression(0));
                Object right = visit(ctx.expression(1));

                if (left instanceof Integer && right instanceof Integer) {
                    return (Integer) left - (Integer) right;
                }
            }
            
            if (operator.equals("*")) {

                Object left = visit(ctx.expression(0));
                Object right = visit(ctx.expression(1));

                if (left instanceof Integer && right instanceof Integer) {
                    return (Integer) left * (Integer) right;
                }
            }

            if (operator.equals("/")) {

                Object left = visit(ctx.expression(0));
                Object right = visit(ctx.expression(1));

                if (left instanceof Integer && right instanceof Integer) {
                    return (Integer) left / (Integer) right;
                }
            }
        }
        
        if (ctx.getChildCount() == 3) {

            String operator = ctx.getChild(1).getText();

            Object left = visit(ctx.expression(0));
            Object right = visit(ctx.expression(1));

            if (left instanceof Integer && right instanceof Integer) {

                int l = (Integer) left;
                int r = (Integer) right;

                switch (operator) {
                    case "<":
                        return l < r;

                    case ">":
                        return l > r;

                    case "<=":
                        return l <= r;

                    case ">=":
                        return l >= r;

                    case "==":
                        return l == r;

                    case "!=":
                        return l != r;
                }
            }
        }

        return visitChildren(ctx);
    }
    
    @Override
    public Object visitIfStatement(MiniLangParser.IfStatementContext ctx) {

        Boolean condition = (Boolean) visit(ctx.expression());

        if (condition) {
            visit(ctx.block(0));
        } else if (ctx.block().size() > 1) {
            visit(ctx.block(1));
        }

        return null;
    }
    
    @Override
    public Object visitForStatement(MiniLangParser.ForStatementContext ctx) {

        String name = ctx.variableDeclarationFor().ID().getText();

        Object initialValue =
                visit(ctx.variableDeclarationFor().expression());

        symbolTable.declare(name, "int", initialValue);

        while ((Boolean) visit(ctx.expression())) {

            visit(ctx.block());

            String variableName =
                    ctx.assignmentFor().ID().getText();

            Object newValue =
                    visit(ctx.assignmentFor().expression());

            symbolTable.assign(variableName, newValue);
        }

        return null;
    }
}