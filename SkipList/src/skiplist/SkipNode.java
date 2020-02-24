package skiplist;

public class SkipNode<E>  {
	private int key;
	private E element;
	private SkipNode<E>[] nextNodes;

	
	
	public int getKey() {
		return this.key;
	}

	public E getElement() {
		return this.element;
	}

}