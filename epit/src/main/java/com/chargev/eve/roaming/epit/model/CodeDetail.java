package com.chargev.eve.roaming.epit.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "code_detail")
public class CodeDetail implements Serializable {

    /** BID 그룹코드 */
    public static final int GROUP_IDX_BUSINESS_ID = 50;
    /** 상태코드 - 사용 */
    public static final int STATUS_IDX_AVAILABLE = 1;

    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column(name = "name")
    private String name;

    @Column(name = "group_idx")
    private int groupIdx;

    @Column(name = "status_idx")
    private int statusIdx;

    @Column(name = "ref_str1")
    private String firstStrReference;
}

