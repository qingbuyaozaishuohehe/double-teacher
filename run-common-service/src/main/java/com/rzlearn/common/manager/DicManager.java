package com.rzlearn.common.manager;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rzlearn.base.exception.BusinessException;
import com.rzlearn.base.support.MessageCode;
import com.rzlearn.base.util.WebUtils;
import com.rzlearn.common.constant.RedisConst;
import com.rzlearn.common.dto.GetDicDTO;
import com.rzlearn.common.dto.SaveDicDTO;
import com.rzlearn.common.dto.UpdateDicDTO;
import com.rzlearn.common.entity.RunschDic;
import com.rzlearn.common.service.IRunschDicService;
import com.rzlearn.common.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>ClassName:DicManager</p>
 * <p>Description:</p>
 *
 * @author JiPeigong
 * @date 2018 -06-08 17:05
 */
@Service
@Slf4j
public class DicManager {

    @Autowired
    private IRunschDicService runschDicService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * Save dic boolean.
     *
     * @param saveDicDTO the save dic dto
     * @return the boolean
     */
    public Boolean saveDic(SaveDicDTO saveDicDTO) {
        RunschDic runschDic = new RunschDic();
        runschDic.setType(saveDicDTO.getType());
        runschDic.setCode(saveDicDTO.getCode());
        runschDic.setName(saveDicDTO.getName());
        runschDic.setLanguage(saveDicDTO.getLanguage());
        runschDic.setParentType(saveDicDTO.getParentType());
        runschDic.setParentCode(saveDicDTO.getParentCode());
        runschDic.setSortNum(saveDicDTO.getSortNum());
        runschDic.setDescription(saveDicDTO.getDescription());
        Boolean result = runschDicService.insert(runschDic);
        if (!result) {
            throw new BusinessException(MessageCode.CRAETE_FAILED);
        }
        this.initAllDic();
        return result;
    }

    /**
     * 删除字典
     * @param type
     * @param code
     * @param language
     * @return
     */
    public Boolean removeDic(String type, String code, String language) {
        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(code) || StringUtils.isEmpty(language)) {
            throw new BusinessException(MessageCode.PARAM_ILLEGAL);
        }

        RunschDic runschDic = new RunschDic();
        runschDic.setType(type);
        runschDic.setCode(code);
        runschDic.setLanguage(language);
        Boolean result = runschDicService.delete(new EntityWrapper<>(runschDic));
        initAllDic();
        return result;
    }


    /**
     * Update dic boolean.
     *
     * @param updateDicDTO the update dic dto
     * @return the boolean
     */
    public Boolean updateDic(UpdateDicDTO updateDicDTO) {
        RunschDic runschDic = new RunschDic();
        runschDic.setId(updateDicDTO.getId());
        runschDic.setCode(updateDicDTO.getCode());
        runschDic.setName(updateDicDTO.getName());
        runschDic.setLanguage(updateDicDTO.getLanguage());
        runschDic.setParentType(updateDicDTO.getParentType());
        runschDic.setParentCode(updateDicDTO.getParentCode());
        runschDic.setSortNum(updateDicDTO.getSortNum());
        runschDic.setDescription(updateDicDTO.getDescription());
        Boolean result = runschDicService.updateById(runschDic);
        if (!result) {
            throw new BusinessException(MessageCode.CRAETE_FAILED);
        }
        return result;
    }

    /**
     * List dic by type list.
     *
     * @param type       the type
     * @param parentType the parent type
     * @param parentCode the parent code
     * @return the list
     */
    public List<GetDicDTO> listDic(String type, String parentType, String parentCode) {
        log.info("字典查询：listDicByType:{},{},{},{}", type, parentType, parentCode, WebUtils.getCurrentUserLanguage());
        if (StringUtils.isEmpty(parentType) || StringUtils.isEmpty(parentCode)) {
            return runschDicService.listDicByType(type, WebUtils.getCurrentUserLanguage());
        } else {
            return runschDicService.listDicByType(type, parentType, parentCode, WebUtils.getCurrentUserLanguage());
        }
    }

    /**
     * 初始化所有字典缓存
     */
    public void initAllDic() {
        redisUtil.removePattern(RedisConst.REDIS_DIC + "*");
        Condition condition = new Condition();
        condition.orderBy("type");
        condition.orderBy("sort_num");
        List<RunschDic> dicList = runschDicService.selectList(condition);
        Map<String, List<GetDicDTO>> dicMap = new HashMap<>(16);
        for (RunschDic dic : dicList) {
            String key = dic.getType() + "_" + dic.getLanguage();
            if (dicMap.get(key) == null) {
                dicMap.put(key, new ArrayList<>());
            }
            GetDicDTO getDicDTO = new GetDicDTO();
            getDicDTO.setCode(dic.getCode());
            getDicDTO.setName(dic.getName());
            getDicDTO.setId(dic.getId());
            getDicDTO.setParentCode(dic.getParentCode());
            getDicDTO.setParentType(dic.getParentType());
            dicMap.get(key).add(getDicDTO);
        }

        for (Map.Entry<String, List<GetDicDTO>> entry : dicMap.entrySet()) {
            List<GetDicDTO> dist = entry.getValue();
            redisUtil.set(RedisConst.REDIS_DIC + entry.getKey(), dist);
        }
    }

    public Map getDicDesc() {
        Map<String, String> descMap = new HashMap<>(16);
        List<RunschDic> dicList = runschDicService.selectList(new EntityWrapper<>());
        if (dicList != null) {
            for (RunschDic runschDic : dicList) {
                descMap.put(runschDic.getType(), runschDic.getDescription());
            }
        }
        return descMap;
    }

}
