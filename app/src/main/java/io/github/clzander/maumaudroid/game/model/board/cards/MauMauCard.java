package io.github.clzander.maumaudroid.game.model.board.cards;

import java.util.HashMap;

import io.github.clzander.maumaudroid.R;

public class MauMauCard implements Card{

    public static int CLUBS_TWO = R.drawable.clubs_two;
    public static int CLUBS_THREE = R.drawable.clubs_three;
    public static int CLUBS_FOUR = R.drawable.clubs_four;
    public static int CLUBS_FIVE = R.drawable.clubs_five;
    public static int CLUBS_SIX = R.drawable.clubs_six;
    public static int CLUBS_SEVEN = R.drawable.clubs_seven;
    public static int CLUBS_EIGHT = R.drawable.clubs_eight;
    public static int CLUBS_NINE = R.drawable.clubs_nine;
    public static int CLUBS_TEN = R.drawable.clubs_ten;
    public static int CLUBS_JACK = R.drawable.clubs_jack;
    public static int CLUBS_QUEEN = R.drawable.clubs_queen;
    public static int CLUBS_KING = R.drawable.clubs_king;
    public static int CLUBS_ACE = R.drawable.clubs_ace;

    public static int SPADES_TWO = R.drawable.spades_two;
    public static int SPADES_THREE = R.drawable.spades_three;
    public static int SPADES_FOUR = R.drawable.spades_four;
    public static int SPADES_FIVE = R.drawable.spades_five;
    public static int SPADES_SIX = R.drawable.spades_six;
    public static int SPADES_SEVEN = R.drawable.spades_seven;
    public static int SPADES_EIGHT = R.drawable.spades_eight;
    public static int SPADES_NINE = R.drawable.spades_nine;
    public static int SPADES_TEN = R.drawable.spades_ten;
    public static int SPADES_JACK = R.drawable.spades_jack;
    public static int SPADES_QUEEN = R.drawable.spades_queen;
    public static int SPADES_KING = R.drawable.spades_king;
    public static int SPADES_ACE = R.drawable.spades_ace;

    public static int HEART_TWO = R.drawable.heart_two;
    public static int HEART_THREE = R.drawable.heart_three;
    public static int HEART_FOUR = R.drawable.heart_four;
    public static int HEART_FIVE = R.drawable.heart_five;
    public static int HEART_SIX = R.drawable.heart_six;
    public static int HEART_SEVEN = R.drawable.heart_seven;
    public static int HEART_EIGHT = R.drawable.heart_eight;
    public static int HEART_NINE = R.drawable.heart_nine;
    public static int HEART_TEN = R.drawable.heart_ten;
    public static int HEART_JACK = R.drawable.heart_jack;
    public static int HEART_QUEEN = R.drawable.heart_queen;
    public static int HEART_KING = R.drawable.heart_king;
    public static int HEART_ACE = R.drawable.heart_ace;

    public static int DIAMONDS_TWO = R.drawable.diamonds_two;
    public static int DIAMONDS_THREE = R.drawable.diamonds_three;
    public static int DIAMONDS_FOUR = R.drawable.diamonds_four;
    public static int DIAMONDS_FIVE = R.drawable.diamonds_five;
    public static int DIAMONDS_SIX = R.drawable.diamonds_six;
    public static int DIAMONDS_SEVEN = R.drawable.diamonds_seven;
    public static int DIAMONDS_EIGHT = R.drawable.diamonds_eight;
    public static int DIAMONDS_NINE = R.drawable.diamonds_nine;
    public static int DIAMONDS_TEN = R.drawable.diamonds_ten;
    public static int DIAMONDS_JACK = R.drawable.diamonds_jack;
    public static int DIAMONDS_QUEEN = R.drawable.diamonds_queen;
    public static int DIAMONDS_KING = R.drawable.diamonds_king;
    public static int DIAMONDS_ACE = R.drawable.diamonds_ace;

    public static HashMap<String, Integer> cardRessourceMap = new HashMap<>();
    private static boolean initilized = false;


    private final CardColor color;
    private final CardType type;

    public MauMauCard(CardColor color, CardType type) {
        if (!initilized) {
            initilized = true;
            setUpMap();
        }
        this.color = color;
        this.type = type;
    }

