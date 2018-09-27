package utility;

import java.util.LinkedList;

public class Util {
	public static <T> LinkedList<T> list(T... ts) {
		LinkedList<T> list = new LinkedList<>();
		for (T e : ts) {
			list.add(e);
		}
		return list;
	}
}
