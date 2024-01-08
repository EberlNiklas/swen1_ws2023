package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.entity.Stack;

import java.util.List;

public interface StackRepository {

    List<Stack> findAll();

    void saveCardsIntoStack(String user_id, String card_id);
}