    private void setUpMap() {
        cardRessourceMap.put("CLUBSTWO", CLUBS_TWO);
        cardRessourceMap.put("CLUBSTHREE", CLUBS_THREE);
        cardRessourceMap.put("CLUBSFOUR", CLUBS_FOUR);
        cardRessourceMap.put("CLUBSFIVE", CLUBS_FIVE);
        cardRessourceMap.put("CLUBSSIX", CLUBS_SIX);
        cardRessourceMap.put("CLUBSSEVEN", CLUBS_SEVEN);
        cardRessourceMap.put("CLUBSEIGHT", CLUBS_EIGHT);
        cardRessourceMap.put("CLUBSNINE", CLUBS_NINE);
        cardRessourceMap.put("CLUBSTEN", CLUBS_TEN);
        cardRessourceMap.put("CLUBSJACK", CLUBS_JACK);
        cardRessourceMap.put("CLUBSQUEEN", CLUBS_QUEEN);
        cardRessourceMap.put("CLUBSKING", CLUBS_KING);
        cardRessourceMap.put("CLUBSACE", CLUBS_ACE);

        cardRessourceMap.put("SPADESTWO", SPADES_TWO);
        cardRessourceMap.put("SPADESTHREE", SPADES_THREE);
        cardRessourceMap.put("SPADESFOUR", SPADES_FOUR);
        cardRessourceMap.put("SPADESFIVE", SPADES_FIVE);
        cardRessourceMap.put("SPADESSIX", SPADES_SIX);
        cardRessourceMap.put("SPADESSEVEN", SPADES_SEVEN);
        cardRessourceMap.put("SPADESEIGHT", SPADES_EIGHT);
        cardRessourceMap.put("SPADESNINE", SPADES_NINE);
        cardRessourceMap.put("SPADESTEN", SPADES_TEN);
        cardRessourceMap.put("SPADESJACK", SPADES_JACK);
        cardRessourceMap.put("SPADESQUEEN", SPADES_QUEEN);
        cardRessourceMap.put("SPADESKING", SPADES_KING);
        cardRessourceMap.put("SPADESACE", SPADES_ACE);

        cardRessourceMap.put("HEARTTWO", HEART_TWO);
        cardRessourceMap.put("HEARTTHREE", HEART_THREE);
        cardRessourceMap.put("HEARTFOUR", HEART_FOUR);
        cardRessourceMap.put("HEARTFIVE", HEART_FIVE);
        cardRessourceMap.put("HEARTSIX", HEART_SIX);
        cardRessourceMap.put("HEARTSEVEN", HEART_SEVEN);
        cardRessourceMap.put("HEARTEIGHT", HEART_EIGHT);
        cardRessourceMap.put("HEARTNINE", HEART_NINE);
        cardRessourceMap.put("HEARTTEN", HEART_TEN);
        cardRessourceMap.put("HEARTJACK", HEART_JACK);
        cardRessourceMap.put("HEARTQUEEN", HEART_QUEEN);
        cardRessourceMap.put("HEARTKING", HEART_KING);
        cardRessourceMap.put("HEARTACE", HEART_ACE);

        cardRessourceMap.put("DIAMONDSTWO", DIAMONDS_TWO);
        cardRessourceMap.put("DIAMONDSTHREE", DIAMONDS_THREE);
        cardRessourceMap.put("DIAMONDSFOUR", DIAMONDS_FOUR);
        cardRessourceMap.put("DIAMONDSFIVE", DIAMONDS_FIVE);
        cardRessourceMap.put("DIAMONDSSIX", DIAMONDS_SIX);
        cardRessourceMap.put("DIAMONDSSEVEN", DIAMONDS_SEVEN);
        cardRessourceMap.put("DIAMONDSEIGHT", DIAMONDS_EIGHT);
        cardRessourceMap.put("DIAMONDSNINE", DIAMONDS_NINE);
        cardRessourceMap.put("DIAMONDSTEN", DIAMONDS_TEN);
        cardRessourceMap.put("DIAMONDSJACK", DIAMONDS_JACK);
        cardRessourceMap.put("DIAMONDSQUEEN", DIAMONDS_QUEEN);
        cardRessourceMap.put("DIAMONDSKING", DIAMONDS_KING);
        cardRessourceMap.put("DIAMONDSACE", DIAMONDS_ACE);
    }

    @Override
    public CardColor getColor() {
        return this.color;
    }

    @Override
    public CardType getType() {
        return this.type;
    }

    @Override
    public int getRessourceId() {
        return cardRessourceMap.get(this.color.toString().concat(this.type.toString()));
    }

}
