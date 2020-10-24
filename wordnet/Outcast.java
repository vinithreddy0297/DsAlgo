/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Outcast {
    private WordNet wordNet;
    public Outcast(WordNet wordnet)  // constructor takes a WordNet object
    {
        this.wordNet = wordnet;
    }
    public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast
    {
        int minOutCast = Integer.MAX_VALUE;
        String outcast = null;
        for (String noun: nouns)
        {
            int distnoun = 0;
            for (String nounCmp: nouns)
            {
                int dist = wordNet.distance(noun, nounCmp);
                if (dist > 0)
                    distnoun += wordNet.distance(noun, nounCmp);
            }
            if (distnoun < minOutCast) {
                minOutCast = distnoun;
                outcast = noun;
            }
        }
        return outcast;
    }
    public static void main(String[] args) // see test client below
    {

    }
}