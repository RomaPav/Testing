package org.example.testing.service;/*
  @author   user
  @project   lab4
  @class  EntityService
  @version  1.0.0 
  @since 07.03.2024 - 00.18
*/


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.testing.dto.EntityDTO;
import org.example.testing.model.*;
import org.example.testing.repository.EntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EntityService {
    private final EntityRepository entityRepository;
    private final ModelMapper modelMapper;

    private final List<Entity> entities = List.of(
            new Entity("1", "name1", "000001", "description1"),
            new Entity("2", "name2", "000002", "description2"),
            new Entity("3", "name3", "000003", "description3")

    );

    @PostConstruct
    void init() {
        //  repository.deleteAll();
        entityRepository.saveAll(entities);
    }

    public Entity create(EntityDTO entityDTO) {
        Entity entity = modelMapper.map(entityDTO, Entity.class);
        return entityRepository.save(entity);
    }

    public List<EntityDTO> getAll() {
        List<Entity> entities = entityRepository.findAll();
        return entities.stream().map(el -> modelMapper.map(el, EntityDTO.class)).toList();
    }

    public EntityDTO getOne(String id) {
        if (entityRepository.getEntityById(id) == null) {
            return null;
        } else {
            return modelMapper.map(entityRepository.getEntityById(id), EntityDTO.class);
        }
    }

    public Entity update(EntityDTO entityDTO) {
        Entity entity = modelMapper.map(entityDTO, Entity.class);
        return entityRepository.save(entity);
    }

    public void delete(EntityDTO entityDTO) {
        Entity entity = modelMapper.map(entityDTO, Entity.class);
        entityRepository.delete(entity);
    }

    public Entity create(EntityInsertRequest request) {
        if (entityRepository.existsEntityByCode(request.code())) {
            throw new IllegalStateException(String.format("Entity with code %s already exists", request.code()));
        }
        if (request.name() != null && request.name().length() < 4 || request.name() != null &&
                request.name().contains("test") ||
                Objects.equals(request.name(), "")) {
            throw new IllegalArgumentException("This name is not valid");
        }
        if (request.code().length() < 6) {
            throw new IllegalStateException(String.format("This code: %s, has length less that 6", request.name()));
        }
        if (request.description().contains("test")) {
            throw new IllegalStateException(String.format("This description: %s, contains \"test\"",
                    request.description()));
        }
        Entity itemToPersist = new Entity(request.name(), request.code(), request.description());
        return this.create(modelMapper.map(itemToPersist, EntityDTO.class));
    }

    public Entity update(EntityEditRequest request) {
        if (!entityRepository.existsEntityById(request.id())) {
            throw new IllegalStateException(String.format("Entity with id %s not exists", request.id()));
        }
        if (entityRepository.existsEntityByCode(request.code())) {
            throw new IllegalStateException(String.format("Entity with code %s already exists", request.code()));
        }
        if (request.name() != null && request.name().length() < 4 || request.name() != null &&
                request.name().contains("test") ||
                Objects.equals(request.name(), "")) {
            throw new IllegalArgumentException("This name is not valid");
        }
        if (request.code().length() < 6) {
            throw new IllegalStateException(String.format("This code: %s, has length less that 6", request.name()));
        }
        if (request.description().contains("test")) {
            throw new IllegalStateException(String.format("This description: %s, contains \"test\"",
                    request.description()));
        }
        if (request.id() != null && request.id().isEmpty())
            throw new IllegalStateException(String.format("This id: %s, is empty",
                    request.id()));
        return this.update(new EntityDTO(request.id(), request.name(), request.code(), request.description()));
    }

    public void delete(EntityDeleteRequest request) {
        if (!entityRepository.existsEntityById(request.id())) {
            throw new IllegalStateException(String.format("Entity with id %s not exists", request.id()));
        } else
            this.delete(new EntityDTO(request.id(), request.name(), request.code(), request.description()));
    }

    public EntityDTO getOne(EntityGetRequest request) {
        if (!entityRepository.existsEntityById(request.id())) {
            throw new IllegalStateException(String.format("Entity with id %s not exists", request.id()));
        } else {
            return this.getOne(request.id());
        }
    }
}