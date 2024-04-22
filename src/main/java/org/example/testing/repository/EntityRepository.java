package org.example.testing.repository;/*
  @author   user
  @project   lab4
  @class  EntityRepository
  @version  1.0.0 
  @since 07.03.2024 - 00.17
*/


import org.example.testing.model.Entity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EntityRepository extends MongoRepository<Entity, String> {
    Entity getEntityById(String id);

    List<Entity> getEntitiesByName(String name);

    List<Entity> getEntitiesByDescription(String description);

    List<Entity> getEntitiesByCreatedAt(LocalDateTime createdAt);

    List<Entity> getEntitiesByUpdatedAt(LocalDateTime updatedAt);

    Entity getEntityByNameAndDescription(String name, String description);

    List<Entity> getEntitiesByNameOrDescription(String name, String description);

    Entity getEntityByNameAndCreatedAt(String name, LocalDateTime createdAt);

    Entity getEntityByNameAndUpdatedAt(String name, LocalDateTime updatedAt);

    Entity getEntityByDescriptionAndCreatedAt(String description, LocalDateTime createdAt);

    Entity getEntityByDescriptionAndUpdatedAt(String name, LocalDateTime updatedAt);

    Entity getEntityByNameAndDescriptionAndCreatedAt(String name, String description, LocalDateTime createdAt);

    Entity getEntityByNameAndDescriptionAndUpdatedAt(String name, String description, LocalDateTime updatedAt);

    List<Entity> getEntitiesByNameAndCreatedAtOrUpdatedAt(String name, LocalDateTime createdAt, LocalDateTime updatedAt);

    List<Entity> getEntitiesByDescriptionAndCreatedAtOrUpdatedAt(String description, LocalDateTime createdAt,
                                                                 LocalDateTime updatedAt);

    List<Entity> getEntitiesByNameAndDescriptionAndCreatedAtOrUpdatedAt(String name, String description,
                                                                        LocalDateTime createdAt, LocalDateTime updatedAt);
    Boolean existsEntityById(String id);
    Boolean existsEntityByCode(String code);
}
