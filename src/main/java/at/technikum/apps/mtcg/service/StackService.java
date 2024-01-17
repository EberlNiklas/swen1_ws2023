package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.Stack;
import at.technikum.apps.mtcg.repository.DatabaseStackRepository;
import at.technikum.apps.mtcg.repository.StackRepository;

import java.util.List;

public class StackService {

    private final StackRepository stackRepository;

    public StackService(StackRepository stackRepository) {
        this.stackRepository = stackRepository;
    }

    public List<Stack> findAll(){
        return stackRepository.findAll();
    }

    public void saveCardsIntoStack(String user_id, String card_id){
        stackRepository.saveCardsIntoStack(user_id, card_id);
    }
}
