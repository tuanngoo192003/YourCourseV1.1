package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.SystemAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemAccountCRUDRepository extends CrudRepository<SystemAccount, Integer> {

}
