package com.atey.service;

import com.atey.dto.AddressBookDTO;
import com.atey.entity.AddressBook;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 收货地址 服务类
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
public interface IAddressBookService extends IService<AddressBook> {

    /**
     * 用户端新增收货地址
     * @param addressBookDTO
     */
    void saveAddress(AddressBookDTO addressBookDTO);

    /**
     * 用户端修改收货地址
     */
    void updateAddress(AddressBookDTO addressBookDTO);

    /**
     * 用户端删除收货地址
     * @param id
     */
    void delete(Integer id);
}
