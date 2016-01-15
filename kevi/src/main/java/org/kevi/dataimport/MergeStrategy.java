package org.kevi.dataimport;

public interface MergeStrategy<T> {
	boolean compare(T t1,T t2);
}
