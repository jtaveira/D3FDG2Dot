import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

/* Generated By:JJTree&JavaCC: Do not edit this line. JSON.java */
public class JSON/*@bgen(jjtree)*/implements JSONTreeConstants, JSONConstants {/*@bgen(jjtree)*/

	
static	boolean sintaticError = false;

//Estrutura de dados auxiliar
static ArrayList<String> input = new ArrayList<String>();//tokens
static ArrayList<Integer> inputId = new ArrayList<Integer>();//tokens kind

static ArrayList<String> nodes = new ArrayList<String>();//node names
//add node groups
static ArrayList<String> nodesId = new ArrayList<String>();

static ArrayList<String> links = new ArrayList<String>();
static ArrayList<String> mirroredLinks = new ArrayList<String>();

static ArrayList<String> sources = new ArrayList<String>();
static ArrayList<String> targets = new ArrayList<String>();
//add link values

static ArrayList<ArrayList<String>> graph = new ArrayList<ArrayList<String>>();

static ArrayList<String> finalGraph = new ArrayList<String>();

//TODO Representaçao intermedia


protected static JJTJSONState jjtree = new JJTJSONState();public static void main(String args[]) throws ParseException, IOException {

	int op = 0;

	while(op!=3){
		System.out.println("D3FDG2DOT Menu:");
		System.out.println("1-Enter code");
		System.out.println("2-Read code from a text file");
		//System.out.println("3-Print example");
		System.out.println("3-Exit");
		
		Scanner inn = new Scanner(System.in);
		op = inn.nextInt();

		if(op==1){
			System.out.println("Enter your code:");

			JSON parser = new JSON(System.in);
			SimpleNode root = parser.Expression();

			//Imprimir arvore
			//root.dump("");
			break;
		}

		else if(op==2){
			System.out.println("Enter your file name:");

			Scanner innn = new Scanner(System.in);
			String filename = innn.next();

			File f = new File("../"+filename);
			if(f.exists() /*&& !f.isDirectory()*/) {

				JSON parser = new JSON(readFile("../"+filename));
				SimpleNode root = parser.Expression();

				//Imprimir arvore
				//root.dump("");
				break;
			}
			else
				System.out.println("The specified file does not exist.");
		}
	}

	System.out.println("The program has ended.");
}

//limpa estrutura de dados auxiliar
static void cleanAuxStructures(){
	
	input.clear();
	inputId.clear();
	nodes.clear();
	links.clear();
}

static InputStream readFile(String fileName) throws IOException {
	BufferedReader br = new BufferedReader(new FileReader(fileName));
	try {
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();

		while (line != null) {
			sb.append(line);
			sb.append("\n");
			line = br.readLine();
		}

		InputStream stream = new ByteArrayInputStream(sb.toString().getBytes());
		return stream;
	} finally {
		br.close();
	}
}

final public SimpleNode Expression() throws ParseException {
	/*@bgen(jjtree) Expression */
	SimpleNode jjtn000 = new SimpleNode(JJTEXPRESSION);
	boolean jjtc000 = true;
	jjtree.openNodeScope(jjtn000);
	try {
		jj_consume_token(OPENB);
		Expr1();
		Expr2();
		jj_consume_token(CLOSEB);
	} catch (Throwable jjte000) {
		if (jjtc000) {
			jjtree.clearNodeScope(jjtn000);
			jjtc000 = false;
		} else {
			jjtree.popNode();
		}
		if (jjte000 instanceof RuntimeException) {
			{if (true) throw (RuntimeException)jjte000;}
		}
		if (jjte000 instanceof ParseException) {
			{if (true) throw (ParseException)jjte000;}
		}
		{if (true) throw (Error)jjte000;}
	} finally {
		if (jjtc000) {
			jjtree.closeNodeScope(jjtn000, true);
		}
	}
	//throw new Error("Missing return statement in function");

	if(sintaticError){
		System.out.println("Read input with syntactical errors.\n");
		return jjtn000;
	}

	else{
		System.out.println("Read input without syntactical errors.\n");

		if(semanticalAnalysis()){//Checkpoint2

			System.out.println("Read input without semantical errors.\n");

			//checkpoint 3
			// passar para uma estrutura de dados e converte-la no formato JSON DOT
		}	
	}

	return jjtn000;
}

//Verifica se a estrutura de nós está corretamente definida do ponto de vista semantico
boolean nodeVerification(){

	boolean noErrors = true;
	int counter = 0;

	for(int i = 0; i < inputId.size(); i++)
		if(inputId.get(i) == 14){
			nodes.add(input.get(i+2));
			nodesId.add(Integer.toString(counter));
			counter++;
		}

	if(findDuplicates(nodes, "nodes"))//verifica se há nós com o mesmo nome
		noErrors = false;

	return noErrors;
}

//Verifica se a estrutura de ligaçoes entre nós está corretamente definida do ponto de vista semantico
boolean linkVefirication(){

	boolean noErrors = true;

	int numNodes = 0;

	for(int i = 0; i < inputId.size(); i++){
		if(inputId.get(i) == 14)//como o file esta sintaticamente certo, basta encontrar 1 elemento da linha do nó para o contar
			numNodes++;
	}

	int linksLine = 0;
	int nodeIdTemp = 0;

	for(int i = 0; i < inputId.size(); i++){//verificaçao dos ids dos nós em links e de ligaçoes de X para X com recuperaçao de erros

		if(inputId.get(i) == 16){//se for source

			linksLine++;
			int nodeId = Integer.parseInt(input.get(i+2));//get source value

			links.add(input.get(i+2) + " " + input.get(i+6));//guarda os valores source e target daquela ligaçao
			mirroredLinks.add(input.get(i+6) + " " + input.get(i+2));//guarda os valores inversos daquela ligaçao
			sources.add(input.get(i+2));//guarda o valor de source
			targets.add(input.get(i+6));//guarda o valor de target

			nodeIdTemp = nodeId;

			if(!(nodeId < numNodes)){
				System.out.println("Node id is out of bounds in source at line " + linksLine + " of the Links Section.");
				noErrors = false;
			}
		}

		else if(inputId.get(i) == 17){// se for target

			int nodeId = Integer.parseInt(input.get(i+2));//get target value

			if(!(nodeId < numNodes)){
				System.out.println("Node id is out of bounds in target at line " + linksLine + " of the Links Section.");
				noErrors = false;
			}

			if(nodeIdTemp == nodeId){
				System.out.println("Source and target have the same node id value at line " + linksLine + " of the Links Section.");
				noErrors = false;
			}
		}
	}

		
	if(findDuplicates(links, "links"))
		noErrors = false;
	
	int counter = 0;
	
	//verifica se se nao há ligaçoes invertidas
	for(int i = 0; i < links.size(); i++){
		
		counter++;
		
		if(!sources.get(i).equals(targets.get(i))){
			if(mirroredLinks.contains(links.get(i))){
				System.out.println("Mirrored link at line " + counter + " of the Links Section.");
				noErrors = false;
			}
		}
	}

	
	return noErrors;
}


//Verifica se a estrutura do grafo está corretamente definida do ponto de vista semantico
boolean graphVerification(){

	boolean noErrors = true;
	
	graph.add(new ArrayList<String>());
	
	for(int i = 0; i < links.size(); i++){
		
		if(i == 0){//se i = 0 adiciona os dois nós da ligaçao
			graph.get(i).add(sources.get(i));
			graph.get(i).add(targets.get(i));
		}
		
		else{//se target ou source estiverem em graph, adicionam o outro dos nos a graph

			for(int j = 0; j < graph.size(); j++){
				
				if(graph.get(j).contains(sources.get(i)) || graph.get(j).contains(targets.get(i))){
					if(!(graph.get(j).contains(sources.get(i))))
						graph.get(j).add(sources.get(i));
					if(!(graph.get(j).contains(targets.get(i))))
						graph.get(j).add(targets.get(i));
				}
				else if (j == graph.size()-1){
					graph.add(new ArrayList<String>());
					graph.get(j+1).add(sources.get(i));
					graph.get(j+1).add(sources.get(i));
				}
			}
		}
	}
	
	//fazer um join das listas dos grafos
	
	for(int i = 0; i < graph.size(); i++){
		
		if(i == 0)
			finalGraph.addAll(graph.get(i));

		if(i < graph.size()-1){
			for(int j = i+1; j < graph.size(); j++){
				
				ArrayList<String> tempList = new ArrayList<String>();
				tempList = graph.get(i);
				tempList.retainAll(graph.get(j));

				if(tempList.size() > 0){//se ha elementos comuns
					
					finalGraph.addAll(graph.get(i));
					finalGraph.addAll(graph.get(j));
				}		
			}
		}
	}
	
	finalGraph = new ArrayList<String>(new LinkedHashSet<String>(finalGraph));

	if(noErrors){
		if(nodesId.size() != finalGraph.size()){ //comparar graph com nodes se todos os nodes estiverem em graph ta certissimo
			System.out.println("Graph is incorrect. There is at least one node that does not belong in the graph network.");
			noErrors=false;
		}
	}
	
	return noErrors;
}

//Verifica se o ficheiro está corretamente definido do ponto de vista semantico
boolean semanticalAnalysis(){

	boolean noNodeErrors = true;
	boolean noLinkErrors = true;
	boolean noGraphErrors = true;

	noNodeErrors = nodeVerification();
	noLinkErrors = linkVefirication();

	if(noNodeErrors && noLinkErrors)//se os nós e ligaçoes estiverem semanticamente corretos, avançamos para a analise semantica do grafo
		noGraphErrors = graphVerification();

	if(!noNodeErrors || !noLinkErrors || !noGraphErrors){
		System.out.println("\nRead input with semantical errors.\n");
		return false;
	}

	return true;
}

//Verifica se uma lista é igual a outra independetemente da ordem dos elementos
boolean equalLists(ArrayList<String> one, ArrayList<String> two){     
    if (one == null && two == null){
        return true;
    }

    if((one == null && two != null) 
      || one != null && two == null
      || one.size() != two.size()){
        return false;
    }

    one = new ArrayList<String>(one); 
    two = new ArrayList<String>(two);   

    Collections.sort(one);
    Collections.sort(two);      
    return one.equals(two);
}

//Verifica elementos duplicados em arrays
public static boolean findDuplicates(ArrayList<String> list, String type) {

	int counter = 0;
	final Set<String> set1 = new HashSet<String>();

	for (String value : list) {
		counter++;

		if (!set1.add(value)){
			if(type == "links")
				System.out.println("Link line duplicated at line " + counter + " of the Links Section.");
			else if(type == "nodes")
				System.out.println("Node name duplicated at line " + counter + " of the Nodes Section.");
			return true;
		}
	}

	return false;
}

static final public void Expr1() throws ParseException {
	/*@bgen(jjtree) Expr1 */
	SimpleNode jjtn000 = new SimpleNode(JJTEXPR1);
	boolean jjtc000 = true;
	jjtree.openNodeScope(jjtn000);
	try {
		jj_consume_token(NODES);
		jj_consume_token(COLON);
		jj_consume_token(OPENA);
		Expr3();
		jj_consume_token(CLOSEA);
		jj_consume_token(COMMA);
	} catch (Throwable jjte000) {
		if (jjtc000) {
			jjtree.clearNodeScope(jjtn000);
			jjtc000 = false;
		} else {
			jjtree.popNode();
		}
		if (jjte000 instanceof RuntimeException) {
			{if (true) throw (RuntimeException)jjte000;}
		}
		if (jjte000 instanceof ParseException) {
			{if (true) throw (ParseException)jjte000;}
		}
		{if (true) throw (Error)jjte000;}
	} finally {
		if (jjtc000) {
			jjtree.closeNodeScope(jjtn000, true);
		}
	}
}

static final public void Expr2() throws ParseException {
	/*@bgen(jjtree) Expr2 */
	SimpleNode jjtn000 = new SimpleNode(JJTEXPR2);
	boolean jjtc000 = true;
	jjtree.openNodeScope(jjtn000);
	try {
		jj_consume_token(LINKS);
		jj_consume_token(COLON);
		jj_consume_token(OPENA);
		Expr4();
		jj_consume_token(CLOSEA);
	} catch (Throwable jjte000) {
		if (jjtc000) {
			jjtree.clearNodeScope(jjtn000);
			jjtc000 = false;
		} else {
			jjtree.popNode();
		}
		if (jjte000 instanceof RuntimeException) {
			{if (true) throw (RuntimeException)jjte000;}
		}
		if (jjte000 instanceof ParseException) {
			{if (true) throw (ParseException)jjte000;}
		}
		{if (true) throw (Error)jjte000;}
	} finally {
		if (jjtc000) {
			jjtree.closeNodeScope(jjtn000, true);
		}
	}
}

static final public void Expr3() throws ParseException {
	/*@bgen(jjtree) Expr3 */
	SimpleNode jjtn000 = new SimpleNode(JJTEXPR3);
	boolean jjtc000 = true;
	jjtree.openNodeScope(jjtn000);
	try {
		Expr5();
		Expr7();
	} catch (Throwable jjte000) {
		if (jjtc000) {
			jjtree.clearNodeScope(jjtn000);
			jjtc000 = false;
		} else {
			jjtree.popNode();
		}
		if (jjte000 instanceof RuntimeException) {
			{if (true) throw (RuntimeException)jjte000;}
		}
		if (jjte000 instanceof ParseException) {
			{if (true) throw (ParseException)jjte000;}
		}
		{if (true) throw (Error)jjte000;}
	} finally {
		if (jjtc000) {
			jjtree.closeNodeScope(jjtn000, true);
		}
	}
}

static final public void Expr4() throws ParseException {
	/*@bgen(jjtree) Expr4 */
	SimpleNode jjtn000 = new SimpleNode(JJTEXPR4);
	boolean jjtc000 = true;
	jjtree.openNodeScope(jjtn000);
	try {
		Expr6();
		Expr8();
	} catch (Throwable jjte000) {
		if (jjtc000) {
			jjtree.clearNodeScope(jjtn000);
			jjtc000 = false;
		} else {
			jjtree.popNode();
		}
		if (jjte000 instanceof RuntimeException) {
			{if (true) throw (RuntimeException)jjte000;}
		}
		if (jjte000 instanceof ParseException) {
			{if (true) throw (ParseException)jjte000;}
		}
		{if (true) throw (Error)jjte000;}
	} finally {
		if (jjtc000) {
			jjtree.closeNodeScope(jjtn000, true);
		}
	}
}

static final public void Expr5() throws ParseException {
	/*@bgen(jjtree) Expr5 */
	SimpleNode jjtn000 = new SimpleNode(JJTEXPR5);
	boolean jjtc000 = true;
	jjtree.openNodeScope(jjtn000);
	try {
		jj_consume_token(OPENB);
		jj_consume_token(NAME);
		jj_consume_token(COLON);
		jj_consume_token(STRING);
		jj_consume_token(COMMA);
		jj_consume_token(GROUP);
		jj_consume_token(COLON);
		jj_consume_token(INTEGER);
		jj_consume_token(CLOSEB);
	} finally {
		if (jjtc000) {
			jjtree.closeNodeScope(jjtn000, true);
		}
	}
}

static final public void Expr6() throws ParseException {
	/*@bgen(jjtree) Expr6 */
	SimpleNode jjtn000 = new SimpleNode(JJTEXPR6);
	boolean jjtc000 = true;
	jjtree.openNodeScope(jjtn000);
	try {
		jj_consume_token(OPENB);
		jj_consume_token(SOURCE);
		jj_consume_token(COLON);
		jj_consume_token(INTEGER);
		jj_consume_token(COMMA);
		jj_consume_token(TARGET);
		jj_consume_token(COLON);
		jj_consume_token(INTEGER);
		jj_consume_token(COMMA);
		jj_consume_token(VALUE);
		jj_consume_token(COLON);
		jj_consume_token(INTEGER);
		jj_consume_token(CLOSEB);
	} finally {
		if (jjtc000) {
			jjtree.closeNodeScope(jjtn000, true);
		}
	}
}

static final public void Expr7() throws ParseException {
	/*@bgen(jjtree) Expr7 */
	SimpleNode jjtn000 = new SimpleNode(JJTEXPR7);
	boolean jjtc000 = true;
	jjtree.openNodeScope(jjtn000);
	try {
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case COMMA:
			jj_consume_token(COMMA);
			Expr5();
			Expr7();
			break;
		default:
			jj_la1[0] = jj_gen;
			;
		}
	} catch (Throwable jjte000) {
		if (jjtc000) {
			jjtree.clearNodeScope(jjtn000);
			jjtc000 = false;
		} else {
			jjtree.popNode();
		}
		if (jjte000 instanceof RuntimeException) {
			{if (true) throw (RuntimeException)jjte000;}
		}
		if (jjte000 instanceof ParseException) {
			{if (true) throw (ParseException)jjte000;}
		}
		{if (true) throw (Error)jjte000;}
	} finally {
		if (jjtc000) {
			jjtree.closeNodeScope(jjtn000, true);
		}
	}
}

