package com.inssa.server.common.data;

import com.inssa.server.common.entity.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@ActiveProfiles("local")
class CategoryRepositoryTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach
    void afterEach() {
        categoryRepository.deleteAll();
    }

    @Test
    void categoryBeanTest() {
        applicationContext.getBean("categoryRepository");
    }

    @Test
    void categorySaveTest() {
        Category category = Category.builder().name("벽지").build();
        Category saved = categoryRepository.save(new Category("벽지"));
        assertThat(saved).isNotNull();
        assertThat(saved.getName()).isEqualTo(category.getName());
    }
}