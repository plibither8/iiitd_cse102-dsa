import java.util.*;

public class MinHeap {
	static long[] Heap;
	static int size;
	static int maxSize;

	MinHeap(int maxSize) {
		this.maxSize = maxSize;
		this.size = -1;
		Heap = new long[maxSize];
	}

	static int parent(int pos) {
		return (pos - 1) / 2;
	}

	static int leftChild(int pos) {
		return pos * 2 + 1;
	}

	static int rightChild(int pos) {
		return pos * 2 + 2;
	}

	static boolean isLeafNode(int pos) {
		return pos > size / 2 && pos < size;
	}

	static boolean hasSingleChild(int pos) {
		return rightChild(pos) > size;
	}

	static boolean isViolation(int pos) {
		return Heap[leftChild(pos)] < Heap[pos] ||
			Heap[rightChild(pos)] < Heap[pos];
	}

	static void swap(int a, int b) {
		long temp = Heap[b];
		Heap[b] = Heap[a];
		Heap[a] = temp;
	}

	static void heapify(int pos) {
		if (isLeafNode(pos) || hasSingleChild(pos)) return;

		int leftIndex = leftChild(pos);
		int rightIndex = rightChild(pos);

		if (isViolation(pos)) {
			if (Heap[leftIndex] < Heap[rightIndex]) {
				swap(leftIndex, pos);
				heapify(leftIndex);
			} else {
				swap(rightIndex, pos);
				heapify(rightIndex);
			}
		}
	}

	static void insert(long val) {
		Heap[++size] = val;

		int index = size;
		while(index > 0  && Heap[parent(index)] > Heap[index]) {
			swap(parent(index), index);
			index = parent(index);
		}
	}

	static long extractMin() {
		long min = Heap[0];
		Heap[0] = Heap[size];
		size--;
		heapify(0);
		return min;
	}

	static void heapifyUpwards(int pos) {
		while (pos > 0 && isViolation(parent(pos))) {
			heapify(parent(pos));
			pos = parent(pos);
		}
	}

	static void change(int pos, long delta) {
		Heap[pos] += delta;
		if (delta < 0) {
			heapifyUpwards(pos);
		} else {
			heapify(pos);
		}
	}

	static void print() {
		int i = -1;
		while (!isLeafNode(++i)) {
			System.out.print("i="+i+"; ");
			System.out.print("P="+Heap[i]+"; ");
			System.out.print("L="+Heap[leftChild(i)]);
			if (!hasSingleChild(i)) System.out.print("; R="+Heap[rightChild(i)]+"\n");
			else System.out.println();
		}
	}

	static void printDirect() {
		for (int i = 0; i < maxSize; i++) System.out.print(Heap[i]+" ");
		System.out.println();
	}

	static void heapSort() {
		while(size >= 0) {
			swap(size--, 0);
			heapify(0);
		}
	}

	static void reheapify(int finalSize) {
		while(size < finalSize - 1) {
			long val = Heap[size+1];
			insert(val);
		}
	}

	static MinHeap merge(MinHeap A, MinHeap B) {
		int totalSize = A.maxSize + B.maxSize;
		MinHeap C = new MinHeap(totalSize);
		for (int i = 1; i <= A.size; i++) C.insert(A.Heap[i]);
		for (int i = 1; i <= B.size; i++) C.insert(B.Heap[i]);
		return C;
	}

	public static void main(String args[]) {
		MinHeap minHeap = new MinHeap(15);
		minHeap.insert(5);
		minHeap.insert(3);
		minHeap.insert(17);
		minHeap.insert(10);
		minHeap.insert(84);
		minHeap.insert(19);
		minHeap.insert(6);
		minHeap.insert(22);
		minHeap.insert(9);
		minHeap.printDirect();
		minHeap.heapSort();
		minHeap.printDirect();
		minHeap.reheapify(9);
		minHeap.printDirect();
	}
}
