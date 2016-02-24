package org.caco.common.util.excel;

public interface MergeStrategy<T> {
	boolean compare(T t1,T t2);
}
