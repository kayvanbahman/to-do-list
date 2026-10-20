package com.example.todo.auth.repository.mongo;

import com.example.todo.auth.entity.User;
import com.example.todo.auth.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@Profile("mongo")
public class MongoUserRepository implements UserRepository {
    private final SpringMongoUserRepository mongo;
    private final MongoTemplate template;

    public MongoUserRepository(SpringMongoUserRepository mongo, MongoTemplate template) {
        this.mongo = mongo;
        this.template = template;
    }

    public Optional<User> findByEmailIgnoreCase(String email) {
        return mongo.findByEmailIgnoreCase(email).map(MongoUser::toUser);
    }
    public boolean existsByEmailIgnoreCase(String email) { return mongo.existsByEmailIgnoreCase(email); }
    public Optional<User> findById(Long id) { return mongo.findById(id).map(MongoUser::toUser); }
    public User save(User user) {
        Long id = user.getId();
        if (id == null) {
            MongoSequence sequence = template.findAndModify(
                    Query.query(Criteria.where("_id").is("users")),
                    new Update().inc("value", 1),
                    FindAndModifyOptions.options().upsert(true).returnNew(true), MongoSequence.class);
            if (sequence == null) throw new IllegalStateException("Could not allocate user ID");
            id = sequence.getValue();
        }
        return mongo.save(new MongoUser(id, user.getEmail(), user.getPasswordHash(), user.getCreatedAt())).toUser();
    }
}
