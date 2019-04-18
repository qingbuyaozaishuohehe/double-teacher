package com.rzlearn.user.service;

import com.rzlearn.common.dto.GetDicDTO;

import java.util.List;

/**
 * <p>ClassName:IUserService</p>
 * <p>Description:</p>
 *
 * @author JiPeigong
 * @date 2018 -07-12 17:13
 */
public interface IUserService {

    /**
     * List dic list.
     *
     * @param type       the type
     * @param parentType the parent type
     * @param parentCode the parent code
     * @return the list
     */
    List<GetDicDTO> listDic(String type, String parentType, String parentCode);

}
