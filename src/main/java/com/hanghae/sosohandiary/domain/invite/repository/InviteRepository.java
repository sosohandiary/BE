package com.hanghae.sosohandiary.domain.invite.repository;

import com.hanghae.sosohandiary.domain.invite.entity.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteRepository extends JpaRepository<Invite, Long> {

}
