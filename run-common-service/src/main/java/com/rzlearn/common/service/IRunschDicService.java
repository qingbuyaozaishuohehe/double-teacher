package com.rzlearn.common.service;

import com.baomidou.mybatisplus.service.IService;
import com.rzlearn.common.dto.GetDicDTO;
import com.rzlearn.common.entity.RunschDic;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author JiPeigong
 * @since 2018 -05-14
 */
public interface IRunschDicService extends IService<RunschDic> {

    /**
     * List dic by type list.
     *
     * @param type       the type
     * @param parentType the parent type
     * @param parentCode the parent code
     * @param language   the language
     * @return the list
     */
    List<GetDicDTO> listDicByType(String type, String parentType, String parentCode, String language);

    /**
     * List dic by type list.
     *
     * @param type     the type
     * @param language the language
     * @return the list
     */
    List<GetDicDTO> listDicByType(String type, String language);

}
