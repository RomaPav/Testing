package org.example.testing.service;


import org.example.testing.model.*;
import org.example.testing.repository.EntityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class EntityServiceTest {
    @Mock
    private EntityRepository mockRepository;
    private EntityService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest=new EntityService(mockRepository, new ModelMapper());
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
        verify(mockRepository).save(any());
        verify(mockRepository).save(any(Entity.class));
        verify(mockRepository, times(1)).existsEntityByCode(code);
        verify(mockRepository, times(1)).save(any(Entity.class));
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
        verify(mockRepository, never()).save(any());
        verify(mockRepository, times(1)).existsEntityByCode(code);
        verify(mockRepository, times(0)).save(any());
    }
    @Test
    void itShouldSaveEntityWhenNameIsValid() {

        //given
        String code = "000000";
        EntityInsertRequest request = new EntityInsertRequest("John", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        assertEquals(true,request.validName());

        // when
        underTest.create(request);

        //then
        verify(mockRepository).save(any());
        verify(mockRepository).save(any(Entity.class));
        verify(mockRepository, times(1)).save(any(Entity.class));
    }
    @Test
    void itShouldNotSaveEntityWhenNameIsNotValid() {

        //given
        String code = "000000";
        EntityInsertRequest request = new EntityInsertRequest("", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        assertEquals(false,request.validName());

        // when
        assertThrows(IllegalArgumentException.class, () -> underTest.create(request));

        //then
        verify(mockRepository, never()).save(any());
        verify(mockRepository, times(0)).save(any(Entity.class));
    }
    @Test
    void itShouldSaveEntityWhenDescriptionIsValid() {

        //given
        String code = "000000";
        EntityInsertRequest request = new EntityInsertRequest("John", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        assertEquals(true,request.validName());

        // when
        underTest.create(request);

        //then
        verify(mockRepository).save(any());
        verify(mockRepository).save(any(Entity.class));
        verify(mockRepository, times(1)).save(any(Entity.class));
    }
    @Test
    void itShouldNotSaveEntityWhenDescriptionIsNotValid() {

        //given
        String code = "000000";
        EntityInsertRequest request = new EntityInsertRequest("John", code, "something test");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        assertEquals(false,request.validDescription());

        // when
        assertThrows(IllegalStateException.class, () -> underTest.create(request));

        //then
        verify(mockRepository, never()).save(any());
        verify(mockRepository, times(0)).save(any(Entity.class));
    }

    @Test
    void itShouldUpdateEntity() {

        //given
        String code = "000000";
        EntityInsertRequest request = new EntityInsertRequest("John", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        given(mockRepository.existsEntityById(code)).willReturn(true);

        // when
        underTest.create(request);

        //then
        verify(mockRepository).save(any());
        verify(mockRepository).save(any(Entity.class));
        verify(mockRepository, times(1)).existsEntityByCode(code);
        verify(mockRepository, times(1)).save(any(Entity.class));
    }
    @Test
    void itShouldNotUpdateEntityWhenCodePresentInDb() {

        //given
        String code = "000000";
        String id="12adfqWEFFDFASDF215412";
        EntityEditRequest request = new EntityEditRequest(id,"John", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(true);
        given(mockRepository.existsEntityById(id)).willReturn(true);

        // when
        assertThrows(IllegalStateException.class, () -> underTest.update(request));

        //then
        verify(mockRepository, never()).save(any());
        verify(mockRepository, times(1)).existsEntityByCode(code);
        verify(mockRepository, times(0)).save(any());
    }
    @Test
    void itShouldNotUpdateEntityWhenIdNotPresentInDb() {

        //given
        String code = "000000";
        String id="12adfqWEFFDFASDF215412";
        EntityEditRequest request = new EntityEditRequest(id,"John", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        given(mockRepository.existsEntityById(id)).willReturn(false);

        // when
        assertThrows(IllegalStateException.class, () -> underTest.update(request));

        //then
        verify(mockRepository, never()).save(any());
        verify(mockRepository, times(1)).existsEntityById(id);
        verify(mockRepository, times(0)).existsEntityByCode(code);

        verify(mockRepository, times(0)).save(any());
    }
    @Test
    void itShouldUpdateEntityWhenNameIsValid() {

        //given
        String code = "000000";
        String id="12adfqWEFFDFASDF215412";
        EntityEditRequest request = new EntityEditRequest(id,"John", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        assertEquals(true,request.validName());
        given(mockRepository.existsEntityById(id)).willReturn(true);

        // when
        underTest.update(request);

        //then
        verify(mockRepository).save(any());
        verify(mockRepository).save(any(Entity.class));
        verify(mockRepository, times(1)).save(any(Entity.class));
    }
    @Test
    void itShouldNotUpdateEntityWhenNameIsNotValid() {

        //given
        String code = "000000";
        String id="12adfqWEFFDFASDF215412";
        EntityEditRequest request = new EntityEditRequest(id,"", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        given(mockRepository.existsEntityById(id)).willReturn(true);
        assertEquals(false,request.validName());

        // when
        assertThrows(IllegalArgumentException.class, () -> underTest.update(request));

        //then
        verify(mockRepository, never()).save(any());
        verify(mockRepository, times(0)).save(any(Entity.class));
    }
    @Test
    void itShouldUpdateEntityWhenDescriptionIsValid() {

        //given
        String code = "000000";
        String id="12adfqWEFFDFASDF215412";
        EntityEditRequest request = new EntityEditRequest(id,"John", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        given(mockRepository.existsEntityById(id)).willReturn(true);
        assertEquals(true,request.validName());

        // when
        underTest.update(request);

        //then
        verify(mockRepository).save(any());
        verify(mockRepository).save(any(Entity.class));
        verify(mockRepository, times(1)).save(any(Entity.class));
    }
    @Test
    void itShouldNotUpdateEntityWhenDescriptionIsNotValid() {

        //given
        String code = "000000";
        String id="12adfqWEFFDFASDF215412";
        EntityEditRequest request = new EntityEditRequest(id,"John", code, "something test");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        given(mockRepository.existsEntityById(id)).willReturn(true);
        assertEquals(false,request.validDescription());

        // when
        assertThrows(IllegalStateException.class, () -> underTest.update(request));

        //then
        verify(mockRepository, never()).save(any());
        verify(mockRepository, times(0)).save(any(Entity.class));
    }
    @Test
    void itShouldDeleteEntityWhenCodePresentInDb() {

        //given
        String code = "000000";
        String id="12adfqWEFFDFASDF215412";
        EntityDeleteRequest request = new EntityDeleteRequest(id,"John", code, "something");
        given(mockRepository.existsEntityById(id)).willReturn(true);

        // when
        underTest.delete(request);

        //then
        verify(mockRepository).delete(any());
        verify(mockRepository, times(1)).existsEntityById(id);
        verify(mockRepository, times(1)).delete(any());
    }
    @Test
    void itShouldNotDeleteEntityWhenIdNotPresentInDb() {

        //given
        String code = "000000";
        String id="12adfqWEFFDFASDF215412";
        EntityDeleteRequest request = new EntityDeleteRequest(id,"John", code, "something");
        given(mockRepository.existsEntityById(id)).willReturn(false);

        // when
        assertThrows(IllegalStateException.class, () -> underTest.delete(request));

        //then
        verify(mockRepository, never()).delete(any());
        verify(mockRepository, times(1)).existsEntityById(id);
        verify(mockRepository, times(0)).delete(any());
    }
    @Test
    void itShouldGetEntityWhenCodePresentInDb() {

        //given
        String id="12adfqWEFFDFASDF215412";
        EntityGetRequest request = new EntityGetRequest(id);
        given(mockRepository.existsEntityById(id)).willReturn(true);

        // when
        underTest.getOne(request);

        //then
        verify(mockRepository).getEntityById(any());
        verify(mockRepository, times(1)).existsEntityById(id);
        verify(mockRepository, times(1)).getEntityById(id);
    }
    @Test
    void itShouldGetEntityWhenCodeNotPresentInDb() {

        //given
        String id="12adfqWEFFDFASDF215412";
        EntityGetRequest request = new EntityGetRequest(id);
        given(mockRepository.existsEntityById(id)).willReturn(false);

        // when
        assertThrows(IllegalStateException.class, () -> underTest.getOne(request));

        //then
        verify(mockRepository, never()).getEntityById(any());
        verify(mockRepository, times(1)).existsEntityById(id);
        verify(mockRepository, times(0)).getEntityById(id);
    }
}