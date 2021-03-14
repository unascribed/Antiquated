package com.unascribed.antiquated.port;
public class ManualHashMap {
	private transient HashEntry[] slots;
	private transient int count;
	private int size;
	private final float field_1592_d;
	private transient volatile int field_1591_e;

	public ManualHashMap() {
		super();
		field_1592_d = 0.75f;
		size = 12;
		slots = new HashEntry[16];
	}

	static int func_1055_e(int n) {
		n ^= (n >>> 20 ^ n >>> 12);
		return n ^ n >>> 7 ^ n >>> 4;
	}

	private static int func_1062_a(final int n, final int n2) {
		return n & n2 - 1;
	}

	public Object lookup(final int n) {
		for (HashEntry next = slots[func_1062_a(func_1055_e(n), slots.length)]; next != null; next = next.next) {
			if (next.hash == n) {
				return next.value;
			}
		}
		return null;
	}

	public void put(final int n, final Object value) {
		final int func_1055_e = func_1055_e(n);
		final int func_1062_a = func_1062_a(func_1055_e, slots.length);
		for (HashEntry next = slots[func_1062_a]; next != null; next = next.next) {
			if (next.hash == n) {
				next.value = value;
			}
		}
		++field_1591_e;
		func_1053_a(func_1055_e, n, value, func_1062_a);
	}

	private void func_1060_f(final int n) {
		if (slots.length == 1073741824) {
			size = Integer.MAX_VALUE;
			return;
		}
		final HashEntry[] slots = new HashEntry[n];
		func_1059_a(slots);
		this.slots = slots;
		size = (int)(n * field_1592_d);
	}

	private void func_1059_a(final HashEntry[] array) {
		final HashEntry[] slots = this.slots;
		final int length = array.length;
		for (int i = 0; i < slots.length; ++i) {
			HashEntry hashEntry = slots[i];
			if (hashEntry != null) {
				slots[i] = null;
				do {
					final HashEntry next = hashEntry.next;
					final int func_1062_a = func_1062_a(hashEntry.slotHash, length);
					hashEntry.next = array[func_1062_a];
					array[func_1062_a] = hashEntry;
					hashEntry = next;
				} while (hashEntry != null);
			}
		}
	}

	public Object func_1052_b(final int n) {
		final HashEntry func_1054_c = func_1054_c(n);
		return (func_1054_c == null) ? null : func_1054_c.value;
	}

	final HashEntry func_1054_c(final int n) {
		final int func_1062_a = func_1062_a(func_1055_e(n), slots.length);
		HashEntry hashEntry2;
		HashEntry next;
		for (HashEntry hashEntry = hashEntry2 = slots[func_1062_a]; hashEntry2 != null; hashEntry2 = next) {
			next = hashEntry2.next;
			if (hashEntry2.hash == n) {
				++field_1591_e;
				--count;
				if (hashEntry == hashEntry2) {
					slots[func_1062_a] = next;
				}
				else {
					hashEntry.next = next;
				}
				return hashEntry2;
			}
			hashEntry = hashEntry2;
		}
		return hashEntry2;
	}

	public void func_1058_a() {
		++field_1591_e;
		final HashEntry[] slots = this.slots;
		for (int i = 0; i < slots.length; ++i) {
			slots[i] = null;
		}
		count = 0;
	}

	private void func_1053_a(final int n, final int n2, final Object o, final int n3) {
		slots[n3] = new HashEntry(n, n2, o, slots[n3]);
		if (count++ >= size) {
			func_1060_f(2 * slots.length);
		}
	}

	static /* synthetic */ int func_1056_d(final int n) {
		return func_1055_e(n);
	}
}
