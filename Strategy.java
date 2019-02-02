import java.util.ArrayList;

public interface Strategy {
    int getBet(); //called when asking for your initial bet
    void startCards(ArrayList<Card> player, ArrayList<Card> dealer); //your starting cards (a and b) and the dealer's first card (c)
    boolean hit(); //take another card?
    boolean takeCard(Card c); //used to take another card after you hit
    boolean doubleDown(); //used to see if you want to double your bet size
    boolean surrender(); //used to see if you want to give up early
    String getStratID(); //returns a unique strategy identifier in the form of "<bath_username>-<strategy_name>"
}
