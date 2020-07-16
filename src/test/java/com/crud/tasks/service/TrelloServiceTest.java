package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Test
    public void shouldFetchTrelloBoard() {
        //GIVEN
        TrelloBoardDto trelloBoard = new TrelloBoardDto("1", "name", new ArrayList<>());
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard);

        //WHEN
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoards);
        List<TrelloBoardDto> fetchedTrelloBoards = trelloService.fetchTrelloBoards();

        //THEN
        assertEquals(1, fetchedTrelloBoards.size());
    }
}
