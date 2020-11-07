import computation.contextfreegrammar.*;

import java.util.ArrayList;
// import java.util.List;
import java.util.HashSet;

public class MyGrammar {
	public static ContextFreeGrammar makeGrammar() {
		// Context-free grammar from the assignment

		// Create variables
		Variable S = new Variable('S');
		Variable E = new Variable('E');
		Variable T = new Variable('T');
		Variable F = new Variable('F');

		Variable A = new Variable("A1");

		Variable B = new Variable("B1");

		Variable P = new Variable("P1");
		Variable M = new Variable("M1");
		Variable N = new Variable("N1");
		Variable C = new Variable('C');

		// Create a collection of variables in a hash table
		HashSet<Variable> variables = new HashSet<>();
		variables.add(S);
		variables.add(E);
		variables.add(T);
		variables.add(F);

		variables.add(A);
		variables.add(B);

		variables.add(P);
		variables.add(M);
		variables.add(N);
		variables.add(C);

		// Create Terminals
		Terminal plus = new Terminal('+');
		Terminal mult = new Terminal('*');
		Terminal neg = new Terminal('-');
		Terminal one = new Terminal('1');
		Terminal zero = new Terminal('0');
		Terminal x = new Terminal('x');

		// Create a collection of Terminals in a hash table
		HashSet<Terminal> terminals = new HashSet<>();
		terminals.add(plus);
		terminals.add(mult);
		terminals.add(neg);
		terminals.add(one);
		terminals.add(zero);
		terminals.add(x);

		// Create a list of Rules and store them in an ArraysList
		ArrayList<Rule> rules = new ArrayList<>();
		rules.add(new Rule(S, new Word(E, A)));
		rules.add(new Rule(S, new Word(T, B)));
		rules.add(new Rule(S, new Word(N, C)));
		rules.add(new Rule(S, new Word(one)));
		rules.add(new Rule(S, new Word(zero)));
		rules.add(new Rule(S, new Word(x)));

		rules.add(new Rule(E, new Word(E, A)));
		rules.add(new Rule(E, new Word(T, B)));
		rules.add(new Rule(E, new Word(N, C)));
		rules.add(new Rule(E, new Word(one)));
		rules.add(new Rule(E, new Word(zero)));
		rules.add(new Rule(E, new Word(x)));

		rules.add(new Rule(T, new Word(T, B)));
		rules.add(new Rule(T, new Word(N, C)));
		rules.add(new Rule(T, new Word(one)));
		rules.add(new Rule(T, new Word(zero)));
		rules.add(new Rule(T, new Word(x)));

		rules.add(new Rule(F, new Word(N, C)));
		rules.add(new Rule(F, new Word(one)));
		rules.add(new Rule(F, new Word(zero)));
		rules.add(new Rule(F, new Word(x)));

		rules.add(new Rule(A, new Word(P, T)));
		rules.add(new Rule(B, new Word(M, F)));

		rules.add(new Rule(N, new Word(neg)));
		rules.add(new Rule(P, new Word(plus)));
		rules.add(new Rule(M, new Word(mult)));
		rules.add(new Rule(C, new Word(one)));
		rules.add(new Rule(C, new Word(zero)));
		rules.add(new Rule(C, new Word(x)));

		ContextFreeGrammar cfg = new ContextFreeGrammar(rules);

		return cfg;
	}
}
