package org.example.testing.repository;


import org.example.testing.model.Entity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/*
  @author   user
  @project   lab4
  @class  EntityRepositoryTest
  @version  1.0.0 
  @since 12.03.2024 - 17.53
*/
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataMongoTest
class EntityRepositoryTest {
    @Autowired
    EntityRepository underTest;

    @BeforeEach
    void setUp() {
        List<Entity> items = List.of(
                new Entity("1", "John", "test", "test"),
                new Entity("2", "Paul", "test", "test"),
                new Entity("3", "Freddie", "test", "test")
        );
        underTest.saveAll(items);
    }

    @AfterEach
    void tearDown() {
        List<Entity> entities = underTest.findAll();
        underTest.deleteAll(entities);
    }

    @Test
    void itShouldCheckThrCollectionIsNotEmpty() {
        assertFalse(underTest.findAll().isEmpty());
        List<Entity> entities = underTest.findAll();
        assertEquals(entities.size(), 3);
    }

//    @Test
//    void itShouldSaveEntity() {
//        Entity testEntity = new Entity("Test", "0004");
//        underTest.save(testEntity);
//        Entity forTest = underTest.findAll().stream()
//                .filter(item -> item.getName().equals("Test"))
//                .filter(item -> item.getDescription().contains("0004"))
//                .findAny()
//                .orElse(null);
//        assertNotNull(forTest);
//        assertNotNull(forTest.getId());
//        assertFalse(forTest.getId().isEmpty());
//        assertEquals(forTest.getDescription(), "0004");
//        underTest.delete(testEntity);
//    }
//
//    @Test
//    void itShouldUpdateEntity() {
//        Entity testEntity = new Entity("1", "Roma", "test", LocalDateTime.now(), LocalDateTime.now());
//        underTest.save(testEntity);
//        Entity forTest = underTest.getEntityById("1");
//        assertNotNull(forTest.getId());
//        assertFalse(forTest.getId().isEmpty());
//        assertEquals(forTest.getName(), "Roma");
//        assertEquals(forTest.getDescription(), "test");
//    }
//
//    @Test
//    void itShouldDeleteEntity() {
//        Entity testEntity = new Entity("1", "John", "test", LocalDateTime.now(), null);
//        underTest.delete(testEntity);
//        Entity forTest = underTest.getEntityById("1");
//        assertNull(forTest);
//    }
//
//    @Test
//    void itShouldFindById() {
//        Entity testEntity = underTest.getEntityById("1");
//        assertNotNull(testEntity);
//        assertNotNull(testEntity.getId());
//        assertEquals(testEntity.getName(), "John");
//    }
//
//    @Test
//    void itShouldFindByName() {
//        List<Entity> testEntityList = underTest.getEntitiesByName("John");
//        List<Entity> checkList = testEntityList.stream().filter(el -> el.getName().equals("John")).toList();
//        assertEquals(testEntityList.size(), checkList.size());
//    }
//
//    @Test
//    void itShouldFindByDescription() {
//        List<Entity> testEntityList = underTest.getEntitiesByDescription("test");
//        List<Entity> checkList = testEntityList.stream().filter(el -> el.getDescription().equals("test")).toList();
//        assertEquals(testEntityList.size(), checkList.size());
//    }
//
//    @Test
//    void itShouldFindByCreatedAt() {
//        LocalDateTime time = LocalDateTime.parse("2024-02-21T00:00:00");
//        Entity testEntity = new Entity("Test", "0004");
//        testEntity.setCreatedAt(time);
//        underTest.save(testEntity);
//        List<Entity> testEntityList = underTest.getEntitiesByCreatedAt(time);
//        List<Entity> checkList = testEntityList.stream().filter(el -> el.getCreatedAt().equals(time)).toList();
//        assertEquals(testEntityList.size(), checkList.size());
//        underTest.deleteAll(testEntityList);
//    }
//
//    @Test
//    void itShouldFindByUpdatedAt() {
//        final LocalDateTime time = LocalDateTime.parse("2024-02-21T00:00:00");
//        Entity testEntity = new Entity("Test", "0004");
//        testEntity.setUpdatedAt(time);
//        underTest.save(testEntity);
//        List<Entity> testEntityList = underTest.getEntitiesByUpdatedAt(time);
//        List<Entity> checkList = testEntityList.stream().filter(el -> el.getUpdatedAt().equals(time)).toList();
//        assertEquals(testEntityList.size(), checkList.size());
//        underTest.deleteAll(testEntityList);
//    }
//
//    @Test
//    void itShouldFindByNameAndDescription() {
//        Entity testEntity = underTest.getEntityByNameAndDescription("John", "test");
//        assertNotNull(testEntity);
//        assertNotNull(testEntity.getId());
//        assertEquals(testEntity.getName(), "John");
//        assertEquals(testEntity.getDescription(), "test");
//    }
//
//    @Test
//    void itShouldFindByNameOrDescription() {
//        List<Entity> testEntityList = underTest.getEntitiesByNameOrDescription("John", "test");
//        List<Entity> checkList = testEntityList.stream()
//                .filter(el -> el.getName().equals("John") || el.getDescription().equals("test")).toList();
//        assertEquals(testEntityList.size(), checkList.size());
//    }
//
//    @Test
//    void itShouldFindByNameAndCreatedAt() {
//        LocalDateTime time = LocalDateTime.parse("2024-02-21T00:00:00");
//        Entity testEntity = new Entity("Test", "0004");
//        testEntity.setCreatedAt(time);
//        underTest.save(testEntity);
//        Entity testEntityCheck = underTest.getEntityByNameAndCreatedAt("Test", time);
//        assertEquals(testEntityCheck.getName(), "Test");
//        assertEquals(testEntityCheck.getCreatedAt(), time);
//        underTest.delete(testEntityCheck);
//    }
//
//    @Test
//    void itShouldFindByNameAndCUpdatedAt() {
//        LocalDateTime time = LocalDateTime.parse("2024-02-21T00:00:00");
//        Entity testEntity = new Entity("Test", "0004");
//        testEntity.setUpdatedAt(time);
//        underTest.save(testEntity);
//        Entity testEntityCheck = underTest.getEntityByNameAndUpdatedAt("Test", time);
//        assertEquals(testEntityCheck.getName(), "Test");
//        assertEquals(testEntityCheck.getUpdatedAt(), time);
//        underTest.delete(testEntityCheck);
//    }
//
//    @Test
//    void itShouldFindByDescriptionAndCreatedAt() {
//        LocalDateTime time = LocalDateTime.parse("2024-02-21T00:00:00");
//        Entity testEntity = new Entity("Test", "0004");
//        testEntity.setCreatedAt(time);
//        underTest.save(testEntity);
//        Entity testEntityCheck = underTest.getEntityByDescriptionAndCreatedAt("0004", time);
//        assertEquals(testEntityCheck.getDescription(), "0004");
//        assertEquals(testEntityCheck.getCreatedAt(), time);
//        underTest.delete(testEntityCheck);
//    }
//
//    @Test
//    void itShouldFindByDescriptionAndUpdatedAt() {
//        LocalDateTime time = LocalDateTime.parse("2024-02-21T00:00:00");
//        Entity testEntity = new Entity("Test", "0004");
//        testEntity.setUpdatedAt(time);
//        underTest.save(testEntity);
//        Entity testEntityCheck = underTest.getEntityByDescriptionAndUpdatedAt("0004", time);
//        assertEquals(testEntityCheck.getDescription(), "0004");
//        assertEquals(testEntityCheck.getUpdatedAt(), time);
//        underTest.delete(testEntityCheck);
//    }
//
//    @Test
//    void itShouldFindByNameAndDescriptionAndCreatedAt() {
//        LocalDateTime time = LocalDateTime.parse("2024-02-21T00:00:00");
//        Entity testEntity = new Entity("Test", "0004");
//        testEntity.setCreatedAt(time);
//        underTest.save(testEntity);
//        Entity testEntityCheck = underTest.getEntityByNameAndDescriptionAndCreatedAt("Test", "0004", time);
//        assertEquals(testEntityCheck.getName(), "Test");
//        assertEquals(testEntityCheck.getDescription(), "0004");
//        assertEquals(testEntityCheck.getCreatedAt(), time);
//        underTest.delete(testEntityCheck);
//    }
//
//    @Test
//    void itShouldFindByNameAndDescriptionAndCUpdatedAt() {
//        LocalDateTime time = LocalDateTime.parse("2024-02-21T00:00:00");
//        Entity testEntity = new Entity("Test", "0004");
//        testEntity.setUpdatedAt(time);
//        underTest.save(testEntity);
//        Entity testEntityCheck = underTest.getEntityByNameAndDescriptionAndUpdatedAt("Test", "0004", time);
//        assertEquals(testEntityCheck.getName(), "Test");
//        assertEquals(testEntityCheck.getDescription(), "0004");
//        assertEquals(testEntityCheck.getUpdatedAt(), time);
//        underTest.delete(testEntityCheck);
//    }
//
//    @Test
//    void itShouldFindByNameAndCreatedAtOrUpdatedAt() {
//        LocalDateTime created = LocalDateTime.parse("2024-02-21T00:00:00");
//        LocalDateTime updated = LocalDateTime.parse("2024-02-21T00:01:00");
//        Entity testEntityFirst = new Entity("Test", "0004");
//        Entity testEntitySecond = new Entity("Test", "0004");
//        testEntityFirst.setCreatedAt(created);
//        testEntitySecond.setUpdatedAt(updated);
//        underTest.save(testEntityFirst);
//        underTest.save(testEntitySecond);
//        List<Entity> testEntityList = underTest.getEntitiesByNameAndCreatedAtOrUpdatedAt("Test", created, updated);
////        List<Entity> checkList = testEntityList.stream().filter(el -> el.getName().equals("Test") &&
////                (el.getCreatedAt() == created || el.getUpdatedAt() == updated)).toList();
////        assertEquals(testEntityList.size(), checkList.size());
//        for(Entity el: testEntityList){
//            assertEquals(el.getName(), "Test");
//            if (el.getCreatedAt() != null) {
//                assertEquals(el.getCreatedAt(), created);
//            } else {
//                assertEquals(el.getUpdatedAt(), updated);
//            }
//        }
//        underTest.deleteAll(testEntityList);
//    }
//
//    @Test
//    void itShouldFindByDescriptionAndCreatedAtOrUpdatedAt() {
//        LocalDateTime created = LocalDateTime.parse("2024-02-21T00:00:00");
//        LocalDateTime updated = LocalDateTime.parse("2024-02-21T00:01:00");
//        Entity testEntityFirst = new Entity("Test", "0004");
//        Entity testEntitySecond = new Entity("Test1", "0004");
//        testEntityFirst.setCreatedAt(created);
//        testEntitySecond.setUpdatedAt(updated);
//        underTest.save(testEntityFirst);
//        underTest.save(testEntitySecond);
//        List<Entity> testEntityList = underTest.getEntitiesByDescriptionAndCreatedAtOrUpdatedAt("0004",
//                created, updated);
////        List<Entity> checkList = testEntityList.stream().filter(el -> el.getDescription().equals("0004") &&
////                (el.getCreatedAt() == created || el.getUpdatedAt() == updated)).toList();
////        assertEquals(testEntityList.size(), checkList.size());
//        for(Entity el: testEntityList){
//            assertEquals(el.getDescription(), "0004");
//            if (el.getCreatedAt() != null) {
//                assertEquals(el.getCreatedAt(), created);
//            } else {
//                assertEquals(el.getUpdatedAt(), updated);
//            }
//        }
//        underTest.deleteAll(testEntityList);
//    }
//
//    @Test
//    void itShouldFindByNameAndDescriptionAndCreatedAtOrUpdatedAt() {
//        LocalDateTime created = LocalDateTime.parse("2024-02-21T00:00:00");
//        LocalDateTime updated = LocalDateTime.parse("2024-02-21T00:01:00");
//        Entity testEntityFirst = new Entity("Test", "0004");
//        Entity testEntitySecond = new Entity("Test", "0004");
//        testEntityFirst.setCreatedAt(created);
//        testEntitySecond.setUpdatedAt(updated);
//        underTest.save(testEntityFirst);
//        underTest.save(testEntitySecond);
//        List<Entity> testEntityList = underTest.getEntitiesByNameAndDescriptionAndCreatedAtOrUpdatedAt("Test",
//                "0004", created, updated);
//        System.out.println(testEntityList);
////        List<Entity> checkList = testEntityList.stream().filter(el -> el.getName().equals("Test") &&
////                el.getDescription().equals("0004") && el.getCreatedAt() == created)
////                .toList();
//        for(Entity el: testEntityList){
//            assertEquals(el.getName(), "Test");
//            assertEquals(el.getDescription(), "0004");
//            if (el.getCreatedAt() != null) {
//                assertEquals(el.getCreatedAt(), created);
//            } else {
//                assertEquals(el.getUpdatedAt(), updated);
//            }
//        }
////        assertEquals(testEntityList.size(), checkList.size());
//        underTest.deleteAll(testEntityList);
//    }
}