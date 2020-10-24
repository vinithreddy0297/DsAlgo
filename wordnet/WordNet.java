// /* *****************************************************************************
//  *  Name:
//  *  Date:
//  *  Description:
//  **************************************************************************** */

import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.List;

public class WordNet {
    private Digraph digraph;
    private List<String> synSetList;
    private BST<String, Bag<Integer>> symbolTable;

    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException();
        In synsetsIn = new In(synsets);
        In hypernymsIn = new In(hypernyms);
        symbolTable = new BST<>();
        synSetList = new ArrayList<>();
        while (!synsetsIn.isEmpty()) {
            String[] nounsSynset = synsetsIn.readLine().split(",");
            synSetList.add(nounsSynset[1]);
            String[] nouns = nounsSynset[1].split(" ");
            for (String noun: nouns)
            {
                if (symbolTable.contains(noun))
                    symbolTable.get(noun).add(Integer.parseInt(nounsSynset[0]));
                else
                {
                    Bag<Integer> bag = new Bag<>();
                    bag.add(Integer.parseInt(nounsSynset[0]));
                    symbolTable.put(noun, bag);
                }
            }
        }
        digraph = new Digraph(symbolTable.size());
        while (!hypernymsIn.isEmpty()) {
            String[] split = hypernymsIn.readLine().split(",");
            for (int col = 1; col < split.length; col++)
                digraph.addEdge(Integer.parseInt(split[0]), Integer.parseInt(split[col]));
        }
    }

    public Iterable<String> nouns() {
        return symbolTable.keys();
    }

    public boolean isNoun(String word) {
        return symbolTable.contains(word);
    }

    public int distance(String nounA, String nounB) {
        if (isNoun(nounA) && isNoun(nounB)) {
            SAP sap = new SAP(digraph);
            return sap.length(symbolTable.get(nounA), symbolTable.get(nounB));
        }
        throw new IllegalArgumentException();
    }

    public String sap(String nounA, String nounB)
    {
        if (isNoun(nounA) && isNoun(nounB)) {
            SAP sap = new SAP(digraph);
            return synSetList.get(sap.ancestor(symbolTable.get(nounA), symbolTable.get(nounB)));
        }
        throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        ArrayList<Integer> sources = new ArrayList<>();
        sources.add(3);
        sources.add(9);
        sources.add(7);
        ArrayList<Integer> sources2 = new ArrayList<>();
        sources2.add(11);
        sources2.add(12);
        sources.add(2);
        System.out.print(sap.ancestor(sources, sources2));
        System.out.print(sap.length(sources, sources2));
    }
}
