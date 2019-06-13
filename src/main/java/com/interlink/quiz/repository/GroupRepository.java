package com.interlink.quiz.repository;

import com.interlink.quiz.object.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    Group findById(int id);

    List<Group> findAllByCuratorId(int curatorId);
}
