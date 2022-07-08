package game;

import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Sort {
	public static void selectionSort(LinkedList<Object> e) {
		int min;
		
		for (int i = 0; i < e.size() - 1; i++) {
			min = i;
			for (int j = i + 1; j < e.size(); j++) {
				if (e.get(j).x < e.get(min).x || !e.get(min).visible) {
					min = j;
				}
				
				if (min != i) {
					swap(e, i, min);
				}
			}
		}
	}
	
	public static void swap(LinkedList<Object> l, int left, int right) {
		Object temp = l.get(left);
		
		l.set(left, l.get(right));
		l.set(right, temp);
	}
	
	public static LinkedList<Object> mergeSort(LinkedList<Object> e) {
		if (e.size() == 1 || e.size() == 0) return e;
		
		int mid = (int) Math.floor(e.size() / 2);
		
		LinkedList<Object> left = (LinkedList<Object>) new LinkedList<>(e.subList(0, mid));
		LinkedList<Object> right = (LinkedList<Object>) new LinkedList<>(e.subList(mid, e.size()));
		
		return merge(mergeSort(left), mergeSort(right));
	}
	
	public static LinkedList<Object> merge(LinkedList<Object> l, LinkedList<Object> r) {
		LinkedList<Object> res = new LinkedList<Object>();
		
		int l_i = 0;
		int r_i = 0;
		
		while (l_i < l.size() && r_i < r.size()) {
			if (l.get(l_i).x > r.get(r_i).x || r.get(r_i).visible) {
				res.addLast(l.get(l_i));
				l_i++;
			} else {
				res.addLast(r.get(r_i));
				r_i++;
			}
		}
		
		return (LinkedList<Object>) new LinkedList<>(Stream.concat(l.subList(l_i, l.size()).stream(), r.subList(r_i, r.size()).stream()).collect(Collectors.toList()));
	}
}
