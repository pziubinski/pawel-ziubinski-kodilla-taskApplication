package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTests {
    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void shouldMapToBoards(){
        //GIVEN
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1", "To do", new ArrayList<>());
        trelloBoardDtoList.add(trelloBoardDto);

        //WHEN
        List<TrelloBoard> trelloBoardList = trelloMapper.mapToBoards(trelloBoardDtoList);

        //THEN
        assertEquals("1", trelloBoardList.get(0).getId());
        assertEquals("To do", trelloBoardList.get(0).getName());
        assertEquals(1, trelloBoardList.size());
        assertEquals(0, trelloBoardList.get(0).getLists().size());
    }

    @Test
    public void shouldMapToBoardsDto() {
        //GIVEN
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        TrelloBoard trelloBoard = new TrelloBoard("1", "To do", new ArrayList<>());
        trelloBoardList.add(trelloBoard);

        //WHEN
        List<TrelloBoardDto> trelloBoardDto = trelloMapper.mapToBoardsDto(trelloBoardList);

        //THEN
        assertEquals("1", trelloBoardDto.get(0).getId());
        assertEquals("To do", trelloBoardDto.get(0).getName());
        assertEquals(0, trelloBoardDto.get(0).getLists().size());
    }

    @Test
    public void shouldMapToList() {
        //GIVEN
        TrelloListDto trelloListDto = new TrelloListDto("1", "To do", false);
        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        trelloListDtoList.add(trelloListDto);

        //WHEN
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtoList);

        //THEN
        assertEquals("1", trelloLists.get(0).getId());
        assertEquals("To do", trelloLists.get(0).getName());
        assertNotEquals(true, trelloLists.get(0).isClosed());
    }

    @Test
    public void shouldMapToListDto() {
        //GIVEN
        TrelloList trelloList = new TrelloList("1", "To do", false);
        List<TrelloList> trelloListList = new ArrayList<>();
        trelloListList.add(trelloList);

        //WHEN
        List<TrelloListDto> trelloListsDtoList = trelloMapper.mapToListDto(trelloListList);

        //THEN
        assertEquals("1", trelloListsDtoList.get(0).getId());
        assertEquals("To do", trelloListsDtoList.get(0).getName());
        assertNotEquals(true, trelloListsDtoList.get(0).isClosed());
    }

    @Test
    public void shouldMapToCard() {
        //GIVEN
        TrelloCardDto trelloCardDto = new TrelloCardDto("Card", "Description", "Pos", "Id1");

        //WHEN
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //THEN
        assertEquals("Card", trelloCard.getName());
        assertEquals("Description", trelloCard.getDescription());
        assertEquals("Pos", trelloCard.getPos());
        assertEquals("Id1", trelloCard.getListId());
    }

    @Test
    public void shouldMapToCardDto() {
        //GIVEN
        TrelloCard trelloCard = new TrelloCard("Card", "Description", "Pos", "Id1");

        //WHEN
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //THEN
        assertEquals("Card", trelloCardDto.getName());
        assertEquals("Description", trelloCardDto.getDescription());
        assertEquals("Pos", trelloCardDto.getPos());
        assertEquals("Id1", trelloCardDto.getListId());
    }
}
