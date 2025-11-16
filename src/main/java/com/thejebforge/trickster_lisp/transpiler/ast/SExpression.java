package com.thejebforge.trickster_lisp.transpiler.ast;

import com.thejebforge.trickster_lisp.transpiler.LispUtils;
import dev.enjarai.trickster.EndecTomfoolery;
import io.wispforest.endec.Endec;
import io.wispforest.endec.StructEndec;
import io.wispforest.owo.serialization.endec.MinecraftEndecs;

public abstract class SExpression {
    @SuppressWarnings("unchecked")
    public static final StructEndec<SExpression> ENDEC = EndecTomfoolery.lazyStruct(() -> (StructEndec<SExpression>) Endec.dispatchedStruct(
            SExpressionType::endec,
            SExpression::type,
            MinecraftEndecs.ofRegistry(SExpressionType.REGISTRY)
    ));

    public abstract SExpressionType<?> type();

    public abstract SExpression deepCopy();

    public long treeSize() {
        return switch (this) {
            case Call call -> call.getSubject().treeSize()
                    + call.getArguments().stream().mapToLong(SExpression::treeSize).sum()
                    + 1L;
            case MacroCall macroCall -> macroCall.getArguments().stream()
                    .mapToLong(SExpression::treeSize).sum()
                    + 2L;
            case ExpressionList list -> list.getExpressions().stream()
                    .mapToLong(SExpression::treeSize).sum()
                    + 1L;
            case MapExpression map -> map.getExpressionMap().entrySet().stream()
                    .mapToLong(e -> e.getKey().treeSize() + e.getValue().treeSize()).sum()
                    + 1L;
            default -> 1L;
        };
    }

    public abstract boolean shallowEquals(SExpression other);

    public String toCode(int indent) {
        return toCode(indent, LispUtils.DEFAULT_TAB_SIZE, false);
    }

    public abstract String toCode(int indent, int tabSize, boolean inline);
}
