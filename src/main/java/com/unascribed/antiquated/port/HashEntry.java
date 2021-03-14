package com.unascribed.antiquated.port;
class HashEntry
{
    final int hash;
    Object value;
    HashEntry next;
    final int slotHash;
    
    HashEntry(final int slotHash, final int hash, final Object value, final HashEntry next) {
        super();
        this.value = value;
        this.next = next;
        this.hash = hash;
        this.slotHash = slotHash;
    }
    
    public final int func_768_a() {
        return this.hash;
    }
    
    public final Object func_767_b() {
        return this.value;
    }
    
    @Override
	public final boolean equals(final Object o) {
        if (!(o instanceof HashEntry)) {
            return false;
        }
        final HashEntry hashEntry = (HashEntry)o;
        final Integer value = this.func_768_a();
        final Integer value2 = hashEntry.func_768_a();
        if (value == value2 || (value != null && value.equals(value2))) {
            final Object func_767_b = this.func_767_b();
            final Object func_767_b2 = hashEntry.func_767_b();
            if (func_767_b == func_767_b2 || (func_767_b != null && func_767_b.equals(func_767_b2))) {
                return true;
            }
        }
        return false;
    }
    
    @Override
	public final int hashCode() {
        return ManualHashMap.func_1055_e(this.hash);
    }
    
    @Override
	public final String toString() {
        return this.func_768_a() + "=" + this.func_767_b();
    }
}
