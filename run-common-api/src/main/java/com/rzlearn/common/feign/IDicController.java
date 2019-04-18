package com.rzlearn.common.feign;

import com.rzlearn.base.dto.ResultMsg;
import com.rzlearn.base.exception.BusinessException;
import com.rzlearn.common.dto.GetDicDTO;
import com.rzlearn.common.dto.SaveDicDTO;
import com.rzlearn.common.dto.UpdateDicDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * <p>ClassName:IDicController</p>
 * <p>Description:字典统一接口api</p>
 *
 * @author JiPeigong
 * @date 2018 -06-08 17:01:06
 */
@Api(tags = {"字典管理接口"})
@FeignClient("run-common")
public interface IDicController {

    /**
     * List dic by type list.
     *
     * @param type       the type
     * @param parentType the parent type
     * @param parentCode the parent code
     * @return the list
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "获取字典", notes = "根据字典code获取字典集合", response = GetDicDTO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "字典类别", required = true, dataType = "String"),
            @ApiImplicitParam(name = "parentType", value = "父类型", dataType = "String"),
            @ApiImplicitParam(name = "parentCode", value = "父code", dataType = "String")
    })
    @RequestMapping(value = "/dic/listDic", method = RequestMethod.GET)
    ResultMsg<List<GetDicDTO>> listDic(@RequestParam(value = "type") String type,
                                       @RequestParam(value = "parentType", required = false) String parentType,
                                       @RequestParam(value = "parentCode", required = false) String parentCode) throws BusinessException;

    /**
     * Save dic by type result msg.
     *
     * @param saveDicDTO the save dic dto
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "保存字典", notes = "保存字典", response = Boolean.class)
    @RequestMapping(value = "/dic/saveDic", method = RequestMethod.POST, consumes = {"application/json"})
    ResultMsg<Boolean> saveDic(@Valid @RequestBody SaveDicDTO saveDicDTO) throws BusinessException;

    /**
     * Update dic result msg.
     *
     * @param updateDicDTO the update dic dto
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "修改字典", notes = "修改字典", response = Boolean.class)
    @RequestMapping(value = "/dic/updateDic", method = RequestMethod.PUT, consumes = {"application/json"})
    ResultMsg<Boolean> updateDic(@Valid @RequestBody UpdateDicDTO updateDicDTO) throws BusinessException;

    /**
     * 删除字典
     * @param type
     * @param code
     * @param language
     * @return
     * @throws BusinessException
     */
    @ApiOperation(value = "删除字典", notes = "删除字典", response = Boolean.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "字典类别", required = true, dataType = "String"),
            @ApiImplicitParam(name = "code", value = "字典code", required = true, dataType = "String"),
            @ApiImplicitParam(name = "language", value = "语言", required = true, dataType = "String")
    })
    @RequestMapping(value = "/dic/removeDic", method = RequestMethod.DELETE)
    ResultMsg<Boolean> removeDic(@Valid @RequestParam(value = "type") String type,
                                 @RequestParam(value = "code") String code,
                                 @RequestParam(value = "language") String language) throws BusinessException;

    /**
     * Refresh dic by type result msg.
     *
     * @return the result msg
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "刷新所有字典", notes = "刷新所有字典", response = List.class)
    @RequestMapping(value = "/dic/sens/refreshADic", method = RequestMethod.GET)
    ResultMsg refreshDicByType() throws BusinessException;

    /**
     * Gets dic desc.
     *
     * @return the dic desc
     * @throws BusinessException the business exception
     */
    @ApiOperation(value = "获取所有字典解释", notes = "获取所有字典解释", response = Map.class)
    @RequestMapping(value = "/dic/getDicDesc", method = RequestMethod.GET)
    ResultMsg<Map> getDicDesc() throws BusinessException;
}