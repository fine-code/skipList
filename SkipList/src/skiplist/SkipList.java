package skiplist;

public class SkipList<E> {

	private class SkipNode<E> {

		private int key;
		private E element;
		private SkipNode[] nextNodes;

		public SkipNode(int k, E element, int levels) {
			this.key = k;
			this.element = element;
			this.nextNodes = (SkipNode<E>[]) new SkipNode[levels + 1];
			for (int i = 0; i <= levels; i++) {
				this.nextNodes[i] = null;
			}
		}

		public SkipNode(int levels) {
			this.key = 0;
			this.element = Double.NEGATIVE_INFINITY;
			this.nextNodes = new SkipNode[levels + 1];
			for (int i = 0; i <= levels; i++) {
				this.nextNodes[i] = null;
			}
		}

		public int getKey() {
			return this.key;
		}

		public E getElement() {
			return this.element;
		}

		public void adjustLevel(int newLevel) {
			SkipNode[] oldLevel = this.nextNodes;
			this.nextNodes = new SkipNode[newLevel + 1];
			for (int i = 0; i < oldLevel.length; i++) {
				this.nextNodes[i] = oldLevel[i];
			}
			for (int i = oldLevel.length; i <= newLevel; i++) {
				this.nextNodes[i] = null;
			}
		}

		public void printNextNodes() {
			for (int i = this.nextNodes.length - 1; i >= 0; i--) {
				if (this.nextNodes[i] == null) {
					System.out.println("Level: " + i + " - null");
				} else {
					System.out.println("Level: " + i + " - (" + this.nextNodes[i].getKey() + ": "
							+ this.nextNodes[i].getElement() + ")");
				}
			}
		}
	}

	private int level;
	private int size;
	private SkipNode<E> headList;

	public SkipList() {
		this.level = 1;
		this.size = 0;
		this.headList = new SkipNode(this.level);
	}

	private static Random randomValue = new Random();

	private static int uniformRandom(int number) {
		return Math.abs(randomValue.nextInt()) % number;
	}

	private int getRandomLevel() {
		int level;
		for (level = 0; uniformRandom(2) == 0; level++)
			;
		return level;
	}

	public void adjustHead(int newLevel) {
		this.headList.adjustLevel(newLevel);
	}

	public void insert(int key, E value) {
		int newLevel = getRandomLevel();
		if (newLevel > level) {
			adjustHead(newLevel);
		}
		this.level = newLevel;
		SkipNode<E>[] updatedList = (SkipNode<E>[]) new SkipNode[level + 1];
		SkipNode<E> currentNode = this.headList;
		for (int i = level; i >= 0; i--) {
			while ((currentNode.nextNodes[i] != null) && (key > currentNode.nextNodes[i].getKey())) {
				currentNode = currentNode.nextNodes[i];
			}
			updatedList[i] = currentNode;
		}
		currentNode = new SkipNode<E>(key, value, newLevel);
		for (int i = 0; i <= newLevel; i++) {
			currentNode.nextNodes[i] = updatedList[i].nextNodes[i];
			updatedList[i].nextNodes[i] = currentNode;
		}
		this.size++;
	}

	public E find(int searchKey) {
		SkipNode<E> headNode = this.headList;
		for (int i = level; i >= 0; i--) {
			while ((headNode.nextNodes[i] != null) && 
					(searchKey > headNode.nextNodes[i].getKey())) {
						headNode = headNode.nextNodes[i]; 
					}
		}
		headNode = headNode.nextNodes[0];  //move to the actual record if it exists
		if ((headNode != null) && (searchKey == headNode.getKey())) {
			return headNode.getElement();
		}
		else {
			return null;
		}
	}

	public void printContents() {
		SkipNode<E> pointer = this.headList;
		while (true) {
			if (pointer.nextNodes[0] == null) {
				break;
			}
			pointer = pointer.nextNodes[0];
			System.out.println(pointer.getKey() + ": " + pointer.getElement());
		}
	}

	public void printAll() {
		SkipNode pointer = this.headList;
		System.out.println("Head Node ");
		pointer.printNextNodes();
		pointer = pointer.nextNodes[0];
		while (pointer != null) {
			System.out.println("Node (" + pointer.getKey() + ": " + pointer.getElement());
			pointer.printNextNodes();
			pointer = pointer.nextNodes[0];
		}
	}

	public static void main(String[] args) {
		SkipList<String> listOfStrings = new SkipList<String>();
		listOfStrings.insert(1, "One");
		System.out.println("\nprinting the contents of the list...");
		listOfStrings.printAll();
		listOfStrings.insert(4, "Four");
		listOfStrings.insert(10, "Ten");
		listOfStrings.insert(5, "Five");
		listOfStrings.insert(20, "Twenty");
		System.out.println("\nPrintln the contents of the updated list...");
		listOfStrings.printAll();
		System.out.println("Printing the traversal:)");
		listOfStrings.printContents();
	}
}