static final public void Expr8() throws ParseException {
	/*@bgen(jjtree) Expr8 */
	SimpleNode jjtn000 = new SimpleNode(JJTEXPR8);
	boolean jjtc000 = true;
	jjtree.openNodeScope(jjtn000);
	try {
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case COMMA:
			jj_consume_token(COMMA);
			Expr6();
			Expr8();
			break;
		default:
			jj_la1[1] = jj_gen;
			;
		}
	} catch (Throwable jjte000) {
		if (jjtc000) {
			jjtree.clearNodeScope(jjtn000);
			jjtc000 = false;
		} else {
			jjtree.popNode();
		}
		if (jjte000 instanceof RuntimeException) {
			{if (true) throw (RuntimeException)jjte000;}
		}
		if (jjte000 instanceof ParseException) {
			{if (true) throw (ParseException)jjte000;}
		}
		{if (true) throw (Error)jjte000;}
	} finally {
		if (jjtc000) {
			jjtree.closeNodeScope(jjtn000, true);
		}
	}
}

static private boolean jj_initialized_once = false;
/** Generated Token Manager. */
static public JSONTokenManager token_source;
static SimpleCharStream jj_input_stream;
/** Current token. */
static public Token token;
/** Next token. */
static public Token jj_nt;
static private int jj_ntk;
static private int jj_gen;
static final private int[] jj_la1 = new int[2];
static private int[] jj_la1_0;
static {
	jj_la1_init_0();
}
private static void jj_la1_init_0() {
	jj_la1_0 = new int[] {0x400,0x400,};
}

