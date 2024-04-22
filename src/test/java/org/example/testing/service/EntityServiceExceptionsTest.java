package org.example.testing.service;


import org.example.testing.model.EntityDeleteRequest;
import org.example.testing.model.EntityEditRequest;
import org.example.testing.model.EntityGetRequest;
import org.example.testing.model.EntityInsertRequest;
import org.example.testing.repository.EntityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

class EntityServiceExceptionsTest {
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
    void  itShouldNotSaveAndThrowExceptionWhenCodeExists() {

        //given
        String code = "000000";
        EntityInsertRequest request = new EntityInsertRequest("John", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(true);

        // when

        //then
        assertThatThrownBy( () -> underTest.create(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("already exists");

        // finally
        then(mockRepository).should(never()).save(any());
        verify(mockRepository, times(1)).existsEntityByCode(code);
    }

    @Test
    void itShouldNotSaveEntityWhenNameIsNotValidAndThrowExceptions() {

        //given
        String code = "000000";
        EntityInsertRequest request = new EntityInsertRequest("", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        assertEquals(false,request.validName());

        // when

        //then
        assertThatThrownBy( () -> underTest.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("This name is not valid");

        // finally
        then(mockRepository).should(never()).save(any());
        verify(mockRepository, times(1)).existsEntityByCode(code);
    }

    @Test
    void itShouldNotSaveEntityWhenDescriptionIsNotValidAndThrowExceptions() {

        //given
        String code = "000000";
        EntityInsertRequest request = new EntityInsertRequest("John", code, "something test");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        assertEquals(false,request.validDescription());

        // when
        assertThatThrownBy( () -> underTest.create(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("contains \"test\"");

        // finally
        then(mockRepository).should(never()).save(any());
        verify(mockRepository, times(1)).existsEntityByCode(code);
    }


    @Test
    void itShouldNotUpdateEntityWhenCodePresentInDbAndThrowExceptions() {

        //given
        String code = "000000";
        String id="12adfqWEFFDFASDF215412";
        EntityEditRequest request = new EntityEditRequest(id,"John", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(true);
        given(mockRepository.existsEntityById(id)).willReturn(true);

        // when

        //then
        assertThatThrownBy( () -> underTest.update(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("already exists");

        // finally
        then(mockRepository).should(never()).save(any());
        verify(mockRepository, times(1)).existsEntityByCode(code);
        verify(mockRepository, times(1)).existsEntityById(id);
    }
    @Test
    void itShouldNotUpdateEntityWhenIdNotPresentInDbAndThrowExceptions() {

        //given
        String code = "000000";
        String id="12adfqWEFFDFASDF215412";
        EntityEditRequest request = new EntityEditRequest(id,"John", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        given(mockRepository.existsEntityById(id)).willReturn(false);

        // when

        //then
        assertThatThrownBy( () -> underTest.update(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("not exists");

        // finally
        then(mockRepository).should(never()).save(any());
        verify(mockRepository, times(0)).existsEntityByCode(code);
        verify(mockRepository, times(1)).existsEntityById(id);
    }

    @Test
    void itShouldNotUpdateEntityWhenNameIsNotValidThrowExceptions() {

        //given
        String code = "000000";
        String id="12adfqWEFFDFASDF215412";
        EntityEditRequest request = new EntityEditRequest(id,"", code, "something");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        given(mockRepository.existsEntityById(id)).willReturn(true);
        assertEquals(false,request.validName());

        // when

        //then
        assertThatThrownBy( () -> underTest.update(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("This name is not valid");

        // finally
        then(mockRepository).should(never()).save(any());
        verify(mockRepository, times(1)).existsEntityByCode(code);
        verify(mockRepository, times(1)).existsEntityById(id);
    }

    @Test
    void itShouldNotUpdateEntityWhenDescriptionIsNotValidThrowExceptions() {

        //given
        String code = "000000";
        String id="12adfqWEFFDFASDF215412";
        EntityEditRequest request = new EntityEditRequest(id,"John", code, "something test");
        given(mockRepository.existsEntityByCode(code)).willReturn(false);
        given(mockRepository.existsEntityById(id)).willReturn(true);
        assertEquals(false,request.validDescription());

        // when

        //then
        assertThatThrownBy( () -> underTest.update(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("contains \"test\"");

        // finally
        then(mockRepository).should(never()).save(any());
        verify(mockRepository, times(1)).existsEntityByCode(code);
        verify(mockRepository, times(1)).existsEntityById(id);
    }

    @Test
    void itShouldNotDeleteEntityWhenIdNotPresentInDb() {

        //given
        String code = "000000";
        String id="12adfqWEFFDFASDF215412";
        EntityDeleteRequest request = new EntityDeleteRequest(id,"John", code, "something");
        given(mockRepository.existsEntityById(id)).willReturn(false);

        // when

        //then
        assertThatThrownBy( () -> underTest.delete(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("not exists");

        // finally
        then(mockRepository).should(never()).delete(any());
        verify(mockRepository, times(1)).existsEntityById(id);
    }

    @Test
    void itShouldGetEntityWhenCodeNotPresentInDb() {

        //given
        String id="12adfqWEFFDFASDF215412";
        EntityGetRequest request = new EntityGetRequest(id);
        given(mockRepository.existsEntityById(id)).willReturn(false);

        // when

        //then
        assertThatThrownBy( () -> underTest.getOne(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("not exists");

        // finally
        then(mockRepository).should(never()).delete(any());
        verify(mockRepository, times(1)).existsEntityById(id);
    }
}