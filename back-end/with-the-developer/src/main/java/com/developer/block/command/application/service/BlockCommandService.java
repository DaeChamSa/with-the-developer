package com.developer.block.command.application.service;

import com.developer.block.command.domain.aggregate.Block;
import com.developer.block.command.domain.aggregate.BlockedUser;
import com.developer.block.command.domain.repository.BlockRepository;
import com.developer.block.command.domain.repository.BlockedUserRepository;
import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlockCommandService {

    private final BlockRepository blockRepository;
    private final BlockedUserRepository blockedUserRepository;

    @Transactional
    public void blockUser(Long blockerCode, Long blockedCode) {
        if (isUserBlocked(blockerCode, blockedCode)) {
            throw new CustomException(ErrorCode.DUPLICATE_BLOCK);
        }

        Block block = Block.builder()
                .userCode(blockerCode)
                .build();
        blockRepository.save(block);

        BlockedUser blockedUser = BlockedUser.builder()
                .blockCode(block.getBlockCode())
                .userCode(blockedCode)
                .build();
        blockedUserRepository.save(blockedUser);
    }

    @Transactional
    public void unblockUser(Long blockerCode, Long blockedCode) {
        BlockedUser blockedUser = blockedUserRepository.findByBlockUserCodeAndUserCode(blockerCode, blockedCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BLOCK));

        blockedUserRepository.delete(blockedUser);
        blockRepository.deleteById(blockedUser.getBlockCode());

    }

    private boolean isUserBlocked(Long blockerCode, Long blockedCode) {
        return blockedUserRepository.existsByBlockUserCodeAndUserCode(blockerCode, blockedCode);
    }
}
