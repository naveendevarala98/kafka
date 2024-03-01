package org.springboot.repository;

import org.springboot.entity.Wikimedia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WikimediaDataRepository extends JpaRepository<Wikimedia,Long> {

}
