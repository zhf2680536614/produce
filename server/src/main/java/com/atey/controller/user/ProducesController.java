package com.atey.controller.user;

import com.atey.result.Result;
import com.atey.service.IProducesService;
import com.atey.vo.ProducesVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("UserProducesController")
@RequestMapping("/user/produces")
@Slf4j
@RequiredArgsConstructor
@Api(tags="用户端产品相关接口")
public class ProducesController {
    private final IProducesService producesService;

    /**
     * 获取所有的产品
     * @return
     */
    @GetMapping("/all")
    @ApiOperation("查询所有产品")
    public Result<List<ProducesVO>> queryAll(){
        log.info("获取所有的产品");
        List<ProducesVO> list = producesService.queryALl();
        return Result.success(list);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询产品")
    public Result<ProducesVO> get(@PathVariable Integer id) {
        log.info("根据id查询产品{}",id);
        ProducesVO producesVO= producesService.getByIdProduces(id);
        return Result.success(producesVO);
    }
}
