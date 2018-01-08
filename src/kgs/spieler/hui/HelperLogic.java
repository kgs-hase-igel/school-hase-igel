package kgs.spieler.hui;

import sc.plugin2018.*;
import sc.plugin2018.util.Constants;

import java.util.ArrayList;

public class HelperLogic {
    /**
     * Die Winning Moves aus den possibleMoves herausfiltern
     * @param possibleMoves
     */
    public ArrayList<Move> extractWinningMoves(int index, ArrayList<Move> possibleMoves) {
        // Store winning moves
        ArrayList<Move> winningMoves = new ArrayList<>();

        for(Move move : possibleMoves) {
            for(Action action : move.actions) {
                if(action instanceof Advance
                        && ((Advance) action).getDistance() + index == Constants.NUM_FIELDS) {
                    winningMoves.add(move);
                }
            }
        }

        // Return winning moves
        return winningMoves;
    }

    /**
     * Die Salad Moves aus den possibleMoves herausfiltern
     * @param possibleMoves
     */
    public ArrayList<Move> extractSaladMoves(GameState gameState, int index, ArrayList<Move> possibleMoves) {
        ArrayList<Move> saladMoves = new ArrayList<>();

        for(Move move : possibleMoves) {
            for(Action action : move.actions) {
                if (action instanceof Advance) {
                    Advance advance = (Advance) action;

                    if (gameState.getBoard().getTypeAt(advance.getDistance() + index) == FieldType.SALAD) {
                        // Zug auf Salatfeld
                        saladMoves.add(move);
                    }
                } else if (action instanceof Card) {
                    Card card = (Card) action;

                    if (card.getType() == CardType.EAT_SALAD) {
                        // Zug auf Hasenfeld und danch Salatkarte
                        saladMoves.add(move);
                    }
                }
            }
        }

        return saladMoves;
    }

    /**
     * Die Hasen Moves aus den possibleMoves herausfiltern
     * @param possibleMoves
     */
    public ArrayList<ExchangeCarrotsMove> extractExchangeCarrotsMoves(GameState gameState, int index, ArrayList<Move> possibleMoves) {
        // Store moves and get current player
        ArrayList<ExchangeCarrotsMove> exchangeCarrotsMoves = new ArrayList<>();
        Player currentPlayer = gameState.getCurrentPlayer();

        for(Move move : possibleMoves) {
            for(Action action : move.actions) {
                if (action instanceof ExchangeCarrots) {
                    ExchangeCarrots exchangeCarrots = (ExchangeCarrots) action;
                    if (exchangeCarrots.getValue() == 10 && currentPlayer.getCarrots() < 30 && index < 40
                            && !(currentPlayer.getLastNonSkipAction() instanceof ExchangeCarrots)) {
                        // Nehme nur Karotten auf, wenn weniger als 30 und nur am Anfang und nicht zwei mal hintereinander
                        exchangeCarrotsMoves.add(new ExchangeCarrotsMove(exchangeCarrots.getValue(), move));
                    } else if (exchangeCarrots.getValue() == -10 && currentPlayer.getCarrots() > 30 && index >= 40) {
                        // abgeben von Karotten ist nur am Ende sinnvoll
                        exchangeCarrotsMoves.add(new ExchangeCarrotsMove(exchangeCarrots.getValue(), move));
                    }
                }
            }
        }

        // Return Exchange Carrots Moves
        return exchangeCarrotsMoves;
    }

    // Struktur um auch den Wert für den Exchange zu speichern
    public class ExchangeCarrotsMove {
        private int value;
        private Move move;

        // Initialize new object
        ExchangeCarrotsMove(int value, Move move) {
            this.value = value;
            this.move = move;
        }

        // Return object values
        public int getValue() { return value; }
        public Move getMove() { return move; }
    }
}
