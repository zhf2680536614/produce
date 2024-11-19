package com.atey.controller.user;


import com.atey.dto.AddressBookDTO;
import com.atey.result.Result;
import com.atey.service.IAddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 收货地址 前端控制器
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@RestController
@RequestMapping("/user/address")
@Api(tags="用户端收货地址相关接口")
@Slf4j
@RequiredArgsConstructor
public class AddressBookController {
    private final IAddressBookService addressBookService;

    /**
     * 用户端新增收货地址
     * @param addressBookDTO
     * @return
     */
    @PostMapping("/save")
    @ApiOperation("用户端添加收货地址")
    public Result save(@RequestBody AddressBookDTO addressBookDTO) {
        log.info("新增收货地址{}", addressBookDTO);
        addressBookService.saveAddress(addressBookDTO);
        return Result.success();
    }

    /**
     * 用户端修改收货地址
     * @param addressBookDTO
     * @return
     */
    @PutMapping("/update")
    @ApiOperation("用户端修改收货地址")
    public Result update(@RequestBody AddressBookDTO addressBookDTO) {
        log.info("用户端修改收货地址{}", addressBookDTO);
        addressBookService.updateAddress(addressBookDTO);
        return Result.success();
    }

    /**
     * 用户端删除收货地址
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation("用户端删除收货地址")
    public Result delete(@PathVariable Integer id) {
        log.info("用户端删除收货地址{}",id);
        addressBookService.delete(id);
        return Result.success();
    }
}