/** Constructor with InputStream. */
public JSON(java.io.InputStream stream) {
	this(stream, null);
}
/** Constructor with InputStream and supplied encoding */
public JSON(java.io.InputStream stream, String encoding) {
	if (jj_initialized_once) {
		System.out.println("ERROR: Second call to constructor of static parser.  ");
		System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
		System.out.println("       during parser generation.");
		throw new Error();
	}
	jj_initialized_once = true;
	try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	token_source = new JSONTokenManager(jj_input_stream);
	token = new Token();
	jj_ntk = -1;
	jj_gen = 0;
	for (int i = 0; i < 2; i++) jj_la1[i] = -1;
}

/** Reinitialise. */
static public void ReInit(java.io.InputStream stream) {
	ReInit(stream, null);
}
/** Reinitialise. */
static public void ReInit(java.io.InputStream stream, String encoding) {
	try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	token_source.ReInit(jj_input_stream);
	token = new Token();
	jj_ntk = -1;
	jjtree.reset();
	jj_gen = 0;
	for (int i = 0; i < 2; i++) jj_la1[i] = -1;
}

/** Constructor. */
public JSON(java.io.Reader stream) {
	if (jj_initialized_once) {
		System.out.println("ERROR: Second call to constructor of static parser. ");
		System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
		System.out.println("       during parser generation.");
		throw new Error();
	}
	jj_initialized_once = true;
	jj_input_stream = new SimpleCharStream(stream, 1, 1);
	token_source = new JSONTokenManager(jj_input_stream);
	token = new Token();
	jj_ntk = -1;
	jj_gen = 0;
	for (int i = 0; i < 2; i++) jj_la1[i] = -1;
}

