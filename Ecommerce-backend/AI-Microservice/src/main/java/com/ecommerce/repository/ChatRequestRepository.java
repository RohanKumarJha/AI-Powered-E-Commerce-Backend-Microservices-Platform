package com.ecommerce.repository;

import com.ecommerce.model.ChatRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRequestRepository extends JpaRepository<ChatRequest,Long> {
}
