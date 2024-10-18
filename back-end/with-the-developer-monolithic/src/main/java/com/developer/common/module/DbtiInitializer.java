package com.developer.common.module;

import com.developer.dbti.command.domain.aggregate.Dbti;
import com.developer.dbti.command.domain.aggregate.DbtiRole;
import com.developer.dbti.command.domain.repository.DbtiRepository;
import com.developer.dbti.query.mapper.DbtiMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DbtiInitializer {

    private final DbtiRepository dbtiRepository;

    @PostConstruct
    public void init() {

        if (dbtiRepository.findAll().isEmpty()) {

            /* 백엔드 성향 저장 */
            Dbti b1dbti = new Dbti("신뢰할 수 있는", DbtiRole.BACKEND);
            dbtiRepository.save(b1dbti);
            Dbti b2dbti = new Dbti("효율적인", DbtiRole.BACKEND);
            dbtiRepository.save(b2dbti);

            /* 프론트 성향 저장 */
            Dbti f1dbti = new Dbti("깔끔한", DbtiRole.FRONTEND);
            dbtiRepository.save(f1dbti);
            Dbti f2dbti = new Dbti("매력적인", DbtiRole.FRONTEND);
            dbtiRepository.save(f2dbti);

            /* 디자이너 성향 저장 */
            Dbti d1dbti = new Dbti("감각적인", DbtiRole.DESIGNER);
            dbtiRepository.save(d1dbti);
            Dbti d2dbti = new Dbti("상징적인", DbtiRole.DESIGNER);
            dbtiRepository.save(d2dbti);

            /* PM 성향 저장 */
            Dbti p1dbti = new Dbti("조정적인", DbtiRole.PM);
            dbtiRepository.save(p1dbti);
            Dbti p2dbti = new Dbti("결정적인", DbtiRole.PM);
            dbtiRepository.save(p2dbti);

            log.info("성향 초기 저장 성공");
        }
    }
}