/** Reinitialise. */
static public void ReInit(java.io.Reader stream) {
	jj_input_stream.ReInit(stream, 1, 1);
	token_source.ReInit(jj_input_stream);
	token = new Token();
	jj_ntk = -1;
	jjtree.reset();
	jj_gen = 0;
	for (int i = 0; i < 2; i++) jj_la1[i] = -1;
}

/** Constructor with generated Token Manager. */
public JSON(JSONTokenManager tm) {
	if (jj_initialized_once) {
		System.out.println("ERROR: Second call to constructor of static parser. ");
		System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
		System.out.println("       during parser generation.");
		throw new Error();
	}
	jj_initialized_once = true;
	token_source = tm;
	token = new Token();
	jj_ntk = -1;
	jj_gen = 0;
	for (int i = 0; i < 2; i++) jj_la1[i] = -1;
}

/** Reinitialise. */
public void ReInit(JSONTokenManager tm) {
	token_source = tm;
	token = new Token();
	jj_ntk = -1;
	jjtree.reset();
	jj_gen = 0;
	for (int i = 0; i < 2; i++) jj_la1[i] = -1;
}

static private Token jj_consume_token(int kind){
	Token oldToken;

	if ((oldToken = token).next != null)
		token = token.next;

	else
		token = token.next = token_source.getNextToken();

	jj_ntk = -1;

	if (token.kind == kind) {
		jj_gen++;
		input.add(token.toString());
		inputId.add(kind);
		return token;
	}

	else if(oldToken.kind == kind){//se faltar um token na gramatica, retrocede um para ficar a par e nao desfazado.
		token = oldToken;
		jj_gen++;
		sintaticError = true;
		return token;
	}

	jj_gen++;
	jj_kind = kind;

	printExpectedTokens(oldToken);
	//throw new ParseException();
	sintaticError = true;
	return getToken(kind);
}

