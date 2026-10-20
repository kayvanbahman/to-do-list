package com.example.todo;

import com.example.todo.auth.repository.RefreshTokenRepository;
import com.example.todo.auth.repository.UserRepository;
import com.example.todo.auth.repository.mongo.MongoRefreshTokenRepository;
import com.example.todo.auth.repository.mongo.MongoUserRepository;
import com.example.todo.auth.repository.mongo.SpringMongoRefreshTokenRepository;
import com.example.todo.auth.repository.mongo.SpringMongoUserRepository;
import com.example.todo.auth.repository.mysql.JpaRefreshTokenRepository;
import com.example.todo.auth.repository.mysql.JpaUserRepository;
import com.example.todo.auth.repository.mysql.MySqlRefreshTokenRepository;
import com.example.todo.auth.repository.mysql.MySqlUserRepository;
import com.example.todo.todo.repository.TodoRepository;
import com.example.todo.todo.repository.mongo.MongoTodoRepository;
import com.example.todo.todo.repository.mongo.SpringMongoTodoRepository;
import com.example.todo.todo.repository.mysql.JpaTodoRepository;
import com.example.todo.todo.repository.mysql.MySqlTodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class PersistenceProfileTest {
    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withUserConfiguration(PersistenceBeans.class);

    @Test
    void mysqlSelectsOnlyMysqlAdapters() {
        runner.withInitializer(context -> context.getEnvironment().setActiveProfiles("mysql"))
                .run(context -> {
                    assertThat(context).hasSingleBean(UserRepository.class)
                            .hasSingleBean(RefreshTokenRepository.class)
                            .hasSingleBean(TodoRepository.class)
                            .hasSingleBean(MySqlUserRepository.class)
                            .hasSingleBean(MySqlRefreshTokenRepository.class)
                            .hasSingleBean(MySqlTodoRepository.class)
                            .doesNotHaveBean(MongoUserRepository.class);
                });
    }

    @Test
    void mongoSelectsOnlyMongoAdapters() {
        runner.withInitializer(context -> context.getEnvironment().setActiveProfiles("mongo"))
                .run(context -> {
                    assertThat(context).hasSingleBean(UserRepository.class)
                            .hasSingleBean(RefreshTokenRepository.class)
                            .hasSingleBean(TodoRepository.class)
                            .hasSingleBean(MongoUserRepository.class)
                            .hasSingleBean(MongoRefreshTokenRepository.class)
                            .hasSingleBean(MongoTodoRepository.class)
                            .doesNotHaveBean(MySqlUserRepository.class);
                });
    }

    @Configuration(proxyBeanMethods = false)
    @Import({MySqlUserRepository.class, MySqlRefreshTokenRepository.class, MySqlTodoRepository.class,
            MongoUserRepository.class, MongoRefreshTokenRepository.class, MongoTodoRepository.class})
    static class PersistenceBeans {
        @Bean JpaUserRepository jpaUsers() { return mock(JpaUserRepository.class); }
        @Bean JpaRefreshTokenRepository jpaTokens() { return mock(JpaRefreshTokenRepository.class); }
        @Bean JpaTodoRepository jpaTodos() { return mock(JpaTodoRepository.class); }
        @Bean SpringMongoUserRepository mongoUsers() { return mock(SpringMongoUserRepository.class); }
        @Bean SpringMongoRefreshTokenRepository mongoTokens() { return mock(SpringMongoRefreshTokenRepository.class); }
        @Bean SpringMongoTodoRepository mongoTodos() { return mock(SpringMongoTodoRepository.class); }
        @Bean MongoTemplate mongoTemplate() { return mock(MongoTemplate.class); }
    }
}
