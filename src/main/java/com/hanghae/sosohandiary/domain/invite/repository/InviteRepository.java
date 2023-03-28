package com.hanghae.sosohandiary.domain.invite.repository;

import com.hanghae.sosohandiary.domain.invite.entity.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InviteRepository extends JpaRepository<Invite, Long> {

    Long countByDiaryId(Long diaryId);

    List<Invite> findAllByToMemberId(Long id);

    void deleteAllByToMemberId(Long id);
}