/** Get the next Token. */
static final public Token getNextToken() {
	if (token.next != null) token = token.next;
	else token = token.next = token_source.getNextToken();
	jj_ntk = -1;
	jj_gen++;
	return token;
}

/** Get the specific Token. */
static final public Token getToken(int index) {
	Token t = token;
	for (int i = 0; i < index; i++) {
		if (t.next != null) t = t.next;
		else t = t.next = token_source.getNextToken();
	}
	return t;
}

static private int jj_ntk() {
	if ((jj_nt=token.next) == null)
		return (jj_ntk = (token.next=token_source.getNextToken()).kind);
	else
		return (jj_ntk = jj_nt.kind);
}

static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
static private int[] jj_expentry;
static private int jj_kind = -1;

static public void printExpectedTokens(Token tok) {
	jj_expentries.clear();
	boolean[] la1tokens = new boolean[20];
	if (jj_kind >= 0) {
		la1tokens[jj_kind] = true;
		jj_kind = -1;
	}
	for (int i = 0; i < 2; i++) {
		if (jj_la1[i] == jj_gen) {
			for (int j = 0; j < 32; j++) {
				if ((jj_la1_0[i] & (1<<j)) != 0) {
					la1tokens[j] = true;
				}
			}
		}
	}
	for (int i = 0; i < 20; i++) {
		if (la1tokens[i]) {
			jj_expentry = new int[1];
			jj_expentry[0] = i;
			jj_expentries.add(jj_expentry);
		}
	}
	int[][] exptokseq = new int[jj_expentries.size()][];
	for (int i = 0; i < jj_expentries.size(); i++) {
		exptokseq[i] = jj_expentries.get(i);
	}

	System.out.println(printError(tok, exptokseq, tokenImage));
}

