package com.atey.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.atey.constant.DefaultConstant;
import com.atey.constant.DeletedConstant;
import com.atey.constant.MessageConstant;
import com.atey.dto.AddressBookDTO;
import com.atey.entity.AddressBook;
import com.atey.exception.BaseException;
import com.atey.mapper.AddressBookMapper;
import com.atey.service.IAddressBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 收货地址 服务实现类
 * </p>
 *
 * @author atey
 * @since 2024-10-23
 */
@Service
@AllArgsConstructor
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements IAddressBookService {
    private final AddressBookMapper addressBookMapper;

    /**
     * 用户端新增收货地址
     *
     * @param addressBookDTO
     */
    @Override
    public void saveAddress(AddressBookDTO addressBookDTO) {
        Integer userId = addressBookDTO.getUserId();
        //判断该用户的收货地址是否大于3
        List<AddressBook> list = lambdaQuery()
                .eq(userId != null, AddressBook::getUserId, userId)
                .eq(AddressBook::getDeleted, DeletedConstant.NOT_DELETED)
                .list();

        if (list.size() > 2) {
            throw new BaseException(MessageConstant.ADDRESS_COUNT_PLUS);
        }

        AddressBook addressBook = new AddressBook();
        BeanUtil.copyProperties(addressBookDTO, addressBook);
        addressBook.setCreateTime(LocalDateTime.now());
        addressBook.setUpdateTime(LocalDateTime.now());
        addressBook.setDeleted(DeletedConstant.NOT_DELETED);
        //判断该用户收货地址是否为0 如果是0则将该地址设置为默认地址
        if (list.isEmpty()) {
            addressBook.setIsDefault(DefaultConstant.IS_DEFAULT);
        } else {
            addressBook.setIsDefault(DefaultConstant.IS_NOT_DEFAULT);
        }

        this.save(addressBook);
    }

    /**
     * 用户端修改收货地址
     */
    @Override
    @Transactional
    public void updateAddress(AddressBookDTO addressBookDTO) {
        //判断默认收货地址

        List<AddressBook> list = lambdaQuery()
                .eq(AddressBook::getUserId, addressBookDTO.getUserId())
                .list();
        if (Objects.equals(addressBookDTO.getIsDefault(), DefaultConstant.IS_DEFAULT)) {
            //获取该用户的所有收货地址，将其他收货地址全部改为非默认
            for (AddressBook addressBook : list) {
                if (!Objects.equals(addressBook.getId(), addressBookDTO.getId())) {
                    addressBook.setIsDefault(DefaultConstant.IS_NOT_DEFAULT);
                    addressBookMapper.update(addressBook);
                }
            }
        }

        addressBookMapper.update(addressBookDTO);
        //修改之后判断是否存在默认地址
        int i = 0;
        List<AddressBook> list2 = lambdaQuery()
                .eq(AddressBook::getUserId, addressBookDTO.getUserId())
                .list();
        for (AddressBook addressBook : list2) {
            if (Objects.equals(addressBook.getIsDefault(), DefaultConstant.IS_DEFAULT)) {
                i++;
            }
        }
        if (i == 0) {
            throw new BaseException(MessageConstant.CHOOSE_DEFAULT_ADDRESS);
        }
    }

    /**
     * 用户端删除收获地址
     * @param id
     */
    @Override
    public void delete(Integer id) {
        AddressBook addressBook = lambdaQuery()
                .eq(AddressBook::getId, id)
                .one();
        addressBook.setDeleted(DeletedConstant.DELETED);
        addressBookMapper.update(addressBook);
    }
}
