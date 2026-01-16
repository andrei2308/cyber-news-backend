package com.example.demo.domain.user.elasticsearch.repository;

import com.example.demo.domain.user.elasticsearch.document.UserDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface UserSearchRepository extends ElasticsearchRepository<UserDocument, String> {

    List<UserDocument> findByUsernameContaining(String username);

    UserDocument findByEmail(String email);
}