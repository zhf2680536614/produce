package com.atey.controller.user;

import com.atey.dto.ShoppingCartDTO;
import com.atey.result.Result;
import com.atey.service.IShoppingCartService;
import com.atey.vo.ShoppingCartVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@AllArgsConstructor
@Api(tags="用户端购物车相关接口")
public class ShoppingCartController {
    private final IShoppingCartService shoppingCartService;

    /**
     * 添加到购物车
     * @param shoppingCartDTO
     * @return
     */
    @PostMapping("/save")
    @ApiOperation("添加到购物车")
    public Result save(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("添加到购物车{}",shoppingCartDTO);
        shoppingCartService.saveShopping(shoppingCartDTO);
        return Result.success();
    }

    /**
     * 查询用户的购物车数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("查询用户的购物车数据")
    public Result<List<ShoppingCartVO>> findById(@PathVariable("id") Integer id){
        log.info("查询用户的购物车数据{}",id);
        List<ShoppingCartVO> list =  shoppingCartService.queryShoppingCart(id);
        return Result.success(list);
    }

    /**
     * 删除购物车数据
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除购物车数据")
    public Result delete(@PathVariable Integer id){
        log.info("删除购物车数据{}",id);
        shoppingCartService.delete(id);
        return Result.success();
    }
}
