package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.entity.Trade;

import java.util.List;

public interface TradeRepository {

    List<Trade> findAllOpenTrades();

    boolean checkIfCardIsInDeck(String card_id);

    Trade saveTrade(Trade trade, String user_id);

    void deleteTrade(String trade_id);

    String getDealerUserIdFromTrade(String trade_id);

    Trade getSpecificTrade(String trade_id);

    int getDamageFromCard(String card_id);

    String getTypeFromCard(String card_id);

    Trade updateTrade(Trade tradeToBeUpdated, String trade_id, String user_id, String card_id);

    void tradeCards(String user_id, String card_id);

}
