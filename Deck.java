import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<>();

    public Deck(){
        buildDeck();
    }

    public int getSize(){
        return deck.size();
    }

     public void buildDeck(){
        for(Card.Rank r: Card.Rank.values())
            for(Card.Suit s: Card.Suit.values())
                this.deck.add(new Card(s,r));
    }

    public Card drawCard(){
        int i;
        Card c;
        if(deck.size() == 0){
            return null;
        }
        else{
            i = (int)(Math.random() * deck.size());
            c = deck.get(i);
            deck.remove(i);
            return c;
        }
    }

}
