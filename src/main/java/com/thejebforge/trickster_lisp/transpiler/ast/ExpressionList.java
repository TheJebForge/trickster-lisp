package com.thejebforge.trickster_lisp.transpiler.ast;

import com.thejebforge.trickster_lisp.transpiler.LispUtils;
import io.wispforest.endec.StructEndec;
import io.wispforest.endec.impl.StructEndecBuilder;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExpressionList extends SExpression {
    public static final StructEndec<ExpressionList> ENDEC = StructEndecBuilder.of(
            SExpression.ENDEC.listOf().fieldOf("list", ExpressionList::getExpressions),
            ExpressionList::new
    );

    private List<SExpression> expressions;

    public ExpressionList(List<SExpression> expressions) {
        this.expressions = expressions;
    }

    public List<SExpression> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<SExpression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExpressionList that)) return false;
        return Objects.equals(expressions, that.expressions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(expressions);
    }

    @Override
    public String toString() {
        return "ExpressionList{\n" +
                "expressions=" + expressions +
                "\n}";
    }

    @Override
    public SExpressionType<?> type() {
        return SExpressionType.LIST;
    }

    @Override
    public SExpression deepCopy() {
        return new ExpressionList(expressions.stream()
                .map(SExpression::deepCopy)
                .toList());
    }

    @Override
    public boolean shallowEquals(SExpression other) {
        return other instanceof ExpressionList;
    }

    public String toCode(int indent, int tabSize, boolean inline) {
        if (expressions.isEmpty()) {
            return LispUtils.addIndent(indent, inline) + "[]";
        } else {
            return LispUtils.addIndent(indent, inline) + "[" + (expressions.size() == 1 ? "" : '\n')
                    + expressions.stream()
                    .map(e -> e.toCode(indent + tabSize, tabSize, expressions.size() == 1))
                    .collect(Collectors.joining(",\n")) + ']';
        }
    }
}
