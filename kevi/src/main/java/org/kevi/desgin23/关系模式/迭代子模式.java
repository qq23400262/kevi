package org.kevi.desgin23.关系模式;

/**
 * 迭代子模式(Iterator)
 * 
 * @author 422575
 *
 */
public class 迭代子模式 {
	public static void main(String[] args) {
		//http://blog.csdn.net/longyulu/article/details/9159589
		Collection collection = new MyCollection();
		Iterator it = collection.iterator();

		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
}

interface Collection {

	public Iterator iterator();

	/* 取得集合元素 */
	public Object get(int i);

	/* 取得集合大小 */
	public int size();
}

interface Iterator {
	// 前移
	public Object previous();

	// 后移
	public Object next();

	public boolean hasNext();

	// 取得第一个元素
	public Object first();
}

class MyCollection implements Collection {

	public String string[] = { "A", "B", "C", "D", "E" };

	@Override
	public Iterator iterator() {
		return new MyIterator(this);
	}

	@Override
	public Object get(int i) {
		return string[i];
	}

	@Override
	public int size() {
		return string.length;
	}
}

class MyIterator implements Iterator {

	private Collection collection;
	private int pos = -1;

	public MyIterator(Collection collection) {
		this.collection = collection;
	}

	@Override
	public Object previous() {
		if (pos > 0) {
			pos--;
		}
		return collection.get(pos);
	}

	@Override
	public Object next() {
		if (pos < collection.size() - 1) {
			pos++;
		}
		return collection.get(pos);
	}

	@Override
	public boolean hasNext() {
		if (pos < collection.size() - 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Object first() {
		pos = 0;
		return collection.get(pos);
	}

}