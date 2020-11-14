
package com.soft1851.my.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 继承自己的MyMapper
 *
 * @author mqxu
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
