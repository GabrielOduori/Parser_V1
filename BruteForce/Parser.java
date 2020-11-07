
package BruteForce;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import computation.contextfreegrammar.*;
import computation.derivation.Derivation;
import computation.derivation.Step;
import computation.parser.*;
import computation.parsetree.*;


public class Parser implements IParser {
	public boolean isInLanguage(ContextFreeGrammar cfg, Word w) {

		ArrayList<Word> stringList = new ArrayList<>();
		stringList.add(new Word(cfg.getStartVariable()));
		int n = w.length();
		int checkSteps = 0;

		while (checkSteps < (2 * n) - 1) {
			ArrayList<Word> newStringList = new ArrayList<>();
			for (Word i : stringList) {
				for (Rule r : cfg.getRules()) {
					// Count the numbers of first rule variables in the word
					int ruleCount = i.count(r.getVariable());
					// Expand the start variabe "S" as many times as t shows up
					for (int j = 0; j < ruleCount; j++) {
						int index = i.indexOfNth(r.getVariable(), j);
						Word updatedWord = i.replace(index, r.getExpansion());
						newStringList.add(updatedWord);
					}
				}
			}
			checkSteps++;
			stringList = newStringList;
		}
		if (stringList.contains(w)) {
			return true;
		}
		return false;
	}

	public ParseTreeNode generateParseTree(ContextFreeGrammar cfg, Word w) {
		return createParseTree(getDerivation(cfg, w));
	}

	public Derivation getDerivation(ContextFreeGrammar cfg, Word w) {
		int dLength;
		int l = w.length();

		if (l == 0) {
			dLength = 1;
		} else {
			dLength = (2 * l) - 1;
		}

		// Create a list of derivations
		List<Derivation> derivations = new ArrayList<>();
		Derivation begin = new Derivation(new Word(cfg.getStartVariable()));
		derivations.add(begin);

		int deriSteps = 0;
		int deriLength = dLength;
		while (deriSteps < deriLength) {
			List<Derivation> newDerivation = new ArrayList<>();
			for (Derivation d : derivations) {
				Word topWord = d.getLatestWord();
				for (Rule r : cfg.getRules()) {
					Variable currentVariable = r.getVariable();
					int latestCount = topWord.count(currentVariable);
					for (int j = 0; j < latestCount; j++) {
						int index = topWord.indexOfNth(r.getVariable(), j);
						Word updatedWord = topWord.replace(index, r.getExpansion());
						Derivation x = new Derivation(d);
						x.addStep(updatedWord, r, index);
						newDerivation.add(x);
					}
				}
				derivations = newDerivation;
			}
			deriSteps++;
		}
		String firstMessage = "Word is part of the language";
		// String secondMessage = " Word is not part of the language";
		for (Derivation d : derivations) {
			if (d.getLatestWord().equals(w)) {
				System.out.println(firstMessage);
				return d;
			} else {

				// Otherwise do nothing
				// System.out.println(secondMessage);
			}
		}

		return null;
	}

	private ParseTreeNode createParseTree(Derivation d) {
		Word lastWord = d.getLatestWord();

    // Using a LinkedList because I am not really sure what the size of the array would be 
    // if if a ArrayList was used instead.
		LinkedList<ParseTreeNode> backList = new LinkedList<>();
		for (int i = 0; i < lastWord.length(); i++) {
			backList.add(new ParseTreeNode(lastWord.get(i)));
		}
		for (Step parseStep : d) {
			if (parseStep.isStartSymbol()) {
				break;
			}
			/*
			 * Checking if the expansion is an terminal i.e ==1 or i ==2 as non terminals to
			 * conform to Chomsky Normal Form.
			 */
			else if (parseStep.getRule().getExpansion().length() == 1) {
				int index = parseStep.getIndex();
				ParseTreeNode parsePairs = new ParseTreeNode(parseStep.getRule().getVariable(), backList.get(index));
				parsePairs.print();
				backList.set(index, parsePairs);
			} else if (parseStep.getRule().getExpansion().length() == 2) {
				int index = parseStep.getIndex();
				int newIndex = index + 1;
				ParseTreeNode parsePairs = new ParseTreeNode(parseStep.getRule().getVariable(), backList.get(index),
						backList.get(newIndex));
				backList.set(index, parsePairs);
				backList.remove(newIndex);

			}
		}
		assert (backList.size() == 1);
		return backList.get(0);

	}

}