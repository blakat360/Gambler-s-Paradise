import java.util.ArrayList;
import java.util.Collections;

public class BlackJack implements Runnable {
    Strategy strategy;
    int roundsToPlay;
    int balance;
    boolean bankrupt;
    final double penaltyFactor = 0.5;

    public BlackJack(Strategy strat, int startBalance, int rounds){
        strategy = strat;
        balance = startBalance;
        roundsToPlay = rounds;
        bankrupt = false;
    }

    boolean validBet(int bet){
        if(bet > 0 && bet <= balance){
            return true;
        }
        return false;
    }

    void cheatHandler(){
        balance = (int)(balance * penaltyFactor);
        if(balance == 0)
            this.bankrupt = true;
    }

    ArrayList<Integer> eval(ArrayList<Card> hand){
        ArrayList<Integer> possibleEvals = new ArrayList<>();
        ArrayList<Integer> temp = new ArrayList<>();
        int sum = 0;
        int aces = 0;
        int tempSize;
        int start = 0;

        for(Card c: hand){
            switch(c.getRank()){
                case ONE:
                    sum+=1;
                    break;
                case TWO:
                    sum+=2;
                    break;
                case THREE:
                    sum+=3;
                    break;
                case FOUR:
                    sum+=4;
                    break;
                case FIVE:
                    sum+=5;
                    break;
                case SIX:
                    sum+=6;
                    break;
                case SEVEN:
                    sum+=7;
                    break;
                case EIGHT:
                    sum+=8;
                case NINE:
                    sum+=9;
                    break;
                case ACE:
                    aces++;
                    break;
                default:
                    sum+=10;
            }
        }

        if(sum <= 21) {
            temp.add(sum);
        }
        else{
            return possibleEvals;
        }
        tempSize = temp.size();
        while(aces > 0){
            for(int i = start; i < tempSize; i++){
                temp.add(temp.get(i) + 1);
                temp.add(temp.get(i) +11);
            }
            start = tempSize;
            tempSize = temp.size();
            aces--;
        }

        for(int i = start; i < tempSize; i++){
            sum = temp.get(i);
            if(sum <= 21){
                possibleEvals.add(sum);
            }
        }

        possibleEvals.sort(Collections.reverseOrder());
        return possibleEvals;
    }

    void bankruptcyTest(){
        if(balance == 0)
            this.bankrupt = true;
    }

    void playRound(){
        ArrayList<Card> dealer = new ArrayList<>();
        ArrayList<Card> player = new ArrayList<>();
        ArrayList<Integer> playerPossibleEvals;
        ArrayList<Integer> dealerPossibleEvals;
        Deck deck = new Deck();

        int bet = strategy.getBet();
        if(!validBet(bet)){
            cheatHandler();
            return;
        }
        balance -= bet;

        dealer.add(deck.drawCard());
        player.add(deck.drawCard());
        player.add(deck.drawCard());
        strategy.startCards(player, dealer);

        if(strategy.surrender()){
            balance += (0.5 * bet);
            return;
        }

        if(strategy.doubleDown()){
            if(!validBet(bet)){
                cheatHandler();
                return;
            }
            balance -= bet;
            bet += bet;
        }
        Card c;
        playerPossibleEvals = eval(player);
        while(strategy.hit()){
            c = deck.drawCard();
            player.add(c);
            playerPossibleEvals = eval(player);
            if(playerPossibleEvals.size() == 0){
                bankruptcyTest();
                return;
            }
            strategy.takeCard(c);
        }

        dealerPossibleEvals = eval(dealer);
        while(dealerPossibleEvals.size() > 0 && dealerPossibleEvals.get(0) >= 17){
            dealer.add(deck.drawCard());
            dealerPossibleEvals = eval(dealer);
        }

        if(dealerPossibleEvals.size() == 0){
            balance += bet * 2;
            return;
        }

        for(int x: playerPossibleEvals){
            if(x > dealerPossibleEvals.get(0)){
                balance += bet * 2;
                return;
            }
        }
        bankruptcyTest();
    }

    @Override
    public void run() {
        while(!bankrupt && roundsToPlay > 0){
            roundsToPlay--;
            playRound();
        }
    }
}
