package com.thejebforge.trickster_lisp.transpiler.ast;

import com.thejebforge.trickster_lisp.transpiler.LispUtils;
import io.wispforest.endec.StructEndec;
import io.wispforest.endec.impl.StructEndecBuilder;

import java.util.List;
import java.util.Objects;

public class MacroCall extends SExpression {
    public static final StructEndec<MacroCall> ENDEC = StructEndecBuilder.of(
            StructEndec.STRING.fieldOf("name", MacroCall::getMacroName),
            SExpression.ENDEC.listOf().fieldOf("args", MacroCall::getArguments),
            MacroCall::new
    );

    private String macroName;
    private List<SExpression> arguments;

    public MacroCall(String macroName, List<SExpression> arguments) {
        this.macroName = macroName;
        this.arguments = arguments;
    }

    public String getMacroName() {
        return macroName;
    }

    public void setMacroName(String macroName) {
        this.macroName = macroName;
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
        if (!(o instanceof MacroCall call)) return false;
        return Objects.equals(macroName, call.macroName) && Objects.equals(arguments, call.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(macroName, arguments);
    }

    @Override
    public String toString() {
        return "Call{\n" +
                "macroName='" + macroName + '\'' +
                ", \narguments=" + arguments +
                "\n}";
    }

    @Override
    public SExpressionType<?> type() {
        return SExpressionType.MACRO_CALL;
    }

    @Override
    public SExpression deepCopy() {
        return new MacroCall(macroName, arguments.stream()
                .map(SExpression::deepCopy)
                .toList());
    }

    @Override
    public boolean shallowEquals(SExpression other) {
        return other instanceof MacroCall;
    }

    public String toCode(int indent, int tabSize, boolean inline) {
        return LispUtils.toCallCode(
                indent,
                tabSize,
                inline,
                macroName + '!',
                arguments
        );
    }
}
