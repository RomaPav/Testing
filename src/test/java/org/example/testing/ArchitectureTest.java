package org.example.testing;



import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.example.testing.dto.EntityDTO;
import org.example.testing.model.Entity;
import org.example.testing.service.EntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ArchitectureTest {
    private JavaClasses importedClasses;

    @BeforeEach
    void init() {
        importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.example.testing");
    }

    @Test
    void testFollowingLayersArchitecture() {
        layeredArchitecture()
                .consideringAllDependencies()
                .layer("Controller").definedBy("..controller")
                .layer("Service").definedBy("..service")
                .layer("Repository").definedBy("..repository")
                .layer("DTO").definedBy("..dto")
                .layer("Configuration").definedBy("..configuration")
                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                .whereLayer("Configuration").mayNotBeAccessedByAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller", "Service")
                .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service")
                .whereLayer("DTO").mayOnlyBeAccessedByLayers("Service")
                .check(importedClasses);
    }
    @Test
    void servicesShouldNotDependOnController() {
        noClasses()
                .that().resideInAPackage("..service..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..controller..")
                .because("out of arch rules")
                .check(importedClasses);
    }
    @Test
    void repositoryShouldNotDependOnControllerAndRepository() {
        noClasses()
                .that().resideInAPackage("..repository..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..controller..")
                .andShould()
                .dependOnClassesThat()
                .resideInAPackage("..repository..")
                .because("out of arch rules")
                .check(importedClasses);
    }
    @Test
    void controllerShouldNotDependOnControllerAndRepositoryAndService() {
        noClasses()
                .that().resideInAPackage("..repository..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..controller..")
                .andShould()
                .dependOnClassesThat()
                .resideInAPackage("..repository..")
                .andShould()
                .dependOnClassesThat()
                .resideInAPackage("..repository..")
                .because("out of arch rules")
                .check(importedClasses);
    }

    @Test
    void controllerClassesShouldHaveNamesXController() {
        classes()
                .that().resideInAPackage("..controller..")
                .should()
                .haveSimpleNameEndingWith("Controller")
                .check(importedClasses);
    }

    @Test
    void repositoryClassesShouldHaveNamesXRepository() {
        classes()
                .that().resideInAPackage("..repository..")
                .should()
                .haveSimpleNameEndingWith("Repository")
                .check(importedClasses);
    }

    @Test
    void serviceClassesShouldHaveNamesXService() {
        classes()
                .that().resideInAPackage("..service..")
                .should()
                .haveSimpleNameEndingWith("Service")
                .check(importedClasses);
    }

    @Test
    void configurationClassesShouldHaveNamesXService() {
        classes()
                .that().resideInAPackage("..configuration..")
                .should()
                .haveSimpleNameEndingWith("Configuration")
                .check(importedClasses);
    }

    @Test
    void dtoClassesShouldHaveNamesXDTO() {
        classes()
                .that().resideInAPackage("..dto..")
                .should()
                .haveSimpleNameEndingWith("DTO")
                .check(importedClasses);
    }

    @Test
    void checkControllerAnnotation() {
        classes()
                .that().resideInAPackage("..controller..")
                .should()
                .beAnnotatedWith(RestController.class)
                .orShould()
                .beAnnotatedWith(Controller.class)
                .andShould()
                .beAnnotatedWith(RequestMapping.class)
                .check(importedClasses);
    }

    @Test
    void checkModelAnnotation() {
        classes()
                .that().resideInAPackage("..model..")
                .should()
                .beAnnotatedWith(Document.class)
                .check(importedClasses);
    }

    @Test
    void checkDTOAnnotation() {
        classes()
                .that().resideInAPackage("..dto..")
                .should()
                .notBeAnnotatedWith(Document.class)
                .check(importedClasses);
    }

    @Test
    void checkRepositoryAnnotation() {
        classes()
                .that().resideInAPackage("..repository..")
                .should()
                .beAnnotatedWith(Repository.class)
                .check(importedClasses);
    }

    @Test
    void checkServiceAnnotation() {
        classes()
                .that().resideInAPackage("..service..")
                .should()
                .beAnnotatedWith(Service.class)
                .check(importedClasses);
    }

    @Test
    public void testModelClassesContainFieldId() {
        Entity entity = new Entity();
        try {
            Field privateFieldId = Entity.class.getDeclaredField("id");
            privateFieldId.setAccessible(true);
            privateFieldId.set(entity, "22easkdjadj1nasdk1en1nd12n41");
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testFieldIdHasAnnotationId() {

        try {
            Field privateFieldId = Entity.class.getDeclaredField("id");
            if (privateFieldId.isAnnotationPresent(Id.class)) {
                System.out.println("Field 'id' has @Id annotation");
            } else {
                System.out.println("Field 'id' does not have @Id annotation");
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testModelClassesContainFieldName() {
        Entity entity = new Entity();
        try {
            Field privateFieldName = Entity.class.getDeclaredField("name");
            privateFieldName.setAccessible(true);
            privateFieldName.set(entity, "Roma");
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testModelClassesContainDescription() {
        Entity entity = new Entity();
        try {
            Field privateFieldDescription = Entity.class.getDeclaredField("description");
            privateFieldDescription.setAccessible(true);
            privateFieldDescription.set(entity, "Student");
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testModelClassesContainFieldCreatedAt() {
        Entity entity = new Entity();
        try {
            Field privateFieldCreatedAt = Entity.class.getDeclaredField("createdAt");
            privateFieldCreatedAt.setAccessible(true);
            privateFieldCreatedAt.set(entity, LocalDateTime.now());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testModelClassesContainFieldUpdatedAt() {
        Entity entity = new Entity();
        try {
            Field privateFieldUpdatedAt = Entity.class.getDeclaredField("updatedAt");
            privateFieldUpdatedAt.setAccessible(true);
            privateFieldUpdatedAt.set(entity, LocalDateTime.now());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void testReturnTypeMethodServiceCreate() {
        try {
            Method method = EntityService.class.getDeclaredMethod("create", EntityDTO.class);
            assertEquals(Entity.class, method.getReturnType(), "");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testReturnTypeMethodServiceGetAll() {
        try {
            Method method = EntityService.class.getDeclaredMethod("getAll");
            assertEquals(List.class, method.getReturnType(), "");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void testReturnTypeMethodServiceGetOne() {
        try {
            Method method = EntityService.class.getDeclaredMethod("getOne",String.class);
            assertEquals(EntityDTO.class, method.getReturnType(), "");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void testReturnTypeMethodServiceUpdate() {
        try {
            Method method = EntityService.class.getDeclaredMethod("update", EntityDTO.class);
            assertEquals(Entity.class, method.getReturnType(), "");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void testReturnTypeMethodServiceDelete() {
        try {
            Method method = EntityService.class.getDeclaredMethod("delete", EntityDTO.class);
            assertEquals(void.class, method.getReturnType(), "");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testAutowiredAnnotation() {
        noFields()
                .should()
                .beAnnotatedWith(Autowired.class)
                .check(importedClasses);
    }

}