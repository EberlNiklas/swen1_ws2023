package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.Trade;
import at.technikum.apps.mtcg.repository.DatabaseTradingRepository;
import at.technikum.apps.mtcg.repository.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

    @InjectMocks
    private TradingService tradingService;

    @Mock
    private TradeRepository tradeRepository;

    private String playerId;
    private String cardId;
    private String tradeId;
    private Trade trade;

    @BeforeEach
    public void setUp() {
        playerId = "testPlayerId";
        cardId = "testCardId";
        tradeId = "testTradeId";
        trade = new Trade();
    }

    @Test
    void findAllOpenTrades_returnsListOfTrades() {
        // Arrange
        List<Trade> expectedTrades = Arrays.asList(new Trade("1", "2", "3", "4", "5", "6", "7", 8), new Trade("1", "2", "3", "4", "5", "6", "7", 8)); // Hier sollten tatsächliche Trade-Objekte verwendet werden
        when(tradeRepository.findAllOpenTrades()).thenReturn(expectedTrades);

        // Act
        List<Trade> actualTrades = tradingService.findAllOpenTrades();

        // Assert
        assertEquals(expectedTrades, actualTrades);
        verify(tradeRepository).findAllOpenTrades();
    }

    @Test
    public void testGetDamageFromCard() {
        int expectedDamage = 10;
        when(tradeRepository.getDamageFromCard(cardId)).thenReturn(expectedDamage);

        double result = tradingService.getDamageFromCard(cardId);

        assertEquals(expectedDamage, result);
    }

    @Test
    public void testUpdateTrade() {
        Trade updatedTrade = new Trade();
        updatedTrade.setCustomerUserId("customerUserId");
        updatedTrade.setCustomerCardId("customerCardId");
        updatedTrade.setStatus("done");

        when(tradeRepository.updateTrade(any(Trade.class), anyString(), anyString(), anyString())).thenReturn(updatedTrade);

        Trade result = tradingService.updateTrade(updatedTrade, tradeId, playerId, cardId);

        assertEquals(updatedTrade, result);
        verify(tradeRepository, times(1)).updateTrade(updatedTrade, tradeId, playerId, cardId);
    }

    @Test
    public void testCheckIfCardIsInDeck() {
        when(tradeRepository.checkIfCardIsInDeck(cardId)).thenReturn(true);

        boolean result = tradingService.checkIfCardIsInDeck(cardId);

        assertEquals(true, result);
    }




}
