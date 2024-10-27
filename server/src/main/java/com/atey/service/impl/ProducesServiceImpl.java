package com.atey.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.atey.constant.DeletedConstant;
import com.atey.constant.MessageConstant;
import com.atey.dto.PageDTO;
import com.atey.dto.ProducesDTO;
import com.atey.entity.User;
import com.atey.query.ProducesQuery;
import com.atey.entity.Produces;
import com.atey.exception.BaseException;
import com.atey.mapper.ProducesMapper;
import com.atey.result.Result;
import com.atey.service.IProducesService;
import com.atey.vo.ProducesVO;
import com.atey.vo.UserVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

/**
 * <p>
 * 农产品 服务实现类
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@Service
@AllArgsConstructor
public class ProducesServiceImpl extends ServiceImpl<ProducesMapper, Produces> implements IProducesService {
    private final ProducesMapper producesMapper;
    /**
     * 新增产品
     * @param producesDTO
     * @return
     */
    @Override
    public void saveProduces(ProducesDTO producesDTO) {
        if(producesDTO.getStatus()==null){
            throw new BaseException(MessageConstant.CHOOSE_STATUS);
        }
        if(producesDTO.getCategory()==null){
            throw new BaseException(MessageConstant.CHOOSE_CATEGORY);
        }

        Produces produces = new Produces();
        BeanUtil.copyProperties(producesDTO,produces);
        produces.setCreateTime(LocalDateTime.now());
        produces.setUpdateTime(LocalDateTime.now());
        produces.setDeleted(DeletedConstant.NOT_DELETED);

        // TODO 图片设置
        produces.setImage("https://th.bing.com/th/id/R.e636e5f9ae0388421d048d93ecfbc5b8?rik=XWWt54hFT3Pknw&pid=ImgRaw&r=0");

        //查询数据库中产品是否存在
        Produces one = lambdaQuery()
                .eq(producesDTO.getName() != null, Produces::getName, producesDTO.getName())
                .eq(Produces::getDeleted, DeletedConstant.NOT_DELETED)
                .one();
        if (BeanUtil.isEmpty(one)) {
            save(produces);
        } else {
            throw new BaseException(MessageConstant.PRODUCES_EXIST_SAVE);
        }
    }

    /**
     * 产品分页查询
     * @param producesQuery
     * @return
     */
    @Override
    @CachePut(value="producesCache",key="#producesQuery.pageNo")
    public Result<PageDTO<ProducesVO>> queryProduces(ProducesQuery producesQuery) {
        String name = producesQuery.getName();
        String origin = producesQuery.getOrigin();
        Integer category = producesQuery.getCategory();
        LocalDateTime startCreateTime = producesQuery.getStartCreateTime();
        LocalDateTime endCreateTime = producesQuery.getEndCreateTime();

        Page<Produces> page = producesQuery.toMpPage();

        Page<Produces> result = lambdaQuery()
                .like(name != null, Produces::getName, name)
                .like(origin != null, Produces::getOrigin, origin)
                .eq(category!=null,Produces::getCategory,category)
                .eq(Produces::getDeleted, DeletedConstant.NOT_DELETED)
                .between(startCreateTime != null && endCreateTime != null, Produces::getCreateTime, startCreateTime, endCreateTime)
                .orderByDesc(Produces::getUpdateTime)
                .page(page);

        return Result.success(PageDTO.of(result, ProducesVO.class));
    }

    /**
     * 删除产品
     * @param id
     * @return
     */
    @Override
    public void deleteProduces(Integer id) {
        Produces produces = lambdaQuery()
                .eq(Produces::getId, id)
                .one();
        produces.setDeleted(DeletedConstant.DELETED);

        producesMapper.update(produces);
    }

    /**
     * 根据id查询产品
     * @param id
     * @return
     */
    @Override
    public ProducesVO getByIdProduces(Integer id) {
        Produces produces = getById(id);
        ProducesVO producesVO = new ProducesVO();
        BeanUtil.copyProperties(produces, producesVO);
        return producesVO;
    }

    /**
     * 修改产品
     * @param producesDTO
     * @return
     */
    @Override
    @Transactional
    public void updateProduces(ProducesDTO producesDTO) {
        Produces produces = new Produces();

        BeanUtil.copyProperties(producesDTO, produces);

        produces.setUpdateTime(LocalDateTime.now());

        producesMapper.update(produces);

        //查询数据库中用户名是否重复
        List<Produces> list = lambdaQuery()
                .eq(producesDTO.getName() != null, Produces::getName, producesDTO.getName())
                .eq(Produces::getDeleted, DeletedConstant.NOT_DELETED)
                .list();

        if (list.size() >= 2) {
            throw new BaseException(MessageConstant.PRODUCES_EXIST_UPDATE);
        }
    }
}
