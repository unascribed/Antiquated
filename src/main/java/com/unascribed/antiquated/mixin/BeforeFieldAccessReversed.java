package com.unascribed.antiquated.mixin;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.spongepowered.asm.mixin.injection.points.BeforeFieldAccess;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;

import java.util.Collection;

public class BeforeFieldAccessReversed extends BeforeFieldAccess {
    protected int insns_size;
    public BeforeFieldAccessReversed(InjectionPointData data) {
        super(data);
    }
    @Override
    protected boolean matchesOrdinal(int ordinal) {
        this.log("{} > > comparing target ordinal {} with current ordinal {}", this.className, this.ordinal, ordinal);
        return this.ordinal == -1 || this.ordinal == insns_size-ordinal;
    }
    @Override
    protected boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes, ITargetSelector selector, SearchType searchType) {
        insns_size=-1;
        if (selector == null)
            return false;
        ITargetSelector target = searchType == SearchType.PERMISSIVE ? selector.configure("permissive") : selector;
        for (AbstractInsnNode insn : insns)
                if (this.matchesInsn(insn) && target.match(insn).isExactMatch())
                    insns_size++;
        return super.find(desc, insns, nodes, selector, searchType);
    }
}
