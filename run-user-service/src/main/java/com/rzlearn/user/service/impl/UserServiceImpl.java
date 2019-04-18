package com.rzlearn.user.service.impl;

import com.rzlearn.common.dto.GetDicDTO;
import com.rzlearn.common.feign.IDicController;
import com.rzlearn.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>ClassName:UserServiceImpl</p>
 * <p>Description:</p>
 *
 * @author JiPeigong
 * @date 2018 -07-12 17:16
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IDicController dicController;

    @Override
    public List<GetDicDTO> listDic(String type, String parentType, String parentCode) {
        return dicController.listDic(type, parentType, parentCode).getResultObject();
    }

}
