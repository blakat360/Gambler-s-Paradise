public class Card{
    public enum Suit{
        HEARTS,
        SPADES,
        CLUBS,
        DIAMONDS
    }

    public enum Rank{
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK,
        QUEEN,
        KING,
        ACE
    }

    private Suit suit;
    private Rank rank;

    public Card(Suit s, Rank r){
        suit = s;
        rank = r;
    }

    public Suit getSuit(){
        return this.suit;
    }

    public Rank getRank(){
        return this.rank;
    }
}
