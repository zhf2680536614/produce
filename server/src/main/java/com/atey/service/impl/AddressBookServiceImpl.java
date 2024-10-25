package com.atey.service.impl;

import com.atey.entity.AddressBook;
import com.atey.mapper.AddressBookMapper;
import com.atey.service.IAddressBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 收货地址 服务实现类
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements IAddressBookService {

}