/** Generate ParseException. */
static public ParseException generateParseException() {
	jj_expentries.clear();
	boolean[] la1tokens = new boolean[20];
	if (jj_kind >= 0) {
		la1tokens[jj_kind] = true;
		jj_kind = -1;
	}
	for (int i = 0; i < 2; i++) {
		if (jj_la1[i] == jj_gen) {
			for (int j = 0; j < 32; j++) {
				if ((jj_la1_0[i] & (1<<j)) != 0) {
					la1tokens[j] = true;
				}
			}
		}
	}
	for (int i = 0; i < 20; i++) {
		if (la1tokens[i]) {
			jj_expentry = new int[1];
			jj_expentry[0] = i;
			jj_expentries.add(jj_expentry);
		}
	}
	int[][] exptokseq = new int[jj_expentries.size()][];
	for (int i = 0; i < jj_expentries.size(); i++) {
		exptokseq[i] = jj_expentries.get(i);
	}
	return new ParseException(token, exptokseq, tokenImage);
}

/** Enable tracing. */
static final public void enable_tracing() {
}

/** Disable tracing. */
static final public void disable_tracing() {
}

static String printError(Token currentToken, int[][] expectedTokenSequences, String[] tokenImage) {

	String eol = System.getProperty("line.separator", "\n");
	StringBuffer expected = new StringBuffer();
	int maxSize = 0;
	for (int i = 0; i < expectedTokenSequences.length; i++) {
		if (maxSize < expectedTokenSequences[i].length) {
			maxSize = expectedTokenSequences[i].length;
		}
		for (int j = 0; j < expectedTokenSequences[i].length; j++) {
			expected.append(tokenImage[expectedTokenSequences[i][j]]).append(' ');
		}

		expected.append(eol).append("    ");
	}
	String retval = "Encountered \"";
	Token tok = currentToken.next;
	for (int i = 0; i < maxSize; i++) {
		if (i != 0) retval += " ";
		if (tok.kind == 0) {
			retval += tokenImage[0];
			break;
		}
		retval += " " + tokenImage[tok.kind];
		retval += " \"";
		retval += add_escapes(tok.image);
		retval += " \"";
		tok = tok.next;
	}
	retval += "\" at line " + currentToken.next.beginLine + ", column " + currentToken.next.beginColumn;
	retval += "." + eol;
	if (expectedTokenSequences.length == 1) {
		retval += "Was expecting: ";
	} else {
		retval += "Was expecting one of: ";
	}
	retval += expected.toString();
	return retval;
}

static String add_escapes(String str) {
	StringBuffer retval = new StringBuffer();
	char ch;
	for (int i = 0; i < str.length(); i++) {
		switch (str.charAt(i))
		{
		case 0 :
			continue;
		case '\b':
			retval.append("\\b");
			continue;
		case '\t':
			retval.append("\\t");
			continue;
		case '\n':
			retval.append("\\n");
			continue;
		case '\f':
			retval.append("\\f");
			continue;
		case '\r':
			retval.append("\\r");
			continue;
		case '\"':
			retval.append("\\\"");
			continue;
		case '\'':
			retval.append("\\\'");
			continue;
		case '\\':
			retval.append("\\\\");
			continue;
		default:
			if ((ch = str.charAt(i)) < 0x20 || ch > 0x7e) {
				String s = "0000" + Integer.toString(ch, 16);
				retval.append("\\u" + s.substring(s.length() - 4, s.length()));
			} else {
				retval.append(ch);
			}
			continue;
		}
	}
	return retval.toString();
}

}