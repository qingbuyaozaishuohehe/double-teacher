package com.rzlearn.base.basic;

import lombok.Data;

import java.util.List;

/**
 * <p>ClassName:Page</p>
 * <p>Description:</p>
 *
 * @author JiPeigong
 * @date 2018-06-28 17:02
 **/
@Data
public class Page<T> {
    /**
     * 总数
     */
    private long total;

    /**
     * 每页显示条数，默认 10
     */
    private int size = 10;

    /**
     * 总页数
     */
    private long pages;

    /**
     * 当前页
     */
    private int current = 1;

    /**
     * 查询总记录数（默认 true）
     */
    private boolean searchCount = true;

    /**
     * 结果集
     */
    private List<T> records;

}
