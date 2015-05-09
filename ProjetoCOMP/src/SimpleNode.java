/* Generated By:JJTree: Do not edit this line. SimpleNode.java Version 4.3 */
/* JavaCCOptions:MULTI=false,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class SimpleNode implements Node {

	protected Node parent;
	protected Node[] children;
	protected int id;
	protected Object value;
	protected JSON parser;

	public SimpleNode(int i) {
		id = i;
	}

	public SimpleNode(JSON p, int i) {
		this(i);
		parser = p;
	}

	public void jjtOpen() {
	}

	public void jjtClose() {
	}

	public void jjtSetParent(Node n) { parent = n; }
	public Node jjtGetParent() { return parent; }

	public void jjtAddChild(Node n, int i) {
		if (children == null) {
			children = new Node[i + 1];
		} else if (i >= children.length) {
			Node c[] = new Node[i + 1];
			System.arraycopy(children, 0, c, 0, children.length);
			children = c;
		}
		children[i] = n;
	}

	public Node jjtGetChild(int i) {
		return children[i];
	}

	public int jjtGetNumChildren() {
		return (children == null) ? 0 : children.length;
	}

	public void jjtSetValue(Object value) { this.value = value; }
	public Object jjtGetValue() { return value; }

	/* You can override these two methods in subclasses of SimpleNode to
     customize the way the node appears when the tree is dumped.  If
     your output uses more than one line you should override
     toString(String), otherwise overriding toString() is probably all
     you need to do. */

	public String toString() { return JSONTreeConstants.jjtNodeName[id]; }
	public String toString(String prefix) { return prefix + toString(); }

	/* Override this method if you want to customize how the node dumps
     out its children. */

	public void dump(String prefix) {
		System.out.print(toString(prefix));

		switch(this.id) {
		case JSONTreeConstants.JJTEXPRESSION:
			System.out.println("\t[ Geral ]");break;
		case JSONTreeConstants.JJTEXPR1:
			System.out.println("\t[ Nodes ]");break;
		case JSONTreeConstants.JJTEXPR2:
			System.out.println("\t[ Links ]");break;
		case JSONTreeConstants.JJTEXPR3:
			System.out.println("\t   [ Node Line Expression ]");break;
		case JSONTreeConstants.JJTEXPR4:
			System.out.println("\t   [ Link Line Expression ]");break;
		case JSONTreeConstants.JJTEXPR5:
			System.out.println("\t[ Node Line ]");break;
		case JSONTreeConstants.JJTEXPR6:
			System.out.println("\t[ Link Line ]");break;
		case JSONTreeConstants.JJTEXPR7:
			System.out.println("\t[ Node Line Recursive Call ]");break;
		case JSONTreeConstants.JJTEXPR8:
			System.out.println("\t[ Link Line Recursive Call ]");break;
		}

		if (children != null) {
			for (int i = 0; i < children.length; ++i) {
				SimpleNode n = (SimpleNode)children[i];
				if (n != null) {
					n.dump(prefix + " ");
				}
			}
		}
	}
}

/* JavaCC - OriginalChecksum=bc59b5802043ae34e08f1330367335c7 (do not edit this line) */
