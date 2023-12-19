package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.entity.Card;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class MemoryCardRepositoryTest {

    @Test
    void whenAddOneCard_ThenFindAllShouldReturnOneMore(){
        //Arrange
        MemoryCardRepository memoryCardRepository
                = new MemoryCardRepository();
        Card card = new Card(
                "1234-1234",
                "Venom",
                "schwarzer Typ",
                false
        );

        //Act
        memoryCardRepository.save(card);
        List<Card> cards = memoryCardRepository.findAll();

        //Assert
        assertEquals(1, cards.size());
    }


  
}