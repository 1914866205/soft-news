package com.soft1851.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName RegionRatioVO.java
 * @Description TODO
 * @createTime 2020年11月26日 16:16:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegionRatioVO {
    private String name;
    private Integer value;
}
