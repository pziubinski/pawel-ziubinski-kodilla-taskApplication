package com.crud.tasks.trello.validator;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import ch.qos.logback.classic.Logger;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TrelloValidatorTest {

    @InjectMocks
    TrelloValidator trelloValidator;

    @Test
    public void shouldValidateCard() {
        //GIVEN
        TrelloCard trelloCard = new TrelloCard("name", "desc", "pos", "id");
        TrelloCard trelloCardTest = new TrelloCard("This name contains test", "desc", "pos", "id");

        Logger validatorLogger = (Logger) LoggerFactory.getLogger(TrelloValidator.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        validatorLogger.addAppender(listAppender);

        //WHEN
        trelloValidator.validateCard(trelloCard);
        trelloValidator.validateCard(trelloCardTest);

        //THEN
        List<ILoggingEvent> logList = listAppender.list;
        assertEquals("Seems that my application is used in proper way.", logList.get(0).getMessage());
        assertEquals("Someone is testing my application!", logList.get(1).getMessage());
    }

    @Test
    public void validateTrelloBoard() {
        //Given
        TrelloBoard trelloBoard = new TrelloBoard("1", "name", new ArrayList<>());
        TrelloBoard trelloBoardTest = new TrelloBoard("2", "test", new ArrayList<>());

        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard);
        trelloBoards.add(trelloBoardTest);

        //When
        List<TrelloBoard> trelloList = trelloValidator.validateTrelloBoards(trelloBoards);

        //Then
        Assert.assertEquals(1, trelloList.size());
    }
}
