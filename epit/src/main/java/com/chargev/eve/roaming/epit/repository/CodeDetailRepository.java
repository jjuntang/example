package com.chargev.eve.roaming.epit.repository;

import com.chargev.eve.roaming.epit.model.CodeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeDetailRepository extends JpaRepository<CodeDetail, String> {
        List<CodeDetail> findByGroupIdxAndStatusIdx(Integer groupIdx, Integer statusIdx);

        CodeDetail findByGroupIdxAndFirstStrReference(Integer groupIdx, String reference);
}
