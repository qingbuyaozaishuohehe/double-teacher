package com.rzlearn.common.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.rzlearn.common.constant.DicConsts;
import com.rzlearn.common.dto.GetDicDTO;
import com.rzlearn.common.entity.RunschDic;
import com.rzlearn.common.mapper.RunschDicMapper;
import com.rzlearn.common.service.IRunschDicService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author JiPeigong
 * @since 2018 -05-14
 */
@Service
public class RunschDicServiceImpl extends ServiceImpl<RunschDicMapper, RunschDic> implements IRunschDicService {

    @Override
    @Cacheable(value = "DIC_", key = "'DIC_'+#parentType+'_'+#parentCode+'_'+#type+'_'+#language")
    public List<GetDicDTO> listDicByType(String type, String parentType, String parentCode, String language) {
        EntityWrapper<RunschDic> wrapper = new EntityWrapper<>();
        RunschDic runschDic = new RunschDic();
        runschDic.setType(type);
        runschDic.setParentType(parentType);
        runschDic.setParentCode(parentCode);
        runschDic.setLanguage(language);
        wrapper.setEntity(runschDic);
        wrapper.orderBy("sort_num");
        List<RunschDic> resultList = baseMapper.selectList(wrapper);

        List<GetDicDTO> listGetDicDTO = initDicMaps(resultList);
        return listGetDicDTO;
    }

    private List<GetDicDTO> initDicMaps(List<RunschDic> resultList) {
        List<GetDicDTO> listGetDicDTO = new ArrayList<>();
        for (RunschDic d : resultList) {
            GetDicDTO getDicDTO = new GetDicDTO();
            getDicDTO.setCode(d.getCode());
            getDicDTO.setName(d.getName());
            getDicDTO.setId(d.getId());
            getDicDTO.setParentCode(d.getParentCode());
            getDicDTO.setParentType(d.getParentType());
            listGetDicDTO.add(getDicDTO);
        }
        return listGetDicDTO;
    }

    /**
     * @param type
     * @return
     */
    @Override
    @Cacheable(value = "DIC_", key = "'DIC_'+#type+'_'+#language")
    public List<GetDicDTO> listDicByType(String type, String language) {
        EntityWrapper<RunschDic> wrapper = new EntityWrapper<>();
        RunschDic runschDic = new RunschDic();
        runschDic.setType(type);
        runschDic.setLanguage(language);
        wrapper.setEntity(runschDic);
        wrapper.orderBy("sort_num");
        List<RunschDic> resultList = baseMapper.selectList(wrapper);

        List<GetDicDTO> listGetDicDTO = initDicMaps(resultList);
        return listGetDicDTO;
    }
}
