package com.unascribed.antiquated.port;
import java.util.*;

import com.unascribed.antiquated.port.adapter.AlphaWorld;

public abstract class WorldGenerator {
	public WorldGenerator() {
		super();
	}

	public abstract boolean populate(final AlphaWorld p0, final Random p1, final int p2, final int p3, final int p4);

	public void func_517_a(final double n, final double n2, final double n3) {
	}
}
