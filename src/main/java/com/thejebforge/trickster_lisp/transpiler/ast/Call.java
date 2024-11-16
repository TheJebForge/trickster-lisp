package com.thejebforge.trickster_lisp.transpiler.ast;

import com.thejebforge.trickster_lisp.transpiler.LispUtils;
import io.wispforest.endec.StructEndec;
import io.wispforest.endec.impl.StructEndecBuilder;

import java.util.List;
import java.util.Objects;

public class Call extends SExpression {
    public static final StructEndec<Call> ENDEC = StructEndecBuilder.of(
            SExpression.ENDEC.fieldOf("id", Call::getSubject),
            SExpression.ENDEC.listOf().fieldOf("args", Call::getArguments),
            Call::new
    );

    private SExpression subject;
    private List<SExpression> arguments;

    public Call(SExpression subject, List<SExpression> arguments) {
        this.subject = subject;
        this.arguments = arguments;
    }

    public SExpression getSubject() {
        return subject;
    }

    public void setSubject(SExpression subject) {
        this.subject = subject;
    }

    public List<SExpression> getArguments() {
        return arguments;
    }

    public void setArguments(List<SExpression> arguments) {
        this.arguments = arguments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Call call)) return false;
        return Objects.equals(subject, call.subject) && Objects.equals(arguments, call.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, arguments);
    }

    @Override
    public String toString() {
        return "Call{\n" +
                "subject='" + subject + '\'' +
                ", \narguments=" + arguments +
                "\n}";
    }

    @Override
    public SExpressionType<?> type() {
        return SExpressionType.CALL;
    }

    @Override
    public SExpression deepCopy() {
        return new Call(subject.deepCopy(), arguments.stream()
                .map(SExpression::deepCopy)
                .toList());
    }

    @Override
    public boolean shallowEquals(SExpression other) {
        return other instanceof Call;
    }

    public String toCode(int indent, int tabSize, boolean inline) {
        return LispUtils.toCallCode(
                indent,
                tabSize,
                inline,
                subject.toCode(indent + tabSize, tabSize, true),
                arguments
        );
    }
}
