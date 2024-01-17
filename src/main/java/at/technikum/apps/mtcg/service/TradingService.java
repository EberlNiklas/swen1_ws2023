package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.Trade;
import at.technikum.apps.mtcg.repository.DatabaseTradingRepository;
import at.technikum.apps.mtcg.repository.TradeRepository;

import java.util.List;

public class TradingService {

    private final TradeRepository tradeRepository;

    public TradingService(TradeRepository tradeRepository){
        this.tradeRepository = tradeRepository;
    }

    public List<Trade> findAllOpenTrades() {
        return tradeRepository.findAllOpenTrades();
    }

    public boolean checkIfCardIsInDeck(String card_id){
        return tradeRepository.checkIfCardIsInDeck(card_id);
    }

    public Trade saveTrade(Trade trade, String user_id){
        trade.setDealerUserId(user_id);
        trade.setCustomerUserId(null);
        trade.setCustomerCardId(null);
        trade.setStatus("trade open");
        return tradeRepository.saveTrade(trade, user_id);
    }

    public void deleteTrade(String trade_id){
        tradeRepository.deleteTrade(trade_id);
    }

    public String getDealerUserIdFromTrade(String trade_id){
        return tradeRepository.getDealerUserIdFromTrade(trade_id);
    }

    public Trade getSpecificTrade(String trade_id){
        return tradeRepository.getSpecificTrade(trade_id);
    }

    public int getDamageFromCard(String card_id){
        return tradeRepository.getDamageFromCard(card_id);
    }

    public String getTypeFromCard(String card_id){
        return tradeRepository.getTypeFromCard(card_id);
    }

    public Trade updateTrade(Trade tradeToBeUpdated, String trade_id, String user_id, String card_id){
        tradeToBeUpdated.setCustomerUserId(user_id);
        tradeToBeUpdated.setCustomerCardId(card_id);
        tradeToBeUpdated.setStatus("trade complete");
        return tradeRepository.updateTrade(tradeToBeUpdated, trade_id, user_id, card_id);
    }

    public void tradeCards(String user_id, String card_id){
        tradeRepository.tradeCards(user_id, card_id);
    }
}
