package org.flowforge.common.repository;

import org.flowforge.common.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BaseEntityRepository extends JpaRepository<BaseEntity, UUID> {

}
