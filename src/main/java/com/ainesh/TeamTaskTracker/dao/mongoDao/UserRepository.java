package com.ainesh.TeamTaskTracker.dao.mongoDao;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.ainesh.TeamTaskTracker.models.mongoModels.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
  public Optional<User> findByName(String name);
  public Optional<User> findByEmail(String email);
}
