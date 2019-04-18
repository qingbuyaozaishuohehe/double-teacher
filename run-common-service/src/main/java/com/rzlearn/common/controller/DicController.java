package com.rzlearn.common.controller;

import com.rzlearn.base.basic.BaseController;
import com.rzlearn.base.dto.ResultMsg;
import com.rzlearn.base.exception.BusinessException;
import com.rzlearn.base.support.MessageCode;
import com.rzlearn.common.dto.GetDicDTO;
import com.rzlearn.common.dto.SaveDicDTO;
import com.rzlearn.common.dto.UpdateDicDTO;
import com.rzlearn.common.feign.IDicController;
import com.rzlearn.common.manager.DicManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * <p>ClassName:DicController</p>
 * <p>Description:字典统一管理</p>
 *
 * @author JiPeigong
 * @date 2018-06-08 17:04:42
 **/
@RestController
@Slf4j
public class DicController extends BaseController implements IDicController {

    @Autowired
    private DicManager dicManager;

    @Override
    public ResultMsg<List<GetDicDTO>> listDic(@RequestParam(value = "type") String type,
                                              @RequestParam(value = "parentType", required = false) String parentType,
                                              @RequestParam(value = "parentCode", required = false) String parentCode) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, dicManager.listDic(type, parentType, parentCode));
    }

    @Override
    public ResultMsg<Boolean> saveDic(@Valid @RequestBody SaveDicDTO saveDicDTO) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, dicManager.saveDic(saveDicDTO));
    }

    @Override
    public ResultMsg<Boolean> updateDic(@Valid @RequestBody UpdateDicDTO updateDicDTO) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, dicManager.updateDic(updateDicDTO));
    }

    @Override
    public ResultMsg<Boolean> removeDic(@Valid @RequestParam(value = "type") String type,
                                        @RequestParam(value = "code") String code,
                                        @RequestParam(value = "language") String language) throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, dicManager.removeDic(type, code, language));
    }

    @Override
    public ResultMsg refreshDicByType() throws BusinessException {
        dicManager.initAllDic();
        return new ResultMsg(MessageCode.SUCCESS);
    }

    @Override
    public ResultMsg<Map> getDicDesc() throws BusinessException {
        return new ResultMsg(MessageCode.SUCCESS, dicManager.getDicDesc());
    }
}