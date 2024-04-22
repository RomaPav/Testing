package org.example.testing.service;


import org.example.testing.model.*;
import org.example.testing.repository.EntityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

class EntityArgServiceTest {
    @Mock
    private EntityRepository mockRepository;
    private EntityService underTest;

    @Captor
    private ArgumentCaptor<Entity> argumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new EntityService(mockRepository, new ModelMapper());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldSaveEntity() {

        //given
        String code = "000000";
        EntityInsertRequest request = new EntityInsertRequest("John", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);

        // when
        underTest.create(request);

        //then
        then(mockRepository).should().save(argumentCaptor.capture());
        Entity itemPersisted = argumentCaptor.getValue();
        assertNotNull(itemPersisted);

    }

    @Test
    void itShouldNotSaveEntityWhenCodePresentInDb() {

        //given
        String code = "000000";
        EntityInsertRequest request = new EntityInsertRequest("John", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(true);

        // when
        assertThrows(IllegalStateException.class, () -> underTest.create(request));

        //then
        then(mockRepository).should(never()).save(argumentCaptor.capture());
    }

    @Test
    void itShouldSaveEntityWhenNameIsValid() {

        //given
        String code = "000000";
        EntityInsertRequest request = new EntityInsertRequest("John", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        assertEquals(true, request.validName());

        // when
        underTest.create(request);

        //then

        then(mockRepository).should().save(argumentCaptor.capture());
        Entity itemPersisted = argumentCaptor.getValue();
        assertNotNull(itemPersisted);
        assertEquals(request.name(), itemPersisted.getName());

    }

    @Test
    void itShouldNotSaveEntityWhenNameIsNotValid() {

        //given
        String code = "000000";
        EntityInsertRequest request = new EntityInsertRequest("", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        assertEquals(false, request.validName());

        // when
        assertThrows(IllegalArgumentException.class, () -> underTest.create(request));
        //then

        then(mockRepository).should(never()).save(argumentCaptor.capture());

    }

    @Test
    void itShouldSaveEntityWhenDescriptionIsValid() {

        //given
        String code = "000000";
        EntityInsertRequest request = new EntityInsertRequest("John", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        assertEquals(true, request.validName());

        // when
        underTest.create(request);

        //then
        then(mockRepository).should().save(argumentCaptor.capture());
        Entity itemPersisted = argumentCaptor.getValue();
        assertNotNull(itemPersisted);
        assertEquals(request.description(), itemPersisted.getDescription());
    }

    @Test
    void itShouldNotSaveEntityWhenDescriptionIsNotValid() {

        //given
        String code = "000000";
        EntityInsertRequest request = new EntityInsertRequest("John", code, "something test");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        assertEquals(false, request.validDescription());

        // when
        assertThrows(IllegalStateException.class, () -> underTest.create(request));

        //then
        then(mockRepository).should(never()).save(argumentCaptor.capture());

    }

    @Test
    void itShouldUpdateEntity() {

        //given
        String code = "000000";
        String id = "12adfqWEFFDFASDF215412";
        EntityEditRequest request = new EntityEditRequest(id, "John", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        given(mockRepository.existsEntityById(id)).willReturn(true);

        // when
        underTest.update(request);

        //then
        then(mockRepository).should().save(argumentCaptor.capture());
        Entity itemPersisted = argumentCaptor.getValue();
        assertNotNull(itemPersisted);
        assertEquals(request.id(), itemPersisted.getId());
        assertEquals(request.code(), itemPersisted.getCode());
        assertEquals(request.name(), itemPersisted.getName());
        assertEquals(request.description(), itemPersisted.getDescription());
    }

    @Test
    void itShouldNotUpdateEntityWhenCodePresentInDb() {

        //given
        String code = "000000";
        String id = "12adfqWEFFDFASDF215412";

        EntityEditRequest request = new EntityEditRequest(id, "John", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(true);
        given(mockRepository.existsEntityById(id)).willReturn(true);

        // when
        assertThrows(IllegalStateException.class, () -> underTest.update(request));

        //then
        then(mockRepository).should(never()).save(argumentCaptor.capture());
    }

    @Test
    void itShouldUpdateEntityWhenCodeNotPresentInDb() {

        //given
        String code = "000000";
        String id = "12adfqWEFFDFASDF215412";
        EntityEditRequest request = new EntityEditRequest(id, "John", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        given(mockRepository.existsEntityById(id)).willReturn(true);

        // when
        underTest.update(request);

        //then
        then(mockRepository).should().save(argumentCaptor.capture());
        Entity itemPersisted = argumentCaptor.getValue();
        assertNotNull(itemPersisted);
        assertEquals(request.id(), itemPersisted.getId());
        assertEquals(request.code(), itemPersisted.getCode());

    }

    @Test
    void itShouldNotUpdateEntityWhenIdNotPresentInDb() {

        //given
        String code = "000000";
        String id = "12adfqWEFFDFASDF215412";
        EntityEditRequest request = new EntityEditRequest(id, "John", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        given(mockRepository.existsEntityById(id)).willReturn(false);

        // when
        assertThrows(IllegalStateException.class, () -> underTest.update(request));

        //then
        then(mockRepository).should(never()).save(argumentCaptor.capture());
    }

    @Test
    void itShouldUpdateEntityWhenNameIsValid() {

        //given
        String code = "000000";
        String id = "12adfqWEFFDFASDF215412";
        EntityEditRequest request = new EntityEditRequest(id, "John", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        assertEquals(true, request.validName());
        given(mockRepository.existsEntityById(id)).willReturn(true);

        // when
        underTest.update(request);

        //then
        then(mockRepository).should().save(argumentCaptor.capture());
        Entity itemPersisted = argumentCaptor.getValue();
        assertNotNull(itemPersisted);
        assertEquals(request.id(), itemPersisted.getId());
        assertEquals(request.code(), itemPersisted.getCode());
        assertEquals(request.name(), itemPersisted.getName());
    }

    @Test
    void itShouldNotUpdateEntityWhenNameIsNotValid() {

        //given
        String code = "000000";
        String id = "12adfqWEFFDFASDF215412";
        EntityEditRequest request = new EntityEditRequest(id, "", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        given(mockRepository.existsEntityById(id)).willReturn(true);
        assertEquals(false, request.validName());

        // when
        assertThrows(IllegalArgumentException.class, () -> underTest.update(request));

        //then
        then(mockRepository).should(never()).save(argumentCaptor.capture());

    }

    @Test
    void itShouldUpdateEntityWhenDescriptionIsValid() {

        //given
        String code = "000000";
        String id = "12adfqWEFFDFASDF215412";
        EntityEditRequest request = new EntityEditRequest(id, "John", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        given(mockRepository.existsEntityById(id)).willReturn(true);
        assertEquals(true, request.validDescription());

        // when
        underTest.update(request);

        //then
        then(mockRepository).should().save(argumentCaptor.capture());
        Entity itemPersisted = argumentCaptor.getValue();
        assertNotNull(itemPersisted);
        assertEquals(request.id(), itemPersisted.getId());
        assertEquals(request.code(), itemPersisted.getCode());
        assertEquals(request.name(), itemPersisted.getName());
        assertEquals(request.description(), itemPersisted.getDescription());

    }

    @Test
    void itShouldNotUpdateEntityWhenDescriptionIsNotValid() {

        //given
        String code = "000000";
        String id = "12adfqWEFFDFASDF215412";
        EntityEditRequest request = new EntityEditRequest(id, "John", code, "something test");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        given(mockRepository.existsEntityById(id)).willReturn(true);
        assertEquals(false, request.validDescription());

        // when
        assertThrows(IllegalStateException.class, () -> underTest.update(request));

        //then
        then(mockRepository).should(never()).save(argumentCaptor.capture());

    }

    @Test
    void itShouldDeleteEntityWhenCodePresentInDb() {

        //given
        String code = "000000";
        String id = "2";
        EntityDeleteRequest request = new EntityDeleteRequest(id, "John", code, "something");
        given(mockRepository.existsEntityById(id)).willReturn(true);

        // when
        underTest.delete(request);

        //then
        then(mockRepository).should().delete(argumentCaptor.capture());
        Entity itemPersisted = argumentCaptor.getValue();
        assertNotNull(itemPersisted);
        assertEquals(request.id(), itemPersisted.getId());

    }

    @Test
    void itShouldNotDeleteEntityWhenIdNotPresentInDb() {

        //given
        String code = "000000";
        String id = "12adfqWEFFDFASDF215412";
        EntityDeleteRequest request = new EntityDeleteRequest(id, "John", code, "something");
        given(mockRepository.existsEntityById(id)).willReturn(false);

        // when
        assertThrows(IllegalStateException.class, () -> underTest.delete(request));

        //then
        then(mockRepository).should(never()).delete(argumentCaptor.capture());

    }

    @Test
    void itShouldGetEntityWhenCodePresentInDb() {

        //given
        String id = "12adfqWEFFDFASDF215412";
        EntityGetRequest request = new EntityGetRequest(id);
        given(mockRepository.existsEntityById(id)).willReturn(true);

        // when
        underTest.getOne(request.id());

        //then
        then(mockRepository).should().getEntityById(id);
    }
    @Test
    void itShouldNotGetEntityWhenIdNotPresentInDb() {

        //given
        String id="12adfqWEFFDFASDF215412";
        EntityGetRequest request = new EntityGetRequest(id);
        given(mockRepository.existsEntityById(id)).willReturn(false);

        // when
        assertThrows(IllegalStateException.class, () -> underTest.getOne(request));

        //then
        then(mockRepository).should(never()).getEntityById(id);

    }
}