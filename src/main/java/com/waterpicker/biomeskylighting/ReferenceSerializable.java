package com.waterpicker.biomeskylighting;

import com.google.common.collect.Multimap;

public interface ReferenceSerializable {
	void processFlags(Multimap<String, String> flags);
}
