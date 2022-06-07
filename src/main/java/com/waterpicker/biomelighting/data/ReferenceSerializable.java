package com.waterpicker.biomelighting.data;

import com.google.common.collect.Multimap;

public interface ReferenceSerializable {
	void processFlags(Multimap<String, String> flags);
}
