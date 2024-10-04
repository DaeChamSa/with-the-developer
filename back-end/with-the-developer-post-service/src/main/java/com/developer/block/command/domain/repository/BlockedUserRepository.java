package com.developer.block.command.domain.repository;

import com.developer.block.command.domain.aggregate.BlockedUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BlockedUserRepository {

    @Query("SELECT CASE WHEN COUNT(bu) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Block b JOIN BlockedUser bu ON b.blockCode = bu.blockCode " +
            "WHERE b.userCode = :blockerCode AND bu.userCode = :blockedCode")
    boolean existsByBlockUserCodeAndUserCode(@Param("blockerCode") Long blockerCode,
                                             @Param("blockedCode") Long blockedCode);

    BlockedUser save(BlockedUser blockedUser);

    @Query("SELECT bu FROM Block b JOIN BlockedUser bu ON b.blockCode = bu.blockCode " +
            "WHERE b.userCode = :blockerCode AND bu.userCode = :blockedCode")
    Optional<BlockedUser> findByBlockUserCodeAndUserCode(@Param("blockerCode") Long blockerCode,
                                                         @Param("blockedCode") Long blockedCode);

    void delete(BlockedUser blockedUser);
}
