package com.example.todo.auth.repository.mongo;

import com.example.todo.auth.entity.RefreshToken;
import com.example.todo.auth.entity.User;
import com.example.todo.auth.repository.RefreshTokenRepository;
import com.example.todo.auth.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.Optional;

@Repository
@Profile("mongo")
public class MongoRefreshTokenRepository implements RefreshTokenRepository {
    private final SpringMongoRefreshTokenRepository mongo;
    private final UserRepository users;
    private final MongoTemplate template;

    public MongoRefreshTokenRepository(SpringMongoRefreshTokenRepository mongo, UserRepository users,
            MongoTemplate template) {
        this.mongo = mongo;
        this.users = users;
        this.template = template;
    }
    public RefreshToken save(RefreshToken token) {
        mongo.save(new MongoRefreshToken(token.getTokenHash(), token.getUser().getId(),
                token.getExpiresAt(), token.isRevoked()));
        return token;
    }
    public Optional<User> consume(String hash, Instant now) {
        MongoRefreshToken token = template.findAndModify(
                Query.query(Criteria.where("_id").is(hash).and("revoked").is(false).and("expiresAt").gt(now)),
                new Update().set("revoked", true), FindAndModifyOptions.options(), MongoRefreshToken.class);
        return token == null ? Optional.empty() : users.findById(token.getUserId());
    }

    public void revoke(String hash) {
        template.updateFirst(Query.query(Criteria.where("_id").is(hash).and("revoked").is(false)),
                new Update().set("revoked", true), MongoRefreshToken.class);
    }

}
